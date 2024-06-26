const fs = require('fs');
const path = require('path');
const { ObjectId, MongoClient } = require('mongodb');

const DATA_DIR = process.env.DATA_DIR || '../data';
const SCHEMA_DIR = './schema';
const MONGO_URI = process.env.MONGODB_URI;
const DB_NAME = 'dnd';

const client = new MongoClient(MONGO_URI);
const db = client.db(DB_NAME);

const contentTypes = [
	'tool',
	'language',
	'weapon',
	'armor',
	'spell',
	'background',
	'race',
	'class',
];

/**
 * resolves all references to other documents in a given document with the following rules:
 * - the reference must be an object named with 'ref' and 'query' properties
 * - the 'ref' property must be the name of the collection / content type to reference
 * - the 'query' object must contain the 'name' of the document to reference
 * - the reference will be queried from the same 'source' as specified in the root of the document
 */
async function resolveReferences(document, withinSource) {
	for (const key in document) {
		if (document[key] && typeof document[key] === 'object') {
			// if the object is a reference, resolve it
			if (document[key].ref && document[key].name) {
				const refObj = document[key];
				// if the source is specified as a parameter, use it, otherwise use the source in the ref
				const query = withinSource ?
					{ name: refObj.name, source: withinSource } :
					{ name: refObj.name, source: refObj.source }
				let result = await db.collection(refObj.ref).findOne(query);
				if (!result) throw new Error(`Error resolving reference to ${refObj.ref} with ` +
					`query ${JSON.stringify(query)}, skipping item`);
				if (key.endsWith('Id')) {
					// if the reference is an id, replace the object with the id
					result = result._id;
				}
				document[key] = result;
			} else {
				// the object could have nested references, continue on that object
				await resolveReferences(document[key], withinSource);
			}
		}
	}
}

/**
 * inserts all documents from one source and of one content type to their respective collection
 * all documents of 'data' will be marked with the source of 'sourceId' and added to the 'collection'
 */
async function insertAllFromSource(data, sourceId, collection) {
	const toImport = [];
	const docSource = await db.collection('source').findOne({ "_id": sourceId });
	for (document of data) {
		document.source = docSource;
		try {
			await resolveReferences(document, document.source);
			toImport.push(document);
		} catch (error) {
			console.error(error.message);
		}
	}
	if (toImport.length === 0) return;
	await db.collection(collection).insertMany(toImport, {ordered: false});
}

/**
 * inserts all documents from all source files of a given content type to their respective collection
 * e.g. all documents from all files in the 'armor' subdirectory will be inserted into the 'armor' collection
 */
async function insertContentType(contentType, sourceIds) {
	console.log(`Importing ${contentType} items...`);
	const dir = path.join(DATA_DIR, contentType);
	const files = fs.readdirSync(dir);
	for (const sourceFile of files) {
		// cleanName is the name without .json and must equal an id of a source
		const cleanName = sourceFile.split('.')[0];
		if (!sourceIds.includes(cleanName)) continue;
		try {
			let data = JSON.parse(fs.readFileSync(path.join(dir, sourceFile)));
			await insertAllFromSource(data, cleanName, contentType);
		} catch (error) {
			if (error.result) {
				console.log(`Could import only ${error.result.insertedCount}/${toImport.length} ` +
					`${contentType} items from ${cleanName}`);
			} else {
				console.error(`Can't import ${contentType} items from ${cleanName}: ${error.message}`);
			}
		}
	}
}

async function insertSources() {
	console.log('Importing sources...');
	try {
		const data = JSON.parse(fs.readFileSync(path.join(DATA_DIR, 'sources.json')));
		await db.collection('source').insertMany(data);
	} catch (error) {
		if (error.result) {
			console.log(`Could import only ${error.result.insertedCount}/${data.length} sources`);
		} else {
			console.error(`Can't import sources: ${error.message}`);
		}
	}
}

async function insertCharacters() {
	console.log('Importing characters...');
	try {
		const data = JSON.parse(fs.readFileSync(path.join(DATA_DIR, 'characters.json')));
		const toImport = [];
		for (character of data) {
			try {
				await resolveReferences(character, null);
				toImport.push(character);
			} catch (error) {
				console.error(error.message);
			}
		}
		if (toImport.length === 0) return;
		await db.collection('character').insertMany(toImport, {ordered: false});
	} catch (error) {
		if (error.result) {
			console.log(`Could import only ${error.result.insertedCount}/${toImport.length} characters`);
		} else {
			console.error(`Can't import characters: ${error.message}`);
		}
	}
}

async function insert() {
	// drop and create source collection
	await db.collection('source').drop();
	const schema = JSON.parse(fs.readFileSync('schema/source.json'));
	await db.createCollection('source', {validator: {$jsonSchema: schema}});

	// drop all collections of defined content types
	for (const collection of contentTypes) await db.collection(collection).drop();
	// create collections for all content types with schema validation
	for (const collection of contentTypes) {
		const schema = JSON.parse(fs.readFileSync(path.join('schema', `${collection}.json`)));
		await db.createCollection(collection, {validator: {$jsonSchema: schema}});
	}

	await db.collection('character').drop();

	await insertSources();
	const sourceIds = await db.collection('source').distinct('_id');
	for (const collection of contentTypes) await insertContentType(collection, sourceIds);
	await insertCharacters();
}

async function createReference(field, collection) {
	if (field && field.name && field.source) {
		field = {
			ref: collection,
			name: field.name,
			source: field.source
		};
	} else if (field instanceof ObjectId) {
		const entity = await db.collection(collection).findOne({ _id: field });
		field = {
			ref: collection,
			name: entity.name,
			source: entity.source
		};
	}
	return field;
}

async function addCharacterReferences(character) {
	for (const key in character) {
		if (key === 'classId' || key === 'raceId' || key === 'backgroundId') {
			character[key] = await createReference(character[key], key.slice(0, -2));
		} else if (key === 'armor') {
			character[key] = await createReference(character[key], key);
		} else if (key === 'weapons' || key === 'spells') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i] = await createReference(character[key][i], key.slice(0, -1));
			}
		} else if (key === 'languages' || key === 'tools') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i].item = await createReference(character[key][i].item, key.slice(0, -1));
			}
		} else if (key === '_id') {
			delete character[key];
		}
	}
}

async function backupCharacters() {
	let characters = await db.collection('character').find().toArray();
	for (let character of characters) await addCharacterReferences(character, db);
	if (characters.length === 0) return;
	fs.writeFileSync(path.join(DATA_DIR, 'characters.json'), JSON.stringify(characters, null, 2));
	console.log(`Backed up ${characters.length} characters`);
}

async function run() {
	try {
		await backupCharacters(db);
		await insert(db);
	} catch (err) {
		console.log(err.stack);
	} finally {
		await client.close();
	}
}

run();

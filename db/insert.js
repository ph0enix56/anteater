const fs = require('fs');
const path = require('path');
const { ObjectId, MongoClient } = require('mongodb');

const DATA_DIR = '../data';
const MONGO_URI = process.env.MONGODB_URI;
const DB_NAME = 'dnd_dev';

const client = new MongoClient(MONGO_URI);
const db = client.db(DB_NAME);

const contentTypes = [
	//'tool',
	//'language',
	//'weapon',
	//'armor',
	'background',
	//'race',
	//'class',
	//'spell',
];

/**
 * resolves all references to other documents in a given document with the following rules:
 * - the reference must be an object named with 'ref' and 'query' properties
 * - the 'ref' property must be the name of the collection / content type to reference
 * - the 'query' object must contain the 'name' of the document to reference
 * - the reference will be queried from the same 'source' as specified in the root of the document
 */
async function resolveReferences(document, source) {
	for (const key in document) {
		if (document[key] && typeof document[key] === 'object') {
			// if the object is a reference, resolve it
			if (document[key].ref && document[key].query) {
				const refObj = document[key];
				const query = { name: refObj.query.name, source: source };
				let result = await db.collection(refObj.ref).findOne(query);
				if (!result) throw new Error(`Failed to resolve reference to ${refObj.ref} with query ${JSON.stringify(query)}`);
				// convert the _id string to an ObjectId to avoid schema validation errors
				if (result._id && typeof result._id === 'string') {
					console.log(`_id before conversion: ${result._id}, type: ${typeof result._id}`);
					result._id = ObjectId(result._id);
					console.log(`_id after conversion: ${result._id}, type: ${typeof result._id}`);
				}
				document[key] = result;
			} else {
				// the object could have nested references, continue on that object
				await resolveReferences(document[key], source);
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
	try {
		if (toImport.length === 0) return;
		const result = await db.collection(collection).insertMany(toImport, {ordered: false});
		console.log(`Inserted ${result.insertedCount} documents of type ${collection} from ${sourceId}`);
	} catch (error) {
		console.error(error.message);
		if (error.result) {
			console.log(`Inserted ${error.result.insertedCount} documents of type ${collection} from ${sourceId}`);
		}
	}
}

/**
 * inserts all documents from all source files of a given content type to their respective collection
 * e.g. all documents from all files in the 'armor' subdirectory will be inserted into the 'armor' collection
 */
async function insertContentType(contentType) {
	const dir = path.join(DATA_DIR, contentType);
	const files = fs.readdirSync(dir);
	for (const sourceFile of files) {
		let data = JSON.parse(fs.readFileSync(path.join(dir, sourceFile)));
		await insertAllFromSource(data, sourceFile.split('.')[0], contentType);
	}
}

async function insert() {
	// drop all collections of defined content types
	for (const collection of contentTypes) await db.collection(collection).drop();
	// create collections for all content types with schema validation
	for (const collection of contentTypes) {
		const schema = JSON.parse(fs.readFileSync(path.join('./schema', `${collection}.json`)));
		await db.createCollection(collection, {	validator: {$jsonSchema: schema} });
	}

	//await db.collection('source').drop();
	//await db.collection('character').drop();

	//const sourceData = JSON.parse(fs.readFileSync(path.join(baseDir, 'sources.json')));
	//const sourceResult = await db.collection('source').insertMany(sourceData);
	//console.log(`Inserted ${sourceResult.insertedCount} sources`);

	for (const collection of contentTypes) await insertContentType(collection);

	//const characterData = JSON.parse(fs.readFileSync(path.join(baseDir, 'characters.json')));
	//const toImport = [];
	//for (document of characterData) {
	//	try {
	//		await resolveReferences(document, null, db);
	//		toImport.push(document);
	//	} catch (error) {
	//		console.error(`Failed to import character ${document.characterName}`);
	//	}
	//}
	//const characterResult = await db.collection('character').insertMany(toImport);
	//console.log(`Inserted ${characterResult.insertedCount} characters`);
}

async function createReference(field, collection) {
	if (field && field.name && field.source) {
		field = {
			ref: collection,
			query: { name: field.name, source: field.source }
		};
	} else if (field instanceof ObjectId) {
		const entity = await db.collection(collection).findOne({ _id: field });
		field = {
			ref: collection,
			query: { name: entity.name, source: entity.source }
		};
	}
	return field;
}

async function handleCharacterReferences(character) {
	for (const key in character) {
		if (key === 'classId' || key === 'raceId' || key === 'backgroundId') {
			character[key] = await createReference(character[key], key.slice(0, -2), db);
		} else if (key === 'armor') {
			character[key] = await createReference(character[key], 'armor', db);
		} else if (key === 'weapons') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i] = await createReference(character[key][i], 'weapon', db);
			}
		} else if (key === 'spells') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i] = await createReference(character[key][i], 'spell', db);
			}
		} else if (key === 'tools') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i].item = await createReference(character[key][i].item, 'tool', db);
			}
		} else if (key === 'languages') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i].item = await createReference(character[key][i].item, 'language', db);
			}
		} else if (key === 'sources') {
			for (let i = 0; i < character[key].length; i++) {
				character[key][i] = {
					ref: 'source',
					query: { _id: character[key][i]._id }
				}
			}
		} else if (key === '_id') {
			delete character[key];
		}
	}
}

async function backupCharacters() {
	let characters = await db.collection('character').find().toArray();
	for (let character of characters) {
		await handleCharacterReferences(character, db);
	}
	if (characters.length === 0) return;
	fs.writeFileSync(path.join(DATA_DIR, 'characters.json'), JSON.stringify(characters, null, 2));
	console.log(`Backed up ${characters.length} characters`);
}

async function run() {
	try {
		//await backupCharacters(db);
		console.log("Inserting data...");
		await insert(db);
	} catch (err) {
		console.log(err.stack);
	} finally {
		await client.close();
	}
}

run();

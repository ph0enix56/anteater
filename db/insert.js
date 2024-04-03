const fs = require('fs');
const path = require('path');
const { ObjectId, MongoClient } = require('mongodb');

const baseDir = './data';
const url = process.env.MONGODB_URI;
const dbName = 'dnd';
const client = new MongoClient(url);

const contentTypes = [
	'tool',
	'language',
	'weapon',
	'armor',
	'background',
	'race',
	'class',
	'spell',
];

async function resolveReferences(document, parentKey = null, db) {
	for (const key in document) {
		if (document[key] && typeof document[key] === 'object') {
			if (document[key].ref && document[key].query) {
				if (key.endsWith('Id') || (parentKey && parentKey.endsWith('Ids'))) {
					document[key] = (await db.collection(document[key].ref).findOne(document[key].query))._id;
				} else {
					document[key] = await db.collection(document[key].ref).findOne(document[key].query);
				}
			} else {
				await resolveReferences(document[key], key, db);
			}
		}
	}
}

async function handleDataFromSource(data, sourceName, collection, db) {
	for (document of data) {
		document.source = {
			ref: 'source',
			query: { _id: sourceName }
		};
		await resolveReferences(document, null, db);
	}
	const result = await db.collection(collection).insertMany(data);
	console.log(`Inserted ${result.insertedCount} documents of type ${collection} from ${sourceName}`);
}

async function handleCollection(collection, db) {
	const dir = path.join(baseDir, collection);
	const files = fs.readdirSync(dir);
	for (const sourceFile of files) {
		const data = JSON.parse(fs.readFileSync(path.join(dir, sourceFile)));
		await handleDataFromSource(data, sourceFile.split('.')[0], collection, db);
	}
}

async function insert(db) {
	for (const collection of contentTypes) await db.collection(collection).drop();
	await db.collection('source').drop();
	await db.collection('character').drop();

	const sourceData = JSON.parse(fs.readFileSync(path.join(baseDir, 'sources.json')));
	const sourceResult = await db.collection('source').insertMany(sourceData);
	console.log(`Inserted ${sourceResult.insertedCount} sources`);

	for (const collection of contentTypes) await handleCollection(collection, db);

	const characterData = JSON.parse(fs.readFileSync(path.join(baseDir, 'characters.json')));
	for (document of characterData) await resolveReferences(document, null, db);
	const characterResult = await db.collection('character').insertMany(characterData);
	console.log(`Inserted ${characterResult.insertedCount} characters`);
}

async function createReference(field, collection, db) {
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

async function handleCharacterReferences(character, db) {
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

async function backupCharacters(db) {
	let characters = await db.collection('character').find().toArray();
	for (let character of characters) {
		await handleCharacterReferences(character, db);
	}
	if (characters.length === 0) return;
	fs.writeFileSync(path.join(baseDir, 'characters.json'), JSON.stringify(characters, null, 2));
	console.log(`Backed up ${characters.length} characters`);
}

async function run() {
	try {
		await client.connect();
		console.log('Connected correctly to MongoDB');
		const db = client.db(dbName);

		await backupCharacters(db);
		await insert(db);

	} catch (err) {
		console.log(err.stack);
	} finally {
		await client.close();
	}
}

run();

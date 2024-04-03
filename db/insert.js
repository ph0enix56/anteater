const fs = require('fs');
const path = require('path');
const MongoClient = require('mongodb').MongoClient;

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

async function run() {
	try {
		await client.connect();
		console.log('Connected correctly to MongoDB');
		const db = client.db(dbName);

		await insert(db);

	} catch (err) {
		console.log(err.stack);
	} finally {
		await client.close();
	}
}

run();

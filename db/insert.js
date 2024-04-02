const fs = require('fs');
const path = require('path');
const MongoClient = require('mongodb').MongoClient;

const url = 'mongodb://mongodb:27017/dnd';
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
	await db.collection(collection).insertMany(data);
}

async function handleCollection(collection, db) {
	const dir = `./data/${collection}`;
	const files = fs.readdirSync(dir);
	for (const sourceFile of files) {
		console.log(`Inserting documents of type ${collection} from ${sourceFile}`);
		const data = JSON.parse(fs.readFileSync(path.join(dir, sourceFile)));
		await handleDataFromSource(data, sourceFile.split('.')[0], collection, db);
	}
}

async function run() {
	try {
		await client.connect();
		console.log("Connected correctly to server");
		const db = client.db(dbName);

		for (const collection of contentTypes) await db.collection(collection).drop();
		await db.collection('source').drop();
		await db.collection('character').drop();

		const sourceData = JSON.parse(fs.readFileSync('./data/sources.json'));
		await db.collection('source').insertMany(sourceData);

		for (const collection of contentTypes) await handleCollection(collection, db);

		const characterData = JSON.parse(fs.readFileSync('./data/characters.json'));
		for (document of characterData) await resolveReferences(document, null, db);
		await db.collection('character').insertMany(characterData);
	} catch (err) {
		console.log(err.stack);
	} finally {
		await client.close();
	}
}

run();

const fs = require('fs');
const path = require('path');

const dndDb = db.getSiblingDB('dnd_dev');
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

function resolveReferences(document, parentKey = null) {
	for (const key in document) {
		if (document[key] && typeof document[key] === 'object') {
			if (document[key].ref && document[key].query) {
				console.log(key);
				if (key.endsWith('Id') || (parentKey && parentKey.endsWith('Ids'))) {
					document[key] = dndDb[document[key].ref].findOne(document[key].query, { _id: 1 })._id;
				} else {
					document[key] = dndDb[document[key].ref].findOne(document[key].query);
				}
			} else {
				resolveReferences(document[key], key);
			}
		}
	}
}

function handleDataFromSource(data, sourceName, collection) {
	for (document of data) {
		document.source = {
			ref: 'source',
			query: { _id: sourceName }
		};
		resolveReferences(document);
	}
	const result = dndDb[collection].insertMany(data);
}

function handleCollection(collection) {
	const dir = `./data/${collection}`;
	const files = fs.readdirSync(dir);
	for (const sourceFile of files) {
		console.log(`Inserting documents of type ${collection} from ${sourceFile}`);
		const data = JSON.parse(fs.readFileSync(path.join(dir, sourceFile)));
		handleDataFromSource(data, sourceFile.split('.')[0], collection);
	}
}

function run() {
	for (const collection of contentTypes) dndDb[collection].drop();
	dndDb.source.drop();
	dndDb.character.drop();

	const sourceData = JSON.parse(fs.readFileSync('./data/sources.json'));
	dndDb.source.insertMany(sourceData);

	for (const collection of contentTypes) handleCollection(collection);

	const characterData = JSON.parse(fs.readFileSync('./data/characters.json'));
	for (document of characterData) resolveReferences(document);
	dndDb.character.insertMany(characterData);
}

run();

let dndDb = db.getSiblingDB('dnd');

// clear all collections
dndDb.source.drop();
dndDb.tool.drop();
dndDb.language.drop();
dndDb.background.drop();
dndDb.race.drop();
dndDb.dndClass.drop();
dndDb.weapon.drop();
dndDb.armor.drop();
dndDb.spell.drop();
dndDb.character.drop();

let sources = [
	{
		"_id": "SRD",
		"name": "System Reference Document",
	},
	{
		"_id": "EXP1",
		"name": "Experimental Source 1",
	},
	{
		"_id": "EXP2",
		"name": "Experimental Source 2",
	},
];
dndDb.source.insertMany(sources);
print(dndDb.source.countDocuments() + " sources inserted");

let srcSRD = dndDb.source.findOne({ "_id": "SRD" });
let srcEXP1 = dndDb.source.findOne({ "_id": "EXP1" });
let srcEXP2 = dndDb.source.findOne({ "_id": "EXP2" });

let artisanTools = [
	"Alchemist's supplies",
	"Brewer's supplies",
	"Calligrapher's supplies",
	"Carpenter's tools",
];
let instrumentTools = [
	"Bagpipes",
	"Drum",
	"Dulcimer",
	"Flute",
];
let gamingTools = [
	"Dice set",
	"Playing card set",
];
let otherTools = [
	"Disguise kit",
	"Forgery kit",
	"Herbalism kit",
	"Navigator's tools",
	"Poisoner's kit",
	"Thieves' tools",
];
let vehicleTools = [
	"Vehicles (land)",
	"Vehicles (water)",
];
let exp1Tools = [
	"Screwdriver",
	"Drill",
	"Chainsaw",
];
let exp2Tools = [
	"Test tool 1",
	"Test tool 2",
	"Test tool 3",
];
dndDb.tool.insertMany(artisanTools.map(tool => { return { "name": tool, "type": "artisan", "source": srcSRD } }));
dndDb.tool.insertMany(instrumentTools.map(tool => { return { "name": tool, "type": "instrument", "source": srcSRD } }));
dndDb.tool.insertMany(gamingTools.map(tool => { return { "name": tool, "type": "gaming", "source": srcSRD } }));
dndDb.tool.insertMany(otherTools.map(tool => { return { "name": tool, "type": "other", "source": srcSRD } }));
dndDb.tool.insertMany(vehicleTools.map(tool => { return { "name": tool, "type": "vehicle", "source": srcSRD } }));
dndDb.tool.insertMany(exp1Tools.map(tool => { return { "name": tool, "type": "artisan", "source": srcEXP1 } }));
dndDb.tool.insertMany(exp2Tools.map(tool => { return { "name": tool, "type": "other", "source": srcEXP2 } }));
print(dndDb.tool.countDocuments() + " tools inserted");

let languagesBasic = [
	"Common",
	"Dwarvish",
	"Elvish",
	"Giant",
	"Gnomish",
	"Goblin",
	"Halfling",
	"Orc",
];
let languagesExotic = [
	"Abyssal",
	"Celestial",
	"Draconic",
	"Deep Speech",
	"Infernal",
	"Primordial",
	"Sylvan",
	"Undercommon",
];
dndDb.language.insertMany(languagesBasic.map(language => { return { "name": language, "exotic": false, "source": srcSRD } }));
dndDb.language.insertMany(languagesExotic.map(language => { return { "name": language, "exotic": true, "source": srcSRD } }));

let languagesExp1 = [
	"English",
	"French",
	"German",
	"Spanish",
];
dndDb.language.insertMany(languagesExp1.map(language => { return { "name": language, "exotic": false, "source": srcEXP1 } }));
print(dndDb.language.countDocuments() + " languages inserted");

let backgrounds = [
	{
		"name": "Acolyte",
		"source": srcSRD,
		"description": "You have spent your life in the service of a temple to a specific god or pantheon of gods. You act as an intermediary between the realm of the holy and the mortal world, performing sacred rites and offering sacrifices in order to conduct worshipers into the presence of the divine.",
		"features": [
			{"title": "Shelter of the Faithful", "levelMinimum": 1, "text": "As an acolyte, you command the respect of those who share your faith, and you can perform the religious ceremonies of your deity. You and your adventuring companions can expect to receive free healing and care at a temple, shrine, or other established presence of your faith, though you must provide any material components needed for spells. Those who share your religion will support you (but only you) at a modest lifestyle."},
		],
		"skills": {
			"amount": 2,
			"defaults": [
				"insight",
				"religion",
			],
		},
		"tools": {
			"amount": 1,
			"defaults": [dndDb.tool.findOne({ "name": "Disguise kit", "source": srcSRD })],
		},
		"languages": {
			"amount": 2,
			"defaults": [dndDb.language.findOne({ "name": "Common", "source": srcSRD })],
		},
	},
	{
		"name": "Artist",
		"source": srcEXP1,
		"description": "You are a master of your craft. You have spent years honing your skills and perfecting your technique. You are a true artisan, and your work is highly sought after by collectors and connoisseurs alike.",
		"features": [
			{"title": "Artisan's Eye", "levelMinimum": 1, "text": "As an artist, you have an eye for detail and a steady hand. You can create works of art that are truly masterpieces. You can sell your work for a high price, and you can expect to receive commissions from wealthy patrons."},
		],
		"skills": {
			"amount": 2,
			"defaults": [
				"performance",
			],
		},
		"tools": {
			"amount": 1,
			"defaults": [
				dndDb.tool.findOne({ "name": "Flute", "source": srcSRD }),
			],
		},
		"languages": {
			"amount": 1,
			"defaults": [],
		},
	},
	{
		"name": "Miner",
		"source": srcEXP1,
		"description": "You have spent your life working in the mines. You are a skilled miner, and you know how to extract valuable minerals from the earth. You are also an expert at navigating the dark and dangerous tunnels that lie beneath the surface.",
		"features": [
			{"title": "Hard Worker", "levelMinimum": 1, "text": "As a miner, you are used to hard work and long hours. You have advantage on Strength checks made to move heavy objects, and you can work for up to 12 hours a day without suffering from exhaustion."},
		],
		"skills": {
			"amount": 2,
			"defaults": [
				"athletics",
				"survival",
			],
		},
		"tools": {
			"amount": 2,
			"defaults": [
				dndDb.tool.findOne({ "name": "Navigator's tools", "source": srcSRD }),
			],
		},
		"languages": {
			"amount": 0,
			"defaults": [],
		},
	},
	{
		"name": "Space Pirate",
		"source": srcEXP2,
		"description": "This is a background that is not the same as the others. It is different and unique.",
		"features": [
			{"title": "Pirate's Code", "levelMinimum": 1, "text": "As a space pirate, you live by a strict code of honor. You never harm the innocent, and you always keep your word. You can expect to receive aid from other pirates, and you can call on them for help when you need it."},
		],
		"skills": {
			"amount": 2,
			"defaults": [
				"acrobatics",
				"athletics",
			],
		},
		"tools": {
			"amount": 2,
			"defaults": [],
		},
		"languages": {
			"amount": 0,
			"defaults": [],
		},
	},
];
dndDb.background.insertMany(backgrounds);
print(dndDb.background.countDocuments() + " backgrounds inserted");

let races = [
	{
		"name": "Half-Elf",
		"source": srcSRD,
		"description": "Walking in two worlds but truly belonging to neither, half-elves combine what some say are the best qualities of their elf and human parents: human curiosity, inventiveness, and ambition tempered by the refined senses, love of nature, and artistic tastes of the elves. Some half-elves live among humans, set apart by their emotional and physical differences, watching friends and loved ones age while time barely touches them. Others live with the elves, growing to adulthood while their peers continue to live as children, growing restless in the timeless elven realms.",
		"features": [{ "title": "Darkvision", "text": "Thanks to your elf blood, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray.", "levelMinimum": 1 },
				{ "title": "Fey Ancestry", "text": "The elf has advantage on saving throws against being charmed, and magic can't put the elf to sleep.", "levelMinimum": 1 }],
		"speed": 30,
		"sizes": ["medium"],
		"languages": {
			"amount": 2,
			"defaults": [ dndDb.language.findOne({ "name": "Common", "source": srcSRD }), dndDb.language.findOne({ "name": "Elvish", "source": srcSRD }) ],
		},
		"abilities_plus_2": {
			"amount": 1,
			"defaults": ["charisma"],
		},
		"abilities_plus_1": {
			"amount": 2,
			"defaults": [],
		},
		"skills": {
			"amount": 2,
			"defaults": [],
		}
	},
	{
		"name": "Gnome (Rock)",
		"source": srcSRD,
		"description": "As a rock gnome, you have a natural inventiveness and hardiness beyond that of other gnomes.",
		"features": [
			{
				"title": "Darkvision",
				"levelMinimum": 1,
				"text": "Accustomed to life underground, you can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."
			},
			{
				"title": "Gnome Cunning",
				"levelMinimum": 1,
				"text": "You have advantage on all Intelligence, Wisdom, and Charisma saving throws against magic."
			},
			{
				"title": "Artificer's Lore",
				"levelMinimum": 1,
				"text": "Whenever you make an Intelligence (History) check related to magic items, alchemical objects, or technological devices, you can add twice your proficiency bonus, instead of any proficiency bonus you normally apply."
			},			
		],
		"speed": 25,
		"sizes": ["small"],
		"languages": {
			"amount": 1,
			"defaults": [ dndDb.language.findOne({ "name": "Common", "source": srcSRD }) ],
		},
		"abilities_plus_2": {
			"amount": 1,
			"defaults": ["dexterity"]
		},
		"abilities_plus_1": {
			"amount": 1,
			"defaults": ["intelligence"]
		},
		"skills": {
			"amount": 0,
			"defaults": []
		}
	},
	{
		"name": "Human (test)",
		"source": srcEXP1,
		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod, nisl eget aliquam ultricies, nunc nisl aliquet nunc, vitae aliquam ni",
		"features": [],
		"speed": 25,
		"sizes": ["small", "medium"],
		"languages": {
			"amount": 3,
			"defaults": [ dndDb.language.findOne({ "name": "Common" }) ],
		},
		"abilities_plus_2": {
			"amount": 0,
			"defaults": [],
		},
		"abilities_plus_1": {
			"amount": 6,
			"defaults": ["strength", "dexterity", "constitution", "intelligence", "wisdom", "charisma"],
		},
		"skills": {
			"amount": 0,
			"defaults": [],
		}
	},
	{
		"name": "Human (test) 2",
		"source": srcEXP2,
		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod, nisl eget aliquam ultricies, nunc nisl aliquet nunc, vitae aliquam ni",
		"features": [],
		"speed": 25,
		"sizes": ["small", "medium"],
		"languages": {
			"amount": 3,
			"defaults": [ dndDb.language.findOne({ "name": "Common" }) ],
		},
		"abilities_plus_2": {
			"amount": 0,
			"defaults": [],
		},
		"abilities_plus_1": {
			"amount": 6,
			"defaults": ["strength", "dexterity", "constitution", "intelligence", "wisdom", "charisma"],
		},
		"skills": {
			"amount": 0,
			"defaults": [],
		}
	},
]
dndDb.race.insertMany(races);
print(dndDb.race.countDocuments() + " races inserted");

let armor = [
	{
		"name": "Leather Armor",
		"source": srcSRD,
		"type": "light",
		"baseArmorClass": 11,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 10 }
		],
		"description": "Nice and light armor"
	},
	{
		"name": "Chain Shirt",
		"source": srcSRD,
		"type": "medium",
		"baseArmorClass": 13,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 2 }
		],
		"description": "Light and flexible armor"
	},
	{
		"name": "Chain Mail",
		"source": srcSRD,
		"type": "heavy",
		"baseArmorClass": 16,
		"strengthRequirement": 13,
		"stealthDisadvantage": true,
		"bonuses": [],
		"description": "Heavy and noisy armor"
	},
	{
		"name": "Plate Armor",
		"source": srcSRD,
		"type": "heavy",
		"baseArmorClass": 18,
		"strengthRequirement": 15,
		"stealthDisadvantage": true,
		"bonuses": [],
		"description": "Heavy and noisy armor"
	},
	{
		"name": "Test Armor",
		"source": srcEXP1,
		"type": "light",
		"baseArmorClass": 12,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 10 }
		],
		"description": "Light and testy armor"
	},
	{
		"name": "Armor of Testing",
		"source": srcEXP1,
		"type": "medium",
		"baseArmorClass": 15,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 2 }
		],
		"description": "Become shrouded in the armor of testing"
	},
	{
		"name": "Bastion of Testing",
		"source": srcEXP2,
		"type": "heavy",
		"baseArmorClass": 22,
		"strengthRequirement": 16,
		"stealthDisadvantage": true,
		"bonuses": [],
		"description": "Impenetrable armor, tested to the limit"
	},
	{
		"name": "Another Armor",
		"source": srcEXP2,
		"type": "medium",
		"baseArmorClass": 14,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 10 }
		],
		"description": "Medium and testy armor"
	},
	{
		"name": "Unarmored",
		"source": srcSRD,
		"type": "unarmored",
		"baseArmorClass": 10,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 10 }
		],
		"description": "No armor at all"
	},
	{
		"name": "Unarmored Defense (Barbarian)",
		"source": srcSRD,
		"type": "unarmored",
		"baseArmorClass": 10,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 10 },
			{ "ability": "constitution", "max": 10 }
		],
		"description": "No armor at all for barbarians"
	},
	{
		"name": "Unarmored Defense (Monk)",
		"source": srcSRD,
		"type": "unarmored",
		"baseArmorClass": 10,
		"strengthRequirement": 0,
		"stealthDisadvantage": false,
		"bonuses": [
			{ "ability": "dexterity", "max": 10 },
			{ "ability": "wisdom", "max": 10 }
		],
		"description": "No armor at all for monks"
	}
];
dndDb.armor.insertMany(armor);
print(dndDb.armor.countDocuments() + " armor sets inserted");

let weapons = [
	{
		"name": "Club",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 5,
		"properties": ["light"],
		"damage": { "amount": 1, "sides": 4 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Dagger",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 20,
		"properties": ["finesse", "light", "thrown"],
		"damage": { "amount": 1, "sides": 4 },
		"damageType": "piercing",
	},
	{
		"name": "Greatclub",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 5,
		"properties": ["two_handed"],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Handaxe",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 20,
		"properties": ["light", "thrown"],
		"damage": { "amount": 1, "sides": 6 },
		"damageType": "slashing",
	},
	{
		"name": "Javelin",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 30,
		"properties": ["thrown"],
		"damage": { "amount": 1, "sides": 6 },
		"damageType": "piercing",
	},
	{
		"name": "Light hammer",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 20,
		"properties": ["light", "thrown"],
		"damage": { "amount": 1, "sides": 4 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Mace",
		"source": srcSRD,
		"type": "simple",
		"ranged": false,
		"range": 5,
		"properties": [],
		"damage": { "amount": 1, "sides": 6 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Shortbow",
		"source": srcSRD,
		"type": "simple",
		"ranged": true,
		"range": 80,
		"properties": ["two_handed"],
		"damage": { "amount": 1, "sides": 6 },
		"damageType": "piercing",
	},
	{
		"name": "Longbow",
		"source": srcSRD,
		"type": "martial",
		"ranged": true,
		"range": 150,
		"properties": ["two_handed", "heavy"],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "piercing",
	},
	{
		"name": "Battleaxe",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": ["versatile"],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "slashing",
	},
	{
		"name": "Flail",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": [],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Glaive",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 10,
		"properties": ["heavy", "reach", "two_handed"],
		"damage": { "amount": 1, "sides": 10 },
		"damageType": "slashing",
	},
	{
		"name": "Greataxe",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": ["heavy", "two_handed"],
		"damage": { "amount": 1, "sides": 12 },
		"damageType": "slashing",
	},
	{
		"name": "Greatsword",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": ["heavy", "two_handed"],
		"damage": { "amount": 2, "sides": 6 },
		"damageType": "slashing",
	},
	{
		"name": "Halberd",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 10,
		"properties": ["heavy", "reach", "two_handed"],
		"damage": { "amount": 1, "sides": 10 },
		"damageType": "slashing",
	},
	{
		"name": "Lance",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 10,
		"properties": ["reach", "special"],
		"damage": { "amount": 1, "sides": 12 },
		"damageType": "piercing",
	},
	{
		"name": "Longsword",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": ["versatile"],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "slashing",
	},
	{
		"name": "Rapier",
		"source": srcSRD,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": ["finesse"],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "piercing",
	},
	{
		"name": "Anklebiter",
		"source": srcEXP1,
		"type": "simple",
		"ranged": false,
		"range": 5,
		"properties": ["light"],
		"damage": { "amount": 1, "sides": 4 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Walking Stick",
		"source": srcEXP1,
		"type": "simple",
		"ranged": false,
		"range": 5,
		"properties": ["light"],
		"damage": { "amount": 1, "sides": 4 },
		"damageType": "bludgeoning",
	},
	{
		"name": "Comically Large Sword",
		"source": srcEXP1,
		"type": "martial",
		"ranged": false,
		"range": 10,
		"properties": ["heavy", "two_handed", "reach"],
		"damage": { "amount": 2, "sides": 8 },
		"damageType": "slashing",
	},
	{
		"name": "Crossbow of Annoyance",
		"source": srcEXP1,
		"type": "simple",
		"ranged": true,
		"range": 80,
		"properties": ["two_handed", "loading"],
		"damage": { "amount": 1, "sides": 8 },
		"damageType": "piercing",
	},
	{
		"name": "Test Weapon",
		"source": srcEXP2,
		"type": "simple",
		"ranged": false,
		"range": 5,
		"properties": ["heavy", "two_handed"],
		"damage": { "amount": 1, "sides": 12 },
		"damageType": "slashing",
	},
	{
		"name": "Another Weapon",
		"source": srcEXP2,
		"type": "martial",
		"ranged": false,
		"range": 5,
		"properties": ["heavy", "two_handed"],
		"damage": { "amount": 1, "sides": 12 },
		"damageType": "slashing",
	},
	{
		"name": "Ranged Masterpiece",
		"source": srcEXP2,
		"type": "martial",
		"ranged": true,
		"range": 150,
		"properties": ["two_handed", "heavy"],
		"damage": { "amount": 4, "sides": 6 },
		"damageType": "piercing",
	}
];
dndDb.weapon.insertMany(weapons);
print(dndDb.weapon.countDocuments() + " weapons inserted");

let classes = [
	{
		"name": "Barbarian",
		"source": srcSRD,
		"description": "A fierce warrior of primitive background who can enter a battle rage.",
		"hitDice": { "amount": 1, "sides": 12 },
		"subclasses": ["Berserker", "Fierce Warrior", "Wild Spirit"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": ["strength", "constitution"],
		"tools": {
			"amount": 0,
			"defaults": []
		},
		"armor_types": ["unarmored", "light", "medium", "shield"],
		"armor": [],
		"weapon_types": ["simple", "martial"],
		"weapons": [],
		"features": [
			{
				"title": "Rage",
				"levelMinimum": 1,
				"text": "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action. While raging, you gain the following benefits if you aren't wearing heavy armor: [Paragraphed text would follow here.]",
			},
			{
				"title": "Unarmored Defense",
				"levelMinimum": 1,
				"text": "While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your Constitution modifier. You can use a shield and still gain this benefit."
			},
			{
				"title": "Reckless Attack",
				"levelMinimum": 2,
				"text": "Starting at 2nd level, you can throw aside all concern for defense to attack with fierce desperation. When you make your first attack on your turn, you can decide to attack recklessly. Doing so gives you advantage on melee weapon attack rolls using Strength during this turn, but attack rolls against you have advantage until your next turn."
			},
			{
				"title": "Extra Attack",
				"levelMinimum": 5,
				"text": "Beginning at 5th level, you can attack twice, instead of once, whenever you take the Attack action on your turn."
			},
			{
				"title": "Feral Instinct",
				"levelMinimum": 7,
				"text": "By 7th level, your instincts are so honed that you have advantage on initiative rolls. [Paragraph break here - TODO.] Additionally, if you are surprised at the beginning of combat and aren't incapacitated, you can act normally on your first turn, but only if you enter your rage before doing anything else on that turn."
			},
			{
				"title": "Primal Champion",
				"levelMinimum": 20,
				"text": "At 20th level, you embody the power of the wilds. Your Strength and Constitution scores increase by 4. Your maximum for those scores is now 24."
			}
		]
	},
	{
		"name": "Rogue",
		"source": srcSRD,
		"description": "A scoundrel who uses stealth and trickery to overcome obstacles and enemies.",
		"hitDice": { "amount": 1, "sides": 8 },
		"subclasses": ["Thief", "Swift Striker", "Knife Master"],
		"skills": {
			"amount": 4,
			"defaults": []
		},
		"saves": ["dexterity", "intelligence"],
		"tools": {
			"amount": 1,
			"defaults": [dndDb.tool.findOne({ "name": "Thieves' tools", "source": srcSRD })]
		},
		"armor_types": ["unarmored", "light"],
		"armor": [],
		"weapon_types": ["simple"],
		"weapons": [dndDb.weapon.findOne({ "name": "Rapier", "source": srcSRD }),
					dndDb.weapon.findOne({ "name": "Longsword", "source": srcSRD })],
		"features": [
			{
				"title": "Expertise",
				"levelMinimum": 1,
				"text": "At 1st level, choose two of your skill proficiencies, or one of your skill proficiencies and your proficiency with thieves' tools. Your proficiency bonus is doubled for any ability check you make that uses either of the chosen proficiencies."
			},
			{
				"title": "Sneak Attack",
				"levelMinimum": 1,
				"text": "Beginning at 1st level, you know how to strike subtly and exploit a foe's distraction. Once per turn, you can deal an extra 1d6 damage to one creature you hit with an attack if you have advantage on the attack roll. The attack must use a finesse or a ranged weapon. You don't need advantage on the attack roll if another enemy of the target is within 5 feet of it, that enemy isn't incapacitated, and you don't have disadvantage on the attack roll. The amount of the extra damage increases by 1d6 every two levels after 1st.",
			},
			{
				"title": "Evasion",
				"levelMinimum": 7,
				"text": "Beginning at 7th level, you can nimbly dodge out of the way of certain area effects, such as a red dragon's fiery breath or an ice storm spell. When you are subjected to an effect that allows you to make a Dexterity saving throw to take only half damage, you instead take no damage if you succeed on the saving throw, and only half damage if you fail."
			},
			{
				"title": "Slippery Mind",
				"levelMinimum": 15,
				"text": "By 15th level, you have acquired greater mental strength. You gain proficiency in Wisdom saving throws."
			}
		]
	},
	{
		"name": "Wizard",
		"source": srcSRD,
		"description": "A powerful mage who uses spells to shape reality to their desires.",
		"hitDice": { "amount": 1, "sides": 6 },
		"subclasses": ["Evocation", "Conjuration", "Necromancy", "Transmutation", "Abjuration", "Divination", "Enchantment", "Illusion"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": ["intelligence", "wisdom"],
		"tools": {
			"amount": 0,
			"defaults": []
		},
		"armor_types": ["unarmored"],
		"armor": [],
		"weapon_types": [],
		"weapons": [dndDb.weapon.findOne({ "name": "Dagger", "source": srcSRD })],
		"features": [
			{
				"title": "Spellcasting",
				"levelMinimum": 1,
				"text": "As a student of arcane magic, you have a spellbook containing spells that show the first glimmerings of your true power."
			},
			{
				"title": "Arcane Recovery",
				"levelMinimum": 1,
				"text": "You have learned to regain some of your magical energy by studying your spellbook. Once per day when you finish a short rest, you can choose expended spell slots to recover. The spell slots can have a combined level that is equal to or less than half your wizard level (rounded up), and none of the slots can be 6th level or higher.",
			},
			{
				"title": "Spell Mastery",
				"levelMinimum": 18,
				"text": "At 18th level, you have achieved such mastery over certain spells that you can cast them at will. Choose a 1st-level wizard spell and a 2nd-level wizard spell that are in your spellbook. You can cast those spells at their lowest level without expending a spell slot when you have them prepared. If you want to cast either spell at a higher level, you must expend a spell slot as normal. By spending 8 hours in study, you can exchange one or both of the spells you chose for different spells of the same levels."
			}
		],
		"spellcasting": {
			"ability": "intelligence",
			"slots": [
				[2, 0, 0, 0, 0, 0, 0, 0, 0],
				[3, 0, 0, 0, 0, 0, 0, 0, 0],
				[4, 2, 0, 0, 0, 0, 0, 0, 0],
				[4, 3, 0, 0, 0, 0, 0, 0, 0],
				[4, 3, 2, 0, 0, 0, 0, 0, 0],
				[4, 3, 3, 0, 0, 0, 0, 0, 0],
				[4, 3, 3, 1, 0, 0, 0, 0, 0],
				[4, 3, 3, 2, 1, 0, 0, 0, 0],
				[4, 3, 3, 2, 1, 0, 0, 0, 0],
				[4, 3, 3, 2, 1, 0, 0, 0, 0],
				[4, 3, 3, 2, 1, 0, 0, 0, 0],
				[4, 3, 3, 2, 1, 1, 0, 0, 0],
				[4, 3, 3, 2, 1, 1, 1, 0, 0],
				[4, 3, 3, 2, 1, 1, 1, 1, 0],
				[4, 3, 3, 2, 1, 1, 1, 1, 0],
				[4, 3, 3, 2, 1, 1, 1, 1, 0],
				[4, 3, 3, 2, 1, 1, 1, 1, 1],
				[4, 3, 3, 2, 1, 1, 1, 1, 1],
				[4, 3, 3, 2, 1, 1, 1, 1, 1],
				[4, 3, 3, 2, 1, 1, 1, 1, 1],
			]
		}
	},
	{
		"name": "Barbar",
		"source": srcEXP1,
		"description": "A sample description for a custom class",
		"hitDice": { "amount": 1, "sides": 12 },
		"subclasses": ["Subclass 1", "Subclass 2", "Subclass 3"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": ["strength", "constitution"],
		"tools": {
			"amount": 3,
			"defaults": [
				dndDb.tool.findOne({ "name": "Alchemist's supplies" }),
				dndDb.tool.findOne({ "name": "Brewer's supplies" }),
			]
		},
		"armor_types": ["unarmored", "light", "medium", "shield"],
		"armor": [],
		"weapon_types": ["simple", "martial"],
		"weapons": [],
		"features": [
			{
				"title": "Rage",
				"levelMinimum": 1,
				"text": "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action. While raging, you gain the following benefits if you aren't wearing heavy armor: [Paragraphed text would follow here.]",
			},
			{
				"title": "Unarmored Defense",
				"levelMinimum": 1,
				"text": "While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your Constitution modifier. You can use a shield and still gain this benefit."
			},
			{
				"title": "Reckless Attack",
				"levelMinimum": 2,
				"text": "Starting at 2nd level, you can throw aside all concern for defense to attack with fierce desperation. When you make your first attack on your turn, you can decide to attack recklessly. Doing so gives you advantage on melee weapon attack rolls using Strength during this turn, but attack rolls against you have advantage until your next turn."
			},
			{
				"title": "Extra Attack",
				"levelMinimum": 5,
				"text": "Beginning at 5th level, you can attack twice, instead of once, whenever you take the Attack action on your turn."
			},
			{
				"title": "Feral Instinct",
				"levelMinimum": 7,
				"text": "By 7th level, your instincts are so honed that you have advantage on initiative rolls. [Paragraph break here - TODO.] Additionally, if you are surprised at the beginning of combat and aren't incapacitated, you can act normally on your first turn, but only if you enter your rage before doing anything else on that turn."
			},
			{
				"title": "Primal Champion",
				"levelMinimum": 20,
				"text": "At 20th level, you embody the power of the wilds. Your Strength and Constitution scores increase by 4. Your maximum for those scores is now 24."
			}
		]
	},
	{
		"name": "Warlock",
		"source": srcEXP2,
		"description": "A wielder of magic that is derived from a bargain with an extraplanar entity.",
		"hitDice": { "amount": 1, "sides": 8 },
		"subclasses": ["A", "B", "C"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": ["wisdom", "charisma"],
		"tools": {
			"amount": 0,
			"defaults": []
		},
		"armor_types": ["unarmored", "light"],
		"armor": [],
		"weapon_types": ["simple"],
		"weapons": [],
		"features": [
			{
				"title": "Otherworldly Patron",
				"levelMinimum": 1,
				"text": "At 1st level, you have struck a bargain with an otherworldly being of your choice."
			}
		],
		"spellcasting": {
			"ability": "charisma",
			"slots": [
				[1, 0, 0, 0, 0, 0, 0, 0, 0],
				[1, 0, 0, 0, 0, 0, 0, 0, 0],
				[0, 2, 0, 0, 0, 0, 0, 0, 0],
				[0, 2, 0, 0, 0, 0, 0, 0, 0],
				[0, 0, 2, 0, 0, 0, 0, 0, 0],
				[0, 0, 2, 0, 0, 0, 0, 0, 0],
				[0, 0, 3, 0, 0, 0, 0, 0, 0],
				[0, 0, 0, 3, 0, 0, 0, 0, 0],
				[0, 0, 0, 4, 0, 0, 0, 0, 0],
				[0, 0, 0, 4, 0, 0, 0, 0, 0],
				[0, 0, 0, 4, 0, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
				[0, 0, 0, 0, 4, 0, 0, 0, 0],
			]
		}
	}
];
dndDb.dndClass.insertMany(classes);
print(dndDb.dndClass.countDocuments() + " classes inserted");

let spells = [
	{
		"name": "Acid Splash",
		"source": srcSRD,
		"level": 0,
		"school": "conjuration",
		"components": {
			"verbal": true,
			"somatic": true
		},
		"castingTime": "1 action",
		"range": "60 feet",
		"duration": "Instantaneous",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "You hurl a bubble of acid. Choose one creature within range, or choose two creatures within range that are within 5 feet of each other. A target must succeed on a Dexterity saving throw or take 1d6 acid damage.",
		"atHigherLevels": "This spell's damage increases by 1d6 when you reach 5th level (2d6), 11th level (3d6), and 17th level (4d6)."
	},
	{
		"name": "Mage Hand",
		"source": srcSRD,
		"level": 0,
		"school": "conjuration",
		"components": {
			"verbal": true,
			"somatic": true
		},
		"castingTime": "1 action",
		"range": "60 feet",
		"duration": "Instantaneous",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "...",
		"atHigherLevels": "..."
	},
	{
		"name": "Prestidigitation",
		"source": srcSRD,
		"level": 0,
		"school": "conjuration",
		"components": {
			"verbal": true,
			"somatic": true
		},
		"castingTime": "1 action",
		"range": "60 feet",
		"duration": "Instantaneous",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
		],
		"description": "...",
		"atHigherLevels": "..."
	},
	{
		"name": "Aid",
		"source": srcSRD,
		"level": 2,
		"school": "abjuration",
		"components": {
			"verbal": true,
			"somatic": true,
			"material": "a tiny strip of white cloth"
		},
		"castingTime": "1 action",
		"range": "30 feet",
		"duration": "8 hours",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "Your spell bolsters your allies with toughness and resolve. Choose up to three creatures within range. Each target's hit point maximum and current hit points increase by 5 for the duration.",
		"atHigherLevels": "When you cast this spell using a spell slot of 3rd level or higher, a target's hit points increase by an additional 5 for each slot level above 2nd."
	},
	{
		"name": "Alarm",
		"source": srcSRD,
		"level": 1,
		"school": "abjuration",
		"components": {
			"verbal": true,
			"somatic": true,
			"material": "a tiny bell and a piece of fine silver wire"
		},
		"castingTime": "1 minute",
		"range": "30 feet",
		"duration": "8 hours",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "...",
		"atHigherLevels": "..."
	},
	{
		"name": "Shield",
		"source": srcSRD,
		"level": 1,
		"school": "abjuration",
		"components": {
			"verbal": true,
			"somatic": true,
			"material": "a tiny bell and a piece of fine silver wire"
		},
		"castingTime": "1 minute",
		"range": "30 feet",
		"duration": "8 hours",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "...",
		"atHigherLevels": "..."
	},	
	{
		"name": "Magic Missile",
		"source": srcSRD,
		"level": 1,
		"school": "abjuration",
		"components": {
			"verbal": true,
			"somatic": true,
			"material": "a tiny bell and a piece of fine silver wire"
		},
		"castingTime": "1 minute",
		"range": "30 feet",
		"duration": "8 hours",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
		],
		"description": "...",
		"atHigherLevels": "..."
	},
	{
		"name": "Fireball",
		"source": srcSRD,
		"level": 3,
		"school": "abjuration",
		"components": {
			"verbal": true,
			"somatic": true,
			"material": "a tiny bell and a piece of fine silver wire"
		},
		"castingTime": "1 minute",
		"range": "30 feet",
		"duration": "8 hours",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "...",
		"atHigherLevels": "..."
	},
];
let testSpells = [];
for (let i = 0; i < 12; i++) {
	testSpells.push({
		"name": "Test Spell " + i,
		"source": srcEXP1,
		"level": 0,
		"school": "conjuration",
		"components": {
			"verbal": true,
			"somatic": true
		},
		"castingTime": "1 action",
		"range": "60 feet",
		"duration": "Instantaneous",
		"dndClassIds": [
			dndDb.dndClass.findOne({ "name": "Wizard" })._id.toString(),
			dndDb.dndClass.findOne({ "name": "Warlock" })._id.toString()
		],
		"description": "This is a test spell",
		"atHigherLevels": "This is a test spell"
	});
}
dndDb.spell.insertMany(spells);
dndDb.spell.insertMany(testSpells);
print(dndDb.spell.countDocuments() + " spells inserted");

let characters = [
	{
		"character_name": "Sisi",
		"player_name": "Filip",
		"card_photo_url": "https://img42.rajce.idnes.cz/d4203/19/19022/19022644_105ea57e17168eebf65eb4668684d0f5/images/sisi-2_2.jpg",
		"sheet_photo_url": "https://img42.rajce.idnes.cz/d4203/19/19022/19022644_105ea57e17168eebf65eb4668684d0f5/images/sisi-2_2.jpg",
		"sources": [],
		"class": dndDb.dndClass.findOne({ "name": "Warlock" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Half-Elf" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Acolyte" }, { "_id": 1 })._id,
		"size": "medium",
		"subclass": "B",
		"level": 5,
		"ability_scores": {
			"strength": { "score": 16, "upByOne": false, "upByTwo": true },
			"dexterity": { "score": 9, "upByOne": true, "upByTwo": false },
			"constitution": { "score": 12, "upByOne": false, "upByTwo": false },
			"intelligence": { "score": 11, "upByOne": false, "upByTwo": false },
			"wisdom": { "score": 12, "upByOne": false, "upByTwo": false },
			"charisma": { "score": 17, "upByOne": true, "upByTwo": false },
		},
		"skills": ["athletics", "perception", "religion", "survival"],
		"saving_throws": ["strength", "constitution"],
		"tools": [
			{
				"item": dndDb.tool.findOne({ "name": "Calligrapher's supplies" }),
				"from": "background"
			},
			{
				"item": dndDb.tool.findOne({ "name": "Playing card set" }),
				"from": "dndClass"
			}
		],
		"languages": [
			{
				"item": dndDb.language.findOne({ "name": "Common" }),
				"from": "race"
			},
			{
				"item": dndDb.language.findOne({ "name": "Dwarvish" }),
				"from": "race"
			}
		],
		"armor": dndDb.armor.findOne({ "name": "Chain Mail", "source": srcSRD }),
		"weapons": [
			dndDb.weapon.findOne({ "name": "Anklebiter", "source": srcEXP1 }),
			dndDb.weapon.findOne({ "name": "Javelin", "source": srcSRD }),
		],
		"spells": [
			dndDb.spell.findOne({ "name": "Acid Splash" }),
			dndDb.spell.findOne({ "name": "Mage Hand" }),
			dndDb.spell.findOne({ "name": "Shield" }),
		]
	},
	{
		"character_name": "Catherine",
		"player_name": "Zanet",
		"card_photo_url": "https://img42.rajce.idnes.cz/d4203/19/19022/19022644_105ea57e17168eebf65eb4668684d0f5/images/sisi.jpg",
		"sheet_photo_url": "https://img42.rajce.idnes.cz/d4203/19/19022/19022644_105ea57e17168eebf65eb4668684d0f5/images/pic-6.jpg",
		"sources": [srcSRD, srcEXP1],
		"class": dndDb.dndClass.findOne({ "name": "Rogue" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Human (test)" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Artist" }, { "_id": 1 })._id,
		"size": "medium",
		"subclass": "Thief",
		"level": 11,
		"ability_scores": {
			"strength": { "score": 10, "upByOne": false, "upByTwo": false },
			"dexterity": { "score": 18, "upByOne": false, "upByTwo": true },
			"constitution": { "score": 14, "upByOne": false, "upByTwo": false },
			"intelligence": { "score": 16, "upByOne": false, "upByTwo": false },
			"wisdom": { "score": 12, "upByOne": false, "upByTwo": false },
			"charisma": { "score": 10, "upByOne": false, "upByTwo": false },
		},
		"skills": ["acrobatics", "deception", "insight", "perception", "sleight_of_hand", "stealth"],
		"saving_throws": ["dexterity", "intelligence"],
		"tools": [
			{
				"item": dndDb.tool.findOne({ "name": "Bagpipes" }),
				"from": "background"
			},
		],
		"languages": [
			{
				"item": dndDb.language.findOne({ "name": "Common" }),
				"from": "race"
			},
			{
				"item": dndDb.language.findOne({ "name": "Elvish" }),
				"from": "background"
			}
		],
		"armor": dndDb.armor.findOne({ "name": "Test Armor", "source": srcEXP1 }),
		"weapons" : [
			dndDb.weapon.findOne({ "name": "Dagger", "source": srcSRD }),
			dndDb.weapon.findOne({ "name": "Longsword", "source": srcSRD }),
			dndDb.weapon.findOne({ "name": "Flail", "source": srcSRD }),
			dndDb.weapon.findOne({ "name": "Shortbow", "source": srcSRD }),
		],
	},
	{
		"character_name": "Amur",
		"player_name": "Nekdo",
		"card_photo_url": "https://img42.rajce.idnes.cz/d4203/19/19022/19022644_105ea57e17168eebf65eb4668684d0f5/images/pic-8_2.jpg",
		"sheet_photo_url": "https://img42.rajce.idnes.cz/d4203/19/19022/19022644_105ea57e17168eebf65eb4668684d0f5/images/pic-7.jpg",
		"sources": [srcSRD],
		"class": dndDb.dndClass.findOne({ "name": "Wizard" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Gnome (Rock)" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Acolyte" }, { "_id": 1 })._id,
		"size": "small",
		"subclass": "Abjuration",
		"level": 14,
		"ability_scores": {
			"strength": { "score": 8, "upByOne": false, "upByTwo": false },
			"dexterity": { "score": 14, "upByOne": false, "upByTwo": false },
			"constitution": { "score": 12, "upByOne": false, "upByTwo": false },
			"intelligence": { "score": 16, "upByOne": false, "upByTwo": true },
			"wisdom": { "score": 10, "upByOne": false, "upByTwo": false },
			"charisma": { "score": 10, "upByOne": false, "upByTwo": false },
		},
		"skills": ["arcana", "deception", "investigation", "sleight_of_hand", "stealth"],
		"saving_throws": ["dexterity", "intelligence"],
		"tools": [],
		"languages": [
			{
				"item": dndDb.language.findOne({ "name": "Common" }),
				"from": "race"
			},
			{
				"item": dndDb.language.findOne({ "name": "Halfling" }),
				"from": "race"
			},
			{
				"item": dndDb.language.findOne({ "name": "Draconic" }),
				"from": "background"
			},
			{
				"item": dndDb.language.findOne({ "name": "Undercommon" }),
				"from": "background"
			}
		],
		"armor": dndDb.armor.findOne({ "name": "Leather Armor", "source": srcSRD }),
		"weapons" : [
			dndDb.weapon.findOne({ "name": "Dagger", "source": srcSRD }),
			dndDb.weapon.findOne({ "name": "Glaive", "source": srcSRD }),
		],
		"spells": [
			dndDb.spell.findOne({ "name": "Prestidigitation" }),
			dndDb.spell.findOne({ "name": "Aid" }),
			dndDb.spell.findOne({ "name": "Alarm" }),
			dndDb.spell.findOne({ "name": "Shield" }),
			dndDb.spell.findOne({ "name": "Magic Missile" }),
			dndDb.spell.findOne({ "name": "Fireball" }),
		]
	},
	{
		"character_name": "Bob",
		"player_name": "Tester",
		"card_photo_url": "",
		"sheet_photo_url": "",
		"sources": [srcSRD, srcEXP1],
		"class": dndDb.dndClass.findOne({ "name": "Barbarian" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Human (test)" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Miner" }, { "_id": 1 })._id,
		"size": "medium",
		"subclass": "Storm Herald",
		"level": 7,
		"ability_scores": {
			"strength": { "score": 18, "upByOne": false, "upByTwo": true },
			"dexterity": { "score": 14, "upByOne": false, "upByTwo": false },
			"constitution": { "score": 16, "upByOne": false, "upByTwo": false },
			"intelligence": { "score": 10, "upByOne": false, "upByTwo": false },
			"wisdom": { "score": 12, "upByOne": false, "upByTwo": false },
			"charisma": { "score": 10, "upByOne": false, "upByTwo": false },
		},
		"skills": ["athletics", "deception", "insight", "perception", "sleight_of_hand", "stealth"],
		"saving_throws": ["strength", "constitution"],
		"tools": [],
		"languages": [
			{
				"item": dndDb.language.findOne({ "name": "Common" }),
				"from": "race"
			},
			{
				"item": dndDb.language.findOne({ "name": "Orc" }),
				"from": "background"
			}
		],
		"armor": dndDb.armor.findOne({ "name": "Unarmored Defense (Barbarian)", "source": srcSRD }),
		"weapons" : [
			dndDb.weapon.findOne({ "name": "Greataxe", "source": srcSRD }),
			dndDb.weapon.findOne({ "name": "Javelin", "source": srcSRD }),
			dndDb.weapon.findOne({ "name": "Longbow", "source": srcSRD }),
		]
	}
];
dndDb.character.insertMany(characters);
print(dndDb.character.countDocuments() + " characters inserted");

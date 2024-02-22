let dndDb = db.getSiblingDB('dnd');

// clear all collections
dndDb.source.drop();
dndDb.tool.drop();
dndDb.language.drop();
dndDb.background.drop();
dndDb.race.drop();
dndDb.dndClass.drop();
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

let sourceSRD = dndDb.source.findOne({ "_id": "SRD" });
let sourceEXP1 = dndDb.source.findOne({ "_id": "EXP1" });
let sourceEXP2 = dndDb.source.findOne({ "_id": "EXP2" });

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
	"Test tool 1",
	"Test tool 2",
	"Test tool 3",
];
let exp2Tools = [
	"Another tool 1",
	"Another tool 2",
	"Another tool 3",
];
dndDb.tool.insertMany(artisanTools.map(tool => { return { "name": tool, "type": "artisan", "source": sourceSRD } }));
dndDb.tool.insertMany(instrumentTools.map(tool => { return { "name": tool, "type": "instrument", "source": sourceSRD } }));
dndDb.tool.insertMany(gamingTools.map(tool => { return { "name": tool, "type": "gaming", "source": sourceSRD } }));
dndDb.tool.insertMany(otherTools.map(tool => { return { "name": tool, "type": "other", "source": sourceSRD } }));
dndDb.tool.insertMany(vehicleTools.map(tool => { return { "name": tool, "type": "vehicle", "source": sourceSRD } }));
dndDb.tool.insertMany(exp1Tools.map(tool => { return { "name": tool, "type": "other", "source": sourceEXP1 } }));
dndDb.tool.insertMany(exp2Tools.map(tool => { return { "name": tool, "type": "other", "source": sourceEXP2 } }));
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
dndDb.language.insertMany(languagesBasic.map(language => { return { "name": language, "exotic": false, "source": sourceSRD } }));
dndDb.language.insertMany(languagesExotic.map(language => { return { "name": language, "exotic": true, "source": sourceSRD } }));
print(dndDb.language.countDocuments() + " languages inserted");

let backgrounds = [
	{
		"name": "Acolyte",
		"source": sourceSRD,
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
			"amount": 0,
			"defaults": [],
		},
		"languages": {
			"amount": 2,
			"defaults": [],
		},
	},
	{
		"name": "Charlatan",
		"source": sourceSRD,
		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod, nisl eget aliquam ultricies, nunc nisl aliquet nunc, vitae aliquam ni",
		"features": [
			{"title": "False Identity", "levelMinimum": 1, "text": "Something about false identity"},
		],
		"skills": {
			"amount": 2,
			"defaults": [
				"deception",
				"sleight_of_hand",
			],
		},
		"tools": {
			"amount": 1,
			"defaults": [ dndDb.tool.findOne({ "name": "Disguise kit" }) ],
		},
		"languages": {
			"amount": 1,
			"defaults": [],
		},
	},
	{
		"name": "Acolyte new",
		"source": sourceEXP1,
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
			"defaults": [],
		},
		"languages": {
			"amount": 2,
			"defaults": [],
		},
	},
	{
		"name": "Random dude",
		"source": sourceEXP2,
		"description": "You have spent your life in the service of a temple to a specific god or pantheon of gods. You act as an intermediary between the realm of the holy and the mortal world, performing sacred rites and offering sacrifices in order to conduct worshipers into the presence of the divine.",
		"features": [
			{"title": "Some feature", "levelMinimum": 1, "text": "As an acolyte, you command the respect of those who share your faith, and you can perform the religious ceremonies of your deity. You and your adventuring companions can expect to receive free healing and care at a temple, shrine, or other established presence of your faith, though you must provide any material components needed for spells. Those who share your religion will support you (but only you) at a modest lifestyle."},
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
			"defaults": [],
		},
		"languages": {
			"amount": 2,
			"defaults": [],
		},
	},
	{
		"name": "Another background",
		"source": sourceEXP1,
		"description": "This is a background that is not the same as the others. It is different and unique.",
		"features": [
			{"title": "Shelter of the Faithful", "levelMinimum": 1, "text": "As an acolyte, you command the respect of those who share your faith, and you can perform the religious ceremonies of your deity. You and your adventuring companions can expect to receive free healing and care at a temple, shrine, or other established presence of your faith, though you must provide any material components needed for spells. Those who share your religion will support you (but only you) at a modest lifestyle."},
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
		"source": sourceSRD,
		"description": "Walking in two worlds but truly belonging to neither, half-elves combine what some say are the best qualities of their elf and human parents: human curiosity, inventiveness, and ambition tempered by the refined senses, love of nature, and artistic tastes of the elves. Some half-elves live among humans, set apart by their emotional and physical differences, watching friends and loved ones age while time barely touches them. Others live with the elves, growing to adulthood while their peers continue to live as children, growing restless in the timeless elven realms.",
		"features": [{ "title": "Darkvision", "text": "Thanks to your elf blood, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray.", "levelMinimum": 1 },
				{ "title": "Fey Ancestry", "text": "You have advantage on saving throws against being charmed, and magic can't put you to sleep.", "levelMinimum": 1 }],
		"speed": 30,
		"sizes": ["medium"],
		"languages": {
			"amount": 2,
			"defaults": [ dndDb.language.findOne({ "name": "Common" }), dndDb.language.findOne({ "name": "Elvish" }) ],
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
		"name": "Human (test)",
		"source": sourceEXP1,
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
		"source": sourceEXP1,
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
		"name": "Gnome (Forest)",
		"source": sourceSRD,
		"description": "A constant hum of busy activity pervades the warrens and neighborhoods where gnomes form their close-knit communities. Louder sounds punctuate the hum: a crunch of grinding gears here, a minor explosion there, a yelp of surprise or triumph, and especially bursts of laughter. Gnomes take delight in life, enjoying every moment of invention, exploration, investigation, creation, and play.",
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
				"title": "Natural Illusionist",
				"levelMinimum": 1,
				"text": "You know the minor illusion cantrip. Intelligence is your spellcasting ability for it."
			},
			{
				"title": "Speak with Small Beasts",
				"levelMinimum": 1,
				"text": "Through sounds and gestures, you can communicate simple ideas with Small or smaller beasts. Forest gnomes love animals and often keep squirrels, badgers, rabbits, moles, woodpeckers, and other creatures as beloved pets."
			}
		],
		"speed": 25,
		"sizes": ["small"],
		"languages": {
			"amount": 1,
			"defaults": [ dndDb.language.findOne({ "name": "Common" }) ],
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
		"name": "Human 2",
		"source": sourceEXP2,
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
		"name": "Human 3",
		"source": sourceEXP2,
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

let classes = [
	{
		"name": "Barbarian",
		"source": sourceSRD,
		"description": "A tall human tribesman strides through a blizzard, draped in fur and hefting his axe. He laughs as he charges toward the frost giant who dared poach his people's elk herd.",
		"hitDice": { "amount": 1, "sides": 12 },
		"subclasses": ["Berserker", "Totem Warrior", "Ancestral Guardian", "Storm Herald", "Zealot"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": {
			"amount": 2,
			"defaults": ["strength", "constitution"]
		},
		"tools": {
			"amount": 0,
			"defaults": []
		},
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
		"name": "Rogue (altered)",
		"source": sourceEXP2,
		"description": "Signaling for her companions to wait, a halfling creeps forward through the dungeon hall. She presses an ear to the door, then pulls out a set of tools and picks the lock in the blink of an eye.",
		"hitDice": { "amount": 1, "sides": 8 },
		"subclasses": ["Thief", "Assassin", "Arcane Trickster", "Mastermind", "Swashbuckler"],
		"skills": {
			"amount": 4,
			"defaults": []
		},
		"saves": {
			"amount": 2,
			"defaults": ["dexterity", "intelligence"]
		},
		"tools": {
			"amount": 1,
			"defaults": [dndDb.tool.findOne({ "name": "Thieves' tools", "source": sourceSRD })]
		},
		"features": [
			{
				"title": "Expertise",
				"levelMinimum": 1,
				"text": "At 1st level, choose two of your skill proficiencies, or one of your skill proficiencies and your proficiency with thieves' tools. Your proficiency bonus is doubled for any ability check you make that uses either of the chosen proficiencies."
			},
			{
				"title": "Sneak Attack",
				"levelMinimum": 1,
				"text": "Beginning at 1st level, you know how to strike subtly and exploit a foe's distraction. Once per turn, you can deal an extra 1d6 damage to one creature you hit with an attack if you have advantage on the attack roll. The attack must use a finesse or a ranged weapon. ..."
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
		"source": sourceEXP1,
		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod, nisl eget aliquam ultricies, nunc nisl aliquet nunc, vitae aliquam ni",
		"hitDice": { "amount": 1, "sides": 6 },
		"subclasses": ["Abjuration", "Conjuration", "Divination", "Enchantment", "Evocation", "Illusion", "Necromancy", "Transmutation"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": {
			"amount": 2,
			"defaults": ["intelligence", "wisdom"]
		},
		"tools": {
			"amount": 0,
			"defaults": []
		},
		"features": [
			{
				"title": "Spellcasting",
				"levelMinimum": 1,
				"text": "As a student of arcane magic, you have a spellbook containing spells that show the first glimmerings of your true power."
			},
			{
				"title": "Arcane Recovery",
				"levelMinimum": 1,
				"text": "Je to fakt super."
			},
			{
				"title": "Spell Mastery",
				"levelMinimum": 18,
				"text": "Wait for it..."
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
			]
		}
	},
	{
		"name": "Barbar",
		"source": sourceEXP1,
		"description": "A fierce warrior of primitive background who can enter a battle rage",
		"hitDice": { "amount": 1, "sides": 12 },
		"subclasses": ["Berserker", "Totem Warrior", "Ancestral Guardian", "Storm Herald", "Zealot"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": {
			"amount": 2,
			"defaults": ["strength", "constitution"]
		},
		"tools": {
			"amount": 3,
			"defaults": [
				dndDb.tool.findOne({ "name": "Alchemist's supplies" }),
				dndDb.tool.findOne({ "name": "Brewer's supplies" }),
			]
		},
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
		"source": sourceEXP2,
		"description": "A wielder of magic that is derived from a bargain with an extraplanar entity",
		"hitDice": { "amount": 1, "sides": 8 },
		"subclasses": ["The Archfey", "The Fiend", "The Great Old One", "The Celestial", "The Hexblade"],
		"skills": {
			"amount": 2,
			"defaults": []
		},
		"saves": {
			"amount": 2,
			"defaults": ["wisdom", "charisma"]
		},
		"tools": {
			"amount": 0,
			"defaults": []
		},
		"features": [
			{
				"title": "Otherworldly Patron",
				"levelMinimum": 1,
				"text": "At 1st level, you have struck a bargain with an otherworldly being of your choice: the Archfey, the Fiend, or the Great Old One, each of which is detailed at the end of the class description"
			}
		],
		"spellcasting": {
			"ability": "charisma",
			"slots": [
				[1, 0, 0, 0, 0, 0, 0, 0, 0],
				[2, 0, 0, 0, 0, 0, 0, 0, 0],
			]
		}
	}
]
dndDb.dndClass.insertMany(classes);
print(dndDb.dndClass.countDocuments() + " classes inserted");

let armor = [
	{
		"name": "Leather Armor",
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
		"type": "heavy",
		"baseArmorClass": 16,
		"strengthRequirement": 13,
		"stealthDisadvantage": true,
		"bonuses": [],
		"description": "Heavy and noisy armor"
	},
	{
		"name": "Plate Armor",
		"source": sourceSRD,
		"type": "heavy",
		"baseArmorClass": 18,
		"strengthRequirement": 15,
		"stealthDisadvantage": true,
		"bonuses": [],
		"description": "Heavy and noisy armor"
	},
	{
		"name": "Test Armor",
		"source": sourceEXP1,
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
		"name": "Another Armor",
		"source": sourceEXP2,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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

let spells = [
	{
		"name": "Acid Splash",
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
		"source": sourceSRD,
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
]
dndDb.spell.insertMany(spells);
print(dndDb.spell.countDocuments() + " spells inserted");

let characters = [
	{
		"character_name": "Test Character",
		"player_name": "Filip",
		"card_photo_url": "https://cdnb.artstation.com/p/assets/images/images/057/787/149/large/hyo-seung-jin-3.jpg",
		"sheet_photo_url": "https://cdnb.artstation.com/p/assets/images/images/057/787/149/large/hyo-seung-jin-3.jpg",
		"sources": [sourceSRD._id, sourceEXP1._id],
		"class": dndDb.dndClass.findOne({ "name": "Barbarian" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Half-Elf" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Acolyte" }, { "_id": 1 })._id,
		"subclass": "Totem Warrior",
		"level": 3,
		"ability_scores": {
			"strength": { "score": 16, "upByOne": false, "upByTwo": true },
			"dexterity": { "score": 9, "upByOne": true, "upByTwo": false },
			"constitution": { "score": 16, "upByOne": false, "upByTwo": false },
			"intelligence": { "score": 11, "upByOne": false, "upByTwo": false },
			"wisdom": { "score": 12, "upByOne": false, "upByTwo": false },
			"charisma": { "score": 8, "upByOne": false, "upByTwo": false },
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
		"armor": dndDb.armor.findOne({ "name": "Chain Mail", "source": sourceSRD }),
	},
	{
		"character_name": "Cool elfka",
		"player_name": "Nekdo",
		"card_photo_url": "https://i.etsystatic.com/40173929/r/il/5f64a8/4498858790/il_fullxfull.4498858790_8b00.jpg",
		"sheet_photo_url": "https://i.etsystatic.com/40173929/r/il/5f64a8/4498858790/il_fullxfull.4498858790_8b00.jpg",
		"sources": [sourceSRD._id],
		"class": dndDb.dndClass.findOne({ "name": "Rogue (altered)" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Human (test)" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Acolyte" }, { "_id": 1 })._id,
		"level": 11,
		"subclass": "Thief",
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
		"armor": dndDb.armor.findOne({ "name": "Chain Shirt", "source": sourceSRD }),
	},
	{
		"character_name": "Gnomey",
		"player_name": "Nekdo",
		"card_photo_url": "https://storage.googleapis.com/pai-images/eb810c78614f4d748669134b6859a06c.jpeg",
		"sheet_photo_url": "https://storage.googleapis.com/pai-images/eb810c78614f4d748669134b6859a06c.jpeg",
		"sources": [sourceSRD._id, sourceEXP2._id],
		"class": dndDb.dndClass.findOne({ "name": "Wizard" }, { "_id": 1 })._id,
		"race": dndDb.race.findOne({ "name": "Gnome (Forest)" }, { "_id": 1 })._id,
		"background": dndDb.background.findOne({ "name": "Charlatan" }, { "_id": 1 })._id,
		"subclass": "Abjuration",
		"level": 2,
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
		"armor": dndDb.armor.findOne({ "name": "Leather Armor", "source": sourceSRD }),
	}
];
dndDb.character.insertMany(characters);
print(dndDb.character.countDocuments() + " characters inserted");

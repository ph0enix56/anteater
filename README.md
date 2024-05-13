# Anteater

Anteater is the backend server for a Dungeons & Dragons character creation app, created as a bachelor's thesis project at the Czech Technical University in Prague. It provides a REST API for managing characters and getting content for character creation. The content, such as class, race and background options, or weapons and armor, can be defined in JSON files and loaded into the app using an import utility included in the project. The app is built with Spring Boot and uses MongoDB for data storage. The frontend for the app was created as a separate project, [manatee](https://github.com/zanetatrosk/manatee).

## Building and running the app

<details>
<summary>Using Docker (start here)</summary>

### First time setup

1. Install Docker including Docker Compose (for example using [Docker Desktop](https://www.docker.com/products/docker-desktop/)).
2. Download or clone the repository and navigate to the project root.
3. Run `docker compose up` to download all the required images (including an embedded database), build the app and start it (this may take a while the first time).
4. Verify that the server is running on port 8080 (for example by visiting `http://localhost:8080/api/sources` in a browser and checking that JSON data is returned).
5. To stop the app, run `docker compose down`.

### Using the app

- The server can be restarted with `docker compose up app` (or `docker compose up app -d` to be able to close the terminal) and stopped with `docker compose down`.
- If you want to update the content of the app, put your JSON files in the `data` directory (see [Adding content](#adding-content)) and run `docker compose up import` to import the data into the app (expect brief app downtime as the data store is reloaded).
- The `data` directory can be moved or renamed as needed as long as you update the path in the `.env` file in the project root.

</details>

<details>
<summary>Manually</summary>

### Prerequisites

- Java Development Kit (JDK) version 17 or newer for the server
- Node.js version 16 or newer for the import utility
- available MongoDB instance (for example a [free tier on MongoDB Atlas cloud](https://www.mongodb.com/cloud/atlas), or a [local instance](https://www.mongodb.com/try/download/community).)
- the repository cloned or downloaded

### Running the server

1. Get the MongoDB connection string for your instance and set it as an environment variable `MONGODB_URI` (alternatively, replace the value in `src/main/resources/application.properties`).
2. Navigate to the project root and run `./gradlew build` to get a Java executable `anteater.jar` in the `build/libs` directory that can be run with `java -jar build/libs/anteater.jar`. (alternatively, run `./gradlew bootRun` to start the app directly in development mode).

### Running the import utility

1. Navigate to the `db` directory and run `npm install` to install the required packages.
2. Open `insert.js` and set the `DATA_DIR`, `MONGODB_URI` and `DB_NAME` variables to match your setup (`MONGODB_URI` value can be kept if you set it as an environment variable above).
3. Run `node insert.js` to import the data from the JSON files into the database.

</details>

## Adding content

As described above, the content used in the app can be imported from JSON files. The items have to be defined in the `data` directory with the following rules:

- Firstly, define your content sources in the `sources.json` file. Each defined source must have a unique `_id` (this will be used later) and a `name` field (also recommended to be unique, as it will be displayed in the app).
- Every item you define will come from one of these sources and you will be able to select which sources to include in the character creation process. These can be used to separate content by books, homebrew, campaign options, etc.
- The items themselves are separated into directories by type (e.g. `class` and `weapon`).
- Inside each type directory, there are JSON files that correspond to a source, using the `_id` from `sources.json` as the filename (for example, you can define a source with `_id` of `srd` and then create a file `srd.json` in the `race` directory to define race options from the SRD).
- The JSON files always are an array (using `[]`) of objects (using `{}`) that define the individual items. The structure of these objects is specific to each type and can be best understood by looking at the sample content provided.
- A feature of note are the objects you can use to reference an item from another (for example, a class may need to reference which weapons it is proficient with). These have a `ref` field that specifies the type of the referenced item and a `name` to field to specify its name.
- Note that currently references are supported only within the same source to avoid cyclic source dependencies.
- When you want to reload the data into the app, run the import utility as described above. This will make the app content match the JSON files.

### More info and tips

- For a better experience, use a JSON editor with syntax highlighting (like [VS Code](https://code.visualstudio.com/)) to avoid syntax errors, such as missing quotes, commas, or brackets.
- For enumerated fields like skills and abilities, enter the name lowercase and with spaces replaced by underscores (e.g. `animal_handling`). This can also be seen in the sample content.
- If you are familiar with JSON Schema, you can find the specific schema for each type in the `db/schema` directory from the project root.
- Unless you know what you are doing, don't enter the `_id` field manually for any items except sources (it is generated automatically by the database).
- You can avoid loading content from a source while keeping all the content files in their directories by simply removing the source entry from the `sources.json` file. Try this if you want to keep the experimental content samples but don't want them to be loaded into the app.
- Within each type and source combination, try to keep the `name` field unique to make the references unambiguous, as otherwise there is no guarantee which item will be referenced.
- Avoid removing or renaming content that is already in use by characters, as this can lead to them being unable to be loaded back into the app.
- The character type and the `character.json` file are special cases, as the characters are best managed through the app itself and not directly in the JSON files. The `character.json` file, however, uses a similar structure to the other content types. It is always updated at the start of the import process and can be useful for safekeeping or moving characters between instances of the app.

### Troubleshooting

- You can see the logs of the import utility in the terminal to see if there are any errors in the JSON files. The program will skip items with invalid references, missing or invalid fields, but it will not import the whole file if there is a syntax error.
- If a character fails to load after an import and you want to restore it, DO NOT rerun the import utility immediately (as it will overwrite the broken character), but follow these steps:
  1. Stop the app.
  2. Check the logs for the reason, this will most likely be a reference to an item that was removed or renamed.
  3. Either re-add the missing item with the same name and source, or go to the `character.json` file and fix the mentioned reference (note that classes, race, and backgrounds must be changed to a new valid one, while others can be simply removed).
  4. Run the `docker volume rm anteater_mongodb_data` command (or drop the character collection in your MongoDB instance). This will force the import to force reload the data from the JSON files.
  5. Run the import utility again and start the app. Note that this reverts all changes made to the characters since the last import.
- Removing the data volume and re-running the import is also useful if the data becomes corrupted making the app unable to load content or characters.
- If you wish to also erase all characters, replace the `character.json` file with an empty array `[]` before running the import utility.

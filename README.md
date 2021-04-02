# Google Task Converter
A simple tool to extract separate task lists from Google Backup .json file. The extracted list can be used to move to another to-do app by copy-paste. Confirmed to work on Todoist.
## Google Takeout
Go to [https://takeout.google.com/settings/takeout](https://takeout.google.com/settings/takeout) and select Google Tasks from the list, and output format as JSON. After some time You will receive an email from Google with archive, which will include backup of your task list.

## Usage
Put downloaded .jar file in the same directory as extracted Google Task backup file which has to be renamed to `Tasks.json`. Run with:
```
java -jar .\GoogleTaskConverter.jar
```
From each Google Task list, there will be created a separate .txt file with one task per line. Completed, hidden, deleted and empty tasks are skipped by default.

## Download
[Download here](https://github.com/LukaszLapaj/GoogleTaskConverter/releases)

## To do
- [ ] Semantic version naming in CI/CD

## Requirements
- Java 14+
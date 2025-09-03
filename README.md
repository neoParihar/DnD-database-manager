# DnD-database-manager
A database that keeps track of your current campaigns, locations, items, equipment and players.

## Usage
Compile all java files to ~/out
```
javac -d out $(find shared server client -name "*.java")
```
Run server in one terminal and client in another terminal.
```
java -cp out server.Server
java -cp out client.Client
```

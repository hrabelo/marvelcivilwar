# Marvel's Civil War!

A RESTful API designed to represent the epic battle between #teamCaptain and #teamIronMan.

### Running

marvel-civil-war API requires [Leningen](https://github.com/technomancy/leiningen) v2+ to run.

To start a web server for the application, run:
```sh
$ cd marvel-civil-war
$ lein ring server
```
### Operating

The first thing we need to do is to create a new arena for the war to take place.

```
POST /api/createarena
````
The response should be:
```
{
    "errorcode": 0,
    "description": "Arena successfully created!",
    "id":0,
    "type":0,
    "xpos":0,
    "ypos":0,
    "direction":0
}
````

Now we have to place some players in the stage. There's an example:

```
POST /api/createplayer
request body:
{
	"type":1,
	"xposition":15,
	"yposition":22,
	"direction":1
}
````
Where:
* type = "1" for #teamCaptain and "2" for #teamIronMan;
* xposition = from "0" to "50" (grid limit)
* yposition = from "0" to "50" (grid limit)
* direction = "1" for north, "2" for south, "3" for east and "4" for west

The response should be:
```
{
    "errorcode": 0,
    "description": "",
    "id":"234e2a78-8c7b-4d24-9fa2-477a06dd2ec8",
    "type":1,
    "xpos":15,
    "ypos":22,
    "direction":1
}
````

To operate a given player, we must do as the following example:

```
POST /api/operateplayer
request body:
{
	"id":"234e2a78-8c7b-4d24-9fa2-477a06dd2ec8",
	"instruction":1
}
````
Where:
* id = the id of the created player
* instruction = "1" move north, "2" move south, "3" move east, "4" move west, "5" attack;

P.S: #teamIronMan players cannot move... yet.

Have fun!

   [Leinigen]:<https://github.com/technomancy/leiningen>

# KickerApp Documentation
## How to test the web application (Windows)
1. (optional) Import build.gradle in IntelliJ
2. Run gradlew.bat
3. Once started, application is ready at http://127.0.0.1:8080/ or an external address (see console output)


## Known issues & Tips
#### Error "Unknown provider: translateFilterProvider <- translateFilter"
Even though translation / internationalization was disabled when creating the app with JHipster, the entity generator creates html files (i.e. __\<name_of_entity\>s.html__) which use a "translate" property. This resulted in the error above when opening the entity page. By deleting the translate part in the following expression in players.html solved the problem (found by searching for "translate" in the newly generated files:
    
    old:
    placeholder="{{ 'kickerappApp.player.home.search' | translate }}"
    new:
    placeholder="{{ 'kickerappApp.player.home.search' }}"

#### Dependency Injection with Kotlin
Following tutorials on the internet I was not able to get DI working with Kotlin. Neither constructor based, nor field injections seem do to the trick. A (to my knowledge) completely identical Java file works just fine. Using __lateinit__ resulted in an exception at runtime, every other approach just provides fields pointing to __null__ (for reference, I tried to inject an HttpSession).

#### Kotlin classes need to be "open"
In order to use the Spring with Kotlin, I needed to declare my __@RestController__ in Kotlin as __open__ (was not mentioned in the [Kotlin tutorials])

#### Update existing Entities
In order to update an existing entity created with 

    yo jhipster:entity <some_entity_name>

it is possible to simply modify the created __<some_entity_name>.json__ in the folder __.\jhipster__. Afterwards a repeated execution of the command will update all necessary sources (overwriting files in the process, use of --force is possible))

## Done

- create entity __player__ (name [String], wins[Integer], losses[Integer], ties[Integer], mmr[Integer], avg_duration[Float]; user[relationship, n-to-1(user)], games_as_team1_player1, games_as_team1_player2, games_as_team2_player1, games_as_team2_player2[relationship, 1-to-n(game)])
- create entity __game__ (team1_score [Integer], team2_score[Integer], duration[Float]; team1_player1, team1_player2, team2_player1, team2_player2  [relationship, n-to-1 (player)])

## To Do (no priority ordering)

- evaluate if the established relationships are indeed bidirectional (does adding a game add a reference for the participating player?)
- entity tournament (max score,?)
- player creation UI for non-admin (limited options, admin user creation via entity menu or also separate)
- game creation UI for non-admin (not the entity default, no duplicate player)



[Kotlin tutorials]: https://kotlinlang.org/docs/tutorials/spring-boot-restful.html


# Official JHipster Documentation

## KickerApp

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Grunt][] as our build system. Install the grunt command-line tool globally with:

    npm install -g grunt-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./gradlew
    grunt

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

## Building for production

To optimize the KickerApp client for production, run:

    ./gradlew -Pprod clean bootRepackage

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar build/libs/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript` and can be run with:

    grunt test



## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `KickerApp`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/KickerApp.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Gradle script / Use Gradle Wrapper / Tasks: `-Pprod clean test bootRepackage`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/

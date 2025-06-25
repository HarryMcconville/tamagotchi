# ReadMe Before Use
## Instructions for using application
1. Clone project into local repository `git clone https://github.com/HarryMcconville/tamagotchi.git`.
2. Open the cloned repository in IDE (IntelliJ).
3. In `src/main/resources` add a file called `application.yml`. Contact developers for the information that needs to be pasted here
4. Inside IDE terminal enter `createdb tamagotchi_development` & `createdb tamagotchi_test`
4. Run project from IDE terminal with `mvn spring-boot:run`.
5. Open browser and direct to `http://localhost:8080/`.

_If the running of the project fails, or the web page doesn't load, please contact one of the developers._

## Expected functionality
* User directed to log in or sign up on first use.
* After sign up / log in, user directed to `adopt a pet` page.
  * Here, user inputs their name and their pets name
  * Clicking `START` saves user and pet to database and redirects to the `play` page
* On the play page, a cat sits in living room
* User can click buttons to "interact" with their pet (pop up appears)
  * User can feed pet
  * User can water pet
  * User can pet pet
  * User can play with pet
* Disowning (shooing) a pet doesn't do anything yet
* Clicking `HELP` brings out a side bar with instructions

## Troubleshooting
* If you are running into issues related to the version of Java go to Pom.xml and edit the following sections to change the value of `XX` to your current version:
  * Find this line in the properties section: `<java.version>XX</java.version>`
  * Find these lines in the plugins section under `Maven Compiler`
    *  `<source>XX</source>`
    *  `<target>XX</target>`
  * if you are struggling to even launch the project with `mvn spring-boot:run` then try invalidating caches:
    * `File` > `Invalidate Caches` > `Invalidate and restart`
  * If you get an error stating Maven is either not present or using the wrong version:
    * Run `mvn wrapper:wrapper` in the terminal then try running `mvn spring-boot:run` again



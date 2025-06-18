# ReadMe Before Use
## Instructions for using application
1. Clone project into local repository `git clone https://github.com/HarryMcconville/tamagotchi.git`.
2. Open the cloned repository in IDE (IntelliJ).
3. In `src/main/resources` add a file called `application.yml`. Contact developers for the information that needs to be pasted here
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



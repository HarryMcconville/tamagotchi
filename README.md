# ReadMe Before Use

A cosy game where users can choose and name a pet to look after. The user must keep the pet happy by feeding, hydrating, entertaining,
and brushing it. When the user runs out of resources to fulfill its cat's needs, they can head to the village to replenish their resources.
**If a cat's happiness reaches 0%, they will relocate to another home, and the user will have to adopt a new pet. Happiness will show as 0%
in the memories tab.**

## Instructions for using application
1. Clone project into local repository `git clone https://github.com/HarryMcconville/tamagotchi.git`.
2. Open the cloned repository in IDE (IntelliJ).
3. In `src/main/resources` add a file called `application.yml`. Contact developers for the information that needs to be pasted here
4. Inside the IDE terminal enter `createdb tamagotchi_development` & `createdb tamagotchi_test`
5. Run project from IDE terminal with `mvn spring-boot:run`. 
6. Open browser and direct to `http://localhost:8080/`.

## Troubleshooting
* If you are running into issues related to the version of Java (e.g. `version XX not supported`):
  * Go to `pom.xml` and edit the following sections to change the value of `XX` to your current version:
    * Find and edit this line in the properties section: `<java.version>XX</java.version>`
    * Find and edit these lines in the plugins section under `Maven Compiler`:
      *  `<source>XX</source>`
      *  `<target>XX</target>`
* If you are struggling to even launch the project with `mvn spring-boot:run` then try invalidating caches:
  * `File` > `Invalidate Caches` > `Invalidate and restart`.
* If you get an error stating Maven is either not present or using the wrong version:
  * Run `mvn wrapper:wrapper` in the terminal then try running `mvn spring-boot:run` again.
* If you are running into issues relating to migrations, databases, or flyway:
  * Direct to the `m` for `maven` at the top right hand of IntelliJ (or wherever it is in your IDE):
    * Go to `plugins`, `flyway`, and `clean flyway`.
  * If this doesn't work, delete the `tamagotchi/target` directory and rerun.

_If the running of the project fails, or the web page doesn't load despite the troubleshooting, please contact one of the developers._


## Expected functionality

#### Log in / Sign up ####
* User directed to log in or sign up on first use.
* After sign up / log in, user directed to `adopt a pet` page.
  * Here, user inputs their name, their pet's name, and chooses a pet image.
  * Clicking `START` saves user and pet to database and redirects to the `play` page. A pop up appears with **traits** of the cat.

#### Play / Home page ####
* On the **Play** page, a cat sits in living room, surrounded by objects, and with a **thought bubble** displaying its current mood.
  * There is a sidebar with the current **status** of the cat as well as amount of **resources** in inventory 
    * Clicking `HELP` brings out a side bar with instructions.
    * Clicking `View memories` shows a collection of the user's previous pets (or nothing, if the user has not had any previous pets)
    * Hovering over the pet's name in the sidebar shows the **traits** of the pet.
  * User can click buttons to "interact" with their pet.
      * User can **feed** pet by clicking the `food bowl`.
        * This replenishes **hunger** bar slightly, replenishes **happiness** slightly, and decreases **cat food** by one.
      * User can **hydrate** pet by clicking the `milk bowl`.
        * This replenishes **thirst** bar slightly, replenishes **happiness** slightly, and decreases **milk** by one.
      * User can **pet** pet by clicking the `pet`.
        * This replenishes **social** bar slightly, replenishes **happiness** slightly, and decreases **brush** by one.
      * User can **play** with pet by clicking the `catnip`.
        * This replenishes **fun** bar slightly, replenishes **happiness** slightly, and decreases **catnip** by one.
  * Clicking `Shoo Away` redirects to another page, where user is given the option to discard pet or go back to play.
  * Clicking `Log out` redirects to log out confirmation.
  * Clicking the `Village` icon redirects to the village page

#### Village Page ####
* On the **Village** page, a small hamlet is displayed and a sidebar.
  * The sidebar displays random quotes from the villagers (to add lore to the game), as well as the amount of **resources** in your **inventory**.
    * Clicking `HELP` brings out a side bar with instructions. 
    * Clicking `Go Home` redirects to the **Play** page.
  * There are 4 clickable icons on the village page:
    * User can **collect milk** by clicking on the `milk well`.
      * The amount of milk available displays on hover, this decreases as user collects milk.
      * User must wait for milk to replenish once value hits 0.
    * User can **collect cat food** by clicking on the `cat food tree`.
      * The amount of cat food available displays on hover, this decreases as user collects cat food.
      * User must wait for cat food to replenish once value hits 0.    
    * User can **collect brushes** by clicking on the `brushes r us store`.
      * The amount of brushes available displays on hover, this decreases as user collects brushes.
      * User must wait for brushes to replenish once value hits 0.
    * User can **collect catnip** by clicking on the `catnip greenhouse`.
      * The amount of catnip available displays on hover, this decreases as user collects catnip.
      * User must wait for catnip to replenish once value hits 0.




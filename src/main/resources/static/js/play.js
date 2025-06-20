// This is for JS scripts for the /play endpoint
// i.e. updating status bars


let currentFun, currentHunger, currentSocial, currentThirst;

// js for bars
//document.addEventListener("DOMContentLoaded", function () {
//    const bar = document.getElementById("hunger-bar");
//    const hungerText = document.getElementById("hunger-text");
//    const bar = document.getElementById("thirst-bar");
//    const thirstText = document.getElementById("thirst-text");
//
//    // Get initial hunger from data attribute
////    let currentHunger = parseInt(window.getComputedStyle(bar).width) || 100;
//
//
//    function decreaseHunger() {
//        if (currentHunger > 0) {
//            currentHunger--;
//            bar.style.width = currentHunger + "%";
//            hungerText.textContent = "Hunger: " + currentHunger + "%";
//        }
//    }
//
//    setInterval(decreaseHunger, 10000); // Every 10 seconds
//});

document.addEventListener("DOMContentLoaded", function () {
    const bar = document.getElementById("hunger-bar");
    const bar = document.getElementById("social-bar");
    const bar = document.getElementById("thirst-bar");
    const bar = document.getElementById("fun-bar");
    const bar = document.getElementById("happiness-bar");

    const hungerText = document.getElementById("hunger-text");
    const socialText = document.getElementById("social-text");
    const thirstText = document.getElementById("thirst-text");
    const funText = document.getElementById("fun-text");
    const happinessText = document.getElementById("happiness-text");

    let currentHunger = parseInt(hungerBar.style.width) || 100;
    let currentSocial = parseInt(socialBar.style.width) || 100;
    let currentThirst = parseInt(thirstBar.style.width) || 100;
    let currentFun = parseInt(funBar.style.width) || 100;

    setInterval(() => {
        if (currentHunger > 0) currentHunger--;
        hungerBar.style.width = currentHunger + "%";
        hungerText.textContent = "Hunger: " + currentHunger + "%";
    }, 10000);

    setInterval(() => {
        if (currentSocial > 0) currentSocial--;
        socialBar.style.width = currentSocial + "%";
        socialText.textContent = "Social: " + currentSocial + "%";
    }, 10000);

    setInterval(() => {
        if (currentThirst > 0) currentThirst--;
        thirstBar.style.width = currentThirst + "%";
        thirstText.textContent = "Thirst: " + currentThirst + "%";
    }, 20000);

    setInterval(() => {
        if (currentFun > 0) currentFun--;
        funBar.style.width = currentFun + "%";
        funText.textContent = "Fun: " + currentFun + "%";
    }, 10000);

    // Recalculate happiness dynamically
    setInterval(() => {
        const currentHappiness = Math.round((currentFun + currentHunger + currentThirst + currentSocial) / 4);
        happinessBar.style.width = currentHappiness + "%";
        happinessText.textContent = "Happiness: " + currentHappiness + "%";
    }, 1000);
});

//// THIRST JS STUFF
//document.addEventListener("DOMContentLoaded", function () {
//    const bar = document.getElementById("thirst-bar");
//    const thirstText = document.getElementById("thirst-text");
//
//    // Get initial thirst from data attribute
////    let currentThirst = parseInt(window.getComputedStyle(bar).width) || 100;
//
//
//    function decreaseThirst() {
//        if (currentThirst > 0) {
//            currentThirst--;
//            bar.style.width = currentThirst + "%";
//            thirstText.textContent = "Thirst: " + currentThirst + "%";
//        }
//    }
//
//    setInterval(decreaseThirst, 20000); // Every 10 seconds
//});
//
//document.addEventListener("DOMContentLoaded", function () {
//    const bar = document.getElementById("social-bar");
//    const socialText = document.getElementById("social-text");
//
//    // Get initial social from data attribute
////    let currentSocial = parseInt(window.getComputedStyle(bar).width) || 100;
//
//
//    function decreaseSocial() {
//        if (currentSocial > 0) {
//            currentSocial--;
//            bar.style.width = currentSocial + "%";
//            socialText.textContent = "Social: " + currentSocial + "%";
//        }
//    }
//
//    setInterval(decreaseSocial, 10000); // Every 10 seconds
//});
//
//document.addEventListener("DOMContentLoaded", function () {
//    const bar = document.getElementById("fun-bar");
//    const funText = document.getElementById("fun-text");
//
//    // Get initial fun from data attribute
////    let currentFun = parseInt(window.getComputedStyle(bar).width) || 100;
//
//
//    function decreaseFun() {
//        if (currentFun > 0) {
//            currentFun--;
//            bar.style.width = currentFun + "%";
//            funText.textContent = "Fun: " + currentFun + "%";
//        }
//    }
//
//    setInterval(decreaseFun, 10000); // Every 10 seconds
//});
//
//// happiness bar
//document.addEventListener("DOMContentLoaded", function () {
//    const bar = document.getElementById("happiness-bar");
//    const happinessText = document.getElementById("happiness-text");
//
//    // Get initial happiness from data attribute
//    let currentHappiness = parseInt(window.getComputedStyle(bar).width) || 100;
//
//
//    function calculateHappiness() {
//        currentHappiness = ((currentFun + currentHunger + currentSocial + currentThirst)/4)
//        bar.style.width = currentHappiness + "%";
//        happinessText.textContent = "Happiness: " + currentHappiness + "%";
//        }
//    }
//
//    setInterval(calculateHappiness, 1000);
//
//});

// for new pet modal on entering play screen
document.addEventListener('DOMContentLoaded', function () {
  if (document.getElementById('newPetModal')) {
    var newPetModal = new bootstrap.Modal(document.getElementById('newPetModal'));
    newPetModal.show();
  }
});
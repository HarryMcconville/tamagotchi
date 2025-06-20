// This is for JS scripts for the /play endpoint
// i.e. updating status bars

// js for hunger bar

document.addEventListener("DOMContentLoaded", function () {
    const bar = document.getElementById("hunger-bar");
    const hungerText = document.getElementById("hunger-text");

    // Get initial hunger from data attribute
    let currentHunger = parseInt(window.getComputedStyle(bar).width) || 100;


    function decreaseHunger() {
        if (currentHunger > 0) {
            currentHunger--;
            bar.style.width = currentHunger + "%";
            hungerText.textContent = "Hunger: " + currentHunger + "%";
        }
    }

    setInterval(decreaseHunger, 10000); // Every 10 seconds
});

// THIRST JS STUFF
document.addEventListener("DOMContentLoaded", function () {
    const bar = document.getElementById("thirst-bar");
    const thirstText = document.getElementById("thirst-text");

    // Get initial thirst from data attribute
    let currentThirst = parseInt(window.getComputedStyle(bar).width) || 100;


    function decreaseThirst() {
        if (currentThirst > 0) {
            currentThirst--;
            bar.style.width = currentThirst + "%";
            thirstText.textContent = "Thirst: " + currentThirst + "%";
        }
    }

    setInterval(decreaseThirst, 20000); // Every 10 seconds
});

document.addEventListener("DOMContentLoaded", function () {
    const bar = document.getElementById("social-bar");
    const socialText = document.getElementById("social-text");

    // Get initial social from data attribute
    let currentSocial = parseInt(window.getComputedStyle(bar).width) || 100;


    function decreaseSocial() {
        if (currentSocial > 0) {
            currentSocial--;
            bar.style.width = currentSocial + "%";
            socialText.textContent = "Social: " + currentSocial + "%";
        }
    }

    setInterval(decreaseSocial, 10000); // Every 10 seconds
});

document.addEventListener("DOMContentLoaded", function () {
    const bar = document.getElementById("fun-bar");
    const funText = document.getElementById("fun-text");

    // Get initial fun from data attribute
    let currentFun = parseInt(window.getComputedStyle(bar).width) || 100;


    function decreaseFun() {
        if (currentFun > 0) {
            currentFun--;
            bar.style.width = currentFun + "%";
            funText.textContent = "Fun: " + currentFun + "%";
        }
    }

    setInterval(decreaseFun, 10000); // Every 10 seconds
});

// happiness bar
document.addEventListener("DOMContentLoaded", function () {
    const bar = document.getElementById("happiness-bar");

    // Get initial happiness from data attribute
    let currentHappiness = parseInt(window.getComputedStyle(bar).width) || 100;


    function calculateHappiness() {
        currentHappiness = ((currentFun + currentHunger + currentSocial + currentThirst)/4)
        bar.style.width = currentHappiness + "%";
        }
    }

    setInterval(decreaseHappiness, 10000); // Every 10 seconds
});

// for new pet modal on entering play screen
document.addEventListener('DOMContentLoaded', function () {
  if (document.getElementById('newPetModal')) {
    var newPetModal = new bootstrap.Modal(document.getElementById('newPetModal'));
    newPetModal.show();
  }
});
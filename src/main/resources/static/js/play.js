// This is for JS scripts for the /play endpoint
// i.e. updating status bars

// NEW js ajax to link front end to db rather than be separate
document.addEventListener("DOMContentLoaded", function () {
  // Grab references to all bars and text
    const bars = {
        hunger: document.getElementById("hunger-bar"),
        thirst: document.getElementById("thirst-bar"),
        social: document.getElementById("social-bar"),
        fun: document.getElementById("fun-bar"),
        happiness: document.getElementById("happiness-bar")
    };

    const texts = {
        hunger: document.getElementById("hunger-text"),
        thirst: document.getElementById("thirst-text"),
        social: document.getElementById("social-text"),
        fun: document.getElementById("fun-text")
        happiness: document.getElementById("happiness-text")
    };

    // Updating the status bars
    function updateStatusBars(data) {
        for (const key in bars) {
            if (data[key] !== undefined) {
                const value = data[key];
                bars[key].style.width = value + "%";
                texts[key].textContent = `${key.charAt(0).toUpperCase() + key.slice(1)}: ${value}%`;
            }
        }
    }

    // this method is what will fetch the data from the db, defined in the Ajax controller route /api/status
    async function fetchStatus() {
        try {
            const response = await fetch("/api/status");
            if (!response.ok) throw new Error("Failed to fetch pet status");
            const data = await response.json();
            updateStatusBars(data);
        } catch (error) {
            console.error("Error fetching status:", error);
        }
    }

    // starting load from db
    fetchStatus();

    // and then this interval sets how often it will ping the backend for data updates.
    // currently set to every 5 seconds
    setInterval(fetchStatus, 5000);
});

//  // Read from data attributes
//  let currentHunger = parseInt(hungerText.dataset.hunger) || 100;
//  let currentThirst = parseInt(thirstText.dataset.thirst) || 100;
//  let currentSocial = parseInt(socialText.dataset.social) || 100;
//  let currentFun = parseInt(funText.dataset.fun) || 100;
//
//  // Set initial widths
//  hungerBar.style.width = currentHunger + "%";
//  thirstBar.style.width = currentThirst + "%";
//  socialBar.style.width = currentSocial + "%";
//  funBar.style.width = currentFun + "%";
//
//  hungerText.textContent = "Hunger: " + currentHunger + "%";
//  thirstText.textContent = "Thirst: " + currentThirst + "%";
//  socialText.textContent = "Social: " + currentSocial + "%";
//  funText.textContent = "Fun: " + currentFun + "%";
//
////
//  function decreaseBar(value, barElement, textElement, label) {
//    if (value > 0) value--;
//    barElement.style.width = value + "%";
//    textElement.textContent = label + ": " + value + "%";
//    return value;
//  }
//
// // setting intervals for the decrease of values
//  setInterval(() => currentHunger = decreaseBar(currentHunger, hungerBar, hungerText, "Hunger"), 10000);
//  setInterval(() => currentThirst = decreaseBar(currentThirst, thirstBar, thirstText, "Thirst"), 20000);
//  setInterval(() => currentSocial = decreaseBar(currentSocial, socialBar, socialText, "Social"), 10000);
//  setInterval(() => currentFun = decreaseBar(currentFun, funBar, funText, "Fun"), 10000);
//
//  // Happiness calculation based on other values
//  function updateHappiness() {
//    const average = Math.round((currentHunger + currentThirst + currentSocial + currentFun) / 4);
//    happinessBar.style.width = average + "%";
//    happinessText.textContent = "Happiness: " + average + "%";
//  }
//
//  updateHappiness();
//  setInterval(updateHappiness, 5000); // updates happiness at a rate quicker than others
//});


//<!-- Javascript to play purr sound before redirect  -->
function playPurrAndRedirect(event) {
    event.preventDefault(); // prevent default navigation

    const audio = document.getElementById('purr');
    audio.currentTime = 0;
    audio.play();

    // Redirect after short delay (adjust as needed)
    setTimeout(() => {
        window.location.href = "/play/pet";
    }, 2200);
}

//<!-- Javascript to play feed sound before redirect  -->
function playFeedAndRedirect(event) {
    event.preventDefault(); // prevent default navigation

    const audio = document.getElementById('feed');
    audio.currentTime = 0;
    audio.play();

    // Redirect after short delay (adjust as needed)
    setTimeout(() => {
        window.location.href = "/play/feed";
    }, 1500);
}

//<!-- Javascript to play Water sound before redirect  -->
function playWaterAndRedirect(event) {
    event.preventDefault(); // prevent default navigation

    const audio = document.getElementById('water');
    audio.currentTime = 0;
    audio.play();

    // Redirect after short delay (adjust as needed)
    setTimeout(() => {
        window.location.href = "/play/water";
    }, 1500);
}

//<!-- Javascript to play game sound before redirect  -->
function playGameAndRedirect(event) {
    event.preventDefault(); // prevent default navigation

    const audio = document.getElementById('game');
    audio.currentTime = 0;
    audio.play();

    // Redirect after short delay (adjust as needed)
    setTimeout(() => {
        window.location.href = "/play/game";
    }, 1500);
}

// for new pet modal on entering play screen
document.addEventListener('DOMContentLoaded', function () {
  if (document.getElementById('newPetModal')) {
    var newPetModal = new bootstrap.Modal(document.getElementById('newPetModal'));
    newPetModal.show();
  }
});

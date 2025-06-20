// This is for JS scripts for the /play endpoint
// i.e. updating status bars

document.addEventListener("DOMContentLoaded", function () {
  // Grab references to all bars and text
  const hungerBar = document.getElementById("hunger-bar");
  const thirstBar = document.getElementById("thirst-bar");
  const socialBar = document.getElementById("social-bar");
  const funBar = document.getElementById("fun-bar");
  const happinessBar = document.getElementById("happiness-bar");

  const hungerText = document.getElementById("hunger-text");
  const thirstText = document.getElementById("thirst-text");
  const socialText = document.getElementById("social-text");
  const funText = document.getElementById("fun-text");
  const happinessText = document.getElementById("happiness-text");

  // Read from data attributes
  let currentHunger = parseInt(hungerText.dataset.hunger) || 100;
  let currentThirst = parseInt(thirstText.dataset.thirst) || 100;
  let currentSocial = parseInt(socialText.dataset.social) || 100;
  let currentFun = parseInt(funText.dataset.fun) || 100;

  // Set initial widths
  hungerBar.style.width = currentHunger + "%";
  thirstBar.style.width = currentThirst + "%";
  socialBar.style.width = currentSocial + "%";
  funBar.style.width = currentFun + "%";

  hungerText.textContent = "Hunger: " + currentHunger + "%";
  thirstText.textContent = "Thirst: " + currentThirst + "%";
  socialText.textContent = "Social: " + currentSocial + "%";
  funText.textContent = "Fun: " + currentFun + "%";

//
  function decreaseBar(value, barElement, textElement, label) {
    if (value > 0) value--;
    barElement.style.width = value + "%";
    textElement.textContent = label + ": " + value + "%";
    return value;
  }

 // setting intervals for the decrease of values
  setInterval(() => currentHunger = decreaseBar(currentHunger, hungerBar, hungerText, "Hunger"), 10000);
  setInterval(() => currentThirst = decreaseBar(currentThirst, thirstBar, thirstText, "Thirst"), 20000);
  setInterval(() => currentSocial = decreaseBar(currentSocial, socialBar, socialText, "Social"), 10000);
  setInterval(() => currentFun = decreaseBar(currentFun, funBar, funText, "Fun"), 10000);

  // Happiness calculation based on other values
  function updateHappiness() {
    const average = Math.round((currentHunger + currentThirst + currentSocial + currentFun) / 4);
    happinessBar.style.width = average + "%";
    happinessText.textContent = "Happiness: " + average + "%";
  }

  updateHappiness();
  setInterval(updateHappiness, 5000); // updates happiness at a rate quicker than others
});


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

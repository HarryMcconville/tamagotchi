// This is for JS scripts for the /play endpoint
// i.e. updating status bars

// The required csrf tokens to get past spring security
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");
// JS FOR STATUS BARS
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
        fun: document.getElementById("fun-text"),
        happiness: document.getElementById("happiness-text")
    };

    // Updating the status bars
    function updateStatusBars(data) {
        for (const key in bars) {
            if (data[key] !== undefined) {
                const value = data[key];
                const bar = bars[key];
                const catImage = document.getElementById("cat-image");
                const imageName = catImage.dataset.imageName; // e.g. "yellow-cat.png"

                // Update width
                bar.style.width = value + "%";

                // Remove old color classes
                bar.classList.remove("bar-green", "bar-orange", "bar-red");

                // Add new color class based on value
                if (value > 50) {
                    bar.classList.add("bar-green");
                } else if (value > 20) {
                    bar.classList.add("bar-orange");
                } else {
                    bar.classList.add("bar-red");
                }

                // Update text
                texts[key].textContent = `${key.charAt(0).toUpperCase() + key.slice(1)}: ${value}%`;
                if (key === "happiness") {
                    currentHappiness = value; // Store the latest happiness

                    if (catImage) {
                            const folder = value < 60 ? "sadCats" : "cats";
                            catImage.src = `/images/${folder}/${imageName}`;
                        }
                }
            }
        }
    }


    // this method is what will fetch the data from the db, defined in the Ajax controller route /api/status
    async function fetchStatus() {
        try {
            const response = await fetch("/api/status");
            if (!response.ok) throw new Error("Failed to fetch pet status");
            const data = await response.json();

            // this checks happiness level and redirects if 0
            if (data.happiness === 0) {
                window.location.href = "/play/relocation";
                return;
            }

            updateStatusBars(data);
            updateThoughtBubble(data.happiness)
        } catch (error) {
            console.error("Error fetching status:", error);
        }
    }
    // getting bubble text to do with happiness
    const bubbleText = document.querySelector(".bubble-text");

    async function updateThoughtBubble(happiness) {
        try {
            const response = await fetch(`/api/status-message?happiness=${happiness}`);
            if (!response.ok) throw new Error("Failed to fetch message");
            const data = await response.json();
            bubbleText.textContent = data.message;
        } catch (err) {
            console.error("Couldn’t update thought bubble:", err);
        }
    }


    // starting load from db
    fetchStatus();


    // and then this interval sets how often it will ping the backend for data updates.
    // currently set to every 5 seconds
    setInterval(fetchStatus, 5000);
});

    // Shared function to handle interaction with pet (AJAX + sound + flash message)
    async function interact(endpoint, soundId) {
        const flashMessage = document.getElementById("flash-message");
        const audio = document.getElementById(soundId);
        if (audio) {
            audio.currentTime = 0;
            audio.play();
        }
    // making sure we are making a POST request and we are using the correct csrf credentials
        try {
            const response = await fetch(endpoint, {
                method: "POST",
                credentials: "include",
                headers: {
                [csrfHeader]: csrfToken
                }
            });

            if (response.ok) {
                const data = await response.json();

                if (flashMessage && data.message) {
                    flashMessage.textContent = data.message;
                    flashMessage.style.display = "block";

                    // hide flash message after 3 seconds
                    setTimeout(() => {
                        flashMessage.style.display = "none";
                    }, 3000);
                }

                fetchStatus(); // refresh bars immediately
            } else {
                console.error("Interaction failed with status:", response.status);
            }
        } catch (error) {
            console.error("Interaction error:", error);
        }
    }

    // Attach click events to buttons
    document.getElementById("feed-btn")?.addEventListener("click", () => interact("/play/feed", "feed"));
    document.getElementById("pet-btn")?.addEventListener("click", () => interact("/play/pet", "purr"));
    document.getElementById("water-btn")?.addEventListener("click", () => interact("/play/water", "water"));
    document.getElementById("game-btn")?.addEventListener("click", () => interact("/play/game", "game"));

// for new pet modal on entering play screen
document.addEventListener('DOMContentLoaded', function () {
  if (document.getElementById('newPetModal')) {
    var newPetModal = new bootstrap.Modal(document.getElementById('newPetModal'));
    newPetModal.show();
  }
});


// memories popup stuff
function openScrapbook() {
    fetch('/memories/fragment')
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const innerContent = doc.querySelector('body').innerHTML;

            document.getElementById('scrapbook-inner-content').innerHTML = innerContent;
            document.getElementById('scrapbook-overlay').style.display = 'flex';


            const cards = document.querySelectorAll('#scrapbook-inner-content .polaroid');
            cards.forEach(card => {
                const tilt = Math.random() < 0.5 ? 'tilt-left' : 'tilt-right';
                card.classList.add(tilt);
            });
        })
        .catch(err => {
            document.getElementById('scrapbook-inner-content').innerHTML = 'Failed to load memories.';
            console.error(err);
        });
}

function closeScrapbook() {
    document.getElementById('scrapbook-overlay').style.display = 'none';
}

// shoo away meow sound

function playMeowAndRedirect(event) {
    event.preventDefault();
    const audio = document.getElementById('meow');
    if (audio) {
        audio.currentTime = 0;
        audio.play();
        setTimeout(() => {
            window.location.href = "/play/confirm_shoo";
        }, 1500);
    }
}

// Attach the event listener after DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    const shooLink = document.getElementById('shoo-away-btn');
    if (shooLink) {
        shooLink.addEventListener('click', playMeowAndRedirect);
    }
});

// script for showing traits reminder
function showSidebarMessage() {
  document.getElementById("traits-reminder").style.display = "block";
}

function hideSidebarMessage() {
  document.getElementById("traits-reminder").style.display = "none";
}
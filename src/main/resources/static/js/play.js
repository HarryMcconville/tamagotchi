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

    const bubbleText = document.querySelector(".bubble-text");
    let currentHappiness = null;
    let currentHappinessTier = null;

    // Get current happiness tier (to generate the correct status message)
    function getHappinessTier(value) {
        if (value < 5) return 0;
        if (value < 15) return 1;
        if (value < 30) return 2;
        if (value < 45) return 3;
        if (value < 60) return 4;
        if (value < 75) return 5;
        if (value < 90) return 6;
        return 7;
    }

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
                    if (catImage && imageName) {
                        const folder = value < 60 ? "sadCats" : "cats";
                        catImage.src = `/images/${folder}/${imageName}`;
                    }
                    const newTier = getHappinessTier(value);
                    if (newTier !== currentHappinessTier) {
                        updateThoughtBubble(value);
                        currentHappinessTier = newTier;
                    }

                }
            }
        }
    }
    // updating village resources
    function updateResourcesUI(data) {
        document.getElementById("catfood-count").textContent = data.catFood ?? 0;
        document.getElementById("milk-count").textContent = data.milk ?? 0;
        document.getElementById("catnip-count").textContent = data.catnip ?? 0;
        document.getElementById("brush-count").textContent = data.brush ?? 0;
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
        } catch (error) {
            console.error("Error fetching status:", error);
        }
    }

    async function fetchResources() {
        try {
            const response = await fetch("/api/village_resources");
            if (!response.ok) throw new Error("Failed to fetch resources");
            const data = await response.json();
            updateResourcesUI(data);
        } catch (err) {
            console.error("Could not update resources:", err);
        }
    }

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

    // initial fetch from database
    fetchStatus().then(() => {
        if (currentHappiness !== null) {
            updateThoughtBubble(currentHappiness);
        }
    });
    fetchResources();

    // update status and resources every 2 seconds
    setInterval(() => {
        fetchStatus();
        fetchResources();
    }, 2000);

    // update thought bubble message every 10 seconds
    setInterval(() => {
        updateThoughtBubble(currentHappiness);
    }, 10000); // every 10 seconds

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
    document.getElementById("gatherFood-btn")?.addEventListener("click", () => interact("/village/catFood", "feed"));
    document.getElementById("gatherMilk-btn")?.addEventListener("click", () => interact("/village/milk", "water"));
    document.getElementById("gatherCatnip-btn")?.addEventListener("click", () => interact("/village/catnip", "water"));
    document.getElementById("gatherBrushes-btn")?.addEventListener("click", () => interact("/village/brush", "game"));

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
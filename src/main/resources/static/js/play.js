// This is for JS scripts for the /play endpoint
// i.e. updating status bars

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



// JS FOR SOUNDS
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



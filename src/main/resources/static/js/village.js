// Village JS - simplified version of play.js for village interactions

// The required csrf tokens to get past spring security
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

document.addEventListener("DOMContentLoaded", function () {

    // Function to fetch initial status and update tooltips
    async function fetchStatus() {
        try {
            const response = await fetch("/api/status", {
                method: "GET",
                credentials: "include"
            });

            if (response.ok) {
                const data = await response.json();
                updateResourceCounts(data);
            }
        } catch (error) {
            console.error("Failed to load initial status:", error);
        }
    }

    // Load initial status when page loads
    fetchStatus();

    // and then this interval sets how often it will ping the backend for data updates.
    // currently set to every 5 seconds
    setInterval(() => {
        fetchStatus();
    }, 5000);

    // Function to update resource counts in the sidebar
    function updateResourceCounts(data) {
        // Update collected resources in the resource grid
        const resourceCounts = document.querySelectorAll('.resource-count');
        resourceCounts.forEach(element => {
            const parentItem = element.closest('.resource-item');
            const label = parentItem.querySelector('.resource-label').textContent;

            if (label.includes('Cat Food') && data.collectedCatFood !== undefined) {
                element.textContent = data.collectedCatFood;
            } else if (label.includes('Milk') && data.collectedMilk !== undefined) {
                element.textContent = data.collectedMilk;
            } else if (label.includes('Catnip') && data.collectedCatnip !== undefined) {
                element.textContent = data.collectedCatnip;
            } else if (label.includes('Brush') && data.collectedBrush !== undefined) {
                element.textContent = data.collectedBrush;
            }
        });

// Update tooltips with village resource amounts
if (data.villageMilk !== undefined) {
    const milkTooltip = document.getElementById('milk_tooltip');
    if (milkTooltip) {
        milkTooltip.textContent = `Available milk: ${data.villageMilk}`;
    }
}

if (data.villageCatFood !== undefined) {
    const foodTooltip = document.getElementById('food_tooltip');
    if (foodTooltip) {
        foodTooltip.textContent = `Available food: ${data.villageCatFood}`;
    }
}

if (data.villageCatnip !== undefined) {
    const catnipTooltip = document.getElementById('catnip_tooltip');
    if (catnipTooltip) {
        catnipTooltip.textContent = `Available catnip: ${data.villageCatnip}`;
    }
}

if (data.villageBrush !== undefined) {
    const brushTooltip = document.getElementById('brush_tooltip');
    if (brushTooltip) {
        brushTooltip.textContent = `Available brushes: ${data.villageBrush}`;
    }
}

}

    // Shared function to handle interaction with village resources (AJAX + sound + flash message)
    async function interact(endpoint, successSoundId, emptySoundId) {
        const flashMessage = document.getElementById("flash-message");

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

                // Determine which sound to play based on the response
                let soundToPlay;
                if (data.isEmpty) {
                    // Resource was empty - play empty sound
                    soundToPlay = document.getElementById(emptySoundId);
                } else {
                    // Resource was available - play success sound
                    soundToPlay = document.getElementById(successSoundId);
                }

                // Play the appropriate sound
                if (soundToPlay) {
                    soundToPlay.currentTime = 0;
                    soundToPlay.play();
                }

                if (flashMessage && data.message) {
                    flashMessage.textContent = data.message;
                    flashMessage.style.display = "block";

                    // hide flash message after 3 seconds
                    setTimeout(() => {
                        flashMessage.style.display = "none";
                    }, 3000);
                }

                // Update the resource counts in the sidebar without reloading
                updateResourceCounts(data);
            } else {
                console.error("Interaction failed with status:", response.status);
            }
        } catch (error) {
            console.error("Interaction error:", error);
        }
    }

    // Updated event listeners with success and empty sound IDs
    document.getElementById("gatherFood-btn")?.addEventListener("click", () => interact("/village/catfood", "pick-food", "fail-sound"));
    document.getElementById("gatherMilk-btn")?.addEventListener("click", () => interact("/village/milk", "pour-milk", "fail-sound"));
    document.getElementById("gatherCatnip-btn")?.addEventListener("click", () => interact("/village/catnip", "pick-catnip", "fail-sound"));
    document.getElementById("gatherBrushes-btn")?.addEventListener("click", () => interact("/village/brush", "buy-brush", "fail-sound"));
});
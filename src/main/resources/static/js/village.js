// Village JS - simplified version of play.js for village interactions

// The required csrf tokens to get past spring security
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

document.addEventListener("DOMContentLoaded", function () {

    // Function to update resource counts in the sidebar
    function updateResourceCounts(data) {
        // Update collected resources in the resource grid
        const resourceCounts = document.querySelectorAll('.resource-count');
        resourceCounts.forEach(element => {
            const parentItem = element.closest('.resource-item');
            const label = parentItem.querySelector('.resource-label').textContent;

            if (label.includes('Catfood') && data.collectedCatFood !== undefined) {
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
            const milkTooltip = document.querySelector('.well-position .tooltip');
            if (milkTooltip) {
                milkTooltip.textContent = `There is ${data.villageMilk} bottles worth of milk in the well!`;
            }
        }

        if (data.villageCatFood !== undefined) {
            const foodTooltip = document.querySelector('.food-position .tooltip');
            if (foodTooltip) {
                foodTooltip.textContent = `There are ${data.villageCatFood} packs of food left on the tree!`;
            }
        }

        if (data.villageCatnip !== undefined) {
            const catnipTooltip = document.querySelector('.greenhouse-position .tooltip');
            if (catnipTooltip) {
                catnipTooltip.textContent = `There are ${data.villageCatnip} packets worth of catnip in the greenhouse!`;
            }
        }

        if (data.villagebrush !== undefined) {
            const brushTooltip = document.querySelector('.shop-position .tooltip');
            if (brushTooltip) {
                brushTooltip.textContent = `There are ${data.villagebrush} brushes left in the shop!`;
            }
        }
    }

    // Shared function to handle interaction with village resources (AJAX + sound + flash message)
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

                // Update the resource counts in the sidebar without reloading
                updateResourceCounts(data);
            } else {
                console.error("Interaction failed with status:", response.status);
            }
        } catch (error) {
            console.error("Interaction error:", error);
        }
    }
        document.getElementById("gatherFood-btn")?.addEventListener("click", () => interact("/village/catfood", "feed"));
        document.getElementById("gatherMilk-btn")?.addEventListener("click", () => interact("/village/milk", "water"));
        document.getElementById("gatherCatnip-btn")?.addEventListener("click", () => interact("/village/catnip", "water"));
        document.getElementById("gatherBrushes-btn")?.addEventListener("click", () => interact("/village/brush", "game"));
});
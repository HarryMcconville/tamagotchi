// this is for scripts relating to the /welcome endpoint

// script for choosing a random cat name
document.addEventListener('DOMContentLoaded', function () {
  const randomNameBtn = document.getElementById('randomNameBtn');
  const catNameInput = document.getElementById('catName');

  randomNameBtn.addEventListener('click', async function () {
    try {
      const response = await fetch('/api/random-cat-name');
      if (!response.ok) throw new Error('Request failed');
      const data = await response.json();
      catNameInput.value = data.name;
    } catch (error) {
      console.error('Failed to fetch cat name:', error);
      alert("Couldn't get a name. Try again!");
    }
  });
});

// Script to show border around selected cat + fill hidden input with selected file
function selectCat(imgElement) {
  // Clear previous selections
  document.querySelectorAll('.cat-option').forEach(img => {
    img.classList.remove('selected-cat');
  });

  // Apply new selection
  imgElement.classList.add('selected-cat');
  document.getElementById('catImageInput').value = imgElement.dataset.name;
}

// Script to make sure user selects a cat
  function validateCatSelection() {
    const selectedCat = document.getElementById('catImageInput').value;
    const errorMsg = document.getElementById('catError');

    if (!selectedCat) {
      errorMsg.style.display = 'block';
      return false;
    }

    errorMsg.style.display = 'none'; // hide if selection is valid
    return true;
  }

  function selectCat(imgElement) {
    // Clear other ticks or highlights if you're using them
    document.querySelectorAll('.cat-option').forEach(img => {
      img.classList.remove('selected-cat');
    });

    imgElement.classList.add('selected-cat');
    document.getElementById('catImageInput').value = imgElement.dataset.name;

    // Hide the error when a cat is selected
    const errorMsg = document.getElementById('catError');
    errorMsg.style.display = 'none';
  }
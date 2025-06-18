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

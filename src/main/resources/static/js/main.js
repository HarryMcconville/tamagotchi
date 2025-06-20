// this is for scripts relating to the /welcome endpoint

// 🐱 Script for choosing a random cat name 🐱
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

// 🐱 Script to make sure user selects a cat 🐱
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

// 🐱 Script for cat & user name validation 🐱

document.addEventListener('DOMContentLoaded', () => {
  // Initialize Bootstrap tooltips (if any remain)
  document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(el => {
    new bootstrap.Tooltip(el);
  });

  const namePattern = /^(?!.*([ '-])\1)[A-Za-z](?:[A-Za-z' -]{0,23}[A-Za-z])?$/;

  function updateChecklist(input, checklistIds) {
    const val = input.value.trim();
    const checklist = document.getElementById(checklistIds.ul);

    if (!checklist) return;

    checklist.style.display = val ? 'block' : 'none';

    const isLengthValid = val.length >= 2 && val.length <= 25;
    const isCharsValid = /^[A-Za-z' -]*$/.test(val);
    const isConsecValid = !(/([ '-])\1/.test(val));
    const isStartEndValid = /^[A-Za-z].*[A-Za-z]$/.test(val);

    document.getElementById(checklistIds.length)?.classList.toggle('text-success', isLengthValid);
    document.getElementById(checklistIds.length)?.classList.toggle('text-danger', !isLengthValid);

    document.getElementById(checklistIds.chars)?.classList.toggle('text-success', isCharsValid);
    document.getElementById(checklistIds.chars)?.classList.toggle('text-danger', !isCharsValid);

    document.getElementById(checklistIds.consec)?.classList.toggle('text-success', isConsecValid);
    document.getElementById(checklistIds.consec)?.classList.toggle('text-danger', !isConsecValid);

    document.getElementById(checklistIds.startEnd)?.classList.toggle('text-success', isStartEndValid);
    document.getElementById(checklistIds.startEnd)?.classList.toggle('text-danger', !isStartEndValid);
  }

  function validateInput(input, checklistIds, feedbackEl) {
    const val = input.value.trim();
    const checklist = document.getElementById(checklistIds.ul);

    const isLengthValid = val.length >= 2 && val.length <= 25;
    const isCharsValid = /^[A-Za-z' -]*$/.test(val);
    const isConsecValid = !(/([ '-])\1/.test(val));
    const isStartEndValid = /^[A-Za-z].*[A-Za-z]$/.test(val);

    const isValid = isLengthValid && isCharsValid && isConsecValid && isStartEndValid;

    if (!isValid) {
      input.classList.add('is-invalid');
      input.setAttribute('aria-invalid', 'true');
      feedbackEl.textContent = 'Please follow all the name rules shown below.';
      if (checklist) checklist.style.display = 'block';
    } else {
      input.classList.remove('is-invalid');
      input.setAttribute('aria-invalid', 'false');
      feedbackEl.textContent = '';
      if (checklist) checklist.style.display = 'none';
    }

    return isValid;
  }

  // Grab form elements
  const catNameInput = document.getElementById('catName');
  const displayNameInput = document.getElementById('displayName');
  const form = document.querySelector('form');

  const catChecklistIds = {
    ul: 'catNameChecklist',
    length: 'lengthRule',
    chars: 'charsRule',
    consec: 'consecRule',
    startEnd: 'startEndRule'
  };

  const displayChecklistIds = {
    ul: 'displayNameChecklist',
    length: 'dLengthRule',
    chars: 'dCharsRule',
    consec: 'dConsecRule',
    startEnd: 'dStartEndRule'
  };

  if (catNameInput) {
    const catFeedback = catNameInput.closest('.mb-3').querySelector('.invalid-feedback');
    catNameInput.addEventListener('input', () => {
      updateChecklist(catNameInput, catChecklistIds);
      validateInput(catNameInput, catChecklistIds, catFeedback);
    });
  }

  if (displayNameInput) {
    const displayFeedback = displayNameInput.closest('.mb-3').querySelector('.invalid-feedback');
    displayNameInput.addEventListener('input', () => {
      updateChecklist(displayNameInput, displayChecklistIds);
      validateInput(displayNameInput, displayChecklistIds, displayFeedback);
    });
  }

  if (form) {
    form.addEventListener('submit', e => {
      const catFeedback = catNameInput?.closest('.mb-3').querySelector('.invalid-feedback');
      const displayFeedback = displayNameInput?.closest('.mb-3').querySelector('.invalid-feedback');

      const validCat = catNameInput
        ? validateInput(catNameInput, catChecklistIds, catFeedback)
        : true;

      const validUser = displayNameInput
        ? validateInput(displayNameInput, displayChecklistIds, displayFeedback)
        : true;

      if (!validCat || !validUser) {
        e.preventDefault();
        e.stopPropagation();
      }
    });
  }
});
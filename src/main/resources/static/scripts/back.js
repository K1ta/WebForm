var BUTTON_SELECTOR = '.back-button';

function init() {
    var button = document.querySelector(BUTTON_SELECTOR);
    button.addEventListener('click', function (e) {
        document.location.href = 'http://localhost:8080/index';
    });
}

document.addEventListener('DOMContentLoaded', init);
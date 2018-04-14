var MENU_ADD_SELECTOR = '.menu-add';
var MENU_GET_SELECTOR = '.menu-get';
var MENU_UPDATE_SELECTOR = '.menu-update';
var MENU_REMOVE_SELECTOR = '.menu-remove';

function init() {
    var Add = document.querySelector(MENU_ADD_SELECTOR);
    var Get = document.querySelector(MENU_GET_SELECTOR);
    var Update = document.querySelector(MENU_UPDATE_SELECTOR);
    var Remove = document.querySelector(MENU_REMOVE_SELECTOR);

    Add.addEventListener('click', function (e) {
        document.location.href = 'http://localhost:8080/add';
    });

    Get.addEventListener('click', function (e) {
        document.location.href = 'http://localhost:8080/get';
    });

    Update.addEventListener('click', function (e) {
        document.location.href = 'http://localhost:8080/update';
    });

    Remove.addEventListener('click', function (e) {
        document.location.href = 'http://localhost:8080/remove';
    });
}

document.addEventListener('DOMContentLoaded', init);
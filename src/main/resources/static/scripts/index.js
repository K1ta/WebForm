var MENU_USER_SELECTOR = '.menu-user';
var MENU_ADD_SELECTOR = '.menu-add';
var MENU_GET_SELECTOR = '.menu-get';
var MENU_UPDATE_SELECTOR = '.menu-update';
var MENU_REMOVE_SELECTOR = '.menu-remove';
var MENU_QUIT_SELECTOR = '.menu-quit';
var MENU_DELETE_SELECTOR = '.menu-delete';

var deleteCookie = require('./Utils/deleteCookie');
var getCookie = require('./Utils/getCookie');

function init() {
    document.querySelector(MENU_USER_SELECTOR).innerHTML = 'You are logged as ' + getCookie('email');
    var Add = document.querySelector(MENU_ADD_SELECTOR);
    var Get = document.querySelector(MENU_GET_SELECTOR);
    var Update = document.querySelector(MENU_UPDATE_SELECTOR);
    var Remove = document.querySelector(MENU_REMOVE_SELECTOR);
    var Quit = document.querySelector(MENU_QUIT_SELECTOR);
    var Delete = document.querySelector(MENU_DELETE_SELECTOR);

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

    Quit.addEventListener('click', function (e) {
        var result = confirm('Are you sure you want to quit?');
        if (!result) return;
        var cookieToken = getCookie('token');
        deleteCookie('token');
        deleteCookie('email');
        var xhr = new XMLHttpRequest();
        xhr.open('POST', document.location.origin + '/api/quit', true);
        xhr.setRequestHeader('Content-type', 'application/json');
        xhr.setRequestHeader('token', cookieToken);
        xhr.send(JSON.stringify({token: cookieToken}));
        document.location.href = 'http://localhost:8080/index';
    });

    Delete.addEventListener('click', function (e) {
        var result = confirm('Are you sure you want to delete your account?');
        if (!result) return;
        var cookieToken = getCookie('token');
        deleteCookie('token');
        deleteCookie('email');
        var xhr = new XMLHttpRequest();
        xhr.open('POST', document.location.origin + '/api/delete', true);
        xhr.setRequestHeader('Content-type', 'application/json');
        xhr.setRequestHeader('token', cookieToken);
        xhr.send(JSON.stringify({token: cookieToken}));
        document.location.href = 'http://localhost:8080/index';
    });
}

document.addEventListener('DOMContentLoaded', init);
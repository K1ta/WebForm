var MENU_ADD_SELECTOR = '.menu-add';
var MENU_GET_SELECTOR = '.menu-get';
var MENU_UPDATE_SELECTOR = '.menu-update';
var MENU_REMOVE_SELECTOR = '.menu-remove';
var MENU_QUIT_SELECTOR = '.menu-quit';
var MENU_DELETE_SELECTOR = '.menu-delete';

function init() {
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
        var xhr = new XMLHttpRequest();
        xhr.open('POST', document.location.origin + '/api/quit', true);
        xhr.send();
        xhr.onreadystatechange = function (e) {
            if (this.readyState !== 4) return;
            if (this.status === 200) {
                document.location.reload();
            }
        };
    });

    Delete.addEventListener('click', function (e) {
        var result = confirm('Are you sure you want to delete your account?');
        if (!result) return;
        var xhr = new XMLHttpRequest();
        xhr.open('POST', document.location.origin + '/api/delete', true);
        xhr.send();
        xhr.onreadystatechange = function (e) {
            if (this.readyState !== 4) return;
            if (this.status === 200) {
                document.location.reload();
            }
        };
    });
}

document.addEventListener('DOMContentLoaded', init);
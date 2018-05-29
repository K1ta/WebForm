var FORM_LOGIN_BUTTON_SELECTOR = '.form-LogIn';
var FORM_SIGNUP_BUTTON_SELECTOR = '.form-SignUp';

var FORM_EMAIL_SELECTOR = '.form-email';
var FORM_PASSWORD_SELECTOR = '.form-password';

var setCookie = require('./Utils/setCookie');

var ERROR_SELECTOR = '.menu-error';
var emailRegExp = '.+@.+';
var passwordRegExp = '.{1,}';

function init() {
    var LogIn = document.querySelector(FORM_LOGIN_BUTTON_SELECTOR);
    var SignUp = document.querySelector(FORM_SIGNUP_BUTTON_SELECTOR);

    LogIn.addEventListener('click', sendAuthRequest.bind(null, '/api/login'));

    SignUp.addEventListener('click', sendAuthRequest.bind(null, '/api/signup'));
}

function sendAuthRequest(dir) {
    //get email and password from form
    var savingData = getSavingData();
    if (!savingData.email.match(emailRegExp)) {
        document.querySelector(ERROR_SELECTOR).innerHTML = 'Illegal email';
        return;
    }
    if (!savingData.password.match(passwordRegExp)) {
        document.querySelector(ERROR_SELECTOR).innerHTML = 'Illegal password';
        return;
    }
    //make post request to server with email and password
    var xhr = new XMLHttpRequest();
    xhr.open('POST', document.location.origin + dir, true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(savingData));
    xhr.onreadystatechange = handleResponse;
}

function getSavingData() {
    var email = document.querySelector(FORM_EMAIL_SELECTOR);
    var password = document.querySelector(FORM_PASSWORD_SELECTOR);
    var savingData = {
        email: email.value,
        password: password.value
    };
    return savingData;
}

function handleResponse(e) {
    if (this.readyState !== 4) return;
    if (this.status === 200) {
        var response = this.responseText;
        var error = JSON.parse(response).error;
        var token = JSON.parse(response).token;
        //if response is ok, save token to cookie
        //else show error message
        if (error === '') {
            var options = 'path=/; expires=3600';
            setCookie('token', token, options);
            //save also user's email
            setCookie('email', document.querySelector(FORM_EMAIL_SELECTOR).value);
            document.location.reload();
        } else {
            document.querySelector(ERROR_SELECTOR).innerHTML = error;
        }
    }
}

document.addEventListener('DOMContentLoaded', init);
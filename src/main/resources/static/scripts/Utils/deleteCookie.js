var setCookie = require('./setCookie');

// удаляет cookie с именем name
function deleteCookie(name) {
    setCookie(name, "", {
        expires: -1
    })
}

module.exports = deleteCookie;
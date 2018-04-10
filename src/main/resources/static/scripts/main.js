var List = require('./List');
var NewMessage = require('./NewMessage');

function init() {
    var list = new List();
    var newMessage = new NewMessage();
}

document.addEventListener('DOMContentLoaded', init);

/*
отправка на сервер запроса о добавлении элемента
сервер добавляет сообщение
посылает обратно айдишник
js обрабатывает это событие и добавляет элемент в список
 */
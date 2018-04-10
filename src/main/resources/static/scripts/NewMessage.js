var INPUT_SELECTOR = '.chat-new-message_input';
var BUTTON_SELECTOR = '.chat-new-message_button';

function NewMessage() {
    this._chatInput = document.querySelector(INPUT_SELECTOR);
    this._chatButton = document.querySelector(BUTTON_SELECTOR);

    this._chatButton.addEventListener('click', this);
}

this.handleEvent = function(e) {
    switch (e.type) {
        case 'click':
            var messageInputValue = this._chatInput.value;
            
            break;
    }
}

module.exports = NewMessage;
function MessageConstructor(MessageData) {
    this.messageData = {
        id: MessageData.id,
        sender_id: MessageData.sender_id,
        message: MessageData.message
    }
}

function a() {
}

module.exports = MessageConstructor;
const socket = new SockJS('/chat');
const stompClient = Stomp.over(socket);

const senderId = 1;
const chatId = 1;
const chatBox = document.getElementById("chat-box");
const messageInput = document.getElementById("message-input");
const sendButton = document.getElementById("send-button");

stompClient.connect({}, function (frame) {
    console.log("Connected: " + frame);
    
    // Subscribe to private chat queue
    stompClient.subscribe('/user/queue/chat-' + chatId, function (message) {
        const msg = JSON.parse(message.body);
        displayMessage("Người nhận: " + msg.content);
    });
});

sendButton.addEventListener("click", function () {
    const messageContent = messageInput.value.trim();
    if (messageContent) {
        const message = {
            senderId: senderId,
            chatId: chatId,
            content: messageContent
        };
        
        stompClient.send("/app/chat/" + chatId, {}, JSON.stringify(message));
        displayMessage("Bạn: " + messageContent);
        messageInput.value = "";
    }
});

function displayMessage(text) {
    const messageElement = document.createElement("p");
    messageElement.textContent = text;
    chatBox.appendChild(messageElement);
}

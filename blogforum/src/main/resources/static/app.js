const socket = new SockJS("http://localhost:8888/chat");
const stompClient = Stomp.over(socket);
const groupId = 1; // Giả sử nhóm ID = 1
/*
stompClient.connect({}, () => {
    console.log("🟢 Connected to WebSocket");
    
    // Subscribe để nhận tin nhắn từ nhóm
    stompClient.subscribe(`/topic/group/${groupId}`, (message) => {
        const chatBox = document.getElementById("chat-box");
        const msg = JSON.parse(message.body);
        
        const messageElement = document.createElement("div");
        messageElement.textContent = `[${msg.sendAt}] ${msg.content}`;
        chatBox.appendChild(messageElement);
        chatBox.scrollTop = chatBox.scrollHeight;
    });
});

document.getElementById("send-button").addEventListener("click", sendMessage);
document.getElementById("message-input").addEventListener("keydown", (event) => {
    if (event.key === "Enter") sendMessage();
});

function sendMessage() {
    const input = document.getElementById("message-input");
    if (input.value.trim() !== "" && stompClient.connected) {
        const message = {
            content: input.value,
            sendAt: new Date().toISOString()
        };
        stompClient.send(`/app/chat-group/${groupId}`, {}, JSON.stringify(message));
        input.value = "";
    }
}
*/


// Sample data for chats
const chatData = [
    {
        id: 1,
        name: "5069",
        avatar: "https://picsum.photos/id/237/200",
        status: "online",
        unreadCount: 1,
        lastMessage: "Thành thật thà: Đm mày hôm k",
        lastMessageTime: "1 giờ",
        messages: [
            { text: "Thành thật thà: Đm mày hôm k", sender: "them", time: "12:30" },
            { text: "Chắc mai đi được", sender: "me", time: "12:35" }
        ]
    },
    {
        id: 2,
        name: "BTL AI",
        avatar: "https://picsum.photos/id/238/200",
        status: "offline",
        unreadCount: 0,
        lastMessage: "Nguyễn: ok",
        lastMessageTime: "1 giờ",
        messages: [
            { text: "Chào bạn, bạn đã hoàn thành BTL AI chưa?", sender: "them", time: "10:25" },
            { text: "Tôi đang làm phần cuối", sender: "me", time: "10:30" },
            { text: "Nguyễn: ok", sender: "them", time: "10:35" }
        ]
    },
    {
        id: 3,
        name: "Tống Công Thành",
        avatar: "https://picsum.photos/id/239/200",
        status: "offline",
        unreadCount: 0,
        lastMessage: "nhớt",
        lastMessageTime: "1 giờ",
        messages: [
            { text: "Mai họp nhóm không bạn?", sender: "them", time: "09:15" },
            { text: "Có, 2h chiều nhé", sender: "me", time: "09:20" },
            { text: "nhớt", sender: "them", time: "09:25" }
        ]
    },
    {
        id: 4,
        name: "Mai Phương",
        avatar: "https://picsum.photos/id/240/200",
        status: "online",
        unreadCount: 3,
        lastMessage: "Bạn: u thi la moi dua 1 thu muc l...",
        lastMessageTime: "2 giờ",
        messages: [
            { text: "Chào Mai, bạn có file bài tập không?", sender: "them", time: "08:45" },
            { text: "Có, mình gửi cho bạn", sender: "me", time: "08:50" },
            { text: "u thi la moi dua 1 thu muc lon luon a", sender: "me", time: "08:55" }
        ]
    },
    {
        id: 5,
        name: "Hương Quỳnh",
        avatar: "https://picsum.photos/id/241/200",
        status: "offline",
        unreadCount: 2,
        lastMessage: "Đã bày tỏ cảm xúc 👍 về tin nhắ...",
        lastMessageTime: "2 giờ",
        messages: [
            { text: "Bạn đã làm xong phần báo cáo chưa?", sender: "them", time: "14:20" },
            { text: "Mình sẽ gửi vào tối nay", sender: "me", time: "14:25" },
            { text: "Đã bày tỏ cảm xúc 👍 về tin nhắn của bạn", sender: "them", time: "14:30" }
        ]
    },
    {
        id: 6,
        name: "AE A8K22TV❤️",
        avatar: "https://picsum.photos/id/242/200",
        status: "online",
        unreadCount: 0,
        lastMessage: "Tùng đã gửi 1 ảnh",
        lastMessageTime: "3 giờ",
        messages: [
            { text: "Ae ơi, cuối tuần đi nhậu không?", sender: "them", time: "16:10" },
            { text: "Tôi ok luôn", sender: "me", time: "16:15" },
            { text: "Tùng đã gửi 1 ảnh", sender: "them", time: "16:20" }
        ]
    },
    {
        id: 7,
        name: "CLB Guitar PTIT - GPC",
        avatar: "https://picsum.photos/id/243/200",
        status: "online",
        unreadCount: 0,
        lastMessage: "Đức Bi",
        lastMessageTime: "3 giờ",
        messages: [
            { text: "Lịch tập tuần này thay đổi nhé mọi người", sender: "them", time: "11:05" },
            { text: "Bao giờ tập vậy?", sender: "me", time: "11:10" },
            { text: "Đức Bi: Thứ 7, 14h", sender: "them", time: "11:15" }
        ]
    },
    {
        id: 8,
        name: "nơi chứa những thứ xàm xi =)))",
        avatar: "https://picsum.photos/id/244/200",
        status: "offline",
        unreadCount: 0,
        lastMessage: "Mai: Đi lấy đồ xong sock vcd",
        lastMessageTime: "4 giờ",
        messages: [
            { text: "Ai đi cafe không?", sender: "them", time: "17:30" },
            { text: "Tôi đi được", sender: "me", time: "17:35" },
            { text: "Mai: Đi lấy đồ xong sock vcd", sender: "them", time: "17:40" }
        ]
    },
];

// DOM elements
const chatListElement = document.getElementById('chatList');
const messagesContainer = document.getElementById('messagesContainer');
const currentChatName = document.getElementById('currentChatName');
const currentChatStatus = document.getElementById('currentChatStatus');
const currentChatAvatar = document.getElementById('currentChatAvatar');
const userInfoName = document.getElementById('userInfoName');
const userInfoStatus = document.getElementById('userInfoStatus');
const userInfoAvatar = document.getElementById('userInfoAvatar');
const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');

// Current selected chat
let currentChat = null;

// Initialize the application
function init() {
    renderChatList();
    
    // Select the first chat by default
    if (chatData.length > 0) {
        selectChat(chatData[0]);
    }
    
    // Add event listener for send button
    sendButton.addEventListener('click', sendMessage);
    
    // Add event listener for enter key in message input
    messageInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
}

// Render the chat list
function renderChatList() {
    chatListElement.innerHTML = '';
    
    chatData.forEach(chat => {
        const chatItem = document.createElement('div');
        chatItem.className = 'chat-item';
        chatItem.dataset.chatId = chat.id;
        
        // Add active class if this is the current chat
        if (currentChat && chat.id === currentChat.id) {
            chatItem.classList.add('active');
        }
        
        chatItem.innerHTML = `
            <div class="chat-avatar">
                <img src="${chat.avatar}" alt="${chat.name}" class="rounded-circle">
                <div class="status-indicator ${chat.status === 'online' ? 'status-online' : 'status-offline'}"></div>
            </div>
            <div class="chat-info">
                <div class="chat-name">
                    <span>${chat.name}</span>
                    <span class="chat-time">${chat.lastMessageTime}</span>
                </div>
                <div class="chat-message">${chat.lastMessage}</div>
            </div>
            ${chat.unreadCount > 0 ? `<div class="unread-badge">${chat.unreadCount}</div>` : ''}
        `;
        
        chatItem.addEventListener('click', () => selectChat(chat));
        chatListElement.appendChild(chatItem);
    });
}

// Select a chat
function selectChat(chat) {
    currentChat = chat;
    
    // Update chat header
    currentChatName.textContent = chat.name;
    currentChatStatus.textContent = `Hoạt động ${chat.status === 'online' ? 'hiện tại' : chat.lastMessageTime + ' trước'}`;
    currentChatAvatar.src = chat.avatar;
    
    // Update user info sidebar
    userInfoName.textContent = chat.name;
    userInfoStatus.textContent = `Hoạt động ${chat.status === 'online' ? 'hiện tại' : chat.lastMessageTime + ' trước'}`;
    userInfoAvatar.src = chat.avatar;
    
    // Clear unread count
    const chatIndex = chatData.findIndex(c => c.id === chat.id);
    if (chatData[chatIndex].unreadCount > 0) {
        chatData[chatIndex].unreadCount = 0;
        renderChatList();
    }
    
    // Render messages
    renderMessages(chat);
}

// Render messages for a chat
function renderMessages(chat) {
    messagesContainer.innerHTML = '';
    
    chat.messages.forEach(message => {
        const messageElement = document.createElement('div');
        messageElement.className = `message ${message.sender === 'me' ? 'message-sent' : 'message-received'}`;
        
        messageElement.innerHTML = `
            <div class="message-content">${message.text}</div>
            <div class="message-time">${message.time}</div>
        `;
        
        messagesContainer.appendChild(messageElement);
    });
    
    // Scroll to bottom
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

// Send a new message
function sendMessage() {
    const text = messageInput.value.trim();
    
    if (text && currentChat) {
        const newMessage = {
            text: text,
            sender: 'me',
            time: getCurrentTime()
        };
        
        // Add message to current chat
        const chatIndex = chatData.findIndex(c => c.id === currentChat.id);
        chatData[chatIndex].messages.push(newMessage);
        chatData[chatIndex].lastMessage = `Bạn: ${text}`;
        chatData[chatIndex].lastMessageTime = 'Vừa xong';
        
        // Update UI
        renderMessages(currentChat);
        renderChatList();
        
        // Clear input
        messageInput.value = '';
    }
}

// Get current time in HH:MM format
function getCurrentTime() {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
}

// Generate random messages periodically to simulate activity
function simulateIncomingMessages() {
    // Only generate messages if there are chats
    if (chatData.length === 0) return;
    
    // Random chat to receive message
    const randomChatIndex = Math.floor(Math.random() * chatData.length);
    const chat = chatData[randomChatIndex];
    
    // Random response templates
    const responses = [
        "Oke bạn nhé",
        "Tôi sẽ gửi file sau",
        "Mai gặp lại nhé",
        "Đã nhận được thông tin",
        "cai day la 1 model cornac toi",
        "con nhi cai khac co",
        "hay 1 file trong do",
        "kieu thu muc nay no co may cai lien",
        "u thi la moi dua 1 thu muc lon luon a"
    ];
    
    // Create new message
    const newMessage = {
        text: responses[Math.floor(Math.random() * responses.length)],
        sender: 'them',
        time: getCurrentTime()
    };
    
    // Add message to chat
    chat.messages.push(newMessage);
    chat.lastMessage = newMessage.text;
    chat.lastMessageTime = 'Vừa xong';
    
    // Increment unread count if this is not the current chat
    if (!currentChat || chat.id !== currentChat.id) {
        chat.unreadCount = (chat.unreadCount || 0) + 1;
    }
    
    // Update UI if this is the current chat
    if (currentChat && chat.id === currentChat.id) {
        renderMessages(chat);
    }
    
    renderChatList();
}

// Initialize app
init();

// Simulate incoming messages every 30-90 seconds
setInterval(simulateIncomingMessages, Math.random() * 60000 + 30000);
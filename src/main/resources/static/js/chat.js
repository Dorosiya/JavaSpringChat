document.addEventListener('DOMContentLoaded', function() {
    let stompClient = null;
    let currentUser = getCurrentUsername();
    let otherUser = null;
    const userMap = {};

    function connect() {
        const socket = new SockJS('http://localhost:8080/ws');
        stompClient = Stomp.over(socket);
        console.log("쿠키 테스트: " + getCookie('access'));
        const headers = {
            'Authorization': `Bearer ${getCookie('access')}`
        };

        stompClient.connect(headers, function(frame) {
            console.log('연결됨: ' + frame);
            const roomId = getRoomId();
            console.log('채팅방 ID: ', roomId);

            // 유저 정보 가져오기
            fetch(`/api/chat/room/${roomId}/members`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${getCookie('access')}`
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log('멤버 데이터 가져옴:', data);
                    otherUser = Object.values(data.members).find(username => username !== currentUser);
                    console.log('현재 유저: ', currentUser, '다른 유저: ', otherUser);

                    Object.entries(data.members).forEach(([memberId, username]) => {
                        userMap[memberId] = username;
                    });
                    console.log('멤버 데이터 가져온 후 userMap:', userMap);

                    loadMessages(roomId);
                })
                .catch(error => console.error('채팅방 멤버 가져오기 에러:', error));

            if (roomId) {
                stompClient.subscribe('/topic/' + roomId, function(message) {
                    const messageBody = JSON.parse(message.body);
                    console.log('받은 메시지:', messageBody);
                    showMessage(messageBody);
                });
            } else {
                console.error('채팅방 ID가 null입니다. 토픽 구독을 할 수 없습니다.');
            }
        }, function(error) {
            console.error('연결 에러: ' + error);
        });
    }

    function loadMessages(roomId) {
        fetch(`/api/chat/room/${roomId}/messages`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getCookie('access')}`
            }
        })
            .then(response => response.json())
            .then(data => {
                data.forEach(message => showMessage(message));
            })
            .catch(error => console.error('메시지 로딩 에러:', error));
    }

    document.getElementById('sendButton').addEventListener('click', function() {
        const messageInput = document.getElementById('messageInput');
        const messageContent = messageInput.value.trim();
        if (messageContent && stompClient && stompClient.connected) {
            const roomId = getRoomId();
            const memberId = getMemberIdFromToken();
            console.log('채팅방에 메시지 전송 중:', roomId, '멤버 ID:', memberId);
            if (roomId) {
                const chatMessage = {
                    content: messageContent,
                    memberId: memberId,
                    roomId: roomId
                };
                console.log('채팅 메시지:', chatMessage);
                stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
                messageInput.value = '';
            } else {
                console.error('채팅방 ID가 null입니다. 메시지를 보낼 수 없습니다.');
            }
        }
    });

    function showMessage(message) {
        const chatContainer = document.getElementById('chat-container');
        const messageElement = document.createElement('div');
        messageElement.className = 'card mb-2';
        console.log('showMessage userMap:', userMap);
        const username = userMap[message.memberId] || message.memberId;
        messageElement.innerHTML = `
            <div class="card-header">${username}</div>
            <div class="card-body">${message.content}</div>
        `;
        chatContainer.appendChild(messageElement);
        chatContainer.scrollTop = chatContainer.scrollHeight;
    }

    function getRoomId() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('roomId');
    }

    function getMemberIdFromToken() {
        const token = getCookie('access');
        if (token) {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return payload.memberId;
        }
        return null;
    }

    function getCurrentUsername() {
        const token = getCookie('access');
        if (token) {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return payload.username;
        }
        return null;
    }

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
        return null;
    }

    connect();
});

document.addEventListener('DOMContentLoaded', function () {
    fetchMembers();
});

function fetchMembers() {
    axios.get('/api/members', { withCredentials: true })
        .then(response => {
            const memberList = document.getElementById('member-list');
            response.data.forEach(member => {
                const listItem = document.createElement('li');
                listItem.className = 'list-group-item';
                listItem.textContent = member.username;
                listItem.addEventListener('click', () => startChat(member.username));
                memberList.appendChild(listItem);
            });
        })
        .catch(error => {
            if (error.response && error.response.status === 401) {
                console.log("세션이 만료되었습니다. 세션을 업데이트하기 위해 페이지를 새로 고칩니다.");
                window.location.reload();
            } else {
                console.error('오류가 발생했습니다!', error);
            }
        });
}

function startChat(receiverUsername) {
    const senderUsername = getUsernameFromToken();

    axios.post('/api/chat/room', {
        sender: senderUsername,
        receiver: receiverUsername
    }, { withCredentials: true })
        .then(response => {
            const roomId = response.data.roomId;
            window.location.href = `/chat?roomId=${roomId}&receiverUsername=${receiverUsername}`;
        })
        .catch(error => {
            console.error('채팅방 생성 또는 가져오기에 실패했습니다', error);
        });
}

function getUsernameFromToken() {
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

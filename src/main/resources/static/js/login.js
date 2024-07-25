document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    axios.post('/login', {
        username: username,
        password: password
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/users';
            } else {
                alert('로그인 실패');
            }
        })
        .catch(error => {
            console.error('오류가 발생했습니다!', error);
        });
});

document.getElementById('registerForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    axios.post('/api/member', {
        username: username,
        password: password,
        email: email
    })
        .then(response => {
            if (response.data.success) {
                alert('회원가입 성공!');
                window.location.href = '/login';
            } else {
                alert('회원가입 실패: ' + response.data.message);
            }
        })
        .catch(error => {
            console.error('오류가 발생했습니다!', error);
        });
});

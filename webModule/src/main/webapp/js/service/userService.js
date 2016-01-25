function signUp(signUpDto) {
    var data = JSON.stringify(signUpDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/signup",
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            return response.userId;
        },
        error: function () {
            alert('User was not registered');
        },
        dataType: 'json'
    });
}

function logIn(logInDto) {
    var data = JSON.stringify(logInDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/login",
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            return response.accessToken;
        },
        error: function () {
            alert('User was not logged in');
        },
        dataType: 'json'
    });
}



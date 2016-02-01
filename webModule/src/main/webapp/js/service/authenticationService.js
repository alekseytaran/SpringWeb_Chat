function signUp(signUpDto, eb) {
    var data = JSON.stringify(signUpDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/signup",
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("GET_USERID", response.userId);
        },
        error: function (response) {
            alert(response.responseText);
        },
        dataType: 'json'
    });
}

function logIn(logInDto, eb) {
    var data = JSON.stringify(logInDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/login",
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("LOGIN", response);
        },
        error: function (response) {
            alert(response.responseText);
        },
        dataType: 'json'
    });
}

function logOut(accessToken, userId, eb) {
    $.ajax({
        url: "http://localhost:8080/chats/chat/logout/" + userId + "?token=" + accessToken,
        type: "GET",
        success: function () {
            eb.postMessage("LOG_OUT");
        },
        error: function () {
            alert('User was not logged out');
        }
    });
}



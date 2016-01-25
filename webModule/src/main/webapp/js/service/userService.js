function signUp(signUpJson) {
    var data = JSON.stringify(signUpJson);
    $.ajax({
        url: "http://localhost:8080/chats/chat/signup",
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function () {
            alert('User was registered')
        },
        error: function () {
            alert('User was not registerd');
        },
        dataType: 'json',
    });
}



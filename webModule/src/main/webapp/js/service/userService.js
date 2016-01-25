function signUp(signUpJson) {
    var data = JSON.stringify(signUpJson);
    $.ajax({
        url: "http://localhost:8080/chats/chat/signup",
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function () {
            console.log('Yes');
        },
        error: function () {
            console.log('error');
        },
        dataType: 'json',
    });
}



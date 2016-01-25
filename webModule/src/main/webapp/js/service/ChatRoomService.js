function createRoom(chatRoomDto, userId, accessToken) {
    var data = JSON.stringify(chatRoomDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/chat/" + userIduserId + "?" + accessToken,
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            return response.chatRoomId;
        },
        error: function () {
            alert('User was not registered');
        },
        dataType: 'json'
    });
}

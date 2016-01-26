function createRoom(chatRoomDto, userId, accessToken, eb) {
    var data = JSON.stringify(chatRoomDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/chat/" + userId + "?token=" + accessToken,
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("CREATE_CHATROOM", response.chatRoomId);
        },
        error: function () {
            alert('Chat room was not created');
        },
        dataType: 'json'
    });
}

function findChatRooms(accessToken, userId, eb) {
    $.ajax({
        url: "http://localhost:8080/chats/chat/chats/" + userId + "?token=" + accessToken,
        type: "GET",
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("FIND_ALL_CHATS", response);
        },
        error: function () {
            alert('ChatRooms was not got');
        },
        dataType: 'json'
    });
}
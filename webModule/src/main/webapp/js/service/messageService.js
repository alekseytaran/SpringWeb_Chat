function postUserMessage(accessToken, userId, messageDto, eb) {
    var data = JSON.stringify(messageDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/message/" + userId + "?token=" + accessToken,
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("POST_MESSAGE", response);
        },
        error: function () {
            alert('Message was not sent');
        },
        dataType: 'json'
    });
}

function getPublicMessagesFromChat(accessToken, userId, chatRoomId, eb) {
    $.ajax({
        url: "http://localhost:8080/chats/chat/messages/" + userId + "/" + chatRoomId + "?token=" + accessToken,
        type: "GET",
        success: function (response) {
            eb.postMessage("UPDATE_CHATS", response);
        },
        error: function () {
            alert('ChatRooms were not not updated');
        },
    });
}

function postPrivateMessage(accessToken, authorId, recipientId, eb) {
    var data = JSON.stringify(messageDto);
    $.ajax({
        url: "http://localhost:8080/chats/chat/message/" + userId + "?token=" + accessToken,
        type: "POST",
        data: data,
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("POST_MESSAGE", response);
        },
        error: function () {
            alert('Message was not sent');
        },
        dataType: 'json'
    });
}
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

function joinUserInChat(chatsInfo, userid, accessToken) {
    var chatId = chatsInfo.id;
    $.ajax({
        url: "http://localhost:8080/chats/chat/join/" + chatId + "/" + userid + "?token=" + accessToken,
        type: "POST",
        success: function () {
        },
        error: function () {
        }
    });
}

function getUsersFromChat(accessToken, userId, chatRoomId, eb, timeout) {
    var xhr = $.ajax({
        url: "http://localhost:8080/chats/chat/users/" + chatRoomId + "/" + userId + "?token=" + accessToken,
        type: "GET",
        contentType:"application/json; charset=utf-8",
        success: function (response) {
            eb.postMessage("GET_USERS_IN_CHAT", response);
        },
        error: function () {
            alert('ChatRooms was not got');
        },
        dataType: 'json'
    });

    return xhr;
}

function leaveChat(chatId, userId, accessToken, eb) {
    $.ajax({
        url: "http://localhost:8080/chats/chat/leave/" + chatId + "/" + userId + "?token=" + accessToken,
        type: "POST",
        success: function () {
            eb.postMessage("LEAVE_CHAT");
        },
        error: function () {
            alert("can't leave chat");
        }
    });
}
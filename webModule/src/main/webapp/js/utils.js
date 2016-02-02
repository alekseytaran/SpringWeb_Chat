var isNeedUpdateChat = true;

Array.prototype.diff = function(a) {
    return this.filter(function(i) {return a.indexOf(i) < 0;});
};

function updateChatsRoomList (accessToken, userId, eb) {
    var timeout = setTimeout(function() {
        findChatRooms(accessToken, userId, eb);
    }, 5000);

    eb.postMessage("TIMEOUT_CHATS_VALUE", timeout);
}

function updateChat(accessToken, userId, chatRoomsId, eb) {
    if (accessToken === -1) {
        isNeedUpdateChat = false;
    }
    if (isNeedUpdateChat) {
        var timeout = setTimeout(function () {
            getPublicMessagesFromChat(accessToken, userId, chatRoomsId, eb);
        }, 5000);
    }
    eb.postMessage("TIMEOUT_MESSAGES_VALUE", timeout);
}

function updateUserStatus (text) {
    var $userstatus = $('#userstatus');
    $userstatus.empty();
    $userstatus.text(text);
}

function goToChatsArea() {
    $('#registration').hide();
    $('#chats').show();
}

function updateChatUsers(accessToken, userId, chatRoomId, eb, onRequestSend) {
    var timeout = setTimeout(function () {
        getUsersFromChat(accessToken, userId, chatRoomId, eb);
    }, 5000);

    eb.postMessage("TIMEOUT_USERS_VALUE", timeout);
}
var isNeedUpdateChat = true;

Array.prototype.diff = function(a) {
    return this.filter(function(i) {return a.indexOf(i) < 0;});
};

function updateChatsRoomList (accessToken, userId, eb) {
    this._eb = eb;
    setTimeout(function() {
        findChatRooms(accessToken, userId, this._eb);
    }, 5000);
}

function updateChat(accessToken, userId, chatRoomsId, eb) {
    if (accessToken === -1) {
        isNeedUpdateChat = false;
    }
    if (isNeedUpdateChat) {
        setTimeout(function () {
            getPublicMessagesFromChat(accessToken, userId, chatRoomsId, eb);
        }, 5000);
    }
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

function updateChatUsers(accessToken, userId, chatRoomId, eb) {
    setTimeout(function () {
        getUsersFromChat(accessToken, userId, chatRoomId, eb);
    }, 5000);
}
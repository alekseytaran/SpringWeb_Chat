var chat = function() {

    var eb = new EventBus();

    var appState = {
        userId: null,
        accessToken: null,
        chatRoomsId: [],
        openChatId: null
    };

    var BRAKE_UPDATE = -1;

    var chatUi = new chatRoom();

    chatUi.init(eb);

    eb.registerConsumer("LOG_OUT", function() {
        location.reload();
    });

    eb.registerConsumer("LEAVE_CHAT", function() {
        chatUi.alertLeaveChat();
        updateChat(BRAKE_UPDATE);
    });

    eb.registerConsumer("POST_PUBLIC_MESSAGE", function() {
        chatUi.sendPublicMessage(appState.openChatId, appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("OPEN_PRIVATE_CHAT", function(recipient) {
        chatUi.sendPrivateMessage(appState.userId, appState.accessToken, recipient.id, appState.openChatId, eb)
    });

    eb.registerConsumer("GET_USERS_IN_CHAT", function (users) {
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatUi.updateUsersList(users, appState.openChatId, appState.userId, appState.accessToken, eb);
    });

    eb.registerConsumer("UPDATE_CHATS", function (messages) {
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatUi.updateChatMessages(messages);
    });

    eb.registerConsumer("POST_MESSAGE", function (messageId) {
    });

    eb.registerConsumer("OPEN_CHAT", function (chatInfo) {
        isNeedUpdateChat = true;
        chatUi.cleanOldMessages();
        chatUi.displayChatName(chatInfo.roomName);
        chatUi.sendPublicMessage(chatInfo.id, appState.accessToken, appState.userId, eb);
        appState.openChatId = chatInfo.id;
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatUi.leaveChat(appState.openChatId, appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("FIND_ALL_CHATS", function (chats) {
        chatUi.renderListChats(chats, appState.userId, appState.accessToken, eb);
        updateChatsRoomList(appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("CREATE_CHATROOM", function (chatRoomId) {
        appState.chatRoomsId.push(chatRoomId);
    });

    eb.registerConsumer("LOGIN", function (logInDto) {
        chatUi.createChatRoom(logInDto.accessToken, logInDto.userId, eb);
        appState.accessToken = logInDto.accessToken;
        appState.userId = logInDto.userId;
        updateUserStatus('Congratulations! You are in chat!');
        goToChatsArea();
        chatUi.logOut(appState.userId, appState.accessToken, eb);
    });

    eb.registerConsumer("GET_USERID", function (userId) {
        appState.userId = userId;
        updateUserStatus('You were registered! Please, log in!');
    });

};

$(function() {
    new chat();
});
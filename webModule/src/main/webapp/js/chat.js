var Chat = function(parentNode) {

    var eb = new EventBus();

    var appState = {
        userId: null,
        accessToken: null,
        chatRoomsId: [],
        openChatId: null
    };

    var chatArea;

    var existedMessagesId = [];

    var BRAKE_UPDATE = -1;

    var chatUi = new chatRoom();

    chatUi.init(eb, parentNode);

    eb.registerConsumer("LOG_OUT", function() {
        location.reload();
    });

    eb.registerConsumer("LEAVE_CHAT", function() {
        chatArea.alertLeaveChat();
        updateChat(BRAKE_UPDATE);
    });

    eb.registerConsumer("POST_PUBLIC_MESSAGE", function() {
        chatArea.sendPublicMessage(appState.openChatId, appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("OPEN_PRIVATE_CHAT", function(recipient) {
        chatArea.sendPrivateMessage(appState.userId, appState.accessToken, recipient.id, appState.openChatId, eb)
    });

    eb.registerConsumer("GET_USERS_IN_CHAT", function (users) {
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatArea.updateUsersList(users, appState.openChatId, appState.userId, appState.accessToken, eb);
    });

    eb.registerConsumer("UPDATE_CHATS", function (messages) {
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        var newExistedMessageId =  chatArea.updateChatMessages(messages, existedMessagesId);
        existedMessagesId = newExistedMessageId;
    });

    eb.registerConsumer("POST_MESSAGE", function (messageId) {
        existedMessagesId.push(messageId);
    });

    eb.registerConsumer("OPEN_CHAT", function (chatInfo) {
        isNeedUpdateChat = true;
        chatArea.cleanOldMessages();
        chatArea.displayChatName(chatInfo.roomName);
        chatArea.sendPublicMessage(chatInfo.id, appState.accessToken, appState.userId, eb);
        appState.openChatId = chatInfo.id;
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatArea.leaveChat(appState.openChatId, appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("FIND_ALL_CHATS", function (chats) {
        chatArea.renderListChats(chats, appState.userId, appState.accessToken, eb);
        updateChatsRoomList(appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("CREATE_CHATROOM", function (chatRoomId) {
        appState.chatRoomsId.push(chatRoomId);
    });

    eb.registerConsumer("LOGIN", function (logInDto) {
        chatArea = new ChatArea();
        chatArea.init();

        chatArea.createChatRoom(logInDto.accessToken, logInDto.userId, eb);
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
    new Chat('#Main_chat');
    //new Chat('#Main_chat2');
    //new Chat('#Main_chat3');
});
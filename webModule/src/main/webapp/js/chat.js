var Chat = function(parentNode) {

    var eb = new EventBus();

    var timeoutForUsers;
    var timeoutForMessages;
    var timeoutForChats;

    var appState = {
        userId: null,
        accessToken: null,
        chatRoomsId: [],
        openChatId: null
    };

    var chatArea;

    var existedMessagesId = [];

    var existedUsersId = [];

    var existedChatsId = [];

    var BRAKE_UPDATE = -1;

    var chatUi = new chatRoom();

    chatUi.init(eb, parentNode);

    eb.registerConsumer("TIMEOUT_CHATS_VALUE",function(timeoutValue) {
        timeoutForChats = timeoutValue;
    });

    eb.registerConsumer("TIMEOUT_MESSAGES_VALUE",function(timeoutValue) {
        timeoutForMessages = timeoutValue;
    });

    eb.registerConsumer("TIMEOUT_USERS_VALUE",function(timeoutValue) {
        timeoutForUsers = timeoutValue;
    });

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
        var newUsersId = chatArea.updateUsersList(users, existedUsersId, eb);
        existedUsersId = newUsersId;
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
        if (appState.openChatId !== chatInfo.id) {
            clearTimeout(timeoutForUsers);
            clearTimeout(timeoutForMessages);
        }

        isNeedUpdateChat = true;
        chatArea.cleanOldData();
        existedUsersId = [];
        existedMessagesId = [];
        chatArea.displayChatName(chatInfo.roomName);
        appState.openChatId = chatInfo.id;

        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);

        chatArea.leaveChat(appState.openChatId, appState.accessToken, appState.userId, eb);
        chatArea.sendPublicMessage(chatInfo.id, appState.accessToken, appState.userId, eb);
        chatArea.addChatPublicButton(eb);
    });

    eb.registerConsumer("FIND_ALL_CHATS", function (chats) {
        var newChatsId = chatArea.renderListChats(chats, existedChatsId, appState.userId, appState.accessToken, eb);
        existedChatsId = newChatsId;
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
});
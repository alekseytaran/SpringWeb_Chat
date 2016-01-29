
var chat = function(rootDivId) {

    var eb = new EventBus();

    var appState = {
        userId: null,
        accessToken: null,
        chatRoomsId: [],
        openChatId: null
    };

    var chatRoomView = new ChatRoomView(rootDivId);

    chatRoomView.init(eb);

    eb.registerConsumer("OPEN_PRIVATE_CHAT", function(recipient) {
        chatRoomView.sendPrivateMessage(appState.userId, appState.accessToken, recipient.id, appState.openChatId, eb)
    });

    eb.registerConsumer("GET_USERS_IN_CHAT", function (users) {
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatRoomView.updateUsersList(users, appState.openChatId, appState.userId, appState.accessToken, eb);
    });

    eb.registerConsumer("UPDATE_CHATS", function (messages) {
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        chatRoomView.updateChatMessages(messages);
    });

    eb.registerConsumer("POST_MESSAGE", function (messageId) {
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
    });

    eb.registerConsumer("OPEN_CHAT", function (chatInfo) {
        chatRoomView.closeChatRoom();
        chatRoomView.openChatRoom(chatInfo.roomName, chatInfo.id, appState.accessToken, appState.userId, eb);
        appState.openChatId = chatInfo.id;
        updateChat(appState.accessToken, appState.userId, appState.openChatId, eb);
        updateChatUsers(appState.accessToken, appState.userId, appState.openChatId, eb);
    });

    eb.registerConsumer("FIND_ALL_CHATS", function (chats) {
        chatRoomView.renderListChats(chats, appState.userId, appState.accessToken, eb);
        updateChatsRoomList(appState.accessToken, appState.userId, eb);
    });

    eb.registerConsumer("CREATE_CHATROOM", function (chatRoomId) {
        appState.chatRoomsId.push(chatRoomId);
    });

    eb.registerConsumer("LOGIN", function (logInDto) {
        chatRoomView.createChatRoom(logInDto.accessToken, logInDto.userId, eb);
        appState.accessToken = logInDto.accessToken;
        appState.userId = logInDto.userId;
        updateUserStatus('Congratulations! You are in chat!');
        hideSignUpAndLogIn();
    });

    eb.registerConsumer("GET_USERID", function (userId) {
        appState.userId = userId;
        updateUserStatus('You were registered! Please, log in!');
    });

};

$(function() {
    new chat('Main_chat');
});
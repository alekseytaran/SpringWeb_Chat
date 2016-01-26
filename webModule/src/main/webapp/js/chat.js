
var chat = function(rootDivId, users) {

    var eb = new EventBus();

    var appState = {
        userId: null,
        accessToken: null,
        chatRoomsId: []
    };

    var chatRoomModel = new ChatRoomModel(eb, users);

    var chatRoomView = new ChatRoomView(users, rootDivId);

    chatRoomView.init(eb);

    eb.registerConsumer("OPEN_CHAT", function(name) {
        chatRoomView.openChatRoom(name);
    });
    eb.registerConsumer("FIND_ALL_CHATS", function(chats) {
        chatRoomView.renderListChats(chats, eb);
        updateChatsRoomList(appState.accessToken, appState.userId, eb);
    });
    eb.registerConsumer("CREATE_CHATROOM", function(chatRoomId) {
        appState.chatRoomsId.push(chatRoomId);
    });
    eb.registerConsumer("LOGIN", function(logInDto) {
        chatRoomView.createChatRoom(logInDto.accessToken, logInDto.userId, eb);
        appState.accessToken = logInDto.accessToken;
        appState.userId = logInDto.userId;
    });
    eb.registerConsumer("GET_USERID", function(userId) {
        chatRoomView.updateStatus(userId);
        appState.userId = userId;
    });
    eb.registerConsumer("ADDED_MESSAGE", chatRoomModel.validatedToPush.bind(chatRoomModel));
    eb.registerConsumer("RERENDER_UI", chatRoomView.renderUI);

};

$(function() {
    new chat('Main_chat', [new User('stas', 0), new User('ira', 1), new User('dasha', 2)]);
});
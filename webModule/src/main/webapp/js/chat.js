
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

    eb.registerConsumer("FIND_ALL_CHATS", function(chats) {
        chatRoomView.renderListChats(chats);
    });
    eb.registerConsumer("CREATE_CHATROOM", function(chatRoomId) {
        appState.chatRoomsId.push(chatRoomId);
    });
    eb.registerConsumer("LOGIN", function(accessToken) {
        chatRoomView.createChatRoom(accessToken, appState.userId, eb);
        appState.accessToken = accessToken;
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
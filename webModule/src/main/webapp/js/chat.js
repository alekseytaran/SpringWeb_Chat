
var chat = function(rootDivId, users) {

    var eb = new EventBus();

    var chatRoomModel = new ChatRoomModel(eb, users);

    var chatRoomView = new ChatRoomView(users, rootDivId);

    chatRoomView.init(eb);

    chatRoomView.listnerClick(eb);
    eb.registerConsumer("LOGIN", chatRoomView.functionalityForLogInUser);
    eb.registerConsumer("GET_USERID", chatRoomView.updateStatus);
    eb.registerConsumer("ADDED_MESSAGE", chatRoomModel.validatedToPush.bind(chatRoomModel));
    eb.registerConsumer("RERENDER_UI", chatRoomView.renderUI);

};

$(function() {
    new chat('Main_chat', [new User('stas', 0), new User('ira', 1), new User('dasha', 2)]);
});
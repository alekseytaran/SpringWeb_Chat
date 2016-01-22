var chat = function(rootDivId, users) {

    var eb = new EventBus();

    var chatRoomModel = new ChatRoomModel(eb, users);

    var chatRoomView = new ChatRoomView(users, rootDivId);

    chatRoomView.init(rootDivId);

    chatRoomView.listnerClick(eb);
    eb.registerConsumer("ADDED_MESSAGE", chatRoomModel.validatedToPush.bind(chatRoomModel));
    eb.registerConsumer("RERENDER_UI", chatRoomView.renderUI);

};

$(function() {
    new chat('Main_chat', [new User('stas', 0), new User('ira', 1), new User('dasha', 2)]);
    new chat('Second_chat', [new User('vasya', 0)]);
    new chat('Test_chat', [new User('tester', 0)]);
});
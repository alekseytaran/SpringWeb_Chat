function Message(text, userId, chatRoomId, recipientId) {
	this.text = text;
	this.userId = userId;
	this.chatRoomId = chatRoomId;

	if(recipientId !== undefined) {
		this.recipientId = recipientId;
	}
}
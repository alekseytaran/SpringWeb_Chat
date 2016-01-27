var ChatRoomModel = function(eb) {
    this._eb = eb;
    this._userOfDialogs = users;
    this._allDialogs = [];
};

 ChatRoomModel.prototype.validatedToPush = function(message) {
     if (message.message !== '') {
         for (var i = 0; i < this._userOfDialogs.length; i++) {
             if(message.userId == this._userOfDialogs[i].userId) {
                 this._allDialogs.push(this._userOfDialogs[i].name + ": " + message.message);
             }
         }

         this._eb.postMessage("RERENDER_UI", this._allDialogs);
     }
 };

ChatRoomModel.prototype.getAllDialogs = function() {
    return this._allDialogs;
};




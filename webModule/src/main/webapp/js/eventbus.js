var EventBus = function() {
    this._consumers = [];
};

EventBus.prototype.registerConsumer = function(topic, context){
    var ctx = context || null;
    this._consumers.push({topic: topic, context: ctx});
};

EventBus.prototype.unsubscribeConsumer = function(topic, context){
    for(var i in this._consumers) {
        if (topic === this._consumers[i].topic) {

        }
    }
};

EventBus.prototype.postMessage = function(topic, message){
    for(var i in this._consumers) {
        if (topic === this._consumers[i].topic) {
            var callback = this._consumers[i];
            setTimeout(function () {
                callback.context(message);
            }, 0);
        }
    }
};



var chatRoom = function() {
    return {
        "init": function (eb) {

            this._eb = eb;

            $('#sb').on('click', function(e) {
                var signUpData = getSignUpData();
                signUp(signUpData, this._eb);
                e.preventDefault(false);
            }.bind(this));

            $('#lb').on('click', function(e) {
                var logInData = getLogInData();
                logIn(logInData, this._eb);
                e.preventDefault(false);
            }.bind(this));
        },

        "createChatRoom": function(accessToken, userId, eb) {
            $('#createchat').on('click', function(e) {
                var name = $('#chatsarea input[name=chatRoomName]').val();
                var chatRoomDto = {};
                chatRoomDto.id = 0;
                chatRoomDto.roomName = name;

                createRoom(chatRoomDto, userId, accessToken, eb);
                e.preventDefault(false);
            }.bind(this));
            findChatRooms(accessToken, userId, eb);
        },

        "renderListChats": function(chats, userId, accessToken, eb) {
            var $chatslist = $('#chatslist');
            $chatslist.empty();

            var $ul  = $('<ul>').addClass("nav nav-tabs");
            var $chatsTxt = $('<h3>');
            if (chats.length === 0) {
                $chatsTxt.html('Available chats: empty');
            } else {
                $chatsTxt.html('Available chats:');
            }
            for(var i = 0; i < chats.length; i++) {
                var catchIndex = (function(x) {
                    var $li = $('<li>').attr({role: "presentation"}).addClass("dropdown");
                    var $a = $('<a>').text(chats[x].roomName);
                    $li.append($a);
                    $li.on('click', function(e) {
                        joinUserInChat(chats[x], userId, accessToken);
                        eb.postMessage("OPEN_CHAT", chats[x]);
                        e.preventDefault(false);
                    });
                    $ul.append($li);
                })(i);
            }

            $chatslist.append($chatsTxt);
            $chatslist.append($ul);
        },

        "displayChatName": function(chatName) {
            $('#chatname').text(chatName + ':');

        },

        "sendPublicMessage": function(chatRoomId, accessToken, userId, eb) {
            var $sendbutton = $('#sendbutton');

            $sendbutton.off('click');

            $sendbutton.on('click', function(e) {
                var text = $('#messageinput').val();
                var messageDto = {};
                messageDto.text = text;
                messageDto.userId = userId;
                messageDto.chatRoomId = chatRoomId;

                postUserMessage(accessToken, userId, messageDto, eb);
                e.preventDefault(false);
            })
        },

        "sendPrivateMessage": function (userId, accessToken, recipientId, chatRoomId, eb) {
            $('#sendbutton').off('click');
            $('#sendbutton').on('click', function(e) {
                var text = $('#messageinput').val();
                var messageDto = {};
                messageDto.text = text;
                messageDto.userId = userId;
                messageDto.recipientId = recipientId;
                messageDto.chatRoomId = chatRoomId;

                postPrivateMessage(accessToken, userId, recipientId, messageDto, eb);
                e.preventDefault(false);
            })
        },

        "cleanOldMessages": function() {
            $('#openchat').show();
            $('#meesagewindow').empty();
        },

        "updateChatMessages": function(messages) {
            var $messageswindow = $('#messagewindow');
            $messageswindow.empty();
            var $ul = $('<ul>').addClass("list-group");

            for(var i = 0; i < messages.length; i++) {
                var messageText = messages[i].userName + ":  <b>" + messages[i].text + '</b> :   ' + messages[i].creationTime;
                var $li = $('<li>').html(messageText).attr({role: "presentation"}).addClass("list-group-item");
                $ul.append($li);
            }
            $messageswindow.append($ul);
        },

        "updateUsersList": function(users, chatId, userId, accessToken, eb) {
            $('#userlist').empty();

            var $ul  = $('<ul>').addClass("nav nav-tabs");
            for(var i = 0; i < users.length; i++) {
                var catchIndex = (function(x) {
                    var $li = $('<li>').attr({role: "presentation"});
                    var $a = $('<a>').text(users[x].name);
                    $li.append($a);
                    $li.on('click', function(e) {
                        $li.addClass("active");
                        eb.postMessage("OPEN_PRIVATE_CHAT", users[x]);
                        e.preventDefault(false);
                    });
                    $ul.append($li);
                })(i);
            }

            var $publicLi = $('<li>').attr({role: "presentation"}).addClass("dropdown");
            $publicLi.append($('<a>').text('public'));
            $ul.append($publicLi);
            $publicLi.on('click', function(e) {
                $publicLi.addClass("active");
                eb.postMessage("POST_PUBLIC_MESSAGE");
                e.preventDefault(false);
            });

            $('#userlist').append($ul);
        },
    };
};
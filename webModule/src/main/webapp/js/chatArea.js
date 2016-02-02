var ChatArea = function() {
    return {
        "init": function () {

            var template = $('#chat-tpl').text();
            var rootNode = $(template);
            var chatArea = rootNode.find('.chat-area');

            var chatTemplate = $('#chat-area-tpl').text();
            var chatNode = $(chatTemplate);

            $('.chat-area').append(chatNode);
        },

        "createChatRoom": function(accessToken, userId, eb) {
            $('#createchat').on('click', function(e) {
                var name = $('#chatsarea input[name=chatRoomName]').val();
                var chatRoomDto = new ChatRoom(name);

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
                        $('#sendbutton').attr('disabled',false);
                        e.preventDefault(false);
                    });
                    $ul.append($li);
                })(i);
            }

            $chatslist.append($chatsTxt);
            $chatslist.append($ul);
        },

        "leaveChat": function (chatId, accessToken, userId, eb) {
            $('#leavechat').on('click', function(e) {
                leaveChat(chatId, userId, accessToken, eb);
                e.preventDefault(false);
            })
        },

        "alertLeaveChat": function () {
            $('#chatname').append(" You leave this chat!!!");
            $('#sendbutton').attr('disabled',true);
        },

        "displayChatName": function(chatName) {
            $('#chatname').html('<b>' + chatName + ':</b>');

        },

        "sendPublicMessage": function(chatRoomId, accessToken, userId, eb) {
            var $sendbutton = $('#sendbutton');
            $sendbutton.off('click');
            $sendbutton.on('click', function(e) {
                var text = $('#messageinput').val();
                var messageDto = new Message(text, userId, chatRoomId);

                postUserMessage(accessToken, userId, messageDto, eb);
                e.preventDefault(false);
            })
        },

        "sendPrivateMessage": function (userId, accessToken, recipientId, chatRoomId, eb) {
            var $sendbutton = $('#sendbutton');
            $sendbutton.off('click');
            $sendbutton.on('click', function(e) {
                var text = $('#messageinput').val();
                var messageDto = new Message(text, userId, chatRoomId, recipientId);

                postPrivateMessage(accessToken, userId, recipientId, messageDto, eb);
                e.preventDefault(false);
            })
        },

        "cleanOldMessages": function() {
            $('#openchat').show();
            $('#meesagewindow').empty();
        },

        "updateChatMessages": function(messages, existedMessagesId) {
            var newIds = messages.map(function(a) {return a.id;});
            var diffIds = newIds.diff(existedMessagesId);

            var startPosition = messages.length - diffIds.length;
            for(var i = startPosition; i < messages.length; i++) {
                var messageText = messages[i].userName + ":  <b>" + messages[i].text + '</b> :   ' + messages[i].creationTime;
                var $li = $('<li>').html(messageText).attr({role: "presentation"}).addClass("list-group-item");
                $('#messagewindow ul').append($li);
            }
            return newIds;
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

        "logOut": function(userId, accessToken, eb) {
            var $logout = $('#logout');
            $logout.show();
            $logout.on('click', function () {
                logOut(accessToken, userId, eb);
            })
        }
    };
};
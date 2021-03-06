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

        "renderListChats": function(chats, existedChatsId, userId, accessToken, eb) {
            var newIds = chats.map(function(a) {return a.id;});
            var diffIds = newIds.diff(existedChatsId);

            var $ul = $('#chatslist ul');

            var startPosition = chats.length - diffIds.length;
            for(var i = startPosition; i < chats.length; i++) {
                var catchIndex = (function(x) {
                    var $li = $('<li>').attr({role: "presentation"}).addClass("dropdown");
                    var $a = $('<a>').text(chats[x].roomName);
                    $li.append($a);
                    $li.on('click', function(e) {
                        $li.addClass('active').siblings().removeClass('active');
                        joinUserInChat(chats[x], userId, accessToken);
                        eb.postMessage("OPEN_CHAT", chats[x]);
                        $('#sendbutton').attr('disabled',false);
                        $('#leavechat').attr('disabled',false);
                        $('#messagewindow .leave-chat-message').empty();
                        e.preventDefault(false);
                    });
                    $ul.append($li);
                })(i);
            }

            return newIds;
        },

        "leaveChat": function (chatId, accessToken, userId, eb) {
            $('#leavechat').on('click', function(e) {
                leaveChat(chatId, userId, accessToken, eb);
                e.preventDefault(false);
            })
        },

        "alertLeaveChat": function () {
            $('#messagewindow .leave-chat-message').text(" You leave this chat!!!");
            $('#leavechat').attr('disabled',true);
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

        "cleanOldData": function() {
            $('#openchat').show();
            $('#meesagewindow').empty();
            $('#userlist').empty();
            $('#messagewindow ul').empty();
        },

        "updateChatMessages": function(messages, existedMessagesId) {
            var newIds = messages.map(function(a) {return a.id;});
            var diffIds = newIds.diff(existedMessagesId);

            var startPosition = messages.length - diffIds.length;
            for(var i = startPosition; i < messages.length; i++) {
                var messageText;
                if ('recipientName' in messages[i]) {
                    messageText = 'from <em>' + messages[i].userName + "</em> (to " + messages[i].recipientName +"):   <b>" + messages[i].text + '</b> :    ' + messages[i].creationTime;
                } else {
                    messageText = 'from <em>' + messages[i].userName + "</em> (to all):   <b>" + messages[i].text + '</b> :    ' + messages[i].creationTime;
                }
                var $li = $('<li>').html(messageText).attr({role: "presentation"}).addClass("list-group-item");
                $('#messagewindow ul').append($li);
            }
            return newIds;
        },

        "updateUsersList": function(users, existedUserId, eb) {
            var newIds = users.map(function(a) {return a.id;});
            var diffIds = newIds.diff(existedUserId);

            var startPosition = users.length - diffIds.length;
            var $ul  = $('.users');
            for(var i = startPosition; i < users.length; i++) {
                var catchIndex = (function(x) {
                    var $li = $('<li>').attr({role: "presentation"});
                    var $a = $('<a>').text(users[x].name);
                    $li.append($a);
                    $li.on('click', function(e) {
                        $li.addClass('active').siblings().removeClass('active');
                        eb.postMessage("OPEN_PRIVATE_CHAT", users[x]);
                        e.preventDefault(false);
                    });
                    $ul.append($li);
                })(i);
            }

            return newIds;
        },

        "addChatPublicButton": function(eb) {
            var $ul  = $('<ul>').addClass("users nav nav-tabs");
            var $publicLi = $('<li>').attr({role: "presentation"}).addClass("active");
            $publicLi.append($('<a>').text('public'));
            $ul.append($publicLi);
            $publicLi.on('click', function(e) {
                $publicLi.addClass('active').siblings().removeClass('active');
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

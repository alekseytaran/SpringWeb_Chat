var ChatRoomView = function(rootDivId) {
    var allText = '';
    return {
        "init": function (eb) {

            this._eb = eb;

            var innerHtml = '';

            var name = '<label class="control-label">Name:</label><input name="name"  type="text" size="40" class="form-control">';
            var password = '<label class="control-label">Password:</label><input name="password" type="password" size="40" class="form-control">';
            var email = '<label class="control-label">Email:</label><input name="email" type="text" size="40" class="form-control">';

            innerHtml += name;
            innerHtml += email;
            innerHtml += password;

            var $signup = $('#signup');
            $signup.append(innerHtml);

            var userId;
            var signUpButton = $('<button>').text('Sign Up').addClass("btn btn-sm btn-primary");
            signUpButton.on('click', function(e) {
                var signUpData = getSignUpData();
                userId = signUp(signUpData, this._eb);
                e.preventDefault(false);
            }.bind(this));

            $signup.parents('.form-horizontal').append(signUpButton);

            var $login = $('#login');
            $login.append(name);
            $login.append(password);

            var logInButton= $('<button>').text('Log In').addClass("btn btn-sm btn-primary");
            var accessToken;
            logInButton.on('click', function(e) {
                var logInData = getLogInData();
                accessToken = logIn(logInData, this._eb);
                e.preventDefault(false);
            }.bind(this));
            $login.parents('.form-horizontal').append(logInButton);
        },

        "renderUI": function(allDialogs) {
            var chat = document.getElementById('chatAreaDialog' + rootDivId);
            for (var i = 0; i < allDialogs.length; i++) {
                allText += allDialogs[i] + "</br>";
            }
            chat.innerHTML = allText;
            allText = '';
        },

        "createChatRoom": function(accessToken, userId, eb) {
            this._eb = eb;

            var createChatRoom = $('<button>').text('Create chat').addClass("btn btn-sm btn-primary");
            var chatRoomName = '<label class="control-label">Create new chat room:</label><input name="chatRoomName" class="form-control" type="text" size="40">';
            var $createchat = $('#createchat');
            $createchat.append(chatRoomName);
            $createchat.append(createChatRoom);
            createChatRoom.on('click', function(e) {
                var name = $('#chatsarea input[name=chatRoomName]').val();
                var chatRoomDto = {};
                chatRoomDto.id = 0;
                chatRoomDto.roomName = name;

                chatRoomId = createRoom(chatRoomDto, userId, accessToken, this._eb);
                e.preventDefault(false);
            }.bind(this));
            findChatRooms(accessToken, userId, eb);
        },

        "renderListChats": function(chats, userId, accessToken, eb) {
            var $chatslist = $('#chatslist');

            $chatslist.empty();

            var $ul  = $('<ul>').addClass("nav nav-tabs");

            var $chatsTxt = $('<h4>');

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

        "openChatRoom": function(chatName, chatRoomId, accessToken, userId, eb) {
            var $openchatarea = $('#openchatarea');

            var postMessageButton = $('<button>').text('Send').addClass("btn btn-sm btn-primary");
            var messagesWindow = $('<div rows="4" cols="50">').attr('id' ,'messageswindow').addClass("panel-footer");
            var userField = $('<input>');

            var $chatName = $('<div>').text(chatName + ':').addClass("panel-body");

            $openchatarea.append($chatName);
            $openchatarea.append(postMessageButton);
            $openchatarea.append(userField);
            $openchatarea.append(messagesWindow);

            postMessageButton.on('click', function(e) {
                var text = userField.val();
                var messageDto = {};
                messageDto.text = text;
                messageDto.userId = userId;
                messageDto.chatRoomId = chatRoomId;

                var messageId = postUserMessage(accessToken, userId, messageDto, eb);
                e.preventDefault(false);
            })
        },

        "closeChatRoom": function() {
            $('#openchatarea').empty();
        },

        "updateChatMessages": function(messages) {
            var $messageswindow = $('#messageswindow');
            $messageswindow.empty();
            var $ul = $('<ul>').addClass("list-group");

            for(var i = 0; i < messages.length; i++) {
                var messageText = messages[i].userName + ":  <b>" + messages[i].text + '</b> :   ' + messages[i].creationTime;
                var $li = $('<li>').html(messageText).attr({role: "presentation"}).addClass("list-group-item");
                $ul.append($li);
            }
            $messageswindow.append($ul);
        },

        "updateUsersList": function(users) {
            $('#userlist').empty();

            var $ul  = $('<ul>').addClass("nav nav-tabs");
            for(var i = 0; i < users.length; i++) {
                var catchIndex = (function(x) {
                    var $li = $('<li>').attr({role: "presentation"}).addClass("dropdown");
                    var $a = $('<a>').text(users[x].name);
                    $li.append($a);
                    $li.on('click', function(e) {
                        joinUserInChat(users[x], userId, accessToken);
                        eb.postMessage("OPEN_CHAT", chats[x]);
                        e.preventDefault(false);
                    });
                    $ul.append($li);
                })(i);
            }

            $('#userlist').append($ul);
        }
    };
};

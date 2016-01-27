var ChatRoomView = function(rootDivId) {
    var allText = '';
    return {
        "init": function (eb) {

            this._eb = eb;

            var innerHtml = '';

            var name = '<p>Name:<br><input name="name"  type="text" size="40"></p>';
            var password = '<p>Password:<br><input name="password" type="password" size="40"></p>';
            var email = '<p>Email:<br><input name="email" type="text" size="40"></p>';

            innerHtml += name;
            innerHtml += email;
            innerHtml += password;

            var $signup = $('#signup');
            $signup.append(innerHtml);

            var userId;
            var signUpButton = $('<button>').text('Sign Up').addClass("btn btn-sm btn-primary");
            signUpButton.on('click', function() {
                var signUpData = getSignUpData();
                userId = signUp(signUpData, this._eb);
            }.bind(this));

            $signup.append(signUpButton);

            var $login = $('#login');
            $login.append(name);
            $login.append(password);

            var logInButton= $('<button>').text('Log In').addClass("btn btn-sm btn-primary");
            var accessToken;
            logInButton.on('click', function() {
                var logInData = getLogInData();
                accessToken = logIn(logInData, this._eb);
            }.bind(this));
            $login.append(logInButton);
        },

        "renderUI": function(allDialogs) {
            var chat = document.getElementById('chatAreaDialog' + rootDivId);
            for (var i = 0; i < allDialogs.length; i++) {
                allText += allDialogs[i] + "</br>";
            }
            chat.innerHTML = allText;
            allText = '';
        },

        "updateStatus": function(userId) {
            if (userId !== null) {
                $('#userstatus').append(' user registered and has id ' + userId);
            } else {
                $('#userstatus').append(' user id is not valid and = ' + userId);
            }
        },

        "createChatRoom": function(accessToken, userId, eb) {
            if (accessToken === null) {
                $('#userstatus').append(' and user access token is not valid and = ' + accessToken);
                return;
            }

            this._eb = eb;

            var createChatRoom = $('<button>').text('Create chat');
            var chatRoomName = '<p>Chat room name:<br><input name="chatRoomName"  type="text" size="40"></p>';
            var $chatsarea = $('#chatsarea');
            $chatsarea.append(chatRoomName);
            $chatsarea.append(createChatRoom);
            createChatRoom.on('click', function() {
                var name = $('#chatsarea input[name=chatRoomName]').val();
                var chatRoomDto = {};
                chatRoomDto.id = 0;
                chatRoomDto.roomName = name;

                chatRoomId = createRoom(chatRoomDto, userId, accessToken, this._eb);
            }.bind(this));
            findChatRooms(accessToken, userId, eb);
        },

        "renderListChats": function(chats, userId, accessToken, eb) {
            var $chatslist = $('#chatslist');

            $chatslist.empty();

            var $ul  = $('<ul>');

            var $chatsTxt = $('<p>');

            if (chats.length === 0) {
                $chatsTxt.html('List of available chats: empty');
            } else {
                $chatsTxt.html('List of available chats:');
            }
            for(var i = 0; i < chats.length; i++) {
                var catchIndex = (function(x) {
                    var $li  = $('<li>');
                    $li.append(chats[x].roomName);
                    $li.on('click', function() {
                        joinUserInChat(chats[x], userId, accessToken);
                        eb.postMessage("OPEN_CHAT", chats[x]);
                    });
                    $ul.append($li);
                })(i);
            }

            $chatslist.prepend($chatsTxt);
            $chatslist.append($ul);
        },

        "openChatRoom": function(chatName, chatRoomId, accessToken, userId, eb) {
            var roomName = chatName+"Room";
            var chatDiv = $('<div>').attr({id: roomName});

            var postMessageButton = $('<button>').text('Send');
            var messageWindow = $('<div rows="4" cols="50">').attr({id: chatRoomId});
            var userField = $('<input>');
            chatDiv.text(roomName + ':');
            chatDiv.append(messageWindow);
            chatDiv.append(postMessageButton);
            chatDiv.append(userField);

            $('#Main_chat').append(chatDiv);

            postMessageButton.on('click', function() {
                var text = userField.val();
                var messageDto = {};
                messageDto.text = text;
                messageDto.userId = userId;
                messageDto.chatRoomId = chatRoomId;

                var messageId = postUserMessage(accessToken, userId, messageDto, eb);
            })
        },

        "updateChatMessages": function(messages) {

            for(var i = 0; i < messages.length; i++) {
                var chatRoomId = messages[i].chatRoomId.chatRoomId;
                var $chatRoomId = $('#' + chatRoomId);
                if (i == 0) {
                    $chatRoomId.empty();
                }
                $($chatRoomId).append(messages[i].text + '<br>');
            }
        }
    };
};

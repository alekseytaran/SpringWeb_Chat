var ChatRoomView = function(users, rootDivId) {
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
            var signUpButton = $('<button></button>').text('Sign Up');
            signUpButton.on('click', function() {
                var signUpData = getSignUpData();
                userId = signUp(signUpData, this._eb);
            }.bind(this));

            $signup.append(signUpButton);

            var $login = $('#login');
            $login.append(name);
            $login.append(password);

            var logInButton= $('<button></button>').text('Log In');
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

            var createChatRoom = $('<button></button>').text('Create chat');
            var chatRoomName = '<p>Chat room name:<br><input name="chatRoomName"  type="text" size="40"></p>';
            var $chatsarea = $('#chatsarea');
            $chatsarea.append(chatRoomName);
            $chatsarea.append(createChatRoom);
            createChatRoom.on('click', function() {
                var name = $('#chatsarea input[name=chatRoomName]').val();
                var chatRoomDto = {};
                chatRoomDto.id = 0;
                chatRoomDto.roomName = name;

                userId = createRoom(chatRoomDto, userId, accessToken, this._eb);
            }.bind(this));

            findChatRooms(accessToken, userId, eb);
        },

        "renderListChats": function(chats) {
            var $ul  = $('#chatslist');
            for(var i = 0; i < chats.length; i++) {
                var $li  = $('<li>');
                txt = chats[i].roomName;
                $li.append(txt);
                $ul.append($li);
            }

        }

    };
};

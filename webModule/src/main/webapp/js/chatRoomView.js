var ChatRoomView = function(users, rootDivId) {
    var allText = '';
    return {
        "init": function () {
            var innerHtml = '';

            var name = '<p>Name:<br><input name="name"  type="text" size="40"></p>';
            var password = '<p>Password:<br><input name="password" type="password" size="40"></p>';
            var email = '<p>Email:<br><input name="email" type="text" size="40"></p>';

            innerHtml += name;
            innerHtml += email;
            innerHtml += password;

            $('#signup').append(innerHtml);

            var userId;
            var signUpButton = $('<button></button>').text('Sign Up');
            signUpButton.on('click', function() {
                var signUpData = getSignUpData();
                userId = signUp(signUpData);
            });

            $('#signup').append(signUpButton);

            $('#login').append(name);
            $('#login').append(password);

            var logInButton= $('<button></button>').text('Log In');
            var accessToken;
            logInButton.on('click', function() {
                var logInData = getLogInData();
                accessToken = logIn(logInData);
            });
            $('#login').append(logInButton);
        },

        "listnerClick": function(eb) {
            var text = '';
            for(var i = 0, len = users.length; i < len; i++) {
                var catchIndex = (function(x) {
                    var currentName = users[x].name;
                    $('#' + currentName + '_button').click(function(){
                        text = $('#' + currentName + '_textarea').val();
                        var userId = $('#' + currentName + '_textarea').attr('userId');
                        eb.postMessage("ADDED_MESSAGE", (new Message(userId, text)));
                    });
                })(i);
            }
        },

        "renderUI": function(allDialogs) {
            var chat = document.getElementById('chatAreaDialog' + rootDivId);
            for (var i = 0; i < allDialogs.length; i++) {
                allText += allDialogs[i] + "</br>";
            }
            chat.innerHTML = allText;
            allText = '';
        }
    };
};

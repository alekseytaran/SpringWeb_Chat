var ChatRoomView = function(users, rootDivId) {
    var allText = '';
    return {
        "init": function () {
            var innerHtml = '';

            innerHtml += 'Sign Up form:';
            innerHtml += '<p>Name:<br><input id="name" type="text" size="40"></p>';
            innerHtml += '<p>Email:<br><input id="email" type="text" size="40"></p>';
            innerHtml += '<p>Password:<br><input id="password" type="password" size="40"></p>';

            $("#" + rootDivId).html(innerHtml);

            var inputSignUp = $('<button></button>').text('Sign Up');

            inputSignUp.on('click', function(){

                var name = $('#name').val();
                var email = $('#email').val();
                var password = $('#password').val();

                var signUpJson = {};
                signUpJson.id = 0;
                signUpJson.name = name;
                signUpJson.email = email;
                signUpJson.password = password;

                signUp(signUpJson);
            });

            $('body').append(inputSignUp);
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

var ChatRoomView = function(users, rootDivId) {
    var allText = '';
    return {
        "init": function () {
            var innerHtml = '';

            for(var i = 0; i < users.length; i++) {
                innerHtml += '<div id="' + users[i].name + '_name">' + users[i].name + ':</div>'
                innerHtml += '<div id="' + users[i].name + '"> </div>';
            }
            innerHtml += '<div id="chatAreaDialog' + rootDivId +'" style="width: 180px; height: 150px; background-color:#E8EDF2"></div>'
            $("#" + rootDivId).html(innerHtml);

            for(var i = 0; i < users.length; i++) {
                var textarea = users[i].name + '_textarea';
                var button = users[i].name + '_button';
                innerHtml = '<textarea id="' + textarea + '" userId = "' + users[i].userId + '" rows="1" cols="15" maxlength="55"></textarea>' +
                    '<button id="' + button + '"> Send! </button>';

                $("#" + users[i].name).html(innerHtml);
            }

            var inputSignUp = $('<button></button>').text('Sign Up');
            inputSignUp.on('click', {user:"Name", email: "email@asd.sd", password: "pass"}, authUser);
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

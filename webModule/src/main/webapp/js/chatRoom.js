var chatRoom = function() {
    return {
        "init": function (eb, parentNode) {

            var template = $('#chat-tpl').text();
            var rootNode = $(template);

            $(parentNode).append(rootNode);

            this.signUpForm = new SignUpForm();
            this.logInForm = new LogInForm();

            this.signUpForm.init(eb, rootNode.find('.sign-up'));
            this.logInForm.init(eb, rootNode.find('.log-in'));

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
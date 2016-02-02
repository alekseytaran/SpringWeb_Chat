var SignUpForm = function () {
    return {
        "init": function (eb, parentNode) {

            this._eb = eb;

            var template = $('#sign-up-tpl').text();
            var rootNode = $(template);

            $(parentNode).append(rootNode);

            $('#sb', rootNode).on('click', function (e) {
                var signUpData = this.getData();
                signUp(signUpData, this._eb);
                e.preventDefault(false);
            }.bind(this));
        },

        "getData": function () {
            var name = $('#signup input[name=name]').val();
            var email = $('#signup  input[name=email]').val();
            var password = $('#signup  input[name=password]').val();

            var signUpUserData = new SignUp(name, email, password);

            return signUpUserData;
        }
    };
};

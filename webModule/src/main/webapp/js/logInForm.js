var LogInForm = function() {
    return {
        "init": function (eb, parentNode) {

            this._eb = eb;

            var template = $('#log-in-tpl').text();
            this.rootNode = $(template);

            $(parentNode).append(this.rootNode);

            $('#lb', this.rootNode).on('click', function (e) {
                var logInData = this.getData();
                logIn(logInData, this._eb);
                e.preventDefault(false);
            }.bind(this));
        },

        "getData": function() {
            var name = $('input[name=name]', this.rootNode).val();
            var password = $('input[name=password]', this.rootNode).val();

            var logInObject = new LogIn(name, password);

            return logInObject;
        }
    };
};

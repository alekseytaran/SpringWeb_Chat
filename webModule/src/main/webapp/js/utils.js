function signUpForm(innerHtml) {

}

function getSignUpData() {
    var name = $('#signup input[name=name]').val();
    var email = $('#signup  input[name=email]').val();
    var password = $('#signup  input[name=password]').val();

    var signUpObject = {};
    signUpObject.id = 0;
    signUpObject.name = name;
    signUpObject.email = email;
    signUpObject.password = password;

    return signUpObject;
}

function getLogInData() {
    var name = $('#login input[name=name]').val();
    var password = $('#login input[name=password]').val();

    var logInObject = {};
    logInObject.name = name;
    logInObject.password = password;

    return logInObject;
}



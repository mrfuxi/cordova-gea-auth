function GAEAuth(){};

GAEAuth.get = function(onSuccess, onFail){
    cordova.exec(onSuccess, onFail, 'GAEAuth', 'getAccounts', []);
};

GAEAuth.getToken = function(username, onSuccess, onFail){
    cordova.exec(onSuccess, onFail, 'GAEAuth', 'getAuthToken', [username]);
};

GAEAuth.authenticate = function(username, application, onSuccess, onFail) {
    GAEAuth.getToken(username, function(token) {
        onSuccess("https://" + application + ".appspot.com/_ah/login?continue=http://localhost&auth=" + token);
    }, onFail);
}

module.exports = GAEAuth;

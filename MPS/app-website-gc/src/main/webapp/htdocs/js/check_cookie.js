var SSO_CHECK_COOKIE_PATH = "http://sso.pay.com:8080/sso-server/check_cookie.html";
var SSO_LOGIN_PATH = "http://sso.pay.com:8080/sso-server/login";
var SSO_SWITCH_USER_PATH = "http://sso.pay.com:8080/sso-server/switch_user";
var loginFormCreated = false;    // whether the loginForm has been created on the page already?

var SsoCookieData;
var LocalCookieData;

function addParam(url, paramName, paramValue) {
    if (url.indexOf("?") >= 0) {
        url += "&";
    } else {
        url += "?";
    }
    
    url += paramName + "=" + paramValue;
    
    return url;
}

function getSsoCookie() {
    var castgc = $.query.get("castgc") == true ? '' : $.query.get("castgc");
    var ticket = $.query.get("ticket") == true ? '' : $.query.get("ticket");
    active_user = $.query.get("active_user");
    
    if ((typeof active_user) != "number") {
        active_user = '';
    }

    return {castgc: castgc, ticket: ticket, active_user: active_user};
}

function getLocalCookie() {
    var castgc = readCookie("castgc");
    var ticket = readCookie("ticket");
    var active_user = readCookie("active_user");
    
    return {castgc: castgc,
            ticket: ticket,
            active_user: active_user};
}

function checkActiveUser(ssoCookie, localCookie) {
    // 切换用户身份
    if (ssoCookie.active_user != localCookie.active_user) {
        parent.switchUserCallback(ssoCookie.active_user);
    }
}

function checkCookie(checkCookieUrl, initSessionAndCookieUrl, destroySessionAndCookieUrl) {
    // go to sso.com to get the sso's ticket first.
    SsoCookieData = getSsoCookie();
    LocalCookieData = getLocalCookie();
    debug("sso.cookie: " + SsoCookieData.castgc 
            + ", " + SsoCookieData.ticket + ", " 
            + SsoCookieData.active_user);
    
    debug("local.cookie: " + LocalCookieData.castgc + ", " 
            + LocalCookieData.ticket+ ", " 
            + LocalCookieData.active_user);
    
    // 用户已经从sso服务器注销了，但是在本地服务器还是登录状态
    if (LocalCookieData.castgc != "" && SsoCookieData.castgc == "") {
        debug("发送注销请求: " + destroySessionAndCookieUrl);
        $.ajax({
            type: 'GET',
            url: destroySessionAndCookieUrl,
            success: function(resp) {
                parent.logoutCallback();
            }
      });
    } else if (LocalCookieData.castgc == SsoCookieData.castgc) {
        debug("本地的castgc已经过期，重新登录");
        // 发送请求初始化本地的session和cookie
        var service = checkCookieUrl;
        alert(service);
        service = addParam(service, "castgc", SsoCookieData.castgc);
        service = addParam(service, "active_user", SsoCookieData.active_user);
        // 把service编码
        service = escape(service);

        var data = {
            castgc: SsoCookieData.castgc,
            ticket: SsoCookieData.ticket,
            service: service,
            active_user: SsoCookieData.active_user
        };
        
        $.ajax({
              type: 'POST',
              url: initSessionAndCookieUrl,
              data: data,
              success: function(resp) {
                  // 初始化session成功，调用loginCallback.
                  parent.loginCallback(data);
              }
        });
    } else {
        debug("本地的cookie和远程cookie相同(都是登录或者都是注销的)");
        
        // 如果用户是登录状态的话，我们要检测下用户有没有切换过用户名
        if (SsoCookieData.castgc != "") {
            checkActiveUser(SsoCookieData, LocalCookieData);
        }
    }
}

function createCheckCookieIframe(checkCookiePath) {
    var castgc = readCookie("castgc");
    debug("本地的castgc: " + castgc);
    
    var url = SSO_CHECK_COOKIE_PATH;
    // 把本地的castgc传给sso服务器
    url = addParam(url, "castgc", castgc);
    url = addParam(url, "service", checkCookiePath);
    
    // 建立iframe
    $(document.body).append('<iframe id="check_cookie" width="0" height="0" frameborder="0"></iframe>');
    $('iframe#check_cookie').attr('src', url);
}

/**
 * 切换用户.
 * 这个方法是用了jsonp, 拿到的数据是用户当前使用的用户
 * @param callback
 * @return
 */
function switchUser(callback) {
    $.getJSON(SSO_SWITCH_USER_PATH + "?callback=?", function(activeUser) {
        callback(activeUser);
    });
}

/**
 * Show a login form in the current page.
 * @return
 */
function showLoginForm(localServicePath) {
    if (loginFormCreated == false) {
        $('<div id="pay_sso_login_div" style="display:none"><iframe id="pay_sso_login_iframe" width="300" height="250" frameborder="0"  scrolling="no"></iframe></div>').appendTo(document.body);
        document.getElementById('pay_sso_login_iframe').src = SSO_LOGIN_PATH + '?service=' + localServicePath;
        loginFormCreated = true;
    }
    $('#pay_sso_login_div').show();
    $('#pay_sso_login_div').dialog({height: "auto", width: 350});
}

/**
 * Gets the login path for the client site.
 * @param service The service which should redirect back
 * after successfully login.
 * @return Login path for client site.
 */
function getLoginUrl(service) {
    return SSO_LOGIN_PATH + "?service=" + encodeURIComponent(service);
}

// --------------------------- Cookie related util methods --------------------
function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length,c.length);
        }
    }
    return "";
}

function eraseCookie(name) {
    createCookie(name,"",-1);
}

// ---------------------------- log utils ---------------------------------
var DEBUG = false;
function debug(msg) {
    if(DEBUG) {
        console.log("DEBUG:: " + msg);
    }
}
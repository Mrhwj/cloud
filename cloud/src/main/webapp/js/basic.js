(function (doc, win) { //rem自适应
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth)
                return;
            docEl.style.fontSize = 100 * (clientWidth / 1080) + 'px';
        };
    if (!doc.addEventListener)
        return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

function changeChartNumber(num) { //因图表参数不能使用rem，进行换算
    var clientWidth = document.documentElement.clientWidth;
    return num * (clientWidth / 1080)
}

function getQueryVariable(variable) { //url参数获取
    var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=');
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}
var XBack = {};

(function (XBack) {
    XBack.STATE = 'x - back';
    XBack.element;

    XBack.onPopState = function (event) {
        event.state === XBack.STATE && XBack.fire();
        XBack.record(XBack.STATE); //初始化事件时，push一下
    };

    XBack.record = function (state) {
        history.pushState(state, null, location.href);
    };

    XBack.fire = function () {
        var event = document.createEvent('Events');
        event.initEvent(XBack.STATE, false, false);
        XBack.element.dispatchEvent(event);
    };

    XBack.listen = function (listener) {
        XBack.element.addEventListener(XBack.STATE, listener, false);
    };

    XBack.init = function () {
        XBack.element = document.createElement('span');
        window.addEventListener('popstate', XBack.onPopState);
        XBack.record(XBack.STATE);
    };

})(XBack);
$(function () {
    XBack.init();
    XBack.listen(function () {});
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
});

function forwardPage(url, flag) {
    var lastUrl = []
    if (flag) {
        if (window.localStorage.getItem('lastUrl')) {
            lastUrl = JSON.parse(window.localStorage.getItem('lastUrl'))
        }
    }
    lastUrl.push(window.location.href)
    lastUrl = JSON.stringify(lastUrl)
    window.localStorage.setItem('lastUrl', lastUrl)
    location.replace(url)
}

function backPage() {
    var lastUrl = JSON.parse(window.localStorage.getItem('lastUrl'))
    var url = lastUrl[lastUrl.length - 1]
    lastUrl.pop()
    lastUrl = JSON.stringify(lastUrl)
    window.localStorage.setItem('lastUrl', lastUrl)
    location.replace(url)
}

function filterText(content) { //文本过滤
    content = content.replace(/(\r\n)+/g, '');
    content = content.replace(/^\s+|\s+$/g, '')
    // content = content.replace(/\<a.*?>(.*?)<\/a>/g, '')
    content = content.replace(/>([^<>]+)</g, function (m, p1) {
        var str2 = "";
        str2 = p1.replace(/[ \u3000]+|\s+|(&nbsp;)+/g, "");
        return ">" + str2 + "<";
    });
    content = content.replace(/<([a-zA-Z0-9\-]+)\s+[^>]*>/gi,
        function (m,
            p1) {
            if (p1.search(/span-2/) > -1) {
                p1 = 'span'
            }
            if (p1.search(/p-2/) > -1) {
                p1 = 'p'
            }
            if (p1.search(/img/) == -1 && p1.search(
                    /input/) == -1 && p1
                .search(/video/) == -1 &&
                p1.search(/source/) == -1 && p1.search(
                    /audio/) == -1 && p1.search(/table/) == -1 && p1
                .search(/td/) == -1 && p1.search(/tr/) == -1) {
                if (m.search(/text\-align:\s+center/) >
                    -1) {
                    return "<" + p1 +
                        " style='text-align:center'>";
                } else if (m.search(
                        /text\-align:\s+left/) > -1) {
                    return "<" + p1 +
                        " style='text-align:left'>";
                } else if (m.search(
                        /text\-align:\s+right/) > -1) {
                    return "<" + p1 +
                        " style='text-align:right'>";
                } else if (m.search(
                        /text\-indent:\s+\d+\.?\dpt/) >
                    -
                    1) {
                    return "<" + p1 +
                        " style='text-indent:2em'>";
                } else {
                    return "<" + p1 + ">";
                }
            } else {
                return m;
            }
        });
    var patt = /<img[^>]+src=['"]([^'"]+)['"]+/g;
    var temp;
    while ((temp = patt.exec(content)) != null) {
        if (temp[1].indexOf('sysimage') >= 0) {
            content = content.replace(temp[1], temp[1] + '" class="editorImg');
        } else {
            content = content.replace(temp[1], temp[1]);
        }
    }
    return content
}

function setString(str, len) {
    var strlen = 0;
    var s = "";
    for (var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) > 128) {
            strlen += 2;
        } else {
            strlen++;
        }
        s += str.charAt(i);
        if (strlen >= len) {
            return s + "...";
        }
    }
    return s;
}

function justText(str) {
    str = str.replace(/(\r\n)+/g, '');
    str = str.replace(/^\s+|\s+$/g, '')
    str = str.replace(/\<a.*?>(.*?)<\/a>/g, '')
    str = str.replace(/<\/?.+?\/?>/g, '')
    return str
}
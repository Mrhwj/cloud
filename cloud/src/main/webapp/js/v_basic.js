var designImg = { width: 1920, height: 1080 }; //效果图尺寸
(function(doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function() {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth)
                return;
            docEl.style.fontSize = 100 * (clientWidth / designImg.width) + 'px';
        };
    if (!doc.addEventListener)
        return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
$(function() {
    //获取当前的浏览器信息
    if (jq.browser.msie && jq.browser.version < 9) { //解决IE9一下 rem不支持的兼容处理
        var clientWidth = document.documentElement.clientWidth;
        document.documentElement.style.fontSize = 100 * (clientWidth / designImg.width) + 'px';
        $.getScript("../js/rem.js", function(data, textStatus, jqxhr) { });
    }
    $("#login_container").fadeIn(200);
})
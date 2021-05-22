 //alert
 var MyAlertCallBack = null

 function MyAlert(text, callback) {
     var str = '<div class="myAlert flex-wrap box-align box-pack" ' +
         'style="position: fixed;top:0;left:0;width:100%;height:100%;z-index:999999;">' +
         '<div class="myAlertPanel" style="display: none; background: #fff;border-radius: 0.2rem;overflow: hidden;width: 7rem;box-shadow: 0 0 0.4rem 0.2rem rgba(0,0,0,0.3);">' +
         '<div style="text-align:center;font-size: 0.45rem;padding:0.1rem;border-bottom: 1px solid #ccc;color: #4facfe;font-weight:bold;">提示</div>' +
         '<div style="text-align:center;font-size: 0.4rem;padding:0.4rem 0.2rem;">' + text + '</div>' +
         '<div style=""' +
         '  onclick="closeMyAlert()">确定</div>' +
         '</div></div>'
     $('body').append(str)
     $('.myAlertPanel').fadeIn(500)
     if (callback) {
         MyAlertCallBack = callback
     }
 }

 function closeMyAlert() {
     $('.myAlert').fadeOut(250)
     setTimeout(function () {
         $('.myAlert').remove()
     }, 250);
     if (MyAlertCallBack) {
         MyAlertCallBack()
     }
     MyAlertCallBack = null
 }
 //confirm
 var MyConfirmCallBack = null

 function MyConfirm(text, callback) {
     var str = '<div class="myConfirm flex-wrap box-align box-pack" ' +
         'style="position: fixed;top:0;left:0;width:100%;height:100%;z-index:999999;">' +
         '<div class="myConfirmPanel" style="display: none; background: #fff;border-radius: 0.2rem;overflow: hidden;width: 7rem;box-shadow: 0 0 0.4rem 0.2rem rgba(0,0,0,0.3);">' +
         '<div style="text-align:center;font-size: 0.45rem;padding:0.1rem;border-bottom: 1px solid #ccc;color: #4facfe;font-weight:bold;">提示</div>' +
         '<div style="text-align:center;font-size: 0.4rem;padding:0.4rem 0.2rem;">' + text + '</div>' +
         '<div class="flex-wrap box-align box-pack">' +
         '<div class="" style=""' +
         '  onclick="okMyConfirm()">确定</div>' +
         '<div class="" style=""' +
         '  onclick="closeMyConfirm()">取消</div>' +
         '</div></div></div>'
     $('body').append(str)
     $('.myConfirmPanel').fadeIn(500)
     if (callback) {
         MyConfirmCallBack = callback
     }
 }

 function okMyConfirm() {
     $('.myConfirm').fadeOut(250)
     setTimeout(function () {
         $('.myConfirm').remove()
     }, 250);
     if (MyConfirmCallBack) {
         MyConfirmCallBack()
     }
     MyConfirmCallBack = null
 }

 function closeMyConfirm() {
     $('.myConfirm').fadeOut(250)
     setTimeout(function () {
         $('.myConfirm').remove()
     }, 250);
     MyConfirmCallBack = null
 }
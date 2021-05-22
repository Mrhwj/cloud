<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="../../css/public.css" media="all">
    <style>
        body {
            background-color: #ffffff;
        }

        .OneUser {
            display: inline-block;
            margin-bottom: 10px;
            white-space: nowrap;
            border: 1px solid #ccc;
            margin-right: 10px;
            padding: 3px 25px 3px 5px;
            position: relative;
        }

        .OneUser img {
            width: 14px;
            height: 14px;
            right: 5px;
            top: 50%;
            margin-top: -7px;
            position: absolute;
            cursor: pointer;
        }

        .OneUser span {
            font-size: 13px;
        }

        .hasSelectRole {
            padding-top: 10px;
        }
        #uploadImg{
            width:100px;
            min-height: 100px;
            cursor: pointer;
        }
        .hide{
        	display: none;
        }
    </style>
</head>

<body>
    <div class="layui-form layuimini-form">

        <div class="layui-form-item">
            <label class="layui-form-label required">用户名</label>
            <div class="layui-input-block">
                <input type="text" name="name" id="name" lay-verify="required" lay-reqtext="用户名不能为空" value="${model.name}"
                    class="layui-input">
                <input type="text" style="display: none;" name="id" id="id" value="${model.id}">
                <input type="text" style="display: none;" name="openId" id="openId" value="${model.openId}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">中文名</label>
            <div class="layui-input-block">
                <input type="text" name="nameCN" id="nameCN"   value="${model.nameCN}"
                    class="layui-input">
              
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <input type="radio" class="sex" name="sex" value="男" title="男"  checked>
                <input type="radio" class="sex" name="sex" value="女" title="女" >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">身份证</label>
            <div class="layui-input-block">
                <input type="text" name="idCard" id="idCard" value="${model.idCard}" lay-verify="required" lay-reqtext="身份证不能为空" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">手机号</label>
            <div class="layui-input-block">
                <input type="text" name="phone" id="phone" value="${model.phone}" lay-verify="required" lay-reqtext="手机号不能为空" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">籍贯</label>
            <div class="layui-input-block">
                <input type="text" name="nativePlace" id="nativePlace" value="${model.nativePlace}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item hide">
            <label class="layui-form-label ">角色</label>
            <div class="layui-input-block">
               <select id="roleId" name="roleId" lay-filter="roleId" lay-verify="" lay-search></select>
            </div>
        </div>
       
        <div class="layui-form-item department">
            <label class="layui-form-label">所属部门</label>
            <div class="layui-input-block">
               <select id="departmentId" name="departmentId" lay-filter="departmentId" lay-verify="" lay-search></select>
            </div>
        </div>
        <div class="layui-form-item company">
            <label class="layui-form-label">所属企业</label>
            <div class="layui-input-block">
               <select id="companyId" name="companyId" lay-filter="companyId" lay-verify="" lay-search></select>
            </div>
        </div>
        <div class="layui-form-item staff">
            <label class="layui-form-label ">是否留秀</label>
            <div class="layui-input-block">
            	<input type="radio" class="isStay" name="isStay" lay-filter="isStay" value="0" title="否" checked>
                <input type="radio" class="isStay" name="isStay" lay-filter="isStay" value="1" title="是" >
                
            </div>
        </div>
        <div class="layui-form-item driveFlag">
            <label class="layui-form-label ">是否自驾</label>
            <div class="layui-input-block">
            	<input type="radio" class="isDriving" name="isDriving" lay-filter="isDriving"  value="0" title="否" checked>
                <input type="radio" class="isDriving" name="isDriving" lay-filter="isDriving" value="1" title="是" >
            </div>
        </div>
        <div class="layui-form-item  driving">
            <label class="layui-form-label ">自驾车牌号码</label>
            <div class="layui-input-block">
             	<input type="text" name="licensePlate" id="licensePlate" value="${model.licensePlate}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" >
            <label class="layui-form-label">是否激活</label>
            <div class="layui-input-block">
                <input type="radio" class="active" name="active" value="1" title="是" checked>
                <input type="radio" class="active" name="active" value="0" title="否">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
            </div>
        </div>
    </div>
    <script src="../../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
    <script src="../../lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="utf-8"></script>
    <script>
        layui.use(['form','laydate','upload'], function () {
            var form = layui.form,
                layer = layui.layer,
                laydate = layui.laydate,
                $ = layui.$;
                
              
            var sex='${model.sex}';
            if(sex!=''){
            	$('input[name=sex]:radio[value='+sex+']').prop("checked",true);
            	form.render();
            }
            var active='${model.active}';
            if(active!=''){
            	$('input[name=active]:radio[value='+active+']').prop("checked",true);
            	form.render();
            }
            var isStay='${model.isStay}';
            if(isStay!=''){
            	$('input[name=isStay]:radio[value='+isStay+']').prop("checked",true);
            	form.render();
            }
            var isDriving='${model.isDriving}';
            if(isDriving!=''){
            	$('input[name=isDriving]:radio[value='+isDriving+']').prop("checked",true);
            	form.render();
            }
            if(isStay!='0'){
                $('.driveFlag').show()
            }else {
                $('.driveFlag').hide()
                $('.driving').hide();
                $('input[name=isDriving]:radio[value="0"]').prop("checked",true);
                form.render();
            }
            var roleId = '${roleId}';
            var companyId = '${model.companyId}';
            var isDriving = '${model.isDriving}';
            var departmentId = '${model.departmentId}';
            $.post("../role/roleList.do", { "mathRandom": Math.random() }, function (result) {
                if (result.status) {
                	$('#roleId').append(new Option('',''));
                    for (var i = 0; i < result.data.length; i++) {
                        $('#roleId').append(new Option(result.data[i].name,result.data[i].id));
                    }
                    $('#roleId').val(roleId);
                    layui.form.render();
                }
            });
            $.post("../company/companyList.do", { "mathRandom": Math.random() }, function (result) {
                if (result.status) {
                	$('#companyId').append(new Option('',''));
                    for (var i = 0; i < result.data.length; i++) {
                        $('#companyId').append(new Option(result.data[i].name,result.data[i].id));
                    }
                    $('#companyId').val(companyId);
                    layui.form.render();
                }
            });
            
           
            if(roleId=='5'){
            	$('.staff').show();
            }
            else{
            	$('.staff').hide();
            	$('.driving').hide();
            }
            if(roleId=='4'||roleId=='5'){
            	$('.company').show();
            }else{
            	$('.company').hide();
            }
            if(roleId=='3' ||roleId=='2')
            {
            	$('.department').show();
            	
            }
            else{
            	$('.department').hide();
            }
            if(roleId!=''){
            	$.post("../department/departmentList.do", { "mathRandom": Math.random(),"roleId":roleId }, function (result) {
                    if (result.status) {
                    	$('#departmentId').empty();
                    	$('#departmentId').append(new Option('',''));
                        for (var i = 0; i < result.data.length; i++) {
                            $('#departmentId').append(new Option(result.data[i].name,result.data[i].id));
                        }
                        $('#departmentId').val(departmentId);
                        layui.form.render();
                    }
                });
            }
            
            if(isDriving=='1'){
            	$('.driving').show();
            }else{
            	$('.driving').hide();
            }
            form.on('select(roleId)',function(data){
            	if(data.value=='5'){
                	$('.staff').show();
                }
                else{
                	$('.staff').hide();
                	$('.driving').hide();
                }
            	if($('input[name=isDriving]:checked').val()=='1'){
                	$('.driving').show();
                }else{
                	$('.driving').hide();
                }
            	if(data.value=='4'||data.value=='5'){
                	$('.company').show();
                }else{
                	$('.company').hide();
                }
            	if(data.value=='3' || data.value=='2')
                {
                	$('.department').show();
                }
                else{
                	$('.department').hide();
                	
                }
            	$.post("../department/departmentList.do", { "mathRandom": Math.random(),"roleId":data.value }, function (result) {
                    if (result.status) {
                    	$('#departmentId').empty();
                    	$('#departmentId').append(new Option('',''));
                        for (var i = 0; i < result.data.length; i++) {
                            $('#departmentId').append(new Option(result.data[i].name,result.data[i].id));
                        }
                        $('#departmentId').val(departmentId);
                        layui.form.render();
                    }
                });
            	
            })
            form.on('radio(isStay)',function(data){
            	if(data.value=='1'){
                	$('.driveFlag').show();
                }else{
                    $('.driveFlag').hide();
                    $('.driving').hide();
                    $('input[name=isDriving]:radio[value="0"]').prop("checked",true);
                    form.render();
                }
            })
            form.on('radio(isDriving)',function(data){
            	if(data.value=='1'){
                	$('.driving').show();
                }else{
                	$('.driving').hide();
                }
            })
            
            //监听提交
            form.on('submit(saveBtn)', function (data) {
                data = data.field;
                if(data.id=='')
                	data.id=0;
                if(data.roleId=='4' || data.roleId=='5')
            	{
                	if(data.roleId=='5'){
                		if(data.isDriving=='0')
                			data.licensePlate='';
                	}else{
                		data.isStay=0
                		data.isDriving=0;
                		data.licensePlate='';
                	}
                	data.departmentId=0;
                	
            	}else{
            		if(data.roleId!='3'&&roleId!='2'){
            			data.departmentId=0;
            			
            		}
            			
            		data.companyId=0;
            		data.isStay=0
            		data.isDriving=0;
            		data.licensePlate='';
            	}
                data.mathRandom = Math.random()
                var loader = layui.layer.load(2, { shade: [0.3, '#000000'] })
                $.post("./save.do", data, function (result) {
                    if (result.status == true) {
                        layer.alert('保存成功', function () {
                            window.parent.closeAllWin()
                        });
                    }
                    else {
                        layer.alert('保存失败,' + result.msg);
                    }
                    layui.layer.close(loader)
                });
                return false;
            });

        });
        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) { return pair[1]; }
            }
            return (false);
        }
    </script>
</body>

</html>
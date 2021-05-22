<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
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
    <link rel="stylesheet" href="../../lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
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

        .hasSelectuser {
            padding-top: 10px;
        }
    </style>
</head>

<body>
    <div class="layui-form layuimini-form">
    	<input type="hidden" name="parentId" id="parentId"  >
        <input type="hidden" name="id" id="id" value="${model.id}" >
        <div class="layui-form-item">
            <label class="layui-form-label required">菜单名</label>
            <div class="layui-input-block">
                <input type="text" name="name" id="name" lay-verify="required" lay-reqtext="菜单名称不能为空" value="${model.name}"
                    class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">链接</label>
            <div class="layui-input-block">
                <input type="text" name="link" id="link" lay-verify="" value="${model.link}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">图标</label>
            <div class="layui-input-block">
                <input type="text" id="iconPicker">
                <input type="text" style="display: none;" name="icon" id="icon" value="${model.icon}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权重</label>
            <div class="layui-input-block">
                <input type="text" name="orderId" id="orderId" value="${model.orderId}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">可见角色</label>
            <div class="layui-input-block">
               	<div id="roleIds" name="roleIds"></div>
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
    <script src="../../js/lay-config.js?v=1.0.5" charset="utf-8"></script>
    <script>
        
        layui.use(['form', 'iconPickerFa','selectM'], function () {
            var form = layui.form,
                layer = layui.layer,
                selectM = layui.selectM,
                $ = layui.$;
            var iconPickerFa = layui.iconPickerFa;
            iconPickerFa.render({
                // 选择器，推荐使用input
                elem: '#iconPicker',
                // fa 图标接口
                url: "../../lib/font-awesome-4.7.0/less/variables.less",
                // 是否开启搜索：true/false，默认true
                search: true,
                // 是否开启分页：true/false，默认true
                page: true,
                // 每页显示数量，默认12
                limit: 12,
                // 点击回调
                click: function (data) {
                    $('#icon').val('fa ' + data.icon)
                },
                success: function () {
                    $('.layui-select-title div span i').addClass($('#icon').val()).removeClass('fa-adjust')
                }
            });
            var parentId='${parentId}';
            var modelParentId='${model.parentId}';
            
            if(parentId!=''){
            	$('#parentId').val(parentId);
            }else if(modelParentId!=''){
            	$('#parentId').val(modelParentId);
            }else{
            	$('#parentId').val('0');
            }
            var keys=[];
            var roleIds='${model.roleIds}';
            $.post("../role/roleList.do", { "mathRandom": Math.random() }, function (result) {
                if (result.status) {
                    for (var i = 0; i < result.data.length; i++) {
                    	var temp = {"name":result.data[i].name,"id":result.data[i].id}
                    	keys.push(temp);
                    }
                    var tagIns1 = selectM({
                //元素容器【必填】
                     elem: '#roleIds'
                    //候选数据【必填】
                    ,data: keys
                    ,max:10
                    ,width:'auto'
                    //添加验证
                    ,verify:'required'      
                        }); 
                        tagIns1.set(roleIds.split(","))
                }
            });
            
            //监听提交
            form.on('submit(saveBtn)', function (data) {
                data = data.field;
                if(data.id=='')
                	data.id=0;
                if(data.orderId=='')
                	data.orderId=0;
                
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
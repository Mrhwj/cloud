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
    <style>
        body {
            background-color: #ffffff;
        }

        .iconInfo {
            padding: 5px;
            width: 100px;
            text-align: center;
            border: 1px solid #000;
            display: none;
            margin-top: 10px;
        }

        #iconList {
            display: none;
            padding: 5px;
        }

        #iconList div {
            padding-top: 20px;
            display: inline-block;
            margin-right: 5px;
            width: 100px;
            height: 80px;
            margin-bottom: 5px;
            text-align: center;
            cursor: pointer;
        }

        #iconList div:nth-child(4n) {
            margin-right: 0;
        }

        #iconList div:hover {
            background: #f5f5f5;
        }

        #iconList img {
            width: 20px;
        }
    </style>
</head>

<body>
    <div class="layui-form layuimini-form">

        <div class="layui-form-item">
            <label class="layui-form-label required">名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" id="name" lay-verify="required" lay-reqtext="名称不能为空" value="${model.name}"
                    class="layui-input">
                <input type="hidden"  name="id" id="id" value="${model.id}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">描述</label>
            <div class="layui-input-block">
               	<textarea name="memo" id="memo" class="layui-textarea">${model.memo}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">可见角色</label>
            <div class="layui-input-block">
               	<div id="roleIds" name="roleIds" ></div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
            </div>
        </div>
    </div>
    <div id="iconList">
    </div>
    <script src="../../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
    <script src="../../lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="utf-8"></script>
    <script src="../../js/lay-config.js?v=1.0.5" charset="utf-8"></script>
    <script>
        var iconList = []
        layui.use(['form','selectM'], function () {
            var form = layui.form,
                layer = layui.layer,
                selectM = layui.selectM,
                $ = layui.$;
            var keys=[];
            var roleIds='${model.roleIds}';
            $.post("./roleList.do", { "mathRandom": Math.random() }, function (result) {
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
                data.mathRandom = Math.random()
                var loader = layui.layer.load(2, { shade: [0.3, '#000000'] })
                $.post("./save.do", data, function (result) {
                    if (result.status) {
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
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
</head>

<body>
    <div class="layuimini-container">
        <div class="layuimini-main">
        
           
            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
            <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs layui-btn-primary data-count-edit" lay-event="see">查看详情</a>
        </script>

        </div>
    </div>
    <script src="../../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
    <script>
        function closeAllWin() {
            layer.closeAll()
            reload()
        }

        layui.use(['form', 'table'], function () {
            var $ = layui.jquery,
                form = layui.form,
                table = layui.table,
                layuimini = layui.layuimini;
            reload = function () {
                table.reload('currentTableId', {
                    page: {
                        curr: 1
                    }
                }, 'data');
            }
            table.render({
                id: 'currentTableId',
                elem: '#currentTableId',
                url: './applyList.do?userId=${userId}&mathRandom='+Math.random(),
                method: 'post',
                toolbar: '#toolbarDemo',
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": res.status ? 0 : '', //解析接口状态
                        "msg": res.msg, //解析提示文本
                        "count": res.count, //解析数据长度
                        "data": res.data //解析数据列表
                    };
                },
                request: {
                    pageName: 'pageIndex' //页码的参数名称，默认：page
                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                },
                defaultToolbar: ['filter', 'exports', 'print'],
                cols: [[
                    { type: "checkbox", width: 50, align: "center" },
                    { field: 'name', title: '事项名称', align: "center" },
                    { field: 'grantPrice', title: '补助金额', align: "center" },
                    { title: '操作', width: 200, templet: '#currentTableBar', fixed: "right", align: "center" }
                ]],
                limits: [10, 15, 20, 25, 50, 100],
                limit: 15,
                page: true
            });
           

            table.on('tool(currentTableFilter)', function (obj) {
                var data = obj.data;
                if (obj.event === 'see') {
                    var index = layer.open({
                        title:data.name+ ' 详情',
                        type: 2,
                        shade: 0.2,
                        maxmin: true,
                        shadeClose: true,
                        area: ['90%', '90%'],
                        content: '../approve/applyEditorPage.do?typeId='+data.typeId+'&id=' + data.id,
                    });
                } 
            });

        });
    </script>
    <script>

    </script>

</body>

</html>
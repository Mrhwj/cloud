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
        <fieldset class="table-search-fieldset">
				<legend>搜索信息</legend>
				<div style="margin: 10px 10px 10px 10px">
					<form class="layui-form layui-form-pane" action="" lay-filter="seachForm" id="seachForm">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">用户名</label>
								<div class="layui-input-inline">
									<input type="text" name="name" autocomplete="off"
										class="layui-input">
								</div>
							</div>
							
							<div class="layui-inline">
								<button type="submit" class="layui-btn layui-btn-primary"
									lay-submit lay-filter="data-search-btn">
									<i class="layui-icon"></i> 搜 索
								</button>
							</div>
							
						</div>
					</form>
				</div>
			</fieldset>
            <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm layui-btn-primary data-add-btn">添加账号</button>
                <button class="layui-btn layui-btn-sm layui-btn-primary data-delete-btn">删除账号</button>
            </div>
        </script>
            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
            <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs layui-btn-primary data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-primary data-count-delete" lay-event="delete">删除</a>
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
                url: './department.do?mathRandom='+Math.random(),
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
                    { field: 'name', title: '用户名', align: "center" },
                    { field: 'sex', title: '性别', align: "center" },
                    { field: 'phone', title: '联系电话', align: "center" },
                    { field: 'department', title: '部门', align: "center" },
                    { title: '操作', width: 150, templet: '#currentTableBar', fixed: "right", align: "center" }
                ]],
                limits: [10, 15, 20, 25, 50, 100],
                limit: 15,
                page: true
            });
            
            
            
            form.on('submit(data-search-btn)', function (data) {
                var result = JSON.stringify(data.field);
                //执行搜索重载
                table.reload('currentTableId', {
                    page: {
                        curr: 1
                    }
                    , where: {
                        searchParams: result
                    }
                }, 'data');
                return false;
            });
            
            // 监听添加操作
            $('.layuimini-main').on('click', '.data-add-btn', function () {
                var index = layer.open({
                    title: '添加',
                    type: 2,
                    shade: 0.2,
                    maxmin: true,
                    shadeClose: true,
                     area: ['90%', '90%'],
                    content: './departmentEdit.do?roleId=${roleId}',
                });

            });
			
            // 监听删除操作
            $('.layuimini-main').on('click', '.data-delete-btn', function () {
                var checkStatus = table.checkStatus('currentTableId')
                var delStr = ''
                for (x in checkStatus.data) {
                    if (x == '0') {
                        delStr += checkStatus.data[x].id
                    } else {
                        delStr += ',' + checkStatus.data[x].id
                    }
                }
                if (delStr == '') {
                    layer.alert('请选择要删除的项')
                    return
                }
                layer.confirm('确定要删除吗？', function (index) {
                    $.post("./del.do", { 'ids': delStr,mathRandom: Math.random()  }, function (res) {
                        layer.msg(res.msg);
                        reload();
                    })
                    layer.close(index);
                });
            });



            table.on('tool(currentTableFilter)', function (obj) {
                var data = obj.data;
                if (obj.event === 'edit') {
                    var index = layer.open({
                        title: '编辑',
                        type: 2,
                        shade: 0.2,
                        maxmin: true,
                        shadeClose: true,
                         area: ['90%', '90%'],
                        content: './departmentEdit.do?roleId=${roleId}&id=' + data.id,
                    });
                } else if (obj.event === 'delete') {
                    layer.confirm('确定要删除吗？', function (index) {
                        $.post("./del.do", { 'ids': data.id,mathRandom: Math.random()  }, function (res) {
                            layer.msg(res.msg);
                            reload();
                        })
                        layer.close(index);
                    });
                }
            });

        });
    </script>
    <script>

    </script>

</body>

</html>
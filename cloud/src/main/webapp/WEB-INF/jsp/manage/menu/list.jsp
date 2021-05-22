<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</head>

<body>
    <form id="form1" runat="server">
    </form>
    <div class="layuimini-container">
        <div class="layuimini-main">
            <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm  layui-btn-primary" id="btn-expand">
                            全部展开</button>
                        <button class="layui-btn layui-btn-sm layui-btn-primary" id="btn-fold">
                            全部折叠</button>
                        <button class="layui-btn layui-btn-sm layui-btn-primary" id="btn-add">
                            新建根栏目</button>
                    </div>
                </script>
            <table id="munu-table" class="layui-table" lay-filter="munu-table">
            </table>
        </div>
    </div>
    <!-- 操作列 -->
    <script type="text/html" id="auth-state">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加子类</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
	</script>
    <script src="../../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
    <script src="../../js/lay-config.js?v=1.0.5" charset="utf-8"></script>
    <script>
        var renderTable
        var tids = []
        var $
        function getTids() {
            tids = []
            $('.treeTable-icon').each(function () {
                if ($(this).attr('lay-ttype') == 'dir' && !$(this).hasClass('open')) {
                    tids.push($(this).attr('lay-tid'))
                }
            })
        }
        function closeAllWin() {
            layer.closeAll()
            getTids()
            renderTable();
        }

        layui.use(['form', 'treetable', 'table', 'jquery'], function () {
            $ = layui.jquery,
                form = layui.form,
                table = layui.table,
                treetable = layui.treetable;
            layuimini = layui.layuimini;

            // 渲染表格
            renderTable = function () {
                layer.load(2);
                treetable.render({
                    treeColIndex: 1,
                    treeSpid: '0',
                    treeIdName: 'id',
                    treePidName: 'parentId',
                    elem: '#munu-table',
                    url: './list.do?mathRandom=' + Math.random(),
                    page: false,
                    toolbar: '#toolbarDemo',
                    defaultToolbar: ['filter', 'exports', 'print'],
                    cols: [[
                        { type: 'numbers' },
                        { field: 'name', width: 180, title: '菜单' },
                        { field: 'link', title: '链接' },
                        {
                            field: 'icon', width: 80, align: 'center', title: '图标', templet: function (d) {
                                return '<i class="' + d.icon + '"></i>'
                            }
                        },
                        { field: 'orderId', width: 80, align: 'center', title: '排序号' },
                        { toolbar: '#auth-state', width: 200, align: 'right', title: '操作' }
                    ]],
                    done: function () {
                        layer.closeAll('loading');
                        $('.treeTable-icon').each(function () {
                            for (x in tids) {
                                if ($(this).attr('lay-tid') == tids[x] && $(this).attr('lay-ttype') == 'dir') {
                                    if ($(this).hasClass('open')) {
                                        $(this).click()
                                    }
                                }
                            }
                        })
                    }
                });
            }
            renderTable();
            $('.layuimini-main').on('click', '#btn-expand', function () {
                treetable.expandAll('#munu-table');
            });
            $('.layuimini-main').on('click', '#btn-fold', function () {
                treetable.foldAll('#munu-table');
            });
            $('.layuimini-main').on('click', '#btn-add', function () {
                layer.open({
                    title: '根栏目添加',
                    type: 2,
                    area: ['90%', '90%'],
                    content: './edit.do'
                });
            })

            //监听工具条
            table.on('tool(munu-table)', function (obj) {
                var data = obj.data;
                var layEvent = obj.event;

                if (layEvent === 'del') {
                    layer.confirm('是否确认删除？', {
                        btn: ['确定', '关闭'] //可以无限个按钮
                    }, function (index, layero) {
                        $.post("./del.do", { 'id': data.id, mathRandom: Math.random() }, function (res) {
                            layer.msg(res.msg);
                            getTids()
                            renderTable();
                        })
                    }, function (index) {
                        ;
                    });

                } else if (layEvent === 'edit') {
                    layer.open({
                        title: '栏目修改',
                        type: 2,
                         area: ['90%', '90%'],
                        content: './edit.do?id=' + data.id
                    });
                } else if (layEvent === 'add') {
                    layer.open({
                        title: data.name+'子栏目添加',
                        type: 2,
                         area: ['90%', '90%'],
                        content: './edit.do?parentId=' + data.id
                    });
                }
            });
        });
    </script>
    <script>

    </script>

</body>

</html>
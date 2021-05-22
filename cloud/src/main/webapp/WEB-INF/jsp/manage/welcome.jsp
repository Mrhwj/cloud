<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta charset="utf-8">
        <title>首页二</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="../lib/layui-v2.5.5/css/layui.css" media="all">
        <link rel="stylesheet" href="../lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
        <link rel="stylesheet" href="../css/public.css" media="all">
        <style>
            .layui-card {
                border: 1px solid #f2f2f2;
                border-radius: 5px;
            }

            .icon {
                margin-right: 10px;
                color: #1aa094;
            }

            .icon-cray {
                color: #ffb800 !important;
            }

            .icon-blue {
                color: #1e9fff !important;
            }

            .icon-tip {
                color: #ff5722 !important;
            }

            .layuimini-qiuck-module {
                text-align: center;
                margin-top: 10px
            }

            .layuimini-qiuck-module a i {
                display: inline-block;
                width: 100%;
                height: 60px;
                line-height: 60px;
                text-align: center;
                border-radius: 2px;
                font-size: 30px;
                background-color: #F8F8F8;
                color: #333;
                transition: all .3s;
                -webkit-transition: all .3s;
            }

            .layuimini-qiuck-module a cite {
                position: relative;
                top: 2px;
                display: block;
                color: #666;
                text-overflow: ellipsis;
                overflow: hidden;
                white-space: nowrap;
                font-size: 14px;
            }

            .welcome-module {
                width: 100%;
                height: 210px;
            }

            .panel {
                background-color: #fff;
                border: 1px solid transparent;
                border-radius: 3px;
                -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
                box-shadow: 0 1px 1px rgba(0, 0, 0, .05)
            }

            .panel-body {
                padding: 10px
            }

            .panel-title {
                margin-top: 0;
                margin-bottom: 0;
                font-size: 12px;
                color: inherit
            }

            .label {
                display: inline;
                padding: .2em .6em .3em;
                font-size: 75%;
                font-weight: 700;
                line-height: 1;
                color: #fff;
                text-align: center;
                white-space: nowrap;
                vertical-align: baseline;
                border-radius: .25em;
                margin-top: .3em;
            }

            .layui-red {
                color: red
            }

            .main_btn>p {
                height: 40px;
            }

            .layui-bg-number {
                background-color: #F8F8F8;
            }

            .layuimini-notice:hover {
                background: #f6f6f6;
            }

            .layuimini-notice {
                padding: 7px 16px;
                clear: both;
                font-size: 12px !important;
                cursor: pointer;
                position: relative;
                transition: background 0.2s ease-in-out;
            }

            .layuimini-notice-title,
            .layuimini-notice-label {
                padding-right: 70px !important;
                text-overflow: ellipsis !important;
                overflow: hidden !important;
                white-space: nowrap !important;
            }

            .layuimini-notice-title {
                line-height: 28px;
                font-size: 14px;
            }

            .layuimini-notice-extra {
                position: absolute;
                top: 50%;
                margin-top: -8px;
                right: 16px;
                display: inline-block;
                height: 16px;
                color: #999;
            }
        </style>
    </head>

    <body>
        <div class="layuimini-container" style="height: 100%;">
            <div class="layuimini-main">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md8">
                        <div class="layui-row layui-col-space15">
                            <div class="layui-col-md8">
                                <div class="layui-card">
                                    <div class="layui-card-header"><i class="fa fa-warning icon"></i>数据统计</div>
                                    <div class="layui-card-body">
                                        <div class="welcome-module">
                                            <div class="layui-row layui-col-space10">
                                                <div class="layui-col-xs4">
                                                    <div class="panel layui-bg-number">
                                                        <div class="panel-body">
                                                            <div class="panel-title">
                                                                <span class="label pull-right layui-bg-blue">实时</span>
                                                                <h5>企业统计</h5>
                                                            </div>
                                                            <div class="panel-content">
                                                                <h1 class="no-margins">${companyNumber}</h1>
                                                                <small>当前分类总记录数</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="layui-col-xs4">
                                                    <div class="panel layui-bg-number">
                                                        <div class="panel-body">
                                                            <div class="panel-title">
                                                                <span class="label pull-right layui-bg-cyan">实时</span>
                                                                <h5>员工统计</h5>
                                                            </div>
                                                            <div class="panel-content">
                                                                <h1 class="no-margins">${staffNumber}</h1>
                                                                <small>当前分类总记录数</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="layui-col-xs4">
                                                    <div class="panel layui-bg-number">
                                                        <div class="panel-body">
                                                            <div class="panel-title">
                                                                <span class="label pull-right layui-bg-orange">实时</span>
                                                                <h5>申报统计</h5>
                                                            </div>
                                                            <div class="panel-content">
                                                                <h1 class="no-margins">${declareNumber}</h1>
                                                                <small>当前分类总记录数</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="layui-col-xs4">
                                                    <div class="panel layui-bg-number">
                                                        <div class="panel-body">
                                                            <div class="panel-title">
                                                                <span class="label pull-right layui-bg-green">实时</span>
                                                                <h5>签到统计</h5>
                                                            </div>
                                                            <div class="panel-content">
                                                                <h1 class="no-margins">${signNumber}</h1>
                                                                <small>当前分类总记录数</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="layui-col-xs4">
                                                    <div class="panel layui-bg-number">
                                                        <div class="panel-body">
                                                            <div class="panel-title">
                                                                <span class="label pull-right layui-bg-cyan">实时</span>
                                                                <h5>留秀统计</h5>
                                                            </div>
                                                            <div class="panel-content">
                                                                <h1 class="no-margins">${totalStay.stay}</h1>
                                                                <small>当前分类总记录数</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="layui-col-xs4">
                                                    <div class="panel layui-bg-number">
                                                        <div class="panel-body">
                                                            <div class="panel-title">
                                                                <span class="label pull-right layui-bg-orange">实时</span>
                                                                <h5>自驾统计</h5>
                                                            </div>
                                                            <div class="panel-content">
                                                                <h1 class="no-margins">${totalStay.driving}</h1>
                                                                <small>当前分类总记录数</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-md4">
                                <div class="layui-card">
                                    <div class="layui-card-header"><i class="fa fa-credit-card icon icon-blue"></i>快捷入口
                                    </div>
                                    <div class="layui-card-body">
                                        <div class="welcome-module">
                                            <div class="layui-row layui-col-space10 layuimini-qiuck">
                                                <div class="layui-col-xs6 layuimini-qiuck-module">
                                                    <a href="javascript:;" data-iframe-tab="company/list.do"
                                                        data-title="企业管理" data-icon="fa fa-plus-square">
                                                        <i class="fa fa-plus-square"></i>
                                                        <cite>企业管理</cite>
                                                    </a>
                                                </div>
                                                <div class="layui-col-xs6 layuimini-qiuck-module">
                                                    <a href="javascript:;" data-iframe-tab="approve/listPage.do"
                                                        data-title="事项审批" data-icon="fa fa-align-justify">
                                                        <i class="fa fa-align-justify"></i>
                                                        <cite>事项审批</cite>
                                                    </a>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="layui-col-md12">
                                <div class="layui-card">
                                    <div class="layui-card-header"><i class="fa fa-line-chart icon"></i>报表统计</div>
                                    <div class="layui-card-body">
                                        <div id="echarts-records" style="width: 100%;min-height:200px"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-col-md4">

                        <div class="layui-card">
                            <div class="layui-card-header"><i class="fa fa-bullhorn icon icon-tip"></i>系统公告</div>
                            <div class="layui-card-body layui-text noticeList">

                            </div>
                        </div>

                        <div class="layui-card">
                            <div class="layui-card-header"><i class="fa fa-fire icon"></i>账号信息</div>
                            <div class="layui-card-body layui-text">
                                <table class="layui-table">
                                    <colgroup>
                                        <col width="100">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <td>账号</td>
                                            <td>
                                                ${user.nameCN}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>角色</td>
                                            <td>${role}</td>
                                        </tr>
                                        <tr class="sex">
                                            <td>性别</td>
                                            <td id="sex">${user.sex}</td>
                                        </tr>
                                        <tr>
                                            <td>联系方式</td>
                                            <td>${user.phone}</td>
                                        </tr>
                                        <tr class="idCard">
                                            <td>身份证号</td>
                                            <td>${user.idCard}</td>
                                        </tr>
                                        <tr id="company">
                                            <td>所属企业/部门</td>
                                            <td>${company}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
        <script src="../js/lay-config.js?v=1.0.5" charset="utf-8"></script>
        <script src="../js/echarts.min2.js"></script>
        <script>
            layui.use(['layer', 'layuimini'], function () {
                var $ = layui.jquery,
                    layer = layui.layer,
                    layuimini = layui.layuimini
                /**
                 * 查看公告信息
                 **/
                if (!'${user.sex}') {
                    var idCard = '${user.idCard}'
                    var sexNumber = idCard.substring(16, 17)
                    if (Number(sexNumber) % 2 == 0) {
                        $('#sex').html('女')
                    } else {
                        $('#sex').html('男')
                    }
                    $('.sex').hide();
                }
                if (!'${user.idCard}') {
                    $('.idCard').hide();
                }
                var company = '${company}';
                if (!company)
                    $('#company').hide();
                $('body').on('click', '.layuimini-notice', function () {
                    var title = $(this).children('.layuimini-notice-title').text(),
                        noticeTime = $(this).children('.layuimini-notice-extra').text(),
                        content = $(this).children('.layuimini-notice-content').html();
                    var html = '<div style="padding:15px 20px; text-align:justify; line-height: 22px;border-bottom:1px solid #e2e2e2;background-color: #2f4056;color: #ffffff">\n' +
                        '<div style="text-align: center;margin-bottom: 20px;font-weight: bold;border-bottom:1px solid #718fb5;padding-bottom: 5px"><h4 class="text-danger">' + title + '</h4></div>\n' +
                        '<div style="font-size: 12px">' + content + '</div>\n' +
                        '</div>\n';
                    parent.layer.open({
                        type: 1,
                        title: '系统公告' + '<span style="float: right;right: 1px;font-size: 12px;color: #b1b3b9;margin-top: 1px">' + noticeTime + '</span>',
                        area: ['800px','90%'],
                        shade: 0.8,
                        id: 'layuimini-notice',
                        btn: ['关闭'],
                        btnAlign: 'c',
                        moveType: 1,
                        content: html,
                        success: function (layero) {
                            // var btn = layero.find('.layui-layer-btn');
                            // btn.find('.layui-layer-btn0').attr({
                            //     href: 'https://gitee.com/zhongshaofa/layuimini',
                            //     target: '_blank'
                            // });
                        }
                    });
                });

                /**
                 * 报表功能
                 */
                var echartsRecords
                $.ajax({
                    url: 'signList.do',
                    type: 'post',
                    dataType: 'json',
                    success: function (res) {
                        var category = []
                        var data1 = []
                        var data2 = []
                        var nowDate = new Date().getTime()
                        res.data.forEach(function (element) {
                            if (new Date(element.date).getTime() <= nowDate) {
                                category.push(element.date)
                                data1.push(Number(element.sign))
                                data2.push(Number(element.apply))
                            }
                        });
                        var datalength = category.length
                        echartsRecords = echarts.init(document.getElementById('echarts-records'));
                        var optionRecords = {
                            color: ['#00EE76', '#00F5FF'],
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: ['签到数', '申请数']
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '10%',
                                containLabel: true
                            },
                            toolbox: {
                                feature: {
                                    saveAsImage: {}
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: category
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: [
                                {
                                    name: '签到数',
                                    type: 'line',
                                    data: data1
                                },
                                {
                                    name: '申请数',
                                    type: 'line',
                                    data: data2
                                }
                            ],
                            dataZoom: [{
                                show: true,
                                start: 100 - (6 / datalength * 100),
                                end: 100,
                                height: 20,
                                showDetail: false,
                                fillerColor: 'rgba(0 ,229, 238,0.5)',
                                borderColor: 'rgba(0 ,229 ,238,0.3)',
                                backgroundColor: 'rgba(0, 229, 238,0.3)',
                                bottom: 10
                            }]
                        };
                        echartsRecords.setOption(optionRecords);
                    }
                })
                $.ajax({
                    url: 'news/list.do?pageIndex=1&pageSize=6',
                    type: 'post',
                    dataType: 'json',
                    success: function (res) {
                        var str = ''
                        res.data.forEach(function (element) {
                            element.memo = filterText(element.memo)
                            var time = element.createTime.split(':')[0] + ':' + element.createTime.split(':')[1]
                            str += '<div class="layuimini-notice"><div class="layuimini-notice-title">' + element.title + '</div><div class="layuimini-notice-extra">' + time + '</div><div class="layuimini-notice-content layui-hide">' + element.memo + '</div></div>'
                        });

                        $('.noticeList').html(str)
                    }
                })
                var docheight = document.documentElement.clientHeight
                $('html').height(docheight)
                $('body').height(docheight - 30)
                $('#echarts-records').height(docheight - 400)
                // echarts 窗口缩放自适应
                window.onresize = function () {
                    echartsRecords.resize();
                    var docheight = document.documentElement.clientHeight
                    $('html').height(docheight)
                    $('body').height(docheight - 30)
                    $('#echarts-records').height(docheight - 400)
                }

            });
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
                        content = content.replace(temp[1], '../..' + temp[1]);
                    }
                }
                return content
            }
        </script>
    </body>

    </html>
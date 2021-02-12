/**
 * 请求封装类
 */
let Table = (function(window) {

    let Table = function(element, option, param, baseurl, methodUrl) {
        return new Table.fn.init(element, option, param, baseurl, methodUrl);
    }

    Table.fn = Table.prototype = {
        constructor: Table,
        init: function(element, option, param, baseurl, methodUrl) {

            this.param = param;
            let columns = option.columns;
            let operation = option.operation;
            let headClass = option.headClass;

            //设置默认操作栏宽度
            if(!operation.width){
                operation.width = '180px;';
            }
            //设置默认表格样式
            if(undefined == headClass || null == headClass || headClass === ""){
                headClass = 'table_header_outer';
            }
            //渲染表格
            this.renderingTable= function () {

                //清空历史数据
                $(element + " > .table_content").html("");
                let table_header = '<div class="' + headClass + '"><ul class="ul_wrap">';
                let table_content = '<div class="table_content">';
                let table_pagination = '<div class="table_pagination"><ul class="pageWrap">\n' +
                        '<li class="page_text " id="page_pre">上一页</li>\n' +
                        '$pagination$' +
                        '<li class="page_text " id="page_next">下一页</li>\n' +
                        '<li class="page_total ">共<span class="total_num">$pageCount$</span>页</li></ul></div>';
                let pagination = null;

                //设置表格Title
                $.each(columns, function(keyc, vc){
                    table_header += '<li class="li_wrap">'+vc['title']+'</li>';
                })
                table_header += '<li class="operation" style="width: '+operation.width+';">操作</li></ul></div>';

                //加载数据，拼接表格行
                new BeastRequest(baseurl, methodUrl, this.param, false,
                    function (response) {
                        if(response && response.validate && response.pagination){

                            let data = response.pagination.datas;
                            pagination = response.pagination;
                            if(data == null || data.length == 0){
                                table_content = '<ul class="ul_wrap no_content">暂无数据</ul></div>';
                            }else{

                                $.each(data, function(dkey, dv){

                                    //生成表格行数据内容部分
                                    table_content += '<ul class="ul_wrap">';
                                    $.each(columns, function(ckey, cv){

                                        let value = dv[cv['key']];
                                        if(cv.format){
                                            value = cv.format(dv[cv['key']]);
                                        }
                                        table_content += '<li class="li_wrap" title="'+value+'">'+value+'</li>';
                                    });

                                    //生成表格行操作部分
                                    let html_operation = '';
                                    $.each(operation.menus, function (k, v){
                                        html_operation += '<span class="'+v.class+'" value="" onclick="'+v.funName+'(\''+dv['id']+'\');">'+v.title+'</span>';
                                    });

                                    table_content += '<li class="operation" style="width: '+operation.width+';">'+ html_operation + '</li></ul>';
                                });
                            }
                        }else{
                            table_content = '<ul class="ul_wrap no_content">暂无数据</ul></div>';
                        }
                    },
                    function () {
                        console.log("error");
                    }).ajaxPost();

                console.log(pagination);
                const pageIndex = pagination != null ?pagination['pageIndex']:0;
                const pageCount = pagination != null ?pagination['pageCount']:0;
                table_pagination = table_pagination.replace("$pageCount$",pageIndex +"/"+ pageCount)

                if(pageCount>3){
                    let s = '';
                    for(let i = pageIndex + 2; i < pageCount; i++){
                        s += '<li class="page_text page_no">'+i+'</li>';
                    }
                    table_pagination = table_pagination.replace("$pagination$",s);
                }else{
                    table_pagination = table_pagination.replace("$pagination$",'');
                }

                table_content += '</div>';

                //渲染到页面
                $(element).html('');
                $(element).append(table_header).append(table_content).append(table_pagination);

                if(null == pagination){
                    //禁用上一页
                    $("#page_pre").unbind('click').removeClass("page_text").addClass("page_text_disable");
                    $("#page_next").unbind('click').removeClass("page_text").addClass("page_text_disable");
                }else{

                    if (pagination.isFirstPage) {

                        //禁用上一页
                        $("#page_pre").unbind('click').removeClass("page_text").addClass("page_text_disable");

                        //禁用下一页
                        if(pagination.isLasePage){
                            $("#page_next").unbind('click').removeClass("page_text").addClass("page_text_disable");
                        }else{

                            //启用下一页
                            this.nextPage(pageIndex);
                        }
                    } else if(pagination.isLasePage) {

                        //禁用下一页,启用上一页
                        $("#page_next").unbind('click').removeClass("page_text").addClass("page_text_disable");
                        this.prePage(pageIndex);
                    }else {
                        this.prePage(pageIndex);
                        this.nextPage(pageIndex);
                    }
                }

                $(".page_no").click(function () {
                    const pageIndex = parseInt($(this).text());
                    param['pagination']['pageIndex'] = pageIndex - 1;
                    new Table(element, option, param, baseurl, methodurl).renderingTable();

                    $(".page_no").each(function (k,v) {
                        if(pageIndex - 1 == k){
                            $(v).addClass("page_text_active");
                        }else{
                            $(v).removeClass("page_text_active");
                        }
                    })
                });
            },
            //下一页
            this.nextPage = function (pageIndex) {
                $("#page_next").click([pageIndex],function (event) {
                        param['pagination']['pageIndex'] = event.data[0] + 1;
                        new Table(element, option, param, baseurl, methodUrl).renderingTable();
                    }
                );
            },
            //上一页
            this.prePage = function (pageIndex) {
                $("#page_pre").click([pageIndex],function (event) {
                    param['pagination']['pageIndex'] = event.data[0] - 1;
                        new Table(element, option, param, baseurl, methodUrl).renderingTable();
                    }
                );
            }
        }
    }
    Table.fn.init.prototype = Table.fn;
    return Table;
})();
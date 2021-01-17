/**
 * 请求封装类
 */
let Table = (function(window) {

    let Table = function(element, column, param, baseurl, methodUrl) {
        return new Table.fn.init(element, column, param, baseurl, methodUrl);
    }

    Table.fn = Table.prototype = {
        constructor: Table,
        init: function(element, column, param, baseurl, methodUrl) {

            this.param = param;
            //渲染表格
            this.renderingTable= function () {

                //清空历史数据
                $(".table_content").html("");
                let html_head = '<div class="table_header"><ul class="ul_wrap">',
                    html_column = '<div class="table_content">',
                    html_pagination = '<div class="table_pagination"><ul class="pageWrap">\n' +
                        '<li class="page_text " id="page_pre">上一页</li>\n' +
                        '$pagination$' +
                        '<li class="page_text " id="page_next">下一页</li>\n' +
                        '<li class="page_total ">共<span class="total_num">$pageCount$</span>页</li></ul></div>',
                    pagination = null;

                //设置表格Title
                $.each(column, function(keyc, vc){
                    html_head += '<li class="li_wrap">'+vc['title']+'</li>';
                })
                html_head += '<li class="operation">操作</li></ul></div>';

                //加载数据，拼接表格行
                new BeastRequest(baseurl, methodUrl, this.param, false,
                    function (response) {
                        if(response && response.validate && response.pagination){

                            let data = response.pagination.datas;
                            pagination = response.pagination;
                            if(data.length == 0){
                                html_column = '<ul class="ul_wrap no_content">暂无数据</ul></div>';
                            }else{
                                $.each(data, function(dkey, dv){

                                    html_column += '<ul class="ul_wrap">';
                                    $.each(column, function(ckey, cv){

                                        if(cv.format){
                                            let value = cv.format(dv[cv['key']]);
                                            html_column += '<li class="li_wrap" title="'+dv[cv['title']]+'">'+value+'</li>';
                                        }else{
                                            html_column += '<li class="li_wrap" title="'+dv[cv['title']]+'">'+dv[cv['key']]+'</li>';
                                        }
                                    })
                                    html_column += '<li class="operation">'+
                                        '<span class="edit" value="" onclick="update(\''+dv['id']+'\');">修改</span>'+
                                        '<span class="delete" onclick="remove(\''+dv['id']+'\');">删除</span>'+
                                        '</li></ul>';
                                });
                            }
                        }
                    },
                    function () {
                        console.log("error");
                    }).ajaxPost();

                console.log(pagination);
                const pageIndex = pagination != null ?pagination['pageIndex']:0;
                const pageCount = pagination != null ?pagination['pageCount']:0;
                html_pagination = html_pagination.replace("$pageCount$",pageIndex +"/"+ pageCount)

                if(pageCount>3){
                    let s = '';
                    for(let i = pageIndex + 2; i < pageCount; i++){
                        s += '<li class="page_text page_no">'+i+'</li>';
                    }
                    html_pagination = html_pagination.replace("$pagination$",s);
                }else{
                    html_pagination = html_pagination.replace("$pagination$",'');
                }

                html_column += '</div>';

                //渲染到页面
                $(element).html('');
                $(element).append(html_head).append(html_column).append(html_pagination);

                if(null != pagination){

                    if (pagination.isFirstPage) {

                        //禁用上一页,启用下一页
                        $("#page_pre").unbind('click').removeClass("page_text").addClass("page_text_disable");
                        this.nextPage(pageIndex);
                    } else if(pagination.isLasePage) {

                        //禁用下一页,启用上一页
                        $("#page_next").unbind('click').removeClass("page_text").addClass("page_text_disable");
                        this.prePage(pageIndex);
                    }
                } else{
                    this.prePage(pageIndex);
                    this.nextPage(pageIndex);
                }

                $(".page_no").click(function () {
                    const pageIndex = parseInt($(this).text());
                    param['pagination']['pageIndex'] = pageIndex - 1;
                    new Table(element, column, param, baseurl, methodurl).renderingTable();

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
                        new Table(element, column, param, baseurl, methodUrl).renderingTable();
                    }
                );
            },
            //上一页
            this.prePage = function (pageIndex) {
                $("#page_pre").click([pageIndex],function (event) {
                    param['pagination']['pageIndex'] = event.data[0] - 1;
                        new Table(element, column, param, baseurl, methodUrl).renderingTable();
                    }
                );
            }
        }
    }

    Table.fn.init.prototype = Table.fn;
    return Table;
})();
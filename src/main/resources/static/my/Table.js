/**
 * 请求封装类
 */
var Table = (function(window) {

    var Table = function(element, column, param, baseurl, methodurl) {
        return new Table.fn.init(element, column, param, baseurl, methodurl);
    }

    Table.fn = Table.prototype = {
        constructor: Table,
        init: function(element, column, param, baseurl, methodurl) {

            //渲染表格
            this.renderingTable= function () {

                var html_head = '<div class="table_header"><ul class="ul_wrap">',
                    html_column = '<div class="table_content">',
                    pagenation = null;

                //设置表格Title
                $.each(column, function(keyc, vc){
                    $.each(vc, function(k, v){
                        html_head += '<li class="li_wrap">'+v+'</li>';
                    })
                })
                html_head += '<li class=" opcity">操作</li></ul></div>';

                //加载数据，拼接表格行
                new Rquest(baseurl, methodurl, param, false,
                    function (response) {
                        if(response && response.validate && response.data){

                            var data = response.data;
                            pagenation = response.pagenation;
                            if(data.length == 0){
                                html_column = '<ul class="ul_wrap no_content">暂无数据</ul></div>';
                            }else{
                                $.each(data, function(datakey, datav){

                                    html_column += '<ul class="ul_wrap">';
                                    $.each(column, function(keyc, vc){
                                        $.each(vc, function(k, v){
                                            html_column += '<li class="li_wrap">'+datav[k]+'</li>';
                                        })
                                    })
                                    html_column += '<li class=" opcity">'+
                                        '<span class="edit" value="" onclick="update(\''+datav['id']+'\');">修改</span>'+
                                        '<span class="delete" onclick="remove(\''+datav['id']+'\');">删除</span>'+
                                        '</li></ul>';
                                });
                            }
                        }
                    },
                    function () {
                        console.log("error");
                    }).ajaxpost();

                //渲染到页面
                html_column += '</div>';
                console.log(pagenation);
                $(element).html('');
                $(element).append(html_head).append(html_column).append('<div class="table_pagenation"><ul class="pageWrap">\n' +
                    '<li class="page_text " id="page_pre">上一页</li>\n' +
                    '<li class="page_text page_text_active page_no">1</li>\n' +
                    '<li class="point_pre">...</li>\n' +
                    '<li class="page_text  page_no">2</li>\n' +
                    '<li class="point_next">...</li>\n' +
                    '<li class="page_text  page_no">5</li>\n' +
                    '<li class="page_text " id="page_next">下一页</li>\n' +
                    '<li class="page_total ">共<span class="total_num">'+pagenation.dataCount+'</span>页</li></ul></div>');

                //绑定分页事件
                if(null == param){
                    param = {};
                }
                param['pagenation'] = pagenation;
                $("#page_pre").click(
                    [element, column, param, baseurl, methodurl],
                    function (event) {

                    console.log(event);
                });
            }
        }
    }

    Table.fn.init.prototype = Table.fn;
    return Table;
})();
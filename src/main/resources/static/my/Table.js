/**
 * 请求封装类
 */
var Table = (function(window) {

    var Table = function(element, column, data) {
        return new Table.fn.init(element, column, data);
    }

    Table.fn = Table.prototype = {
        constructor: Table,
        init: function(element, column, data) {

            this.renderingTable= function () {

                var html_head = '<ul class="ul_wrap title">', html_column = '';

                //设置表格Title
                $.each(column, function(keyc, vc){
                    $.each(vc, function(k, v){
                        html_head += '<li class="li_wrap">'+v+'</li>';
                    })
                })
                html_head += '<li class=" opcity">操作</li></ul>';

                //拼接表格行
                if(data.length == 0){
                    html_column = '<ul class="ul_wrap">暂无数据</ul>';
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
                $(element).html('');
                $(element).append(html_head).append(html_column);
            }
        }
    }

    Table.fn.init.prototype = Table.fn;
    return Table;
})();
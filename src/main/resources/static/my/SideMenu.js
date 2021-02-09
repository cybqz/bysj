let SideMenu = (function(window) {

    let SideMenu = function(element, modelUrl, data) {
        return new SideMenu.fn.init(element, modelUrl, data);
    }

    SideMenu.fn = SideMenu.prototype = {
        constructor: SideMenu,
        init: function(element, data) {
            this.data = data;
            this.element = element;
            this.modelUrl = modelUrl;
            this.defaultOpenModel = null;

            /**
             * 渲染导航栏html
             */
            this.render = function (){
                let html = '';
                let index = 1;
                let defaultIconClass = 'icon_1';

                //获取默认打开的菜单
                if(this.data[0].children && this.data[0].children.length > 0){
                    this.modelUrl = this.data[0].children[0].url;
                }else {
                    this.modelUrl = this.data[0].url;
                }

                $(this.data).each(function (key, value){
                    if(value['iconClass']){
                        defaultIconClass = value['iconClass'];
                    }
                    html += '<li class="lsm-sidebar-item">';
                    if(value.children && value.children.length > 0){
                        html += '<a href="javascript:;" index= "' + index + '">'+
                            '<i class="my-icon lsm-sidebar-icon ' + defaultIconClass + '"></i>'+
                            '<span>' + value.navbar + '</span>'+
                            '<i class="my-icon lsm-sidebar-more"></i></a>';
                    }else {
                        html += '<a href="javascript:;" index= "' + index + '">'+
                            '<i class="my-icon lsm-sidebar-icon icon_1"></i>'+
                            '<span>' + value.navbar + '</span></a>';
                    }

                    if(value.children && value.children.length > 0){
                        html += '<ul>';
                        for(let i in value.children){
                            html += '<li><a href="javascript:;" parentAIndex = "' + index + '" url= "' + value.children[i].url + '" onclick="toModelIndex(this);"><span>' + value.children[i].navbar + '</span></a></li>';
                        }
                        html += '</ul>';
                    }
                    index ++;
                    html += '</li>'
                });

                $(this.element).html('<ul>' + html + '</ul>');
            },
            /**
             * 添加事件
             */
            this.addEvent = function (){
                $('.lsm-scroll').slimscroll({
                    height: 'auto',
                    position: 'right',
                    railOpacity: 1,
                    size: "5px",
                    opacity: .4,
                    color: '#fffafa',
                    wheelStep: 5,
                    touchScrollStep: 50
                });

                $('.lsm-container ul ul').css("display", "none");

                // lsm-sidebar收缩展开
                $('.lsm-sidebar a').on('click',function(){
                    $('.lsm-scroll').slimscroll({
                        height: 'auto',
                        position: 'right',
                        size: "8px",
                        color: '#9ea5ab',
                        wheelStep: 5,
                        touchScrollStep: 50
                    });
                    if (!$('.left-side-menu').hasClass('lsm-mini')) {
                        $(this).parent("li").siblings("li.lsm-sidebar-item").children('ul').slideUp(200);
                        if ($(this).next().css('display') == "none") {
                            //展开未展开
                            $(this).next('ul').slideDown(200);
                            $(this).parent('li').addClass('lsm-sidebar-show').siblings('li').removeClass('lsm-sidebar-show');
                        }else{
                            //收缩已展开
                            $(this).next('ul').slideUp(200);
                            $(this).parent('li').removeClass('lsm-sidebar-show');
                        }
                    }
                });

                //lsm-mini
                $('.lsm-mini-btn svg').on('click',function(){
                    if ($('.lsm-mini-btn input[type="checkbox"]').prop("checked")) {
                        $('.lsm-sidebar-item.lsm-sidebar-show').removeClass('lsm-sidebar-show');
                        $('.lsm-container ul').removeAttr('style');
                        $('.left-side-menu').addClass('lsm-mini');
                        $('.left-side-menu').stop().animate({width : 60},200);
                    }else{
                        $('.left-side-menu').removeClass('lsm-mini');
                        $('.lsm-container ul ul').css("display", "none");
                        $('.left-side-menu').stop().animate({width: 205},200);
                    }

                });

                $(document).on('mouseover','.lsm-mini .lsm-container ul:first>li',function(){
                    $(".lsm-popup.third").hide();
                    $(".lsm-popup.second").length == 0 && ($(".lsm-container").append("<div class='second lsm-popup lsm-sidebar'><div></div></div>"));
                    $(".lsm-popup.second>div").html($(this).html());
                    if(!$(this).children('ul').length){
                        $(".lsm-popup.second").css('width', '180px');
                    }else {
                        $(".lsm-popup.second").css('width', '');
                    }
                    $(".lsm-popup.second").show();
                    $(".lsm-popup.third").hide();
                    let top = $(this).offset().top;
                    let d = $(window).height() - $(".lsm-popup.second>div").height();
                    if(d - top <= 0 ){
                        top  = d >= 0 ?  d - 8 : 0;
                    }
                    $(".lsm-popup.second").stop().animate({"top":top}, 100);
                });

                $(document).on('mouseover','.second.lsm-popup.lsm-sidebar > div > ul > li',function(){
                    if(!$(this).hasClass("lsm-sidebar-item")){
                        $(".lsm-popup.third").hide();
                        return;
                    }
                    $(".lsm-popup.third").length == 0 && ($(".lsm-container").append("<div class='third lsm-popup lsm-sidebar'><div></div></div>"));
                    $(".lsm-popup.third>div").html($(this).html());
                    $(".lsm-popup.third").show();
                    let top = $(this).offset().top;
                    let d = $(window).height() - $(".lsm-popup.third").height();
                    if(d - top <= 0 ){
                        top  = d >= 0 ?  d - 8 : 0;
                    }
                    $(".lsm-popup.third").stop().animate({"top":top}, 100);
                });

                $(document).on('mouseleave','.lsm-mini .lsm-container ul:first, .lsm-mini .slimScrollBar,.second.lsm-popup ,.third.lsm-popup',function(){
                    $(".lsm-popup.second").hide();
                    $(".lsm-popup.third").hide();
                });

                $(document).on('mouseover','.lsm-mini .slimScrollBar,.second.lsm-popup',function(){
                    $(".lsm-popup.second").show();
                });
                $(document).on('mouseover','.third.lsm-popup',function(){
                    $(".lsm-popup.second").show();
                    $(".lsm-popup.third").show();
                });
            },
            /**
             * 打开二级菜单
             */
            this.openLevelTwo = function (){
                let currTag = $("a[url='" + this.modelUrl + "']");
                let parentAIndex = $(currTag).attr("parentAIndex");
                if(parentAIndex && parentAIndex != null){

                    let target = $("a[index='" + parentAIndex + "']");
                    $(target).trigger("click");
                    $(currTag).css("background", "#6e809c");
                }else{

                    //一级菜单处理
                    $(currTag).parent().addClass("lsm-sidebar-show");
                }
            }
        }
    }
    SideMenu.fn.init.prototype = SideMenu.fn;
    return SideMenu;
})();

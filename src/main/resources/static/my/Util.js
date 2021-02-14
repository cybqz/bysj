(function(window, document, $) {
    window.Util = {

        /**
         * 判断字符串是否无值
         */
        isBlankStr: function (val){
            if(undefined == val || null == val || "" === val){
                return true;
            }
        },

        /**
         * 判断字符串是否有值
         */
        isNotBlankStr: function (val){
            return this.isBlankStr(val)?false:true;
        },

        /**
         * 判断对象是否无值
         */
        isBlankObj: function (obj){
            if(undefined == obj || null == obj){
                return false;
            }
            let length = Object.keys(obj).length;
            return length == 0?true:false;
        },
    }
    ///jQ扩展方法js结束//
})(window, document, $);
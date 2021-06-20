/**
 * 请求封装类
 */
let BeastRequest = (function(window, document) {

    let BestRequest = function(baseURL, methodURL, data, async, successCallback, errorCallback) {
        return new BestRequest.fn.init(baseURL, methodURL, data, async, successCallback, errorCallback);
    }

    BestRequest.fn = BestRequest.prototype = {
        constructor: BestRequest,
        init: function(baseURL, methodURL, data, async, successCallback, errorCallback) {

            if(!baseURL || baseURL.replace(/\s*/g,"") == ""){
                baseURL = "";
            }
            if(!methodURL || methodURL.replace(/\s*/g,"") == ""){
                methodURL = "";
            }

            this.requestURL = baseURL + methodURL;
            this.data = data;
            this.async = async;
            this.dataType = "json";

            this.ajaxPost= function () {

                if(this.requestURL && this.requestURL.replace(/\s*/g,"") != ""){
                    let jsonData = null;
                    if(null != this.data){
                        jsonData = JSON.stringify(this.data)
                    }
                    console.log("request: " + this.requestURL +"  with:  " + jsonData);
                    $.ajax({
                        url:this.requestURL,
                        type:"post",
                        data:jsonData,
                        async: this.async,
                        dataType:this.dataType,
                        contentType: "application/json;charset=UTF-8",
                        beforeSend: function (XHR){
                            //console.log("request before:  " + XHR);
                        },
                        complete: function (XHR, TS){
                            //console.log("request complete");
                        },
                        success:function(data){
                            if(data.show && !data.validate){
                                tips(data.msg);
                            }
                            successCallback(data);
                        },
                        error:function(xhr, textStatus, errorThrown){
                            console.log("request error:  " + textStatus);
                            if(null != errorCallback){
                                errorCallback(xhr, textStatus, errorThrown);
                            }
                        }
                    })
                }else{
                    console.log("BestRequest url is empty");
                }
            }
        }
    }

    BestRequest.fn.init.prototype = BestRequest.fn;
    return BestRequest;
})(window, document);
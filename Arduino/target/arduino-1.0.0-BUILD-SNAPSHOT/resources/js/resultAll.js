/**
 * ResultAll 
 * by Joon : 2017.06.20
 */

(function ( $, undefined ) 
{
    "use strict";

	var ResultAll = ResultAll || ( function ()
    {
        var thisApp = this;
        
        console.log("adsf");

        function initOnce()
        {

            console.log("adsf");
            dataLoad();

            setApp(0);
        }

        function dataLoad()
        {
            var url = thisApp.options.url;
            $.ajax({
                url: url,
                dataType:'json', //
                success: function(data)
                {
                    //console.log("success");
                    console.log(data);
                    thisApp.data = data;
                    thisApp.maxQue = data.all.length;
                    setApp(0);
                },
                error:function(e)
                {
                    console.log("error:", e.status);
                }
            });
        }

        // 문제 마크업 셋팅;
        function setApp(idx)
        {
            var appendHtml = "";
            var ani_delay = 100;
            var ani_cnt = 0;
            var ani_duration = 500;
            var ani_easing = "easeInOutCubic";
            var targetTop, maxTop = 160;

            thisApp.nowQue = idx;
            
            var dataAll = thisApp.data.all[idx];
            var dataMy = thisApp.data.my[idx];

            var thisEle = thisApp.element;

            thisEle.find(".title").text("결과 - "+dataAll.title);

            // 그래프 초기화;
            thisEle.find(".graph_name").empty();
            thisEle.find(".graph_box").empty();

            // 그래프 박스 넓이, 위치 값 계산
            var wid_max = 530; //
            var wid = 13; // 박스 넓이
            var wid_dum1 = 2; 
            var length = dataAll.value.length;
            var wid_x = Math.round(wid_max/length);


            for(var i=0; i<length; i++)
            {
                // 그래프 텍스트
                appendHtml = $('<span>'+dataAll.name[i]+'</span>');
                thisEle.find(".graph_name").append(appendHtml);
                //appendHtml.hide();
                appendHtml.css("position","absolute");
                var txtWid = parseInt(appendHtml.css("width"));
                var name_tx = parseInt((wid_x/2) + (i*wid_x) - txtWid/2);
                appendHtml.css("left", name_tx);
                //appendHtml.show();

                // 그래프 박스 블루 - 전체
                appendHtml = $('<li class="box blue_box"></li>');
                appendHtml.clearQueue();
                appendHtml.stop();
                var box_tx = parseInt(wid_x/2) + (i*wid_x) - (wid+wid_dum1);
                appendHtml.css("left", box_tx).css("top", maxTop).css("width", wid);
                // appendHtml.css("width", 20);
                thisEle.find(".graph_box.blue").append(appendHtml);
                ani_cnt ++;
                targetTop = maxTop - ((maxTop/5) * dataAll.value[i]);
                
                appendHtml.delay(ani_delay*ani_cnt).animate({top: targetTop}, ani_duration, ani_easing);

                // 그래프 박스 레드 - 내꺼
                appendHtml = $('<li class="box red_box"></li>');
                appendHtml.clearQueue();
                appendHtml.stop();
                box_tx = parseInt(wid_x/2) + (i*wid_x) + (wid_dum1);;
                appendHtml.css("left", box_tx).css("top", maxTop).css("width", wid);
                // appendHtml.css("width", 20);
                thisEle.find(".graph_box.red").append(appendHtml);
                ani_cnt ++;
                targetTop = maxTop - ((maxTop/5) * dataMy.value[i]);
                appendHtml.delay(ani_delay*ani_cnt).animate({top: targetTop}, ani_duration, ani_easing);
            }
         }

        $.extend(this, 
        {
            /** @public init
             */
            init : function (element, options)
            {
                this.element = element;
                this.options = {url:null};
                $.extend(this.options, options);
                
                this.nowQue = 0;
                this.maxQue = 0;

                initOnce.call(this);
            },

            /** @public prev
             */
            prev : function ()
            {
                if(this.nowQue > 0)
                {
                    setApp(this.nowQue-1);
                }else{
                    // console.log("첫페이지임");
                }
            },

            /** @public next
             */
            next : function ()
            {
                if(this.nowQue < this.maxQue -1)
                {
                    setApp(this.nowQue+1);
                }else{
                    // console.log("마지막페이지임");
                }
            }

        });

        this.init.apply(this, arguments);

        return this;
    });


    window.ResultAll = ResultAll;
})(jQuery);

/**
 * ResultAll 
 * by Joon : 2017.06.20
 */

(function ( $, undefined ) 
{
    "use strict";

	var ResultAvg = ResultAvg || ( function ()
    {
        var thisApp = this;

        function initOnce()
        {
            dataLoad();
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
            
            var dataAll = thisApp.data.all;
            var dataMy = thisApp.data.my;

            var thisEle = thisApp.element;

            // thisEle.find(".title").text("결과 - "+dataAll.title);

            // 그래프 초기화;
            thisEle.find(".graph_name").empty();
            thisEle.find(".graph_box").empty();

            // 그래프 박스 넓이, 위치 값 계산
            var wid_max = 570; //
            var wid = 21; // 박스 넓이
            var wid_dum1 = 2; 
            var length = dataAll.length;
            var wid_x = Math.round(wid_max/length);


            for(var i=0; i<length; i++)
            {
                // 그래프 텍스트
                appendHtml = $('<span>'+dataAll[i].title+'</span>');
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
                targetTop = maxTop - ((maxTop/5) * dataAll[i].value);
                // appendHtml.data("value", dataAll[i].value);
                appendHtml.attr("data-value", dataAll[i].value);
                appendHtml.delay(ani_delay*ani_cnt).animate({top: targetTop}, ani_duration, ani_easing);
                appendHtml.on("mouseover mouseout", followTextView);

                // 그래프 박스 레드 - 내꺼
                appendHtml = $('<li class="box red_box"></li>');
                appendHtml.clearQueue();
                appendHtml.stop();
                box_tx = parseInt(wid_x/2) + (i*wid_x) + (wid_dum1);;
                appendHtml.css("left", box_tx).css("top", maxTop).css("width", wid);
                // appendHtml.css("width", 20);
                thisEle.find(".graph_box.red").append(appendHtml);
                ani_cnt ++;
                targetTop = maxTop - ((maxTop/5) * dataMy[i].value);
                // appendHtml.data("value", dataMy[i].value);
                appendHtml.attr("data-value", dataMy[i].value);
                appendHtml.delay(ani_delay*ani_cnt).animate({top: targetTop}, ani_duration, ani_easing);
                appendHtml.on("mouseover mouseout", followTextView);
            }

            // flow 텍스트;
            var follow_text = $('<span class="follow_text">0</span>');
            follow_text.hide();
            $("#contents").append(follow_text);
            follow_text.on("mouseover",function(event){
                event.stopPropagation;
                event.preventDefault;
            })

            $(window).on("mousemove",function(e)
            {
                //var left = e.pageX, top = e.pageY;
                var left = e.pageX - $("#contents").offset().left;
                var top = e.pageY - $("#contents").offset().top - $(".follow_text").height()-20;
                //$(".follow_text").text(left +" / "+ top);
                $(".follow_text").css({left:left, top:top});
            })

        }

        function followTextView(event)
        {
            var ele = event.target;
            var value = $(ele).data("value");

            switch(event.type){
                case "mouseover":
                    $(".follow_text").text(value);
                    $(".follow_text").stop().fadeIn();
                break;
                case "mouseout":
                    $(".follow_text").stop().hide();
                break;
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
            }

        });

        this.init.apply(this, arguments);

        return this;
    });


    window.ResultAvg = ResultAvg;
})(jQuery);

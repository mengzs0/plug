/**
 * Question 
 * by Joon : 2017.06.18
 */

(function ( $, undefined ) 
{
    "use strict";

	var Question = Question || ( function ()
    {
        var question = this;

        function initOnce()
        {
            dataLoad();
        }

        function dataLoad()
        {
            var xml_url = "./data/questionList.xml";
            $.ajax({
                url: xml_url,
                dataType:'xml', //
                success: function(xml)
                {
                    //console.log("success");
                    //console.log(xml);
                    question.xml = xml;
                    question.maxQue = $(xml).find("group").length;

                    for(var i=0; i<question.maxQue; i++)
                    {
                        if(i>0)
                        {
                            question.element.clone().appendTo(".question_wrap");
                        } 
                        setQuestion(i);
                    }
                    showQuestion(0);
                },
                error:function(e)
                {
                    console.log("error:", e.status);
                }
            });
        }

        // 문제 마크업 셋팅;
        function setQuestion(idx)
        {
            //question.nowQue = idx;

            var xml = question.xml;
            var group = $(xml).find("group")[idx];
            var title = $(group).find("title").text();
            question.titleArr.push(title);
            var title_text = (idx+1)+". "+title;
            $(".s_title").find("span").text(title_text);
            
            var principles = $(group).find("principles");
            var questionXml = $(group).find("question");

            // 기본 마크업
            var contEle = $(".question_cont").eq(idx);
            contEle.empty();

            // contEle.append("<div class='rule'></div>");
            contEle.append("<div class='prop'></div>");
            contEle.find(".prop").append('<ul class="prop_list">');
            
            var principlesTop = [];
            var principlesCount = [];
            
            //contEle.show();
            contEle.css("opacity", 0);
            contEle.css("display", "block");
            // contEle.animate({
            //     opacity:1
            //     },500,function(){
            // });
            
            
            // 속성;
            for(var i=0; i<questionXml.length; i++)
            {
                var li = $("<li>");
                li.append('<p class="prop_txt tBox">');
                var radio_list = $('<div class="radio_list">');
                for(var j=0; j<6; j++)
                {
                    var n1 = idx+1;
                    var n2 = i+1;
                    var n3 = 5 - j;
                    var name = "q"+n1+"_"+n2;
                    var id = "q"+n1+"_"+n2+"_"+n3;
                    
                    radio_list.append('<input type="radio" id="'+id+ '" name="'+name+'" value='+n3+'><label for="'+id+'">'+n3+'</label>');
                }

                var text = $(questionXml[i]).text();
                var numText = (idx+1)+"."+(i+1);
                var cText = text;
                
                li.find(".prop_txt").append('<span class="num"></span>');
                li.find(".prop_txt .num").text(numText);
                li.find(".prop_txt").append($('<span>'+cText+'</span>'));
                
                li.append(radio_list);
                contEle.find(".prop_list").append(li);

                // 원칙 top 위치 확인
                var principles_cnt = parseInt($(questionXml[i]).attr('num'));
                if(principles_cnt > 0)
                {
                    principlesTop.push(li.position().top);
                    principlesCount.push(i);
                }
            }

            var li = $("<li>");
            var tBox = $('<div class="prop_txt tBox textarea">');
            var ta = $('<textarea id="ta'+(idx+1)+'" name="ta'+(idx+1)+'" rows="5" maxlength="200" placeholder = "' + title + '에 대해 개인적인 의견을 자유롭게 기술해주세요."></textarea>');
            tBox.append(ta);
            li.append(tBox);
            contEle.find(".prop_list").append(li);

            // 원칙;
            for(var i=0; i<principles.length; i++)
            {
                var list = $('<p class="rule_txt tBox">');
                list.text($(principles[i]).text());
                // list.css("top", principlesTop[i]);
                contEle.find(".prop_list li").eq(principlesCount[i]).addClass("line").prepend(list);
            }

            // 라디오 클릭;
            contEle.find("input[type=radio]").on("change",function(e)
            {
                //console.log($(this).val());
            })
        }

        function showQuestion(idx){
            question.nowQue = idx;

            var group = $(question.xml).find("group")[idx];
            var title = $(group).find("title").text();
            var title_text = (idx+1)+". "+title;
            $(".s_title").find("span").text(title_text);

            $(".question_cont").hide();

            var contEle = $(".question_cont").eq(idx);

            contEle.css("opacity", 1);
            contEle.fadeIn();
            $(".question_wrap").scrollTop(0);
        }


        $.extend(this, 
        {
            /** @public init
             */
            init : function (element, options)
            {
                this.element = element;
                this.options = {};
                $.extend(this.options, options);

                this.nowQue = 0;
                this.maxQue = 0;
                this.titleArr = [];

                initOnce.call(this);
            },

            /** @public prev
             */
            prev : function ()
            {
                if(this.nowQue > 0)
                {
                    showQuestion(this.nowQue-1);
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
                    showQuestion(this.nowQue+1);
                }else{
                    // console.log("마지막페이지임");
                }
            },

            /** @public go
             */
            go : function (idx)
            {
                showQuestion(idx);
            },

            /** @public getChecked
             */
            getChecked : function ()
            {
                // var data = {"q1":{"1.1)":5,"1.2)":4},"q2":{}};
                //{"title":"안전 리더십","name":["1.1","1.2","1.3","1.4","1.5","1.6","1.7","1.8","원칙1"],"value":[5,1,2,3,4,5,6,4,3]}

                var returnData = {"data":null,"sum":0, "dataNull":false};
                var data = [];
                var sum = 0;
                var dataNull = false;
                $(".question_cont").each(function(n1)
                {
                    var obj = {};
                    obj.title = question.titleArr[n1];
                    obj.name = [];
                    obj.value = [];

                    var queEle = $(this);
                    $(this).find(".radio_list").each(function(n2)
                    {
                        var c_name = "q" + (n1+1) +"_" + (n2+1);
                        var name = (n1+1) +"." + (n2+1); //queEle.find(".prop_txt .num").text();
                        obj.name.push(name);
                        var value = $("input:radio[name='"+c_name+"']:checked").val();
                        obj.value.push(parseInt(value));
                        if(value == undefined){
                            if(!dataNull)
                            {
                                console.log(c_name);
                                returnData.nullIdx = n1;
                                returnData.nullId = c_name;
                            }
                            dataNull = true;
                        }else{
                            sum = sum + parseInt(value);
                        }
                    })
                    obj.name.push("text");
                    //var ta_value = encodeURIComponent($("textarea[name='ta"+(n1+1)+"']").val());
                    var ta_value = $("textarea[name='ta"+(n1+1)+"']").val();
                    // console.log(decodeURIComponent(ta_value));
                    obj.value.push(ta_value);
                    data.push(obj);
                })
                returnData.dataNull = dataNull;
                returnData.data = data;
                returnData.sum = sum;
                console.log(returnData.data);
                return returnData;
            }

        });

        this.init.apply(this, arguments);

        return this;
    });


    window.Question = Question;
})(jQuery);

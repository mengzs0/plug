// common.js
function sendData(jsonData, urlStr, callback)
{
    $.ajax({
        type : "POST",
        dataType : "json",
        url : urlStr,
        data : {
                json : jsonData
        },
        success : function(data) {
            console.log("success");
            console.log(data);
            if(callback) callback(data);
        },
        error : function(e){
            console.log("error: ",e.status);
        }
    })
}

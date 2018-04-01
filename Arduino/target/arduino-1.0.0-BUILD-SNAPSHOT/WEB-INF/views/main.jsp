<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>
<style>
body{
  text-align:center;
}
body:before{
  content:'';
  height:100%;
  display:inline-block;
  vertical-align:middle;
}
.rbutton{
  background:#ffebcd;
  color:#000;
  border:none;
  position:relative;
  height:60px;
  font-size:1.6em;
  padding:0 2em;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.rbutton:hover{
  background:#000;
  color:#ffebcd;
}
.rbutton:before,.rbutton:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: #ffebcd;
  transition:400ms ease all;
}
.rbutton:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.rbutton:hover:before,.rbutton:hover:after{
  width:100%;
  transition:800ms ease all;
}



.bbutton{
  background:#1AAB8A;
  color:#fff;
  border:none;
  position:relative;
  height:60px;
  font-size:1.6em;
  padding:0 2em;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.bbutton:hover{
  background:#fff;
  color:#1AAB8A;
}
.bbutton:before,.bbutton:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: #1AAB8A;
  transition:400ms ease all;
}
.bbutton:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.bbutton:hover:before,.bbutton:hover:after{
  width:100%;
  transition:800ms ease all;
}
</style>
<script type="text/javascript">
$(function(){
	

	
	
	$('#rOn').on("click",function() {
		alert("RED ON");
		$("#pinNo").val("3");
		$("#pinVal").val("1");
		$("#main").submit();
	}); 
	$('#rOff').on("click",function() {
		alert("RED OFF");
		$("#pinNo").val("3");
		$("#pinVal").val("0");
		$("#main").submit();
	}); 
	$('#bOn').on("click",function() {
		alert("BLUE ON");
		$("#pinNo").val("6");
		$("#pinVal").val("1");
		$("#main").submit();
	}); 
	$('#bOff').on("click",function() {
		alert("BLUE OFF");
		$("#pinNo").val("6");
		$("#pinVal").val("0");
		$("#main").submit();
	}); 
	
});

</script>
</head>
<body>
<h1>
	온 습도 측정
</h1>
<h2>
<B><P>온도 : ${resp.temp} <sup>o</sup>C</P></B>
<B><P>습도 : ${resp.humid} %</P></B>
</h2>
<button class="rbutton" id="rOn">Red ON</button>
<button class="rbutton" id="rOff">Red OFF</button>
<BR/><BR/>
<button class="bbutton" id="bOn">BLUE ON</button>
<button class="bbutton" id="bOff">BLUE OFF</button>

<form action="/main" id="main" method="POST" >
<input type="hidden" value="" name="pinNo" id="pinNo">
<input type="hidden" value="" name="pinVal" id="pinVal">
</form>
</body>
</html>

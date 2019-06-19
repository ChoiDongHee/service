<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ChoiDongHee</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">Web Socket Test 2016 - 08 - 04 </a>
        </div>
        <div class="collapse navbar-collapse">

        </div>
      </div>
</div>


<div class="container">
      <div style="margin-top: 100px;">        
       <div id="info"class="alert alert-danger" role="alert"><strong>STATE:</strong> Not yet connected </div>
      </div>
      <div class="input-group">
      <span class="input-group-addon" id="basic-addon1">SAID</span>
      <input type="text" id="input_said" class="form-control" value='TT1508030' aria-describedby="basic-addon2">
      <span class="input-group-addon" id="basic-addon1">Domain</span>
	   <input type="text" id="input_url" class="form-control" value='ws://localhost:8080/websocket/' aria-describedby="basic-addon2">
	   <span class="input-group-addon" id="basic-addon2"><a id='btnCon' href="#">Connect Server</a></span>
	   
	</div>
	<BR>
	<textarea id="input_server_msg"readonly cols="50" rows="20"  class="form-control col-xs-12">...</textarea>
	<BR>
	<!- 경고차->
	<br><br>	
  	<div class="alert alert-success" style="display:none" role="alert"><strong>Warning!</strong> Better check yourself, you're not looking too good</div>
  	<div class="alert alert-danger" style="display:none" role="alert"><strong>Warning!</strong> Better check yourself, you're not looking too good</div>
  	<!- 경고차->	  	
	  <div style="margin-top: 100px;">      
	    <div class="input-group">
	      <div class="input-group-btn">
	     <div class="container">
			<h3>Send Message</h3>
			<textarea id='input_msg'  class="form-control col-sm-5" rows="5"></textarea>
			<span class="input-group-addon"><a id='btnSend' href="#">Send Message</a></span>
		</div>
	  <br>

</div>
</body>
</html>
<script type="text/javascript">

	var wsurl = "";
	var websocket = "";
	

	$(document).ready(function() {   
		
		inputSetSaid();	
		
		$('#btnCon').click(function(){
			if(websocket == "" || websocket.readyState==3){
				var url = $("#input_url").val();
				var said = $("#input_said").val();
				wsurl = url+"?said="+said;
				//wsurl =url
				onOpen_WriteToScreen(wsurl);				
				connetServer(wsurl);
			}else{
				
				$('#info').attr('class','alert alert-danger');
				$('#btnCon').html("Connect Server");
				websocket.close();
			}
			
		});
		
		$('#btnSend').click(function(){
			doSend();
			
		});
		
	    $("#input_msg").keydown(function (key) { 
		        if(key.keyCode == 13){
		            doSend();
		        }
		 
		 });

		$("#input_msg").focus(function(){		
			inputSetSaid();	
		});
		
		 $("#input_server_msg").change(function() {
			textBoaxScrollTop();
		});
		
	});
	
function connetServer(wsUri) {
    
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) {   
    	
    	onOpen_WriteToScreen(wsUri);	
    	
    	 websocket.onmessage = function(evt) {
    		 onMessage(evt.data)
    		 
         };
         websocket.onerror = function(evt) {
        	 onClose_WriteToScreen();
         };
         
         websocket.onclose = function() {
        	 onClose_WriteToScreen();
		}

    };
}

function inputSetSaid(){
	var org_said =$("#input_said").val();
	//TT1508030
	if(org_said.length <= 9 ){
		org_said = org_said + String(generateRandom(2,100));
	}
	
	$("#input_said").val(org_said);
	//$("#input_msg").val($("#input_said").val()+"[Say]");
}

function onOpen_WriteToScreen(wsurl){
	var div_msg = "<strong>STATE:</strong> Connection Success ... "+ wsurl ;
	$('#info').html(div_msg);
	$('#info').attr('class','alert alert-success');
	$('#btnCon').html("Disconnect Server");
}

function textBoaxScrollTop(){
	var psconsole = $('#input_server_msg');
	if(psconsole.length)
	   psconsole.scrollTop(psconsole[0].scrollHeight - psconsole.height());
}
   
function onClose_WriteToScreen(){
	var div_msg = "<strong>STATE:</strong> Closed Connection " ;
	$('#info').html(div_msg);
	$('#info').attr('class','alert alert-danger');
	$('#btnCon').html("Connect Server");
}

function onMessage(msg) {
	
	var oldMsg = $("#input_server_msg").val();
	msg = oldMsg +"\r\n"+ msg;
	
	$("#input_server_msg").val(msg);
	textBoaxScrollTop();
}
function onError(evt) {
    writeToScreen('ERROR: ' + evt.data);
}
function doSend() {
	
	if(websocket == "" || websocket.readyState == 3){
		
	}else{
		var tmp_msg = $("#input_msg").val();
		if(tmp_msg.length > 0 ){
			websocket.send(tmp_msg);
			$("#input_msg").val('');
			
		}
	}
	
}
function writeToTitleScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    
    output.appendChild(pre);
}
function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;    
    output.appendChild(pre);
}
function generateRandom(min, max) {
	   var ranNum = Math.floor(Math.random()*max) + min;
	      return ranNum;
}


</script>
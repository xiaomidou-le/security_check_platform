var nameid = $('.username').attr('id');	
//跑马灯消息
$.ajax({        			
   type: 'GET',
   data: {
   		userid:nameid  
   },
   url: "/security_check_platform/system/querySystemmessageSend.do",		   
   success: function(data) {	
   	   if(data != "0"){
		   var dataObj=eval("("+data+")");//转换为json对象				   
		   var list = dataObj.rows;	
		   //$('#tishi').html("系统消息："+list.messageContent)			   
	   }  
 	}
}) 
		
//websocket消息推送
var webSocket = function(id) {
  var webSocket;
//  webSocketPath = "ws:IP:port/websocket";//wss:SSL
  webSocketPath = "ws:localhost:8081/XDL_CRM/websocket";//wss:ws+ssl   
  														//WS:webservice
  try {
      webSocket = new ReconnectingWebSocket(webSocketPath);
      webSocket.debug = true;
      webSocket.timeoutInterval = 5400;
  } catch (e) {
      //IE8使用定时器更新数据
      setInterval(function() {
          living.onTimer();
          chat.onTimer();
      }, 2000);
      return;
  }
  webSocket.close = function() {
	  webSocket.close();
  };
//  window.onbeforeunload = function() {
//      webSocket.close();
//  };
  webSocket.onopen = function(event) {
	  var urseid = "1_"+id;
	  webSocket.send(urseid);
  };
  
  webSocket.onmessage = function(event) {		  			      
      var objdata = eval('(' + event.data + ')');		      
      var objId = objdata.objId;
      var objdeptid = objdata.deptid;		     
      
      if(objId != "" && objId != null){
      	 if(id == objId){		      	
      	     //$('#message').css('bottom','20px');
      	     //$('#messcontent').html(objdata.content);
      	     alert(objdata.content)
      	     //setTime();
     	 }
      }else if(objdeptid != null && objdeptid != "" && (objdeptid == 3 || objdeptid == 4)){		      
      	 //$('.message').css('bottom','20px').html(objdata.content);
      	 alert(objdata.content)
      	 //setTime()
      }
  };
}
webSocket(nameid);





















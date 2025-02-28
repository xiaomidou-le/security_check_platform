$(window.parent.document).find("#detailiframe").load(function(){
    var main = $(window.parent.document).find("#detailiframe");
    var thisheight = $(document).height();
    main.height(thisheight);
});
var todaydate = new Date();
var todayStarttime = todaydate.getFullYear() + "-" + (todaydate.getMonth() + 1) + "-"+ todaydate.getDate()+ " "+todaydate.getHours()+":"+todaydate.getMinutes()+":"+todaydate.getSeconds();
$('#dealtime').val(todayStarttime)
/* 初始化时间插件*/
/*$('#dealtime').datetimepicker({
	lang: 'ch',
	format:"Y-m-d H:m:s", //格式化日期
	validateOnBlur:false //删除选中的时间
});*/
$('#ch-dealtime').datetimepicker({
	lang: 'ch',
	format:"Y-m-d H:m:s", //格式化日期
	validateOnBlur:false //删除选中的时间
});


$("#ch-courseid").change(function() {
	var courseid = $('#ch-courseid option:selected').val();
	if(courseid==""||courseid==null){
		$('#ch-subjectid').html('');
	}else{
		checkRolename("#ch-subjectid",courseid,'../subject/querySubject.do');
	}
	
});
/*根据课程ID，查询课程下的科目*/
function checkRolename(ele,courseid,url) {
    var clearname = $(ele);
    clearname.html(''); //清空原有的选项    
    $.ajax({        			
	   type: 'GET',
	   data: {
		   courseid:courseid
	   },
	   url: url,		   
	   success: function(data) {
		   var dataObj=eval("("+data+")");//转换为json对象
		   var list = dataObj.rows;			   
	        $.each(list, function(index, array) {
	        	//循环获取数据
                var subjectid = array.subjectid|| "";	
                var subjectname = array.subjectname || "";
	        	var checkbox = '<label class="checkbox-inline">' + '<input type="checkbox" value="' + subjectid + '"><span>'+ subjectname +'</span></label>';
	        	clearname.append(checkbox);
		    });
	   }
	});
}
var mydate = new Date();
	var hftime = mydate.getFullYear() + "-" + (mydate.getMonth() + 1) + "-"+ mydate.getDate()+" "+mydate.getHours()+":"+ mydate.getMinutes();
	$('#timeid').html(hftime);
	
var userid = $("#userid").val();//用户Id
var deptid = $("#deptid").val();//部门Id
var roleid = $("#roleid").val();//角色Id
var studentId = $('#studentId').val();//资源Id
var deldata = null;

/*增加回访记录*/
function addrecord(){	
	var visitRecord = $('#visitRecord').val();
	$.ajax({        			
	   type: "POST",
	   data: {
		   userid:userid,		   
		   studentId:studentId,
		   visitRecord:visitRecord			   
	   },
	   url: '../student/addStudentVisitrecord.do',
	   success: function(data) {
		   if(data=="success"){
			  var tr ="<tr>"+
				"<td class='text-primary col-md-2'>"+hftime+"</td>"+
				"<td class='text-primary col-md-6'>"+visitRecord+"</td>"+
				"</tr>"
		  	$('#recoredtable').prepend(tr);
		   }		  
	   }
	});
}
function check(dealtime,courseid,dealprice,ishavenetedu,netedumoney,remituser){
	 var reg = /^(([0-9]+)|([0-9]+\.[0-9]{1,2}))$/;  
	 var flag = true;
	 if(dealtime==""||dealtime==null){
		 $('#dealtimeError').html("成交时间不能为空");
		 flag = false;
	 } else {
		 $('#dealtimeError').html("")
	 }	 
	 if(courseid==""||courseid==null){
		 $('#courseError').html("成交课程不能为空");
		 flag = false;
	 } else {
		 $('#courseError').html("")
	 }	 
	 if(dealprice==""||dealpriceError==null){  
		 $('#dealpriceError').html("成交金额不能为空");
		 flag = false;
	 }
	 if(!reg.test(dealprice)){  
		 $('#dealpriceError').html("请输入数字");
		 flag = false;
	 }   
	 if(dealprice<0){  
		 $('#dealpriceError').html("成交金额需大于0");
		 flag = false;
	 } 	
	 //代汇款人
    if(remituser == null || remituser == ""){               
       $("#remituserError").html("内容不能为空");
       flag = false;                  
    }else {
    	 $("#remituserError").html("");
    }
	if(ishavenetedu=="1"){
		 if(netedumoney =="" || netedumoney == null){ 
			 $('#netedumoneyError').html("金额不能为空");
			 flag = false;
		 }else if(!reg.test(netedumoney)){
			 $('#netedumoneyError').html("请输入数字");
			 flag = false;
		 }else if(netedumoney<0){
			 $('#netedumoneyError').html("金额需大于0");
			 flag = false;
		 }else{
			 $('#netedumoneyError').html("");
		 } 
	 }	 
	return flag
}
/*添加新增成交记录和根据状态控制不可编辑*/
var studentstate = $.trim($('#studentstate').val());
if(studentstate =="新增" || studentstate =="已成交" || studentstate =="已退回"){
	if (studentstate=="新增"){
		$('#saveinfor').css('display','block')
	}
}else {
	$('input,select,textarea,button').attr('disabled',true);
	$('.xiugai span').removeClass('changedit');
}
/*添加成交记录*/
function adddeal(){
	var dealtime = $('#dealtime').val();
	var courseid = $('#courseid option:selected').val();
	var courseName = $('#courseid option:selected').html();
	var remituser = $('#remituser').val();
	var subjectid = [];
	var subjectname = [];
	$("#subjectid input[type='checkbox']:checked").each(function() {
		var boxid = $(this).val();
		var name = $(this).siblings().text();
		subjectid.push(boxid);
		subjectname.push(name);		
	});
	var dealprice = $('#dealprice').val();
	var ishavenetedu = $("input[name='ishavenetedu']:checked").val();
	var isnetmoney = ishavenetedu;
	var netedumoney = $('#netedumoney').val();
	var preferinfo = $('#preferinfo').val();
	var isOnlineBuy = $("input[name='isOnlineBuy']:checked").val();
	var onlineBuy = isOnlineBuy;
	subjectids = subjectid.join(',');
	
	if(isnetmoney == "0"){
		isnetmoney = "无"
	}else if(isnetmoney == "1"){
		isnetmoney = "有"
	}
	deldata = {
	   userid:userid,
	   studentId:studentId,
	   courseid:courseid,
	   dealtime:dealtime,
	   subjects:subjectids,
	   dealprice:dealprice,
	   ishavenetedu:ishavenetedu,
	   isOnlineBuy:onlineBuy,
	   netedumoney:netedumoney,
	   preferinfo:preferinfo,
	   remituser:remituser
	}
	if(check(dealtime,courseid,dealprice,ishavenetedu,netedumoney,remituser)){
		$('.themisWrap').css('display','block');
		$('#isnetmoney').html(isnetmoney);
		$('#netmoney').html(netedumoney+"元");
	}
	
}
function dernetwork(){
	$.ajax({        			
	   type: "POST",
	   url: '../student/addDealrecord.do',
	   data: deldata,
	   success: function(data) {
		   if(data=="success"){
			   alert("成交记录添加成功")
			   $('.xiugai').remove();
			   window.location.reload();
		   }else if(data=="-1"){
			   alert("匹配失败，请联系行政部");
		   }
	   }
	});
}
/*保存详情*/
function saveDetails(){
	var studentName= $('#studentName').val();
	var phone= $('#phone').val().trim();
	var idCard= $('#idCard').val();
	var school= $('#school').val();
	var education= $('#education').val();
	var LCWname= $('#LCWname').val();
	var LCWpassword= $('#LCWpassword').val();
	var email= $('#email').val();
	var company= $('#company').val();
	var position= $('#position').val();
	var companyTel= $('#companyTel').val();	
	var tel = $('#tel').val();
	var weixin = $('#weixin').val();
	var qq = $('#qq').val();	
	var address = $("#address option:selected").val();
	var banci = $("#banci option:selected").val(); 
	var companyAddr = $('#companyAddr').val();
	var isjieye = $('#isjieye').val();
	
	$.ajax({        			
	   type: "POST",
	   data: {
		   userid:userid,
		   studentId:studentId,		   
		   studentName:studentName,
		   phone:phone,		   
		   idCard:idCard,		   
		   school:school,
		   education:education,		   
		   LCWname:LCWname,
		   LCWpassword:LCWpassword,		   
		   email:email,
		   company:company,		   
		   position:position,
		   companyTel:companyTel,
		   tel:tel,
		   weixin:weixin,
		   qq:qq,
		   address:address,
		   banci:banci,
		   companyAddr:companyAddr,
		   isjieye:isjieye
		  
	   },
	   url: '../student/addStudent.do',
	   success: function(data) {
		   	if(data=="success"){		   		
		   		$.winTip({
					title: "提示~~~",
					msg: "信息保存成功",
					src:"./system/img/tishi.png"
				});		   		
		   }else if(data=="8"){
			   $.winTip({
					title: "提示~~~",
					msg: "当前用户没有操作权限",
					src:"./system/img/tishi.png"
				});
		   }
		   
	   }
	});
}
function reload(){
	window.location.href="../sales/resourcesMang.do";
}
$('#quxiao').on('click',function(){
	$('.themisWrap').css('display','none');
})
/*修改成交记录的数据*/
var dealrecordId = null;
$('.changedit').click(function(){
	dealrecordId = $(this).attr('data-id');
	$('#changedeal').show();
	changeText(dealrecordId)
})
/*修改成交记录的数据*/
function changeText(objTag){
	
	var dealtime=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(0).html();
	var courseName=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(1).html();
	var courseid=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(1).attr('data-id');
	var subjectname=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(2).html();
	var subjectid=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(2).attr('data-id');
	var dealprice=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(3).html();
	var ishavenetedu=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(4).html();
	var netedumoney=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(5).html();
	var preferinfo=$('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(6).html();
	var remituser = $('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(8).html();
	var isOnlineBuy = $('#dealtable tr[data-id='+'"'+objTag+'"'+']').children('td').eq(7).html();
	
	if($.trim(ishavenetedu)=="有"){
		ishavenetedu="1";
	}else if($.trim(ishavenetedu)=="无"){
		ishavenetedu="0";
	}	
	if($.trim(isOnlineBuy)=="是"){
		isOnlineBuy="1";
	}else if($.trim(isOnlineBuy)=="否"){
		isOnlineBuy="0";
	}
	
	$('#ch-dealtime').val(dealtime);
	$('#ch-courseid option:selected').html(courseName);
	$('#ch-courseid option:selected').val(courseid);
	$('#ch-dealprice').val(dealprice);
	$('#ch-remituser').val(remituser);	
	/*$('#ch-subjectid').html('');
	var subjectnames= new Array(); //定义一数组 
	subjectnames=subjectname.split("、"); //字符分割 	
	if (subjectnames.length<0){
		console.log(0)
	}else {
		for (i=1;i<subjectnames.length ;i++ ){ 
			
		}
	}
	var checkbox = '<label class="checkbox-inline">' + '<input type="checkbox" value="'+subjectid+'"><span>'+ subjectname+'</span></label>';
	$('#ch-subjectid').append(checkbox);*/
	$('input[type=radio][name="ch-ishavenetedu"][value='+'"'+ishavenetedu+'"'+']').attr('checked','checked');
	$('input[type=radio][name="ch-isOnlineBuy"][value='+'"'+isOnlineBuy+'"'+']').attr('checked','checked')
	$('#ch-netedumoney').val(netedumoney);	
	$('#ch-preferinfo').val(preferinfo);
};	
/*修改成交记录*/
function changedeal(){	
	var dealtime = $('#ch-dealtime').val();
	var courseid = $('#ch-courseid option:selected').val();
	var courseName = $('#ch-courseid option:selected').text();
	var subjectname = $("#ch-subjectid label").text();
	var subjectid = [];
	$("#ch-subjectid input[type='checkbox']:checked").each(function() {
		var boxid = $(this).val();
		subjectid.push(boxid);
	});
	var dealprice = $('#ch-dealprice').val();
	var ishavenetedu = $("input[name='ch-ishavenetedu']:checked").val();
	var isOnlineBuy = $("input[name='ch-isOnlineBuy']:checked").val();
	var ishavenetedu = $("input[name='ch-ishavenetedu']:checked").val();
	var netedumoney = $('#ch-netedumoney').val();
	var preferinfo = $('#ch-preferinfo').val();
	var remituser = $('#ch-remituser').val();
	subjectids = subjectid.join(',');	
	$.ajax({        			
	   type: "POST",
	   url: '../student/editDealrecord.do',
	   data: {
		   userid:userid,
		   studentId:studentId,
		   courseid:courseid,
		   dealtime:dealtime,
		   subjects:subjectids,
		   dealprice:dealprice,
		   ishavenetedu:ishavenetedu,
		   isOnlineBuy:isOnlineBuy,
		   netedumoney:netedumoney,
		   preferinfo:preferinfo,
		   remituser:remituser
	   },
	   success: function(data) {		  
		   if(data=="success"){
			   
			   //$('#changedeal').css('display','none');
			   window.location.reload()
			   /*var tr ="<tr>"+
				"<td>"+dealtime+"</td>"+
				"<td>"+courseName+"</td>"+
				"<td>"+subjectname+"</td>"+
				"<td>"+dealprice +"</td>"+
				"<td>"+ishavenetedu+"</td>"+
				"<td>"+netedumoney+"</td>"+
				"<td>"+preferinfo+"</td>"+
				"<td>"+remituser+"</td>"+	
				"</tr>"
				$('#dealtable').append(tr);*/
		   }
	   }
	});
}


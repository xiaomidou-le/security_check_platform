 //angular 分页展示数据
var myapp = angular.module('app', []);
myapp.controller('securityCheckResult',function($scope,$http){                  	
	$scope.state = function (aa) {            	    
	    var red = "";
	    if (0 == aa) {            	       
	        red = '可用'
	    } else if (1 == aa) {
	    	red = '不可用'
	    } 
	    return red;            	   
	};
	$scope.setColor = function (r) {
        var p = "";
        if (1 == r) {
            p = '#f00';
        } 
        return {"color": p};
    };
	//配置
    $scope.count = 0;//总条数
    $scope.pageSize = 15;//每页15条
    //变量
    $scope.currentPage = 1;//当前页
    $scope.pageTotal =0; // 总页数 
    $scope.pages = [];
 	$scope.getScanResult = function(result) {
		if  (result == 0) {
			return "检测中";
		} else if (result == 1) {
			return "失败";
		} else {
			return "成功";
		}
	   }
    //初始化第一页
    forPages($scope.currentPage,$scope.pageSize,function(){});
	var timer = setInterval(function(){forPages($scope.currentPage,$scope.pageSize,function(){});}, 10000);

	//获取数据
     function forPages (page,size,callback){
        $http.get("../checkList/securityCheckResultList.do?currentPage="+page+"&pageSize="+size).success(function(Data){         	 		        	             	
         	$scope.count=Data.total;
         	$scope.list = Data.rows;
            $scope.currentPage = Data.currentPage;
            $scope.pageTotal =Math.ceil($scope.count/$scope.pageSize);
            reloadPno();
            callback();           
        }); 
    }
    //首页
    $scope.firstPage = function(){
        $scope.loadPage(1);
    }
    //尾页
    $scope.lastPage = function(){
        $scope.loadPage($scope.pageTotal);
    }
    //加载某一页
    $scope.loadPage = function(page){
    	forPages(page,$scope.pageSize,function(){ });
    };
    //初始化页码  
   	var reloadPno = function(){  
       	$scope.pages=calculateIndexes($scope.currentPage,$scope.pageTotal,10);  
    };  
	//分页算法  
	var calculateIndexes = function (current, length, displayLength) {  
	   var indexes = [];  
	   var start = Math.round(current - displayLength / 2);  
	   var end = Math.round(current + displayLength / 2);  
	    if (start <= 1) {  
	        start = 1;  
	        end = start + displayLength - 1;  
	       if (end >= length - 1) {  
	           end = length - 1;  
	        }  
	    }  
	    if (end >= length - 1) {  
	       end = length;  
	        start = end - displayLength + 1;  
	       if (start <= 1) {  
	           start = 1;  
	        }  
	    }  
	    for (var i = start; i <= end; i++) {  
	        indexes.push(i);  
	    }  
	    return indexes;  
	 		console.info(indexes);
	};

	
});


//全选
$('.all').click(function() {
	if(this.checked) {
		$("input[type='checkbox']").prop("checked", true);
	} else {
		$("input[type='checkbox']").prop("checked", false);
	}
});
var checkboxval = [];
function changeUser(){
	
	checkboxval = [];//清空上次的修改条数
	
	$("input[type='checkbox']:not(:first):checked").each(function() {
		var boxid = $(this).attr('id');
		checkboxval.push(boxid);
	});
	var dataid =$('input[type="checkbox"]:checked').parent().parent().attr('data-id');
		
	if(checkboxval.length == 0) {				
		$.winTip({
			title: "提示~~~",
			msg: "至少选择一条信息",
			src:"./system/img/tishi.png"
		});
		
	}else if(checkboxval.length > 1){
		$.winTip({
			title: "提示~~~",
			msg: "请选择一条信息修改",
			src:"./system/img/tishi.png"
		});
	}else {		
		changeText(dataid);
	}
	
}


//==============获取循环添加的tr展示修改数据====================
function changeText(objTag){
		
	var username=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(2).html();
	var email=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(3).html();
	var password=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(4).html();
	var phone=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(5).html();
	var mobile=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(6).html();
	var sex=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(11).html();
	var deptname=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(8).html();
	var roleid=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(9).html();
	var state=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(13).html();
	var userphoto=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(10).html();	
	var note=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(14).html();
	
	var deptid=$('tr[data-id='+'"'+objTag+'"'+']').children('td').eq(7).attr('data-id');
	
	
	if(sex=="男"){
		sex=0;
	}else if(sex=="女"){
		sex=1;
	}
	if(state=="可用"){
		state=0;
	}else if(state=="不可用"){
		state=1;
	}
	
	$('#change').show();
	
	$('#ch-username').val(username);
	$('#ch-email').val(email);
	$('#ch-password').val(password);
	$('#ch-phone').val(phone);
	$('#ch-mobile').val(mobile);
	$('input:radio[name="ch-sex"][value='+'"'+sex+'"'+']').attr("checked",true);
	checkRolename("#ch-roleid",deptid); 
	
	$('input:radio[name="ch-state"][value='+'"'+state+'"'+']').attr("checked",true);	
	$('#ch-userphoto').val(userphoto);	
	$('#ch-note').val(note);
	
	
};

var ids = [];	
function show() {		
/*	$("input[type='checkbox']:not(:first):checked").each(function() {
		var boxid = $(this).attr('class');
		ids.push(boxid);
	});*/
	ids = getUsecaseIds();
	if(ids.length == 0) {
		$.winTip({
			title: "友情提示",
			msg: "至少选择一条检测规则",
			src:"../../system/img/tishi.png"
		});
	} else {
		$('.themisWrap').css('display','block');
	}
};
function getUsecaseIds() {
	var idList = [];
	$("input[type='checkbox']:not(:first):checked").each(function() {
		var boxid = $(this).attr('class');
		idList.push(boxid);
	});
	return idList;
};
function startSecurityCheck() {		
	$("input[type='checkbox']:not(:first):checked").each(function() {
		var boxid = $(this).attr('class');
		ids.push(boxid);
	});
	
	if(ids.length == 0) {
		$.winTip({
			title: "友情提示",
			msg: "至少选择一条检测规则",
			src:"../../system/img/tishi.png"
		});
	} else {
		//$('.themisWrap').css('display','none');
		var idstr = ids.join(",");
		$.ajax({
			type: "POST",
			url: '../checkList/securityCheckStart.do',
			data: {
				usecaseIds: idstr
			},
			success: function(res) {
				ids=[];
				if (res == "success") {
					alert("扫描完成");
				}
				//window.location.reload();
			}
		});
	}
};
function exportSecurityCheckReport() {
		//var usecaseIdList = getUsecaseIds;
		//var idstr = ids.join(",");
/*		$.ajax({
			type: "POST",
			url: '../checkList/securityCheckExport.do',
			success: function(res) {
				ids = [];
				//document.location.href = "/security_check_platform/haha20201229133646.zip";
			}
		});*/
		document.location.href = "../checkList/securityCheckExport.do";
};
//确定删除
function del() {
	$('.themisWrap').css('display','none');
	var id = ids.join(",");
	$.ajax({
		type: "POST",
		url: '../system/deleteUser.do',
		data: {
			ids: id
		},
		success: function() {
			window.location.reload();
		}
	});
};
$('#quxiao').on('click',function(){
	$('.themisWrap').css('display','none');
})

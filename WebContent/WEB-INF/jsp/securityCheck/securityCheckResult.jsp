<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta name="msapplication-tap-highlight" content="no" />
	<meta name="robots" content="noindex" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
	<meta name="renderer" content="webkit" />
	<link rel="stylesheet" type="text/css" href="../common/css/bootstrap.min.css" />	
	<link rel="stylesheet" type="text/css" href="../common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="../common/css/winTip.css" />	
	<link rel="stylesheet" type="text/css" href="../system/css/securityCheckResult.css" />
	<title>安全扫描结果</title>
</head>
<body ng-app="app" ng-controller="securityCheckResult">
	<div class="meun-btn">
       	<ul class="nav navbar-nav">									
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" name="mobile" class="form-control check_tiaoj" placeholder="请输入检测项关键字搜索" ng-model="query">
				</div>
				<a href="javascript:;"  class="btn" style="background: #4c7cba;">查询</a>
			</form>

			<a href="javascript:;"  class="btn" style="background: #4c7cba;" onclick="exportSecurityCheckReport()">导出</a>
	
		</ul>
	</div>
    <div class="bs-example table-responsive" data-example-id="simple-table"> 
    	<div class="coursePanel">
		  <span class="glyphicon glyphicon-th-list"></span>安全检测结果	  
		</div>              
     	<table class="table table-bordered table-hover table-striped">                    
               <tr class="henglan">
                   <td class="text-primary"><input type="checkbox" class="all" /></td>
                   <td class="text-primary">序号</td>
                   <td class="text-primary">检测项</td>
                   <td class="text-primary">描述</td>
                   <td class="text-primary">创建人</td>
                   <td class="text-primary">开始时间</td>
                   <td class="text-primary">结束时间</td>
                   <td class="text-primary">检测结果</td>
               </tr> 
               <tr ng-repeat="tmp in list | filter:query " data-id="{{tmp.id}}">
                   <td><input type="checkbox" class="{{tmp.id}}" /></td>
                   <td ng-bind="{{(currentPage-1)*pageSize+($index+1)}}"></td>
                   <td ng-bind="tmp.name" class="username"></td>
                   <td ng-bind="tmp.description"></td>
                   <td ng-bind="tmp.create_by"></td>
                   <td ng-bind="tmp.create_time"></td>
                   <td ng-bind="tmp.update_time"></td>
                   <td ng-bind="getScanResult(tmp.result)"></td>
               </tr>
		</table>
        <!-- 分页 -->
	 	<nav>
	      <ul class="pagination">
	          <li ng-class="{true:'disabled'}[currentPage==1]"><a href="javascript:void(0);" ng-click="firstPage()">首页</a></li>
	          <li ng-class="{true:'active'}[currentPage==page]" ng-repeat="page in pages"><a href="javascript:void(0);" ng-click="loadPage(page)">{{ page }}</a></li>
	          <li ng-class="{true:'disabled'}[currentPage==pageTotal]"><a href="javascript:void(0);" ng-click="lastPage()">尾页</a></li>
	      </ul>
	      <span style="vertical-align: 30px;">&nbsp;&nbsp;共：{{count}} 条</span>
	      <span style="vertical-align: 30px;">&nbsp;&nbsp;共：{{pageTotal}} 页</span>
	  	</nav>
    </div>
<script type="text/javascript" src="../common/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../common/js/angular.min.js"></script>
<script type="text/javascript" src="../common/js/winTip.js"></script>
<script type="text/javascript" src="../common/js/boot.js"></script>
<script type="text/javascript" src="../system/js/login.js"></script>
<script type="text/javascript" src="../system/js/securityCheckResult.js"></script>
</body>
</html>
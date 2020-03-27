<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>知享个人博客${title}</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="stylesheet" type="text/css" href="../resources/admin/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../resources/admin/css/nprogress.css">
<link rel="stylesheet" type="text/css" href="../resources/admin/css/style.css">
<link rel="stylesheet" type="text/css" href="../resources/admin/css/font-awesome.min.css">
<link rel="apple-touch-icon-precomposed" href="images/icon.png">
<script src="../resources/admin/js/jquery-2.1.4.min.js"></script>
<script src="../resources/admin/js/nprogress.js"></script>
<script src="../resources/admin/js/jquery.lazyload.min.js"></script>
<script>
function addRSS(){ 	
	alert("请按 Ctrl+D 键添加到收藏夹");
}

</script>
</head>
<body class="user-select">
<header class="header">
<nav class="navbar navbar-default" id="navbar">
<div class="container">
  <div class="header-topbar hidden-xs link-border">
	<ul class="site-nav topmenu">
		<li><a href="#" onclick="addRSS()" title="RSS订阅" >
			<i class="fa fa-rss">
			</i> RSS订阅
		</a></li>
	</ul>
			我们不生产知识，我们只是知识的搬运工！</div>
  <div class="navbar-header">
	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-navbar" aria-expanded="false"> <span class="sr-only"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
	<h1 class="logo hvr-bounce-in"><a href="#" title="知享个人博客"><img  src="../resources/upload/logo.png" alt="知享个人博客"></a></h1>
  </div>
  <div class="collapse navbar-collapse" id="header-navbar">
	<form class="navbar-form visible-xs" action="/Search" method="post">
	  <div class="input-group">
		<input type="text" name="keyword" class="form-control" placeholder="请输入关键字" maxlength="20" autocomplete="off">
		<span class="input-group-btn">
		<button class="btn btn-default btn-search" name="search" type="submit">搜索</button>
		</span> </div>
	</form>
	<ul class="nav navbar-nav navbar-right">
	<li><a data-cont="知享个人博客"  title="知享个人博客" href="index">首页</a></li>
	<c:forEach items="${newsCategoryList}" var="newsCategory">
	<li><a data-cont="${newsCategory.name}" title="${newsCategory.name}" href="../news/category_list?cid=${newsCategory.id}">${newsCategory.name }</a></li>
	</c:forEach>
	<li><a data-cont="资源分享"  title="资源分享" href="resource">资源分享</a></li>
	<li><a data-cont="留言条"  title="留言板" href="note">留言条</a></li>
	<li><a data-cont="关于我"  title="关于我" href="about">关于我</a></li>		
	</ul>
</div>
</div>
</nav>
</header>
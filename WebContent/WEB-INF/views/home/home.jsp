<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@include file="header.jsp"%>
<section class="container">
<div class="content-wrap">
<div class="content">
  <div id="focusslide" class="carousel slide" data-ride="carousel">
	 </div>
  <div class="top" style="height:85px;background:#ffffff">
  <div style="padding:15px">
  <a style="font-size:14pt;font-weight:bold;color:red" >[置顶]<font color="#000000">Zero</font></a>
  <p style="margin-top:10px">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个博客还勉勉强强活着......
	</p>
  </div>	
  </div>
  
  <c:forEach items="${newsList}" var="news">
  <article class="excerpt excerpt-1" style="margin-top:10px">
  <a class="focus" href="../news/detail?id=${news.id}" title="${news.title}" target="_blank" ><img class="thumb" data-original="${news.photo}" src="${news.photo}" alt="${news.title}"  style="display: inline;"></a>
		<header><a class="cat" href="../news/category_list?cid=${news.categoryId}" title="${news.newsCategory.name}" style="background:rgb(1, 167, 1)">${news.newsCategory.name}<i></i></a>
			<h2><a href="../news/detail?id=${news.id}" title="${news.title}" target="_blank" >${news.title}</a>
			</h2>
		</header>
		<p class="meta">
			<time class="time"><i class="glyphicon glyphicon-time"></i><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></time>
			<span class="views"><i class="glyphicon glyphicon-eye-open"></i> ${news.viewNumber}</span> 
			<a class="comment" href="../news/detail?id=${news.id}" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i> ${news.commentNumber}</a>
		</p>
		<p class="note">${news.abstrs}</p>
	</article>
</c:forEach>	
<!-- <div class="ias_trigger" ><a href="javascript:;"  id="load-more-btn">查看更多</a></div> -->
</div>
</div>
<script>
$(document).ready(function(){
	$.ajax({
		url:'../news/top_photo',
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.type == 'success'){
				var photoList = data.photoList;
					var li = '<div class="top" style="height:240px;background:#ffffff"><div style="width:50%;height:240px;float: left;" class="img-left"><a class="focus" href="../news/detail?id='+photoList[0].id+'"><img class="thumb" data-original="${news.photo}" style="width:100%;padding:10px;height:239px" src="'+photoList[0].photo+'"></a><div style="height:50px;"><b style="top:-40px;position:relative;z-index:20;background-color:#00000080;padding:10px;margin-left:10px;color:#ffffff">'+photoList[0].tags+'</b></div></div><div style="width:50%;height:240px;float: right;" class="img-right"><div class="img-right1"  style="width:50%;height:120px;float: left;"><a class="focus" href="../news/detail?id='+photoList[1].id+'"><img class="thumb" style="width:100%;padding:10px 10px 10px 0px;height:129px" src="'+photoList[1].photo+'"></a><div style="height:50px;"><b style="top:-40px;position:relative;z-index:20;width:200px;background-color:#00000080;padding:10px;color:#ffffff">'+photoList[1].tags+'</b></div></div><div class="img-right2" style="width:50%;height:120px;float: right;"><a class="focus" href="../news/detail?id='+photoList[2].id+'"><img class="thumb" style="width:100%;padding:10px 10px 10px 0px;height:129px" src="'+photoList[2].photo+'"></a><div style="height:50px;"><b style="top:-40px;position:relative;z-index:20;width:200px;background-color:#00000080;padding:10px;color:#ffffff">'+photoList[2].tags+'</b></div></div><div class="img-right3" style="width:50%;height:120px;float: left;"><a class="focus" href="../news/detail?id='+photoList[3].id+'"><img class="thumb" style="width:100%;padding:0px 10px 0px 0px;height:109px" src="'+photoList[3].photo+'"></a><div style="height:50px;"><b style="top:-30px;position:relative;z-index:20;width:200px;background-color:#00000080;padding:10px;color:#ffffff">'+photoList[3].tags+'</b></div></div><div class="img-right4" style="width:50%;height:120px;float: right;"><a class="focus" href="../news/detail?id='+photoList[4].id+'"><img class="thumb" style="width:100%;padding:0px 10px 0px 0px;height:109px" src="'+photoList[4].photo+'"></a><div style="height:50px;"><b style="top:-30px;position:relative;z-index:20;width:200px;background-color:#00000080;padding:10px;color:#ffffff">'+photoList[4].tags+'</b></div></div></div></div>';

				$("#focusslide").append(li);
			}
		}
	});
})
</script>
<%@include file="sidebar.jsp"%>
</section>
<%@include file="footer.jsp"%>

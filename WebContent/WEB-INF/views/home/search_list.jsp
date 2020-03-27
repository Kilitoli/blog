<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@include file="header.jsp"%>
<section class="container">
<div class="content-wrap">
<div class="content" id="news-list">
  <div class="title">
	<h3>关键字：${keyword}</h3>
  </div>
  <c:forEach items="${newsList}" var="news">
  <article class="excerpt excerpt-1" style="">
  <a class="focus" href="../news/detail?id=${news.id}" title="${news.title}" target="_blank" >
  	<img class="thumb" data-original="${news.photo}" src="${news.photo}" alt="${news.title}"  style="display: inline;">
  </a>
		<header>
		<a class="cat" href="../news/category_list?cid=${news.categoryId}" title="${news.newsCategory.name}" >${news.newsCategory.name}
		<i></i>
		</a>
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
</div>
</div>
<%@include file="sidebar.jsp"%>
</section>
<%@include file="footer.jsp"%>
<script>
$(document).ready(function(){
	$("#load-more-btn").click(function(){
		$.ajax({
			url:'../news/get_search_list',
			type:'post',
			data:{rows:10,page:page++,keyword:'${keyword }'},
			dataType:'json',
			success:function(data){
				if(data.type == 'success'){
					var newsList = data.newsList;
					var html = '';
					for(var i=0;i<newsList.length;i++){
						
						var article = '<article class="excerpt excerpt-1" style="">';
						article +='<a class="focus" href="../news/detail?id='+newsList[i].id+'" title="'+newsList[i].title+'" target="_blank" >';
						article +='<img class="thumb" data-original="'+newsList[i].photo+'" src="'+newsList[i].photo+'" alt="'+newsList[i].title+'"  style="display: inline;"></a>';
						article +='<header><a class="cat" href="../news/category_list?cid='+newsList[i].categoryId+'" title="'+newsList[i].newsCategory.name+'" >'+newsList[i].newsCategory.name+'<i></i></a>';
						article +='<h2><a href="../news/detail?id='+newsList[i].id+'" title="'+newsList[i].title+'" target="_blank" >'+newsList[i].title+'</a></h2></header>';
						article +='<p class="meta"><time class="time"><i class="glyphicon glyphicon-time"></i>'+format(newsList[i].createTime)+'</time>';
						article +='<span class="views"><i class="glyphicon glyphicon-eye-open"></i> '+newsList[i].viewNumber+'</span>';
						article +='<a class="comment" href="../news/detail?id='+newsList[i].id+'#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>'+newsList[i].commentNumber+'</a></p>';
						article +='<p class="note">'+newsList[i].abstrs+'</p>';
						article +='</article>';
						html += article;
					}
					$("#load-more-btn").parent("div").before(html);
				}
			}
		});
	});
	
});	
</script>
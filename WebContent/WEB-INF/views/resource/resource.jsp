<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="header.jsp"%>
<section class="container">
<div class="content-wrap">
<div class="content">
  <div id="focusslide" class="carousel slide" data-ride="carousel">
	<ol class="carousel-indicators">
	  <li data-target="#focusslide" data-slide-to="0" class="active"></li>
	  <li data-target="#focusslide" data-slide-to="1"></li>
	</ol>
	<h3>资源分享</h3>
  </div>
  <c:forEach items="${resourceList}" var="resources">
  <article class="excerpt excerpt-1" >
  
  <a class="focus" style="width:600px" href="${resources.address}" title="" target="_blank" >
  ${resources.name}
  <p>  
  ${resources.password}
  </p>
  </a>
  
	</article>
	</c:forEach>
</div>
</div>
<%@include file="sidebar.jsp"%>
</section>
<%@include file="footer.jsp"%>
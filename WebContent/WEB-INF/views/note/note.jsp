<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="header.jsp"%>
<section class="container">
<div class="content-wrap">
<div class="content" >
<header class="article-header">
<h2>留言板⭐</h2>
 </header>
<article class="article-content">
<center><h3>内啥，这只是个简易留言板</h3><p></p></br></br></center>
<center><h3>欢迎留言哈~~</h3></center>
<center><h3>请不要在本博客发布无意义内容，相信我！删除真的只需要1秒！</h3></center>
<!--PC和WAP自适应版-->
<div id="SOHUCS" ></div> 
<script type="text/javascript"> 
(function(){ 
var appid = 'cyuzWzRyy'; 
var conf = 'prod_c9bb6084efb227c24f7fc0afbd3d52c2'; 
var width = window.innerWidth || document.documentElement.clientWidth; 
if (width < 960) {
var head = document.getElementsByTagName('head')[0]||document.head||document.documentElement;
var script = document.createElement('script');
script.type = 'text/javascript';
script.charset = 'utf-8';
script.id = 'changyan_mobile_js';
script.src = 'https://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf;
head.appendChild(script);
} else { var loadJs=function(d,a){var c=document.getElementsByTagName("head")[0]||document.head||document.documentElement;var b=document.createElement("script");b.setAttribute("type","text/javascript");b.setAttribute("charset","UTF-8");b.setAttribute("src",d);if(typeof a==="function"){if(window.attachEvent){b.onreadystatechange=function(){var e=b.readyState;if(e==="loaded"||e==="complete"){b.onreadystatechange=null;a()}}}else{b.onload=a}}c.appendChild(b)};loadJs("https://changyan.sohu.com/upload/changyan.js",function(){window.changyan.api.config({appid:appid,conf:conf})}); } })(); </script>

</article>
</div>
</div>
<%@include file="sidebar.jsp"%>
</section>
<%@include file="footer.jsp"%>
<script>
$(document).ready(function(){
	$("body").addClass("single");
});	
</script>
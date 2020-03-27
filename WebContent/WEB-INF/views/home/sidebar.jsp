<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<aside class="sidebar">
<div class="fixed">
 <div class="widget widget_search">
	<form class="navbar-form" action="../news/search_list?keyword=${keyword}" method="get">
	  <div class="input-group">
		<input type="text" name="keyword" class="form-control" size="35" value="${keyword}" placeholder="请输入关键字" maxlength="15" autocomplete="off">
		<span class="input-group-btn">
		<input class="btn btn-default btn-search" type="submit" value="搜索">
		</span> </div>
	</form>
  </div>
   <div class="widget widget_sentence" >
   <h3><i class="fa fa-meh-o" aria-hidden="true"></i>&nbsp;|&nbsp;名片</h3>
    <div style="height:130px;margin-left:70px" >
    <p style="margin-top:10px">
	站长昵称：Zero
	<p style="margin-top:10px">
	职业：学生
	<p style="margin-top:10px">
	现居：四川省-成都市
	<p style="margin-top:10px">
	Email：1326689662@qq.com
	<img id="weixin" style="width:21%;float:right;"  src="../resources/upload/weixin.png">
    </div>
    <div style="height:50px;margin-left:40px">  
    <a  href="../index/index" style="margin-left:20px"><img style="width:10%" src="../resources/upload/index.png"></a>
    <a  target="_blank" href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=rZyen5ublZSbm5-t3NyDzsLA" style="margin-left:30px"><img style="width:10%" src="../resources/upload/email.png"></a>
    <a  target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1326689662&site=qq&menu=yes" style="margin-left:30px"><img style="width:11%" src="../resources/upload/QQ.png"></a>
    <a  id="tu" href= "" style="margin-left:30px"><img style="width:10%" src="../resources/upload/wechat.png"></a>
    </div>  
 </div>
 
</div>
<div class="widget widget_hot">
	
	  <h3><i class="fa fa-comments" aria-hidden="true"></i>&nbsp;|&nbsp;最新评论</h3>
	  <ul id="last-comment-list">            
			
				

	  </ul>
 </div>


<div class="widget widget_sentence">
  <h3><i class="fa fa-chain" aria-hidden="true"></i>&nbsp;|&nbsp;友情链接</h3>
  <div class="widget-sentence-link">
	<a href="https://github.com/Kilitoli" title="GitHub账号" target="_blank" >站长GitHub账号</a>&nbsp;&nbsp;&nbsp;
  </div>
</div>
 <div class="widget widget_sentence">    
	<h3><i class="fa fa-rmb" aria-hidden="true"></i>&nbsp;|&nbsp;恰饭环节</h3>
	<img style="width: 90%;margin-left:5%"  src="../resources/upload/qiafan.JPG" alt="恰饭" >
	<h5 align="center" style="margin:5%">&nbsp;&nbsp;&nbsp;打开支付宝首页搜: 生活费4488870!</h5>  
 </div>
 <div class="widget widget_sentence">    
	<h3><i class="fa fa-battery-empty" aria-hidden="true"></i>&nbsp;|&nbsp;真的没有了</h3>
	<img style="width: 100%" src="../resources/upload/huoying.jpg" alt="没有了" >    
 </div>
</aside>
<script>
function add0(m){return m<10?'0'+m:m }
function format(shijianchuo){
//shijianchuo是整数，否则要parseInt转换
	var time = new Date(shijianchuo);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}
$(document).ready(function(){
	
	$.ajax({
		url:'../news/last_comment_list',
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.type == 'success'){
				var newsList = data.newsList;
				var html = '';
				for(var i=0;i<newsList.length;i++){
					var li = '<li style="margin-left:-130px"><a title="'+newsList[i].title+'" href="../news/detail?id='+newsList[i].id+'" >';
					li += '<span class="text">'+newsList[i].title+'</span><span class="muted"><i class="glyphicon glyphicon-time"></i>';
					li += format(newsList[i].createTime)  + '</span></a></li>';
					html += li;
				}
				$("#last-comment-list").append(html);
			}
		}
	});
})
 $(()=>{
	$('#weixin').hide();
	$('#tu').mousemove(function(){
         $('#weixin').show();
       		})
         $('#tu').mouseout(function(){
         $('#weixin').hide();
         })
 })   
</script>
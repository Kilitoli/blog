<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/layui/css/layui.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">知享个人博客平台</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          ${role.name}:${admin.username}
        </a>
      </li>
      <li class="layui-nav-item"><a href="logout">退了</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">用户管理</a>
          <dl class="layui-nav-child">
            <dd><a href="layui" target="main_self_frame">用户管理</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">系统日志</a>
          <dl class="layui-nav-child">
            <dd><a href="loglist" target="main_self_frame">日志管理</a></dd>
          </dl>
        </li>
	<li class="layui-nav-item">
          <a href="javascript:;">博客管理</a>
          <dl class="layui-nav-child" >
            <dd><a href="newscategorylist" target="main_self_frame">分类管理</a></dd>
            <dd><a href="newslist" target="main_self_frame">博客管理</a></dd>
             <dd><a href="commentlist" target="main_self_frame">评论管理</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">资源管理</a>
          <dl class="layui-nav-child">
            <dd><a href="resourcelist" target="main_self_frame">资源管理</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">留言管理</a>
          <dl class="layui-nav-child">
            <dd><a href="http://changyan.kuaizhan.com/" target="main_self_frame">留言管理</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <iframe src="layui" name="main_self_frame" style="width:100%;height:100%;background:#dfdfdf3a" frameborder="0" class="layadmin-iframe"></iframe>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © layui.com - 知享个人博客后台
  </div>
</div>
<script src="../resources/layui/layui.js"></script>
<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
function refreshHighlight(url) {
        $ = layui.jquery;
        $(".layui-nav[lay-filter='navList'] a").each(function (ind, val) {
            if($(this).attr('href') === url){
                $('.layui-nav dd').removeClass('layui-this');
                $(this).parent('dd').addClass('layui-this');
            }
        })
    }
</body>
</html>
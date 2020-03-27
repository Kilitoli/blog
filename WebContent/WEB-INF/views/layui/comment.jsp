<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/layui/css/layui.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
<div id="box" style="padding:50px;background:#ffffff">
<h2><b>评论管理</b></h2><br>
 <div  class="demoTable">
	 评论昵称:
  <div class="layui-inline">
    <input class="layui-input" id="searchname" >
  </div>
  <button id="btn-serach" class="layui-btn layui-btn-normal" >搜索</button>
  <button id="btn-remove" class="layui-btn layui-btn-danger" >删除</button>
</div> 


<table id="demo" lay-filter="test"></table>
 
<script src="../resources/layui/layui.js"></script>
<script>
//一般直接写在一个js文件中
layui.use(['layer', 'form','table','jquery'], function(){
  var layer = layui.layer;
  var table=layui.table;
  var form=layui.form;
  var $=layui.jquery;
//第一个实例
$("#btn-serach").click(function(){
	table.reload('demo', {
		  url: 'comment'
		  ,where: {
			  nickname:$("#searchname").val(),
		  } //设定异步数据接口的额外参数
		});
})
//删除
$("#btn-remove").click(function(){
	var obj=table.checkStatus('demo');
	var array=obj.data;
	var id=[];
	if(array.length==0){
	layer.msg('请选择要删除的数据',{icon:5});
	}
	else{
		for(var i in array){
			id.push(array[i].id);
		}
		$.post('commentdelete',{"id[]":id},
			function(data){
			if(data.type == 'success'){
				layer.msg('删除成功',{icon:6});
				table.reload("demo");
			}
			
			else{
				layer.msg('删除失败',{icon:5});
			}
		}		
		)
	}
	form.render();//重新渲染表单
})

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

  table.render({
    elem: '#demo'
    ,url: 'comment' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
       {field: 'id', title: '博客ID',type:'checkbox'}
      ,{field: 'newsId', title: '评论博客', templet :function (row){
          return row.news.title;
      } }
      ,{field: 'nickname', title: '评论昵称'}
      ,{field: 'content', title: '评论内容'}
      ,{field: 'createTime', title: '评论时间', templet :function (row){
          return format(row.createTime);
      }     }
    ]]
  });
});

</script>
</div> 
</body>
</html>
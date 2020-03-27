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
<h2><b>博客管理</b></h2><br>
 <div  class="demoTable">
	 博客标题:
  <div class="layui-inline">
    <input class="layui-input" id="searchname" >
  </div>
  <button id="btn-serach" class="layui-btn layui-btn-normal" >搜索</button>
  <button id="btn-add" class="layui-btn" >添加博客</button>
  <button id="btn-remove" class="layui-btn layui-btn-danger" >删除</button>
</div> 
<script id="barDemo" type="text/html">
  <a class="layui-btn layui-btn-xs" id="btn-edit" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
</script>

<table id="demo" lay-filter="test"></table>
<script src="../resources/layui/layui.js"></script>
<script>
//一般直接写在一个js文件中
layui.use(['layer', 'form','table','jquery'], function(){
  var layer = layui.layer;
  var table=layui.table;
  var form=layui.form;
  var $=layui.jquery;
  var layedit = layui.layedit;
  layui.use('layedit', function(){
	var layedit = layui.layedit;
	var index=  layedit.build('remark'); //建立编辑器
	form.verify({		
		content: function(value) {
			// 将富文本编辑器的值同步到之前的textarea中
			layedit.sync(index);
		}
	});	 
	});
//第一个实例
$("#btn-serach").click(function(){
	table.reload('demo', {
		  url: 'news'
		  ,where: {
			  title:$("#searchname").val(),
		  } //设定异步数据接口的额外参数
		});
})
$("#btn-add").click(function(){
	window.location.href='newsadd';
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
		$.post('newsdelete',{"id[]":id},
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

//编辑
table.on('tool(test)', function(obj){
	var method=obj.event;
	if(method === 'edit'){
		window.location.href='newsedit?id='+obj.data.id;
	}
})
  table.render({
    elem: '#demo'
    ,url: 'news' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
       {field: 'id', title: '博客ID',type:'checkbox'}
      ,{field: 'title', title: '标题'}
      ,{field: 'author', title: '作者'}
      ,{field: 'categoryId', title: '分类', templet :function (row){
          return row.newsCategory.name;
      }     }
      ,{field: 'tags', title: '标签'}
      ,{field: 'viewNumber', title: '浏览量'}
      ,{field: 'commentNumber', title: '评论数'}
      ,{fixed: 'right', align:'center',title:'操作', toolbar: '#barDemo'}
    ]]
  });
});
function upload(){
	if($("#photo-file").val() == '')return;
	var formData = new FormData();
	formData.append('photo',document.getElementById('photo-file').files[0]);
	$.ajax({
		url:'upload_photo',
		type:'post',
		data:formData,
		contentType:false,
		processData:false,
		success:function(data){
			if(data.type == 'success'){
				$("#preview-photo").attr('src',data.filepath);
				$("#add-photo").val(data.filepath);
				$("#edit-preview-photo").attr('src',data.filepath);
				$("#edit-photo").val(data.filepath);
				layer.msg('上传成功',{icon:6});
				
			}else{
				layer.msg('上传失败',{icon:5});
				 
			}
		},
		error:function(data){
			$.messager.alert("消息提醒","上传失败!","warning");
		}
	});
}

function uploadPhoto(){
	$("#photo-file").click();
	
}
</script>
</div> 
</body>
</html>
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
<h2><b>用户管理</b></h2><br>
 <div  class="demoTable">
	 姓名:
  <div class="layui-inline">
    <input class="layui-input" id="username" >
  </div>
  <button id="btn-serach" class="layui-btn layui-btn-normal" >搜索</button>
  <button id="btn-add" class="layui-btn" >添加</button>
  
  <button id="btn-remove" class="layui-btn layui-btn-danger" >删除</button>
</div> 


<script id="barDemo" type="text/html">
  <a class="layui-btn layui-btn-xs" id="btn-edit" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
</script>

<!-- 添加对话框 -->
<script type="text/html" id="add-dialog">
<form class="layui-form" id="add-form">
<div class="layui-form-item">
    <label class="layui-form-label">用户头像</label>
    <div class="layui-input-block">
      <img id="edit-preview-photo" style="float:left;" src="/BaseProjectSSM/resources/admin/easyui/images/upload.png" width="100px">
       <a style="float:left;margin-top:40px;" href="javascript:void(0)" class="layui-icon layui-icon-add-1" onclick="uploadPhoto()" plain="true">上传图片</a>
    </div>
  </div>
<div class="layui-form-item">
    <label class="layui-form-label">头像</label>
    <div class="layui-input-block">
      <input type="text" id="add-photo" style="width:300px" name="photo" required  lay-verify="required"  readonly="readonly" autocomplete="off" class="layui-input" value="/BaseProjectSSM/resources/admin/easyui/images/upload.png">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">用户名</label>
    <div class="layui-input-block">
      <input type="text"  name="username" style="width:200px" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">密码框</label>
    <div class="layui-input-inline">
      <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">所属角色</label>
    <div class="layui-input-block">
	<select name="roleId" id="edit-roleId" name="roleId" panelHeight="auto" lay-verify="required" >
		                <c:forEach items="${roleList }" var="role">
		                <option value="${role.id }">${role.name }</option>
		                </c:forEach>
		            </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">性别</label>
    <div class="layui-input-block">
	<select  name="sex" id="search-sex" panelHeight="auto" lay-verify="required">
            	<option value="-1">全部</option>
            	<option value="0">未知</option>
            	<option value="1">男</option>
            	<option value="2">女</option>
            </select>
    </div>
  </div>
   <div class="layui-form-item">
    <label class="layui-form-label">年龄</label>
    <div class="layui-input-block">
      <input type="text" name="age" required  lay-verify="required" placeholder="请输入年龄" autocomplete="off" class="layui-input">
    </div>
  </div>
  
 <div class="layui-form-item">
    <label class="layui-form-label">地址</label>
    <div class="layui-input-block">
      <input type="text" name="address" required  lay-verify="required" placeholder="请输入地址" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit lay-filter="add-submit">立即提交</button>
      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
  </div>
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
</form>
</script>

<!-- 修改表单 -->
<script type="text/html" id="edit-dialog">
<form class="layui-form" id="edit-form" lay-filter="load-form">
<input type="hidden" name="id" id="edit-id">
<div class="layui-form-item">
    <label class="layui-form-label">用户头像</label>
    <div class="layui-input-block">
      <img id="edit-preview-photo" style="float:left;" src="/BaseProjectSSM/resources/admin/easyui/images/upload.png" width="100px">
       <a style="float:left;margin-top:40px;" href="javascript:void(0)" class="layui-icon layui-icon-add-1" onclick="uploadPhoto()" plain="true">上传图片</a>
    </div>
  </div>
<div class="layui-form-item">
    <label class="layui-form-label">头像</label>
    <div class="layui-input-block">
      <input type="text" id="add-photo" name="photo" required  lay-verify="required"  readonly="readonly" autocomplete="off" class="layui-input" value="/BaseProjectSSM/resources/admin/easyui/images/upload.png">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">用户名</label>
    <div class="layui-input-block">
      <input type="text"  name="username" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">所属角色</label>
    <div class="layui-input-block">
	<select name="roleId" id="edit-roleId" name="roleId" panelHeight="auto" lay-verify="required" >
		                <c:forEach items="${roleList }" var="role">
		                <option value="${role.id }">${role.name}</option>
		                </c:forEach>
		            </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">性别</label>
    <div class="layui-input-block">
	<select  name="sex" id="search-sex" panelHeight="auto" lay-verify="required">
            	<option value="-1">全部</option>
            	<option value="0">未知</option>
            	<option value="1">男</option>
            	<option value="2">女</option>
            </select>
    </div>
  </div>
   <div class="layui-form-item">
    <label class="layui-form-label">年龄</label>
    <div class="layui-input-block">
      <input type="text" name="age" required  lay-verify="required" placeholder="请输入年龄" autocomplete="off" class="layui-input">
    </div>
  </div>
  
 <div class="layui-form-item">
    <label class="layui-form-label">地址</label>
    <div class="layui-input-block">
      <input type="text" name="address" required  lay-verify="required" placeholder="请输入地址" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit lay-filter="edit-submit">保存</button>
      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
  </div>
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
</form>
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
//第一个实例
$("#btn-serach").click(function(){
	table.reload('demo', {
		  url: 'list'
		  ,where: {
			  username:$("#username").val(),
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
		$.post('delete',{"id[]":id},
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
//添加
$("#btn-add").click(function(){
	layer.open({
		type:1,
		title:'添加',
		content:$('#add-dialog').html(),
		area:['460px']
	})
	form.render();//重新渲染表单
})

form.on('submit(add-submit)', function(data){
  console.log(data.field);
  $.post('add',data.field,
	function(data){
	  if(data.type == 'success'){
		  layer.msg('添加成功',{icon:6});
		  layer.closeAll('page');
		  location.reload();
		}else{
		  layer.msg(data.msg,{icon:5});	
		}
  }		  
  )
  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
});
//编辑
table.on('tool(test)', function(obj){
	var method=obj.event;
	if(method === 'edit'){
		layer.open({
			type:1,
			title:'修改',
			content:$('#edit-dialog').html(),
			area:['460px']
		});
		form.val('load-form',{
			'id':obj.data.id,
			'photo':obj.data.photo,
			'username':obj.data.username,
			'roleId':obj.data.roleId,
			'sex':obj.data.sex,
			'age':obj.data.age,
			'address':obj.data.address
		});
		$("#edit-preview-photo").attr('src',obj.data.photo);
		form.render();//重新渲染表单
		form.on('submit(edit-submit)', function(d){
			$.post('edit',d.field,
					function(data){
					  if(data.type == 'success'){
						  layer.msg('修改成功',{icon:6});
						  layer.closeAll('page');
						  table.reload("demo");
						}else{
						  layer.msg(data.msg,{icon:5});	
						}
				  })
				  return false;
		});
		
			
	}
})
		


  table.render({
    elem: '#demo'
    ,url: 'list' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
       {field: 'id', title: '用户ID',type:'checkbox'}
      ,{field: 'photo', title: '用户头像'}
      ,{field: 'username', title: '用户名'}
      ,{field: 'password', title: '密码'} 
      ,{field: 'roleId', title: '所属角色'}
      ,{field: 'sex', title: '性别' ,templet :function (row){
    	  switch(row.sex){
			case 0:{
				return '未知';
			}
			case 1:{
				return '男';
			}
			case 2:{
				return '女';
			}
			case -1:{
				return '默认';
			}
		}
		return row;
      }     }
      ,{field: 'age', title: '年龄'}
      ,{field: 'address', title: '地址'}
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
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
<body style="background:#ffffff"">
<div id="box" style="padding:70px">
<form class="layui-form" id="edit-form">
<input type="hidden" name="id" value="${news.id }">
<div class="layui-form-item">
    <label class="layui-form-label">博客标题</label>
    <div class="layui-input-block">
      <input type="text"  name="title" required  lay-verify="required" placeholder="请输入博客标题" autocomplete="off" value="${news.title}" class="layui-input">
    </div>
  </div>
    <div class="layui-form-item">
    <label class="layui-form-label">所属分类</label>
    <div class="layui-input-block">
	<select  name="CategoryId"  panelHeight="auto" lay-verify="required" >
		                <c:forEach items="${newsCategoryList }" var="category">
		                	   <c:if test="${news.categoryId == category.id}">
		                	   <option value="${category.id }" selected>${category.name }</option>
		                	   </c:if>
		                	    <<c:if test="${news.categoryId != category.id}">
		                	   <option value="${category.id }" >${category.name }</option>
		                	   </c:if>
		                       </c:forEach>
		            </select>
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">博客摘要</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入博客摘要" name="abstrs"  class="layui-textarea">${news.abstrs}</textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">博客标签</label>
    <div class="layui-input-block">
      <input type="text"   name="tags" required  lay-verify="required" placeholder="请输入博客标签" autocomplete="off" value="${news.tags}" class="layui-input">
    </div>
  </div>
<div class="layui-form-item">
    <label class="layui-form-label">博客封面</label>
    <div class="layui-input-block">
      <img id="edit-preview-photo" style="float:left;" src="${news.photo}" width="100px">
       <a style="float:left;margin-top:40px;" href="javascript:void(0)" class="layui-icon layui-icon-add-1" onclick="uploadPhoto()" plain="true">上传图片</a>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">博客地址</label>
    <div class="layui-input-block">
      <input type="text" id="add-photo" value="${news.photo}" style="width:300px" name="photo" required  lay-verify="required"  readonly="readonly" autocomplete="off" class="layui-input" value="/BaseProjectSSM/resources/admin/easyui/images/upload.png">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">博主</label>
    <div class="layui-input-block">
      <input type="text"   name="author"  value="${news.author}" required  lay-verify="required" placeholder="请输入博主" autocomplete="off" class="layui-input">
    </div>
  </div>
<div class="layui-form-item layui-form-text">
    <label class="layui-form-label">内容</label>
    <div class="layui-input-block">
      <textarea name="content" id="remark" placeholder="请输入内容" lay-verify="content" class="layui-textarea">${news.content}</textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit lay-filter="edit-submit">修改</button>
      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
  </div>
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
</form>
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
//添加
form.on('submit(edit-submit)', function(data){
  console.log(data.field);
  $.post('newsedit',data.field,
	function(data){
	  if(data.type == 'success'){
		  layer.msg('修改成功',{icon:6});
		  window.location.href='newslist';
		}else{
		  layer.msg(data.msg,{icon:5});	
		}
  }		  
  )
  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
});
})
		

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
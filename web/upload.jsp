<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="UploadServlet" method="post" enctype="multipart/form-data">
		
		用户名:<input type="text" name="username" id="username" /><br/>
		上传文件:<input type="file" name="pic" /><br/>
		<input type="submit" value="提交" />
		
	</form>

</body>
</html>
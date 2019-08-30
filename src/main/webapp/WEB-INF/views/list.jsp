<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<%
	//若以斜线开头，则要加入项目名称
	pageContext.setAttribute("APP_path", request.getContextPath());
%>

<script type="text/javascript" 
	src="${APP_path}/static/bootstrap/jquery-3.4.1/jquery-1.11.3.min.js"></script>
<link href="${APP_path}/static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${APP_path}/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM_CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-lg-4 col-lg-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-striped table-hover">
					<tr class="success">
						<th>#</th>
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pageInfo.list}" var="emp">
						<tr class="active">
							<th>${emp.empId}</th>
							<th>${emp.empName}</th>
							<th>${emp.gender=="M"?"男":"女"}</th>
							<th>${emp.email}</th>
							<th>${emp.department.deptName}</th>
							<th>
								<button class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									新增
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</button>
							</th>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">
				当前${pageInfo.pageNum}页，总共${pageInfo.pages}页，共${pageInfo.total}条数据</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				<ul class="pagination">
					<li><a href="${APP_path}/emps?pn=1">首页</a></li>
					
					<c:if test="${pageInfo.hasPreviousPage}">
						<li><a href="${APP_path}/emps?pn=${pageInfo.pageNum-1}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>


					<c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
						<c:if test="${page_Num==pageInfo.pageNum}">
							<li class="active"><a href="#">${page_Num}</a></li>
						</c:if>
						<c:if test="${page_Num!=pageInfo.pageNum}">
							<li><a href="${APP_path}/emps?pn=${page_Num}">${page_Num}</a></li>
						</c:if>
					</c:forEach>

					<c:if test="${pageInfo.hasNextPage}">
						<li><a href="${APP_path}/emps?pn=${pageInfo.pageNum+1}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
					
					<li><a href="${APP_path}/emps?pn=${pageInfo.pages}">末页</a></li>
				</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>
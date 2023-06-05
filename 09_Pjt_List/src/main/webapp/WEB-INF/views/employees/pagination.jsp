<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script>
	$(function(){
		// recordPerPage의 변경
		$('#recordPerPage').on('change', function(){
			location.href = '${contextPath}/employees/change/record.do?recordPerPage=' + $(this).val();	// session에 올리기(스크립트말고 백단에서 처리해야함.)
		})
		
		// 세션에 저장된 recordPerPage값으로 <select> 태그의 값을 세팅
		// pageContext, request, session, application - 속성을 저장하는 이 네군데는 EL문법으로 가져올 수 있다.
		// Scope 키워드 붙여서 가져오는게 제일 정확하다.
		let recordPerPage = '${sessionScope.recordPerPage}' == '' ? '10' : '${sessionScope.recordPerPage}';
		$('#recordPerPage').val(recordPerPage);
		// 제목을 클릭하면 정렬 방식을 바꿈
		// 현재 정렬상태가 오름차순이면 내림차순으로, 내림차순이면 오름차순으로 바꿔줘야 한다.
		// 즉, pagination은 현재 정렬상태가 무엇인지 알고 있어야 한다. 그런 후 앞으로 해야할 정렬상태를 넘기는 것이다.
		// 또한 현재 페이지가 몇 페이지인지에 대한 정보가 필요하다.
		
		$('.title').on('click', function() {
			location.href = '${contextPath}/employees/pagination.do?column=' + $(this).data('column') + '&order=' + $(this).data('order') + '&page=' + ${page}
		})
	})

</script>
<style>
	.pagination{ 
		width: 350px;
		margin: 0 auto;
	}
	.pagination span, .pagination a{
		display: inline-block;
		width: 50px;
	}
	.hidden {
		visibility: hidden;
	}
	.strong {
		font-weight: 900;
	}
	.link {
		color: orange;
	}
	table {
		width: 1500px;
	}
	table td:nth-of-type(1) { width: 100px }
	table td:nth-of-type(2) { width: 150px }
	table td:nth-of-type(3) { width: 300px }
</style>

</head>
<body>

	<div>
		<a href="${contextPath}/employees/search.do">사원 조회 화면으로 이동</a>	
	</div>
	
	<div>
		<h1>사원 목록</h1>
		<div>
			<select id="recordPerPage">
				<option value="10">10개</option>
				<option value="20">20개</option>
				<option value="30">30개</option>
			</select>
		</div>
		<hr>
		<table border="1">
			<thead>
				<tr>	
					<td>순번</td>
					<!-- 태그에 속성을 부여하는 data- 속성 -->
					<td><span class="title" data-column="E.EMPLOYEE_ID" data-order="${order}">사원번호</span></td>
					<td><span class="title" data-column="E.FIRST_NAME" data-order="${order}">사원명</span></td>
					<td><span class="title" data-column="E.EMAIL" data-order="${order}">이메일</span></td>
					<td><span class="title" data-column="E.PHONE_NUMBER" data-order="${order}">전화번호</span></td>
					<td><span class="title" data-column="E.HIRE_DATE" data-order="${order}">입사일자</span></td>
					<td><span class="title" data-column="E.JOB_ID" data-order="${order}">직업</span></td>
					<td><span class="title" data-column="E.SALARY" data-order="${order}">연봉</span></td>
					<td><span class="title" data-column="E.COMMISSION_PCT" data-order="${order}">커미션</span></td>
					<td><span class="title" data-column="E.MANAGER_ID" data-order="${order}">매니저</span></td>
					<td><span class="title" data-column="E.DEPARTMENT_ID" data-order="${order}">부서번호</span></td>
					<td><span class="title" data-column="D.DEPARTMENT_NAME" data-order="${order}">부서명</span></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${employees}" var="emp" varStatus="vs"> <!-- varStatus : 인덱스를 사용하고 싶으면 이 속성을 부여해야한다. -->
					<tr>
						<td>${beginNo - vs.index}</td> <!-- .index키워드로 인덱스를 꺼내올 수 있다. -->
						<td>${emp.employeeId}</td>
						<td>${emp.firstName} ${emp.lastName}</td>
						<td>${emp.email}</td>
						<td>${emp.phoneNumber}</td>
						<td>${emp.hireDate}</td>
						<td>${emp.jobId}</td>
						<td>${emp.salary}</td>
						<td>${emp.commissionPct}</td>
						<td>${emp.managerId}</td>
						<td>${emp.deptDTO.departmentId}</td>
						<td>${emp.deptDTO.departmentName}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="12">
						${pagination} <!-- 하단에 페이지번호 뿌릴 곳 -->
					</td>
				</tr>
			</tfoot>		
		</table>
	</div>
</body>
</html>
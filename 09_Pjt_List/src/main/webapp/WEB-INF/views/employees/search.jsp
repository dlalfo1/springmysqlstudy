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
		// 자동 완성 목록 초기화
		$('#column').on('change', function(){
			$('#auto_complete').empty();
			$('#query').val('');
		})
		// 자동 완성 목록 가져오기
		// 글자입력할 때마다 function을 실행시킨다. (keyup 이벤트)
		$('#query').on('keyup', function(){
			$('#auto_complete').empty(); // 검색어 목록 초기화
			$.ajax({
				// 요청
				type: 'get',
				url: '${contextPath}/employees/autoComplete.do',
				data: $('#frm1').serialize(), // form태그 안에 있는 모든 name속성을 보내는 serialize()
				// 응답
				dataType: 'json',
				success: function(resData){	// resData = {"employees": [{"firstName": "xxx", "phoneNumber": "xxx", "deptDTO":{"departmentName": "xxx"}}, {}, {}, /..]}
					let property = '';
					switch($('#column').val()){
					case "E.FIRST_NAME": property = 'firstName'; break;
					case "E.PHONE_NUMBER": property = 'phoneNumber'; break;
					case "D.DEPARTMENT_NAME": property = 'deptDTO'; break;
					
					}
					$.each(resData.employees, function(i, employee){
						// employee.property   : employee.'firstName'  (X)
						// 	employee[property] : employee['firstName'] (O)
						// 속성이 문자열로 되어있다면 객체['속성'] 방법으로 속성을 꺼내와야한다.
						switch($('#column').val()) {
						case "E.FIRST_NAME":
							$('#auto_complete').append('<option value="' + employee.firstName + '" />');
							break;
						case "E.PHONE_NUMBER":
							$('#auto_complete').append('<option value="' + employee.phoneNumber + '" />');
							break;
						case "D.DEPARTMENT_NAME":
							$('#auto_complete').append('<option value="' + employee.deptDTO.departmentName + '" />');
							break;
						}
					})
				}

			})
			
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

	<h1>사원 검색</h1>

	<div>
		<form id="frm1" action="${contextPath}/employees/search.do">
			<select name="column" id="column">
				<option value="E.FIRST_NAME">FIRST_NAME</option>
				<option value="E.PHONE_NUMBER">PHONE_NUMBER</option>
				<option value="D.DEPARTMENT_NAME">DEPARTMENT_NAME</option>
			</select>
			<input list="auto_complete" type="text" name="query" id="query"> <!-- 목록을 고를 수도 있고 입력을 할 수도 있고 -->
			<datalist id="auto_complete">
		
			
			</datalist>
			<button>조회</button>
		
		</form>
	</div>
	
	<div>

		<hr>
		<table border="1">
			<thead>
				<tr>	
					<td>순번</td>
					<td>사원번호</td>
					<td>사원명</td>
					<td>이메일</td>
					<td>전화번호</td>
					<td>입사일자</td>
					<td>직업</td>
					<td>연봉</td>
					<td>커미션</td>
					<td>매니저</td>
					<td>부서번호</td>
					<td>부서명</td>
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
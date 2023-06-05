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
			location.href = '${contextPath}/upload/change/record.do?recordPerPage=' + $(this).val();	// session에 올리기(스크립트말고 백단에서 처리해야함.)
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
			location.href = '${contextPath}/upload/pagination.do?column=' + $(this).data('column') + '&order=' + $(this).data('order') + '&page=' + ${page}
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
		<a href="${contextPath}/upload/write.do">게시글 작성하러 가기</a>
	</div>

	<div>
		<h1>게시글 목록</h1>
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
					<td><span class="title" data-column="UPLOAD_NO" data-order="${order}">업로드 번호</span></td>
					<td><span class="title" data-column="U.UPLOAD_TITLE" data-order="${order}">제목</span></td>
					<td><span class="title" data-column="U.UPLOAD_CONTENT" data-order="${order}">내용</span></td>
					<td><span class="title" data-column="U.CREATED_AT" data-order="${order}">작성일자</span></td>
					<td><span class="title" data-column="U.MODIFIED_AT" data-order="${order}">수정일자</span></td>
					<td><span class="title" data-column="ATTACH_COUNT" data-order="${order}">첨부개수	</span></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${uploads}" var="upload" varStatus="vs"> <!-- varStatus : 인덱스를 사용하고 싶으면 이 속성을 부여해야한다. -->
					<tr>
						<td>${beginNo - vs.index}</td> <!-- .index키워드로 인덱스를 꺼내올 수 있다. -->
						<td>${upload.uploadNo}</td>
						<td><a href="${contextPath}/upload/detail.do?uploadNo=${upload.uploadNo}">${upload.uploadTitle}</a></td>
						<td>${upload.uploadContent}</td>
						<td>${upload.createdAt}</td>
						<td>${upload.modifiedAt}</td>
						<td>${upload.attachCount}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="7">
						${pagination} <!-- 하단에 페이지번호 뿌릴 곳 -->
					</td>
				</tr>
			</tfoot>		
		</table>
	</div>
</body>
</html>
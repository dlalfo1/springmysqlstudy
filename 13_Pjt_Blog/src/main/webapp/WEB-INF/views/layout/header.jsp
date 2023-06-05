<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${param.title eq null ? 'Welcome' : param.title}</title> <!-- 파라미터 타이틀이 온다. -->
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.css">
<link rel="stylesheet" href="${contextPath}/resources/css/init.css?dt=${dt}" />
<link rel="stylesheet" href="${contextPath}/resources/css/header.css?dt=${dt}" />
<!-- css파일 link태그로 포함할 때 첫 실행시 캐싱으로 css파일의 내용을 기억해두기 때문에 -
	 css파일을 변경해도 jsp파일에 적용되지 않는다. 
	 주소값을 변경해주는걸(가짜 파라미터사용)로 이 문제를 해결할 수 있다. 파라미터는 주소에 영향을 미치지 않으니 아무것나 붙여서 매번 주소값을 변경해준다.
-->
</head>
<body>
  <div>
  	<!-- 우리 로고 뿌려주기,,, -->
    <h1>My Blog</h1>
  	<!-- gnb만들어서 뿌리기 -->
  	<ul>
  	  <li><a href="${contextPath}/blog/list.do">블로그</a></li>
  	  <li><a href="${contextPath}/qna/list.do">QnA</a></li>
  	</ul>
  </div>
  
  <hr>
	
</body>
</html>
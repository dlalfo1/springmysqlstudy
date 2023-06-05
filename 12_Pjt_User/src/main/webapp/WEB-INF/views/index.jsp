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
    
    function fnLeave(){
    	if(confirm('동일한 아이디로 재가입이 불가능합니다. 회원 탈퇴하시겠습니까?')){
    		// 확인버튼 누르면 탈퇴페이지로 보내주는것도 좋을듯 (이런정보 삭제되는데 동의하냐~ 이런거?)
    		location.href = '${contextPath}/user/leave.do';
    	}
    }

</script>
</head>
<body>

	<div>
    <!-- 로그인이 안 된 상태 -->
    <c:if test="${sessionScope.loginId == null}">
      <!-- form : 페이지(뷰)로 넘어가기, .do : 디비까지 거쳐서 실제 기능 작동 -->
      <a href="${contextPath}/user/agree.form">회원가입</a>
      <a href="${contextPath}/user/login.form">로그인</a>
    </c:if>
    
    <!-- 로그인이 된 상태 -->
    <c:if test="${sessionScope.loginId != null}">
      <div>
        <a href="#">${sessionScope.loginId}</a>님 반갑습니다!
      </div>
      <div>
        <a href="${contextPath}/user/logout.do">로그아웃</a>
        <a href="javascript:fnLeave()">회원탈퇴</a> <!-- javascript:fnLeave() : 자바스크립트 펑션 호출 -->
      </div>
    </c:if>
 
   
    <!-- 관리자 접속 -->
    <c:if test="${sessionScope.loginId == 'admin'}">
      <!-- 관리자화면에서 보여주고 싶은걸 여기서 하면 됨. -->
    
    </c:if>

	</div>
	
</body>
</html>
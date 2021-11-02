<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="head.jsp"%>

<h1>Error</h1>
<c:choose>
	<c:when test="${not empty error}">
		<H2>
			<fmt:message key="${error}" />
		</H2>
	</c:when>
	<c:when test="${not empty pageContext.errorData.requestURI}">
		<H2>Запрошенная страница ${pageContext.errorData.requestURI} не
			найдена на сервере</H2>
	</c:when>
	<c:otherwise>Непредвиденная ошибка приложения</c:otherwise>
</c:choose>

<c:import url="/WEB-INF/jsp/fotter.jsp" />

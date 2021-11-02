<!-- <!DOCTYPE html> -->
<!-- <html> -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tld/custom.tld" prefix="ctg"%>


<head>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>

	
</head>
	<style><%@include file="/WEB-INF/main.css"%></style>

 <script>
//             function locationreload() {
//                 location.reload();  
//         }
        </script>

<body>
<div class="wrap">
<jsp:useBean id="random" class="java.util.Random" scope="application" />
		<c:set var="requestIdentity" value="${random.nextInt(1000000)}" scope="session" />

<c:if test="${not empty cookie.localeName}">
		<fmt:setLocale value="${cookie.localeName.getValue()}" scope="session" />
	</c:if>
	<fmt:setBundle basename="pagecontent" />

	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
		<div class="container">
			<div class="row">
				<div class="col-md-2">
<!-- 192.168.0.102 -->
					<a class="navbar-brand"> <img
						src="http://localhost:8080/webCoffeeApp/img/logo1.png" alt="Logod"
						style="width: 50px;" class="rounded-pill">
					</a>
				</div>

				<div class="col-md-4">
					<c:if test="${not empty role }">
						<c:url value="/jsp/menu.html" var="menuUrl" />
						<form name="menuUrl" action="${menuUrl}" method="POST">
							<button type="submit" class="btn btn-secondary pl-0">
								<fmt:message key="Head.MainMenu" />
							</button>
						</form>
					</c:if>

				</div>

				<div class="col-md-3">
					<c:if
						test="${not empty role  }"> 
						<c:url value="/jsp/logOut.html" var="logOutUrl" />
						<form name="logOutUrl" action="${logOutUrl}" method="POST">
							<button type="submit" class="btn btn-secondary">
								<fmt:message key="Head.LogOut" />
							</button>
						</form>
					</c:if>
				</div>

				<div class="col-md-2">
					<c:url value="/jsp/locale.html" var="setLocaleUrl" />
					<form name="setLocale" action="${setLocaleUrl}" method="POST">
						<input type="hidden" name="localeName" value="en_US"> <input
							type="hidden" name="currentPage"
							value="${pageContext.request.requestURI}">
						<button type="submit" class="btn btn-secondary">ENG</button>
					</form>
				</div>

				<div class="col-md-1">
					<c:url value="/jsp/locale.html" var="setLocaleUrl" />
					<form name="setLocale" action="${setLocaleUrl}" method="POST">
						<input type="hidden" name="localeName" value="ru_RU"> <input
							type="hidden" name="currentPage"
							value="${pageContext.request.requestURI}">
						<button type="submit" class="btn btn-secondary">RU</button>
					</form>
				</div>

			</div>
		</div>

	</nav>


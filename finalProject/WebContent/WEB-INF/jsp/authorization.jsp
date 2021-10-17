<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="head.jsp"%>

<div class="content">
	<div class="container">
		<div class="v-wrap">
			<article class="v-box">


				<h4>
					<fmt:message key="AuthorizationPage.Head" />
				</h4>

				<br> <br>
				<c:url value="/jsp/result.html" var="resultUrl" />
				<form name="AccessAction" action="${resultUrl}" method="POST">
					<label for="username"><fmt:message
							key="AuthorizationPage.Login" /></label> <br> <input type="text"
						id="username" name="username"> <br> <label for="pwd"><fmt:message
							key="AuthorizationPage.Password" /></label> <br> <input
						type="password" id="pwd" name="pwd"> <br> <br> <input
						type="submit" value=<fmt:message key="AuthorizationPage.Button" />>
				</form>

			</article>
		</div>
	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>


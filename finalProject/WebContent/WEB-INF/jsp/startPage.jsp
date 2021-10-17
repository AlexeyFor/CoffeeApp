<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="head.jsp"%>




<div class="content">
	<div class="container">
		<div class="krassivo">
			<div class="v-wrap">
				<article class="v-box">

					<div class="brd">

						<h1 class="display-5">
							<fmt:message key="StartPage.Head" />
						</h1>
					</div>
					<c:url value="/jsp/authorization.html" var="authorizationUrl" />
					<form name="AccessAction" action="${authorizationUrl}"
						method="POST">
						<%-- 		<label for="authorization"><fmt:message key="StartPage.Login" /></label> --%>
						<br>
						<button type="submit" class="btn btn-secondary"
							data-bs-toggle="tooltip"
							title="<fmt:message key="StartPage.Login" />">
							<fmt:message key="StartPage.LoginButton" />
						</button>
					</form>

					<c:url value="/jsp/guest.html" var="guestUrl" />
					<form name="guestAction" action="${guestUrl}" method="POST">
						<%-- 		<label for="guest"><fmt:message key="StartPage.GuestAccess" /></label> --%>
						<br>
						<button type="submit" class="btn btn-secondary "
							data-bs-toggle="tooltip"
							title="<fmt:message key="StartPage.GuestAccess" />">
							<fmt:message key="StartPage.GuestAccessButton" />
						</button>
					</form>

					<c:url value="/jsp/registration.html" var="registrationUrl" />
					<form name="registrationUrl" action="${registrationUrl}"
						method="POST">
						<%-- 		<label for="guest"><fmt:message key="StartPage.GuestAccess" /></label> --%>
						<br>
						<button type="submit" class="btn btn-secondary">
							<fmt:message key="StartPage.Registration" />
						</button>
					</form>

				</article>
			</div>
		</div>
	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

<!-- </body> -->
<%-- </u:html> --%>
<!-- </html> -->
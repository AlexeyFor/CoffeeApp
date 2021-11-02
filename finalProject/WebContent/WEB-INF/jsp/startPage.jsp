<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="head.jsp"%>




<div class="content">
	<div class="container">
		<div class="krassivo">
			<div class="v-wrap">
				<article class="v-box">

					<div class="brd">
						<c:if test="${not empty message}">
							<div class="alert alert-danger alert-dismissible">
								<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
								<fmt:message key="${message}" />

							</div>
						</c:if>

						<c:if test="${not empty securutyFilterMessage}">
							<c:if test="${securutyFilterMessage !=''}">
								<div class="alert alert-danger alert-dismissible">
									<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
									<fmt:message key="${securutyFilterMessage}" />
									<c:set var="securutyFilterMessage" value="" scope="session" />

								</div>
							</c:if>
						</c:if>

						<h1 class="display-5">
							<fmt:message key="StartPage.Head" />
						</h1>

					</div>
					<c:url value="/jsp/authorization.html" var="authorizationUrl" />
					<form name="AccessAction" action="${authorizationUrl}"
						method="POST">
						<br>
						<button type="submit" class="btn btn-secondary"
							data-bs-toggle="tooltip"
							title="<fmt:message key="StartPage.Login" />">
							<fmt:message key="StartPage.LoginButton" />
						</button>
					</form>

					<c:url value="/jsp/guest.html" var="guestUrl" />
					<form name="guestAction" action="${guestUrl}" method="POST">
						<br>
						<button type="submit" class="btn btn-secondary "
							data-bs-toggle="tooltip"
							title="<fmt:message key="StartPage.GuestAccess" />">
							<fmt:message key="StartPage.GuestAccessButton" />
						</button>
					</form>

					<c:url value="/jsp/nothing.html" var="registrationUrl" />
					<form name="registrationUrl" action="${registrationUrl}"
						method="POST">
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


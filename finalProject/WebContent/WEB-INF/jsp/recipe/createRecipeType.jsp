<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="container">
		<h3 class="display-5">
			<fmt:message key="CreateRecipe.Head" />
		</h3>
		<fmt:message key="${message}"></fmt:message>
		<br>
		<h3>${recipeId}</h3>
		<br>
		<h3>${infusions}</h3>
		<br>
<h3 class="display-5">
			WAZZZAP
		</h3>

	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

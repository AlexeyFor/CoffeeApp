<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="content">
		<div class="container">

<%-- 			<c:choose> --%>
<%-- 				<c:when test="${not empty messageHead}"> --%>
					<h3 class="display-5">
						<fmt:message key="ShowUserInfo.Name" />
						${user.userInfo.name}
					</h3>
<%-- 				</c:when> --%>
<%-- 				<c:otherwise> --%>
<!-- 					<h3 class="display-5"> -->
<%-- 						<fmt:message key="ShowAllRecipes.Head" /> --%>
<!-- 					</h3> -->
<%-- 				</c:otherwise> --%>
<%-- 			</c:choose> --%>

			<br>
			<table class="table table-striped">
				<tr>
					<th><fmt:message key="ShowUserInfo.Email" />:</th>
					<td>${user.userInfo.email}</td>
				</tr>
				<tr>
					<th><fmt:message key="ShowUserInfo.Country" />:</th>
					<td>${user.userInfo.country.countryName}</td>
				</tr>
			</table>
			<p>
				<strong><fmt:message key="Recipe.Coffee.Information" />:</strong>
				${user.userInfo.information}
			</p>

<strong><fmt:message key="ShowUserInfo.Recipes" />:</strong>
			<table class="table table-striped">
				<tr>
					<th><fmt:message key="ShowAllRecipes.DateOfCreating" /></th>
					<th><fmt:message key="ShowAllRecipes.RecipeType" /></th>
					<th><fmt:message key="ShowAllRecipes.CoffeeName" /></th>
					<th><fmt:message key="ShowAllRecipes.CoffeeCountry" /></th>
					<th><fmt:message key="ShowAllRecipes.RoastDegree" /></th>
					<th><fmt:message key="ShowAllRecipes.ProcessingMethod" /></th>
					<th><fmt:message key="ShowAllRecipes.ShowRecipeButton" /></th>

				</tr>
				<c:forEach items="${recipes}" var="recipe">
					<tr>

						<td><fmt:formatDate value="${recipe.dateOfCreating}"
								pattern="dd.MM.yyyy" /></td>
						<td>${recipe.recipeType}</td>
						<td>${recipe.coffeeType.name}</td>
						<td>${recipe.coffeeType.country.countryName}</td>
						<td>${recipe.coffeeType.roastDegree}</td>
						<td>${recipe.coffeeType.processingMethod}</td>


						<td><c:url value="/jsp/recipe/showRecipe.html"
								var="ShowRecipeUrl" />
							<form name="ShowRecipe" action="${ShowRecipeUrl}" method="POST">
								<input type="hidden" name="recipeID" value="${recipe.ID}">
								<c:if test="${not empty allSaved and allSaved}">
									<input type="hidden" name="allSaved" id="allSaved"
										value="${allSaved}">
								</c:if>
								<input type="submit" value="show recipe">

							</form></td>
					</tr>
				</c:forEach>
			</table>

		</div>
	</div>
</div>
<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>
	
<div class="content">
	<div class="content">
		<div class="container">
		<h3 class="display-5">
			<fmt:message key="ShowAllRecipes.Head" />
		</h3>


		<table class="table table-striped">
			<tr>
				<th><fmt:message key="ShowAllRecipes.Author" /></th>
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

					<td><ctg:userNameTag>${recipe.authorUserId}</ctg:userNameTag></td>
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


<!-- </body> -->
<%-- </u:html> --%>
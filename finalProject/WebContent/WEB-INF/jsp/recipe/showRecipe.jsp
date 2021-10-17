<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/custom.tld" prefix="ctg"%>


<%@include file="/WEB-INF/jsp/head.jsp"%>

	<div class="content">
		<div class="container">
			<h3>
				<fmt:message key="Recipe.Head" />
			</h3>
					<c:if test="${not empty role and role eq 'user'}">
								<c:if test="${recipe.common}">
								<h6><fmt:message key="Recipe.Note" /></h6>
						</c:if>
			</c:if>
			
			<p>
				<strong><fmt:message key="Recipe.Author" />:</strong>
				<ctg:userNameTag>${recipe.authorUserId}</ctg:userNameTag>
			</p>
			<c:url value="/jsp/user/showPublicUserInfo.html"
				var="showPublicUserInfoUrl" />
			<form name="showPublicUserInfoUrl" action="${showPublicUserInfoUrl}"
				method="POST">
				<INPUT type="hidden" name="userID" value="${recipe.authorUserId}">
				<input type="submit" value="more about user">
			</form>
			<p>
				<strong><fmt:message key="Recipe.DateOfCreating" />:</strong>
				<fmt:formatDate value="${recipe.dateOfCreating}"
					pattern="dd.MM.yyyy" />
			</p>
			<p>
				<strong><fmt:message key="Recipe.RecipeType" />:</strong>
				${recipe.recipeType.name}
			</p>


			<div class="row">
				<div class="col-md-12">
					<button type="button" class="btn btn-secondary"
						data-bs-toggle="collapse" data-bs-target="#MainCoffee">
						<fmt:message key="Recipe.CoffeeInformation" />
					</button>
					<div id="MainCoffee" class="collapse">
						<table class="table table-striped">
							<tr>
								<th><fmt:message key="Recipe.Coffee.CoffeeName" />:</th>
								<td>${recipe.coffeeType.name}</td>
							</tr>
							<tr>
								<th><fmt:message key="Recipe.Coffee.CoffeeCountry" />:</th>
								<td>${recipe.coffeeType.country.countryName}</td>
							</tr>
							<tr>
								<th><fmt:message key="Recipe.Coffee.ArabicPercent" />:</th>
								<td>${recipe.coffeeType.arabicPercent}%</td>
							</tr>
							<tr>
								<th><fmt:message key="Recipe.Coffee.RobustaPercent" />:</th>
								<td>${recipe.coffeeType.robustaPercent }%</td>
							</tr>
							<tr>
								<th><fmt:message key="Recipe.Coffee.Roaster" />:</th>
								<td>${recipe.coffeeType.roaster}</td>
							</tr>
							<tr>
								<th><fmt:message key="Recipe.Coffee.RoastDegree" />:</th>
								<td>${recipe.coffeeType.roastDegree}</td>
							</tr>
							<tr>
								<th><fmt:message key="Recipe.Coffee.ProcessingMethod" />:</th>
								<td>${recipe.coffeeType.processingMethod}</td>
							</tr>

						</table>
						<p>
							<strong><fmt:message key="Recipe.Coffee.Information" />:</strong>
							${recipe.coffeeType.information}
						</p>

					</div>
				</div>
			</div>

<c:if
							test="${not empty recipe.recipeName}">
							
			<div class="row">
				<div class="col-md-12">
					<button type="button" class="btn btn-secondary"
						data-bs-toggle="collapse" data-bs-target="#MainRecipe">
						<fmt:message key="Recipe.RecipeHead" />
					</button>
					<div id="MainRecipe" class="collapse">
						<c:if
							test="${not empty recipe.recipeType and recipe.recipeType eq 'FRENCHPRESS'}">
							<table class="table table-striped">
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.RecipeName" />:</th>
									<td>${recipe.recipeName}:</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.FrechPressVolume" />:</th>
									<td>${recipe.frechPressVolume}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.MassOfCoffee" />:</th>
									<td>${recipe.massOfCoffee}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.GrindSettings" />:
										<button type="button" class="btn btn-link"
											data-bs-toggle="popover"
											data-bs-content="<fmt:message key="Recipe.FrenchPress.GrindSettings.More"/>:">
											?</button></th>
									<td>${recipe.grindSettings}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.CoffeeGrinder" />:</th>
									<td>${recipe.coffeeGrinder}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.CapBreakingTime" />:</th>
									<td>${recipe.capBreakingTime}</td>
								</tr>
								<tr>
									<th><fmt:message
											key="Recipe.FrenchPress.PlungerLoweringTime" />:</th>
									<td>${recipe.plungerLoweringTime}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.FrenchPress.TotalTime" />:</th>
									<td>${recipe.totalTime}</td>
								</tr>
							</table>
							<p>
								<strong><fmt:message
										key="Recipe.FrenchPress.Description" />:</strong>${recipe.disription}
							</p>
						</c:if>

						<c:if
							test="${not empty recipe.recipeType and recipe.recipeType eq 'POUROVER'}">
							<table class="table table-striped">
								<tr>
									<th><fmt:message key="Recipe.Pourorver.RecipeName" />:</th>
									<td>${recipe.recipeName}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.Pourorver.FunnelType" />:</th>
									<td>${recipe.funnelType.name}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.Pourorver.MassOfCoffee" />:</th>
									<td>${recipe.massOfCoffee}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.Pourorver.GrindSettings" />:
										<button type="button" class="btn btn-link"
											data-bs-toggle="popover"
											data-bs-content="<fmt:message key="Recipe.FrenchPress.GrindSettings.More"/>:">
											?</button></th>
									<td>${recipe.grindSettings}</td>
								</tr>
								<tr>
									<th><fmt:message key="Recipe.Pourorver.CoffeeGrinder" />:</th>
									<td>${recipe.coffeeGrinder}</td>
								</tr>
								<tr>

									<th><fmt:message key="Recipe.Pourorver.TotalTime" />:</th>
									<td>${recipe.totalTime}</td>
								</tr>
							</table>
							<p>
								<strong><fmt:message key="Recipe.Pourorver.Description" />:</strong>${recipe.disription}
							</p>
						</c:if>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<button type="button" class="btn btn-secondary"
						data-bs-toggle="collapse" data-bs-target="#MainInfusion">
						<fmt:message key="Recipe.Infusion.Head" />
					</button>
					<div id="MainInfusion" class="collapse">

						<table class="table table-striped">
							<tr>
								<th><fmt:message key="Recipe.Infusion.TimeStart" />:</th>
								<th><fmt:message key="Recipe.Infusion.WaterVolume" />:</th>
								<th><fmt:message key="Recipe.Infusion.TimeEnd" />:</th>
								<th><fmt:message key="Recipe.Infusion.WaterTemperature" />:</th>
							</tr>
							<c:forEach items="${recipe.infusions}" var="infusion">
								<tr>
									<td>${infusion.timeStart}</td>
									<td>${infusion.waterVolume}</td>
									<td>${infusion.timeEnd}</td>
									<td>${infusion.waterTemperature}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
</c:if>
			<c:if test="${not empty role and role eq 'user'}">
				<c:if test="${recipe.common}">

					<c:url value="/jsp/user/saveRecipeToYourself.html"
						var="saveRecipeToYourselfUrl" />
					<form name="saveRecipeToYourselfUrl"
						action="${saveRecipeToYourselfUrl}" method="POST">
						<button type="submit" class="btn btn-secondary ">save to
							yourself</button>
					</form>

					<c:url value="/jsp/user/editRecipe.html" var="editRecipeUrl" />
					<form name="editRecipeUrl" action="${editRecipeUrl}" method="POST">
						<button type="submit" class="btn btn-secondary ">edit</button>
					</form>
				</c:if>

				<c:if test="${!recipe.common}">

					<c:url value="/jsp/user/deleteRecipe.html" var="deleteRecipeUrl" />
					<form name="deleteRecipeUrl" action="${deleteRecipeUrl}"
						method="POST">
						<button type="submit" class="btn btn-secondary ">delete
							recipe</button>
					</form>

					<c:url value="/jsp/user/editRecipe.html" var="editRecipeUrl" />
					<form name="editRecipeUrl" action="${editRecipeUrl}" method="POST">
						<button type="submit" class="btn btn-secondary ">edit</button>
					</form>
				</c:if>
			</c:if>

			<c:if test="${not empty role and role eq 'moder'}">
				<c:if test="${recipe.common}">

					<c:url value="/jsp/user/deleteRecipe.html" var="deleteRecipeUrl" />
					<form name="deleteRecipeUrl" action="${deleteRecipeUrl}"
						method="POST">
						<button type="submit" class="btn btn-secondary ">delete
							recipe</button>
					</form>

					<c:url value="/jsp/user/editRecipe.html" var="editRecipeUrl" />
					<form name="editRecipeUrl" action="${editRecipeUrl}" method="POST">
						<button type="submit" class="btn btn-secondary ">edit</button>
					</form>
				</c:if>
			</c:if>


		</div>
	</div>
	<div class="fotter">
		<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

<script>
	var popoverTriggerList = [].slice.call(document
			.querySelectorAll('[data-bs-toggle="popover"]'))
	var popoverList = popoverTriggerList.map(function(popoverTriggerEl) {
		return new bootstrap.Popover(popoverTriggerEl)
	})
</script>



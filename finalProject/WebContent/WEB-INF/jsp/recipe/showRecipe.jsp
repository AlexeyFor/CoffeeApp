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
				<h6>
					<fmt:message key="Recipe.Note" />
				</h6>
			</c:if>
		</c:if>
		<c:if test="${not empty message}">
			<h3>
				<fmt:message key="${message}"></fmt:message>
			</h3>
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
			<input type="submit"
				value="<fmt:message key="ShowRecipe.button.UserMore" />">
		</form>
		<p>
			<strong><fmt:message key="Recipe.DateOfCreating" />:</strong>
			<fmt:formatDate value="${recipe.dateOfCreating}" pattern="dd.MM.yyyy" />
		</p>
		<p>
			<strong><fmt:message key="Recipe.RecipeType" />:</strong>
			<fmt:message key="${recipe.recipeType}" />

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
							<td><fmt:message key="${recipe.coffeeType.roastDegree}" />
							</td>
						</tr>
						<tr>
							<th><fmt:message key="Recipe.Coffee.ProcessingMethod" />:</th>
							<td><fmt:message key="${recipe.coffeeType.processingMethod}" />
							</td>
						</tr>

					</table>
					<p>
						<strong><fmt:message key="Recipe.Coffee.Information" />:</strong>
						${recipe.coffeeType.information}
					</p>

				</div>
			</div>
		</div>

		<c:if test="${not empty recipe.recipeName}">

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

									<td><fmt:message key="${recipe.funnelType}" /></td>
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
								<strong><fmt:message key="Recipe.Pourorver.Description" />:
								</strong>${recipe.disription}
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

				<c:url value="/jsp/recipe/saveCommonRecipe.html"
					var="saveRecipeToYourselfUrl" />
				<form name="saveRecipeToYourselfUrl"
					action="${saveRecipeToYourselfUrl}" method="POST">
					<input type="hidden" name="recipeId" id="recipeId"
						value="${recipe.ID}"> <input type="hidden"
						name="recipeCommon" id="recipeCommon" value="${recipe.common}">
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="ShowRecipe.button.SaveYourself" />
					</button>
				</form>

				<c:if test="${not empty allSaved and allSaved}">
					<c:url value="/jsp/recipe/editRecipeStep1Coffee.html"
						var="editRecipeStep1CoffeeUrl" />
					<form name="editRecipeStep1CoffeeUrl"
						action="${editRecipeStep1CoffeeUrl}" method="POST">
						<input type="hidden" name="recipeId" id="recipeId"
							value="${recipe.ID}"><input type="hidden"
							name="infusionsNumber" id="infusionsNumber"
							value="${recipe.infusions.size()}">
						<button type="submit" class="btn btn-secondary ">
							<fmt:message key="ShowRecipe.button.Edit" />
						</button>
					</form>
				</c:if>


				<c:if test="${not empty allSaved and allSaved}">

					<a href="#deleteCommonID" class="btn btn-secondary"
						data-bs-toggle="collapse"> <fmt:message
							key="ShowRecipe.button.Delete" />
					</a>
					<div id="deleteCommonID" class="collapse">
						<br> Are you sure?

						<c:url value="/jsp/recipe/deleteSavedCommonRecipe.html"
							var="deleteSavedCommonRecipeUrl" />
						<form name="deleteSavedCommonRecipeUrl"
							action="${deleteSavedCommonRecipeUrl}" method="POST">
							<input type="hidden" name="recipeId" id="recipeId"
								value="${recipe.ID}"> <input type="hidden"
								name="recipeCommon" id="recipeCommon" value="${recipe.common}">
							<button type="submit" class="btn btn-secondary ">
								<fmt:message key="ShowRecipe.button.Delete" />
							</button>
						</form>
					</div>

				</c:if>
			</c:if>



			<c:if test="${!recipe.common}">
			
				<c:url value="/jsp/recipe/editRecipeStep1Coffee.html"
					var="editRecipeStep1CoffeeUrl" />
				<form name="editRecipeStep1CoffeeUrl"
					action="${editRecipeStep1CoffeeUrl}" method="POST">
					<input type="hidden" name="recipeId" id="recipeId"
						value="${recipe.ID}"> <input type="hidden"
						name="infusionsNumber" id="infusionsNumber"
						value="${recipe.infusions.size()}">
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="ShowRecipe.button.Edit" />
					</button>
				</form>
				<br>
				<a href="#deleteNotCommonID" class="btn btn-secondary"
					data-bs-toggle="collapse"> <fmt:message
						key="ShowRecipe.button.Delete" />
				</a>
				<div id="deleteNotCommonID" class="collapse">
					Are you sure?
					<c:url value="/jsp/recipe/deleteNotCommonRecipe.html"
						var="deleteNotCommonRecipeUrl" />
					<form name="deleteNotCommonRecipeUrl"
						action="${deleteNotCommonRecipeUrl}" method="POST">
						<input type="hidden" name="recipeId" id="recipeId"
							value="${recipeID}"> <input type="hidden"
							name="recipeCommon" id="recipeCommon" value="${recipe.common}">
						<button type="submit" class="btn btn-secondary ">

							<fmt:message key="yes" />
						</button>
					</form>
				</div>
				
			
			</c:if>
		</c:if>

		<c:if test="${not empty role and role eq 'moder'}">
			<c:if test="${recipe.common}">

				<c:url value="/jsp/user/deleteRecipe.html" var="deleteRecipeUrl" />
				<form name="deleteRecipeUrl" action="${deleteRecipeUrl}"
					method="POST">
					<input type="hidden" name="recipeId" id="recipeId"
						value="${recipe.id}"> <input type="hidden"
						name="recipeCommon" id="recipeCommon" value="${recipe.common}">
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="ShowRecipe.button.Delete" />

					</button>
				</form>

				<c:url value="/jsp/user/editRecipe.html" var="editRecipeUrl" />
				<form name="editRecipeUrl" action="${editRecipeUrl}" method="POST">
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="ShowRecipe.button.Edit" />
					</button>
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




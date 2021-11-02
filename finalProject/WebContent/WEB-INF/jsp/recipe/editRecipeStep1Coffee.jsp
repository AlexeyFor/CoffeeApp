<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="container">
		<h3 class="display-5">
			<fmt:message key="EditRecipe.Head" />
		</h3>
		<h3 class="display-5">
			<fmt:message key="CreateRecipe.CreateType" />
			:
			<fmt:message key="${recipeType}" />
		</h3>

		<c:if test="${not empty message}">
			<h3>
				<fmt:message key="${message}"></fmt:message>
			</h3>
		</c:if>

		<c:url value="/jsp/recipe/showAllCoffeeTypeEdit.html"
			var="showAllCoffeeTypeEditUrl" />
		<form action="${showAllCoffeeTypeEditUrl}" method="POST">
			<input type="hidden" name="recipeId" id="recipeId"
				value="${recipeId}"> <input type="hidden"
				name="infusionsNumber" id="infusionsNumber"
				value="${infusionsNumber}"> <input type="hidden"
				name="startPosition" id="startPosition" value="0"> <input
				type="hidden" name="number" id="number" value="10"> <input
				type="hidden" name="sortType" id="sortType" value="NONE">


			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="CreateRecipe.SelectCoffee" />
			</button>
		</form>


		<c:url value="/jsp/recipe/editRecipeStep2.html"
			var="editRecipeStep2Url" />
		<form action="${editRecipeStep2Url}" method="POST">
			<input type="hidden" name="wasCreated" id="wasCreated" value="true">
			<input type="hidden" name="recipeId" id="recipeId"
				value="${recipeId}"> <input type="hidden"
				name="coffeWasEdit" id="coffeWasEdit" value="true">
			<table class="table table-striped">

				<tr>
					<th><fmt:message key="CreateRecipe.InfusionsNumber" />:</th>
					<td><input type="text" id="infusionsNumber"
						name="infusionsNumber" required pattern="^[1-9]{1}[0-9]{0,1}$"
						value="${infusionsNumber}"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.Roaster" />:</th>
					<td><input list="roasters" name="roaster" required
						pattern="^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$"
						value="${coffeeType.roaster}"> <datalist id="roasters">
							<c:forEach items="${roasters}" var="roaster">
								<option value="${roaster}">
							</c:forEach>
						</datalist></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.CoffeeCountry" />:</th>
					<td><select id="countries" name="countries" required>
							<option value="${coffeeType.country.ID}" selected>${coffeeType.country.countryName}</option>
							<c:forEach items="${countries}" var="country">
								<option value="${country.ID}">${country.countryName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.CoffeeName" />:</th>
					<td><input type="text" id="CoffeeName" name="CoffeeName"
						required pattern="^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$" size="50"
						value="${coffeeType.name}"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.RoastDegree" />:</th>
					<td><select id="roastDegree" name="roastDegree">
							<option value="${coffeeType.roastDegree}" selected>${coffeeType.roastDegree.name}</option>
							<c:forEach items="${roastDegrees}" var="roastDegree">
								<option value="${roastDegree}">${roastDegree.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.ProcessingMethod" />:</th>
					<td><select id="processingMethod" name="processingMethod">
							<option value="${coffeeType.processingMethod}" selected>${coffeeType.processingMethod.name}</option>
							<c:forEach items="${processingMethods}" var="processingMethod">
								<option value="${processingMethod}">${processingMethod.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.ArabicPercent" />, %:</th>
					<td><input type="text" id=ArabicPercent name="ArabicPercent"
						required pattern="^[0-9]{1,3}$"
						value="${coffeeType.arabicPercent}"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.RobustaPercent" />, %:</th>
					<td><input type="text" id=RobustaPercent name="RobustaPercent"
						required pattern="^[0-9]{1,3}$"
						value="${coffeeType.robustaPercent}"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.Information" />:</th>
					<td><textarea name="information" id="information" rows="10"
							cols="50" maxlength="1000">${coffeeType.information}</textarea></td>
				</tr>
			</table>
			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="Button.Next" />
			</button>
		</form>

		<c:url value="/jsp/recipe/editRecipeStep2.html"
			var="editRecipeStep2Url" />
		<form action="${editRecipeStep2Url}" method="POST">
			<input type="hidden" name="coffeWasEdit" id="coffeWasEdit"
				value="false"> <input type="hidden" name="infusionsNumber"
				id="infusionsNumber" value="${infusionsNumber}"> <input
				type="hidden" name="recipeId" id="recipeId" value="${recipeId}">
			<input type="hidden" name="coffeeTypeId" id="coffeeTypeId"
				value="${coffeeType.ID}">
			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="EditRecipe.NoChange" />
			</button>
		</form>
	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

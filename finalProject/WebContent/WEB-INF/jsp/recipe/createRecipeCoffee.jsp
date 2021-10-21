<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="container">
		<h3 class="display-5">
			<fmt:message key="CreateRecipe.Head" />
		</h3>
		<h3 class="display-5"><fmt:message key="CreateRecipe.CreateType" />:
		<fmt:message key="${recipeType}" />
		 </h3>
		
				<c:if test="${not empty message}">
		<h3>
			<fmt:message key="${message}"></fmt:message>
		</h3>
		</c:if>
		
		<c:url value="/jsp/recipe/showAllCoffeeType.html"
			var="showAllCoffeeTypeUrl" />
		<form action="${showAllCoffeeTypeUrl}" method="POST">
		<input type="hidden" name="recipeType" id="recipeType"
				value="${recipeType}">
		<button type="submit" class="btn btn-secondary ">
				select from all types
			</button>
		</form>
		
		
		<c:url value="/jsp/recipe/createRecipeType.html"
			var="createRecipeTypeUrl" />
		<form action="${createRecipeTypeUrl}"  method="POST">
			<input type="hidden" name="recipeType" id="recipeType"
				value="${recipeType}">
				<input type="hidden" name="wasCreated" id="wasCreated"
				value="true">
			<table class="table table-striped">

				<tr>
					<th><fmt:message key="CreateRecipe.InfusionsNumber" />:</th>
					<td><input type="text" id="infusions" name="infusions"
						required pattern="^[0-9]{1,2}$"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.Roaster" />:</th>
					<td><input list="roasters" name="roaster" required
						pattern="^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$"> <datalist
							id="roasters">
							<c:forEach items="${roasters}" var="roaster">
								<option value="${roaster}">
							</c:forEach>
						</datalist></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.CoffeeCountry" />:</th>
					<td><select id="countries" name="countries" required>
							<c:forEach items="${countries}" var="country">
								<option value="${country.ID}">${country.countryName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.CoffeeName" />:</th>
					<td><input type="text" id="CoffeeName" name="CoffeeName"
						required pattern="^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$" size="50"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.RoastDegree" />:</th>
					<td><select id="roastDegree" name="roastDegree">
							<c:forEach items="${roastDegrees}" var="roastDegree">
								<option value="${roastDegree}">${roastDegree.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.ProcessingMethod" />:</th>
					<td><select id="processingMethod" name="processingMethod">
							<c:forEach items="${processingMethods}" var="processingMethod">
								<option value="${processingMethod}">${processingMethod.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.ArabicPercent" />, %:</th>
					<td><input type="text" id=ArabicPercent name="ArabicPercent"
						required pattern="^[0-9]{1,3}$"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.RobustaPercent" />, %:</th>
					<td><input type="text" id=RobustaPercent name="RobustaPercent"
						required pattern="^[0-9]{1,3}$"></td>
				</tr>
				<tr>
					<th><fmt:message key="Recipe.Coffee.Information" />:</th>
					<td><textarea name="information" id="information" rows="10"
							cols="50" maxlength="1000"></textarea></td>
				</tr>
			</table>
			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="Button.Next" />
			</button>
		</form>



	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

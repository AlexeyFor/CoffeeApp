<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="content">
		<div class="container">
			<h3 class="display-5">
				<fmt:message key="CoffeeType.Head" />
			</h3>
			<h3><fmt:message key="CreateRecipe.CreateType" />:
		<fmt:message key="${recipeType}" />
		 </h3>
		 <h6>
				<fmt:message key="CoffeeType.ForFirst" />:
			</h6>

			<c:url value="/jsp/recipe/createRecipeType.html"
				var="createRecipeTypeUrl" />
			<form name="SelectCoffeeType" action="${createRecipeTypeUrl}"
				method="POST">

				<input type="hidden" name="recipeType" id="recipeType"
					value="${recipeType}"> <input type="hidden"
					name="wasCreated" id="wasCreated" value="false">

				<fmt:message key="CreateRecipe.InfusionsNumber" />
				: <input type="text" id="infusions" name="infusions" required
					pattern="^[0-9]{1,2}$">

				<h6>
					<fmt:message key="CoffeeType.ForSecond" />:
				</h6>

				<table class="table table-striped">
					<tr>
						<th><fmt:message key="Recipe.Coffee.CoffeeName" /></th>
						<th><fmt:message key="Recipe.Coffee.CoffeeCountry" /></th>
						<th><fmt:message key="Recipe.Coffee.Roaster" /></th>
						<th><fmt:message key="Recipe.Coffee.ArabicPercent" /></th>
						<th><fmt:message key="Recipe.Coffee.RobustaPercent" /></th>
						<th><fmt:message key="Recipe.Coffee.ProcessingMethod" /></th>
						<th><fmt:message key="Recipe.Coffee.RoastDegree" /></th>
						<th><fmt:message key="Recipe.Coffee.Information" /></th>
						<th><fmt:message key="CoffeeType.SelectCoffee" /></th>
					</tr>
					<c:forEach items="${coffeeTypes}" var="coffeeType">
						<tr>

							<td>${coffeeType.name}</td>
							<td>${coffeeType.country.countryName}</td>
							<td>${coffeeType.roaster}</td>
							<td>${coffeeType.arabicPercent}</td>
							<td>${coffeeType.robustaPercent}</td>
							<td>${coffeeType.processingMethod}</td>
							<td>${coffeeType.roastDegree}</td>
							<td>
								<button type="button" class="btn btn-link"
									data-bs-toggle="popover"
									data-bs-content="${coffeeType.information}">
									<fmt:message key="CoffeeType.InformationButton" />
								</button>
							</td>

							<td><input type="hidden" name="coffeeTypeID"
								value="${coffeeType.ID}"> <input type="submit"
								value="<fmt:message key="CoffeeType.SelectButton" />"></td>
						</tr>
					</c:forEach>
				</table>
			</form>

		</div>
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



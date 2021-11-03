<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>
<div class="content">
	<div class="container">
		<h3 class="display-5">
			<fmt:message key="CoffeeType.Head" />
		</h3>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				<fmt:message key="${message}" />

			</div>
		</c:if>

		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible">
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				<fmt:message key="${errorMessage}" />

			</div>
		</c:if>
		<h3>
			<fmt:message key="CreateRecipe.CreateType" />
			:
			<fmt:message key="${recipeType}" />
		</h3>
		<h6>
			<fmt:message key="CoffeeType.ForFirst" />
			:
		</h6>

		<c:url value="/jsp/recipe/createRecipeStep2.html"
			var="createRecipeStep2Url" />
		<form name="SelectCoffeeType" action="${createRecipeStep2Url}"
			method="POST">
			<input type="hidden" name="recipeType" id="recipeType"
				value="${recipeType}"> <input type="hidden"
				name="wasCreated" id="wasCreated" value="false">

			<fmt:message key="CreateRecipe.InfusionsNumber" />
			: <input type="text" id="infusionsNumber" name="infusionsNumber"
				required pattern="^[1-9]{1}[0-9]{0,1}$">

			<h6>
				<fmt:message key="CoffeeType.ForSecond" />
				:
			</h6>


			<table class="table table-striped align-middle">
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
						<td>${coffeeType.information}<!-- 								<button type="button" class="btn btn-primary"		 -->
							<!-- 									data-bs-toggle="popover"  data-bs-placement="right" -->
							<%-- 									data-bs-content="${coffeeType.information}"> --%> <%-- 									<fmt:message key="CoffeeType.InformationButton" /> --%>
							<!-- 								</button> -->
						</td>
						<td class="align-middle"><input type="radio"
							id="coffeeTypeID" name="coffeeTypeID" value="${coffeeType.ID}" required></td>
					</tr>

				</c:forEach>
			</table>

			<input type="submit"
				value="<fmt:message key="CoffeeType.SelectButton" />">
		</form>

		<div class="row">
			<div class="col-md-8">
				<c:url value="/jsp/recipe/showAllCoffeeType.html"
					var="showAllCoffeeTypeUrl" />

				<ul class="pagination pagination">
					<c:forEach items="${pagesArray}" var="page">
						<li class="page-item">

							<form action="${showAllCoffeeTypeUrl}" method="POST">
								<input type="hidden" name="recipeType" id="recipeType"
									value="${recipeType}"> <input type="hidden"
									name="startPosition" id="startPosition" value="${page[1]}">
								<input type="hidden" name="number" id="number" value="10">
								<input type="hidden" name="sortType" id="sortType"
									value="${sortType}">

								<button type="submit" class="btn btn-secondary">
									${page[0]}</button>
							</form>
						</li>
					</c:forEach>
				</ul>
				<c:if test="${not empty startPositionSelected}">
					<fmt:message key="CurreentPage" />: ${startPositionSelected}
					</c:if>
			</div>
			<div class="col-md-4">
				<form action="${showAllCoffeeTypeUrl}" method="POST">
					<input type="hidden" name="recipeType" id="recipeType"
						value="${recipeType}"> <input type="hidden"
						name="startPosition" id="startPosition" value="0"> <input
						type="hidden" name="number" id="number" value="10"> <select
						id="sortType" name="sortType">
						<option value="${sortType}" selected><fmt:message
								key="${sortType}" /></option>
						<c:forEach items="${sortTypesList}" var="sortTypeOpt">
							<option value="${sortTypeOpt}"><fmt:message
									key="${sortTypeOpt}" /></option>
						</c:forEach>
					</select>
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="Select.sort" />
					</button>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>




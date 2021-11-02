<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="container">
		<h3 class="display-5">
			<fmt:message key="EditRecipePourover.head"></fmt:message>
		</h3>

		<c:if test="${not empty message}">
			<fmt:message key="${message}"></fmt:message>
			<br>
		</c:if>


		<c:url value="/jsp/recipe/editSaveChangesRecipePourover.html"
			var="editPouroveRecipeUrl" />
		<form action="${editPouroveRecipeUrl}" method="POST">
			<input type="hidden" name="recipeId" id="recipeId"
				value="${recipe.ID}"> <input type="hidden"
				name="coffeeTypeId" id="coffeeTypeId" value="${coffeeTypeId}">

			<table class="table table-striped">

				<tr>
					<th><fmt:message key="Recipe.Pourorver.RecipeName" />:</th>
					<td><input type="text" id="recipeName" name="recipeName"
						required pattern="^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$" size="30"
						value="${recipe.recipeName}"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.FunnelType" />:</th>
					<td><select id="funnelType" name="funnelType">
							<option value="${recipe.funnelType}" selected>${recipe.funnelType.name}</option>
							<c:forEach items="${funnelTypes}" var="funnelType">
								<option value="${funnelType}">${funnelType.name}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.MassOfCoffee" />:</th>
					<td><input type="text" id=massOfCoffee name="massOfCoffee"
						required pattern="^[0-9]{1,3}[\\.]{0,1}[0-9]{0,2}$"
						value="${recipe.massOfCoffee}"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.GrindSettings" />:
						<button type="button" class="btn btn-link"
							data-bs-toggle="popover"
							data-bs-content="<fmt:message key="Recipe.FrenchPress.GrindSettings.More"/>">
							?</button></th>
					<td><input type="text" id=grindSettings name="grindSettings"
						required pattern="^[0-9]{1,3}[\\.]{0,1}[0-9]{0,2}$"
						value="${recipe.grindSettings}"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.CoffeeGrinder" />:</th>
					<td><input type="text" id="coffeeGrinder" name="coffeeGrinder"
						required pattern="^[A-Za-z0-9а-яА-ЯёЁ\s\\.\\-]{1,100}$" size="30"
						value="${recipe.coffeeGrinder}"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.TotalTime" />:</th>
					<td><input type="text" id=totalTime name="totalTime" required
						pattern="^[0-9]{1,4}$" value="${recipe.totalTime}"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.Description" />:</th>
					<td><textarea name="description" id="description" rows="10"
							cols="50" maxlength="1000">${recipe.disription} </textarea></td>
				</tr>
			</table>


			<h3>
				<fmt:message key="Recipe.Infusion.Head">:</fmt:message>

			</h3>
			<input type="hidden" name="infusionsNumber" id="infusionsNumber"
				value="${infusionsNumber}">
			<table class="table table-striped">
				<tr>
					<th><fmt:message key="Recipe.Infusion.Number" />:</th>
					<th><fmt:message key="Recipe.Infusion.TimeStart" />:</th>
					<th><fmt:message key="Recipe.Infusion.WaterVolume" />:</th>
					<th><fmt:message key="Recipe.Infusion.TimeEnd" />:</th>
					<th><fmt:message key="Recipe.Infusion.WaterTemperature" />:</th>
				</tr>

				<c:forEach items="${infusionsArray}" var="infusion">

					<tr>
						<td>${infusion[0]}</td>
						<td><input type="text" id="${infusion[1]}"
							name="${infusion[1]}" required pattern="^[0-9]{1,4}$"
							value="${infusion[5]}"></td>
						<td><input type="text" id="${infusion[2]}"
							name="${infusion[2]}" required pattern="^[0-9]{1,4}$"
							value="${infusion[6]}"></td>
						<td><input type="text" id="${infusion[3]}"
							name="${infusion[3]}" required pattern="^[0-9]{1,4}$"
							value="${infusion[7]}"></td>
						<td><input type="text" id="${infusion[4]}"
							name="${infusion[4]}" required pattern="^[0-9]{1,4}$"
							value="${infusion[8]}"></td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${!recipe.common}">
				<button type="submit" class="btn btn-secondary ">
					<fmt:message key="Button.SaveChanges" />
				</button>
			</c:if>
			<c:url value="/jsp/recipe/createSaveRecipePourover.html"
				var="createSaveRecipePouroverUrl" />
			<button type="submit" formaction="${createSaveRecipePouroverUrl}"
				class="btn btn-secondary ">
				<fmt:message key="Button.SaveAsNew" />
			</button>
		</form>
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

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/head.jsp"%>

<div class="content">
	<div class="container">
		<h3 class="display-5">
			<fmt:message key="CreateRecipePourover.head"></fmt:message>
		</h3>

		<c:if test="${not empty message}">
			<fmt:message key="${message}"></fmt:message>
			<br>
		</c:if>


		<c:url value="/jsp/CreateRecipeFinal.html" var="createRecipeFinalUrl" />
		<form action="${createRecipeFinalUrl}">
			<input type="hidden" name="recipeId" id="recipeId"
				value="${recipeId}">

			<table class="table table-striped">

				<tr>
					<th><fmt:message key="Recipe.Pourorver.RecipeName" />:</th>
					<td><input type="text" id="RecipeName" name="RecipeName"
						required pattern="^[A-Za-z0-9а-яА-Я\s\\.\\-]{1,100}$" size="30"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.FunnelType" />:</th>
					<td><select id="funnelType" name="funnelType">
							<c:forEach items="${funnelTypes}" var="funnelType">
								<option value="${funnelType}">${funnelType.name}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.MassOfCoffee" />:</th>
					<td><input type="text" id=massOfCoffee name="massOfCoffee"
						required pattern="^[0-9\\.]{1,4}$"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.GrindSettings" />:</th>
					<td><input type="text" id=grindSettings name="grindSettings"
						required pattern="^[0-9\\.]{1,4}$"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.CoffeeGrinder" />:</th>
					<td><input type="text" id="coffeeGrinder" name="coffeeGrinder"
						required pattern="^[A-Za-z0-9а-яА-Я\s\\.\\-]{1,100}$" size="30"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.TotalTime"/>:</th>
					<td><input type="text" id=totalTime name="totalTime" required
						pattern="^[0-9]{1,4}$"></td>
				</tr>

				<tr>
					<th><fmt:message key="Recipe.Pourorver.Description" />:</th>
					<td><textarea name="description" id="description" rows="10"
							cols="50" maxlength="1000"></textarea></td>
				</tr>
			</table>


			<h3>
				<fmt:message key="Recipe.Infusion.Head">:</fmt:message>
				
			</h3>
			<table class="table table-striped">
				<tr>
					<%-- 					<th><fmt:message key="Recipe.Infusion.Number" />:</th> --%>
					<th><fmt:message key="Recipe.Infusion.TimeStart" />:</th>
					<th><fmt:message key="Recipe.Infusion.WaterVolume" />:</th>
					<th><fmt:message key="Recipe.Infusion.TimeEnd" />:</th>
					<th><fmt:message key="Recipe.Infusion.WaterTemperature" />:</th>
				</tr>
				<c:forEach items="${infusions}" var="infusion">
					<tr>
						<c:forEach items="${infusion}" var="infusionValue">
							<td><input type="text" id="${infusionValue}"
								name="${infusionValue}" required pattern="^[0-9]{1,4}$">
							</td>

						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="Button.Create" />
			</button>
		</form>
	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

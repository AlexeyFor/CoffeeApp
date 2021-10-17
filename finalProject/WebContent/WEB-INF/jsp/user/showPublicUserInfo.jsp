<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%-- <%@ taglib tagdir="/WEB-INF/tags" prefix="u"%> --%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<%@taglib uri="/WEB-INF/tld/custom.tld" prefix="ctg"%>

<%-- <u:html title="show public user info"> --%>

<!-- <body> -->
<%@include file="/WEB-INF/jsp/head.jsp"%>

	Information and recipes of user ${user.userInfo.name}
	<br>



	<TABLE border='1'>
		<TR>
			<TH>Автор</TH>
			<TH>Дата создания</TH>
			<TH>Тип рецепта</TH>
			<TH>Кофеек</TH>
			<TH>Страна кофеечка</TH>
			<TH>Степень обжарки</TH>
			<TH>Метод обработки</TH>
			<TH>Кнопа</TH>

		</TR>
		<c:forEach items="${recipes}" var="recipe">
			<TR>
				<TD><ctg:userNameTag>${recipe.authorUserId}</ctg:userNameTag></TD>
				<TD><fmt:formatDate value="${recipe.dateOfCreating}"
						pattern="dd.MM.yyyy" /></TD>
				<TD>${recipe.recipeType}</TD>
				<TD>${recipe.coffeeType.name}</TD>
				<TD>${recipe.coffeeType.country.countryName}</TD>
				<TD>${recipe.coffeeType.roastDegree}</TD>
				<TD>${recipe.coffeeType.processingMethod}</TD>


				<TD><c:url value="/jsp/recipe/showRecipe.html"
						var="ShowRecipeUrl" />
					<form name="ShowRecipe" action="${ShowRecipeUrl}" method="POST">
						- <INPUT type="hidden" name="recipeID" value="${recipe.ID}">
						<input type="submit" value="show recipe">
					</form></TD>
			</TR>
		</c:forEach>
	</TABLE>

		<c:import url="/WEB-INF/jsp/fotter.jsp"/>

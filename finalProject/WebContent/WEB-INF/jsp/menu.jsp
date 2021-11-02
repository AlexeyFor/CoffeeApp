<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="head.jsp"%>

<!-- <div class="wrap"> -->
<div class="content">
	<div class="container">

		<h1 class="display-5">
			<fmt:message key="MenuPage.Head" />
		</h1>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible">
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				<fmt:message key="${errorMessage}" />

			</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				<fmt:message key="${message}" />

			</div>
		</c:if>

		<c:if test="${not empty role and role eq 'user'}">
			<c:if test="${not empty ID and ID > 0}">
				<h4>
					Hello,
					<ctg:userNameTag>${ID}</ctg:userNameTag>
					!
				</h4>
			</c:if>
		</c:if>

		<c:url value="/jsp/recipe/showAllRecipes.html" var="showAllRecipesUrl" />
		<form name="showAllRecipesUrl" action="${showAllRecipesUrl}">
			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="MenuPage.ShowAllRecipes" />
			</button>
		</form>

		<c:if test="${not empty role and role eq 'moder'}">
			<c:url value="/jsp/moder/showAllUsers.html" var="showAllUsersUrl" />
			<form name="showAllUsers" action="${showAllUsersUrl}">
				<button type="submit" class="btn btn-secondary ">
					<fmt:message key="MenuPage.ShowAllUsers" />
				</button>
			</form>
		</c:if>

		<c:if test="${not empty role and role eq 'user'}">
			<c:url value="/jsp/user/showAllSavedRecipes.html"
				var="showAllSavedRecipes" />
			<form name="showAllSavedRecipes" action="${showAllSavedRecipes}"
				method="POST">
				<button type="submit" class="btn btn-secondary ">
					<fmt:message key="MenuPage.ShowAllSavedRecipes" />
				</button>
			</form>
		</c:if>


		<c:if test="${not empty role and role eq 'user'}">

			<a href="#recID" class="btn btn-secondary" data-bs-toggle="collapse">
				<fmt:message key="MenuPage.CreateRecipe" />
			</a>
			<div id="recID" class="collapse">
				<br>

				<c:url value="/jsp/recipe/createRecipeStep1Coffee.html"
					var="createRecipeCoffeeURL" />
				<form name="createRecipeCoffeeURL" action="${createRecipeCoffeeURL}"
					method="POST">
					<input type="hidden" name="recipeType" id="recipeType"
						value="FRENCHPRESS">
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="MenuPage.FrenchPress" />
					</button>
				</form>

				<c:url value="/jsp/recipe/createRecipeStep1Coffee.html"
					var="createRecipeCoffeeURL" />
				<form name="createRecipeCoffeeURL" action="${createRecipeCoffeeURL}"
					method="POST">
					<input type="hidden" name="recipeType" id="recipeType"
						value="POUROVER">
					<button type="submit" class="btn btn-secondary ">
						<fmt:message key="MenuPage.Pourover" />
					</button>
				</form>
			</div>
		</c:if>

		<c:if test="${not empty role and role eq 'user'}">
			<br>
			<br>
			<c:url value="/jsp/nothing.html" var="showUserInfoUrl" />
			<form name="showUserInfoUrl" action="${showUserInfoUrl}">
				<button type="submit" class="btn btn-secondary ">
					<fmt:message key="MenuPage.ShowUserInfo" />
				</button>
			</form>
		</c:if>

		<c:url value="/jsp/nothing.html" var="moreAboutAppUrl" />

		<form name="moreAboutAppUrl" action="${moreAboutAppUrl}">
			<button type="submit" class="btn btn-secondary ">
				<fmt:message key="MenuPage.MoreAboutApp" />
			</button>
		</form>
	</div>
</div>

<div class="fotter">
	<%@include file="/WEB-INF/jsp/fotter.jsp"%>
</div>

<script>
	if (window.history.replaceState) {
		window.history.replaceState(null, null, window.location.href);
	}
</script>

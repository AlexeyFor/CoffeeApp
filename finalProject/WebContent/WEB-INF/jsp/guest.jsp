<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="resources.pagecontent" />

<u:html title="guest page">


<p>
	<fmt:message key="StartPage.Head" />
</p>
<p>page for guest</p>
<c:url value="/jsp/thanksPage.html" var="thanksPageUrl" />
<form name="AccessAction" action="${thanksPageUrl}" method="POST">
	<label for="thanksPage">Please, press it!</label> <br> <input
		type="submit" value="thanksPage">
</form>
<br>
<c:url value="/jsp/showAllRecipes.html" var="showAllRecipesUrl" />
<form name="showAllRecipes" action="${showAllRecipesUrl}" method="POST">
	<label for="showRec">Show all recipes</label> <br> <input
		type="submit" value="show all">
</form>
<br>

<c:url value="/jsp/Nothing.html" var="NothingUrl" />
<form name="Nothing" action="${NothingUrl}" method="POST">
	<label for="show Nothing">Show Nothing</label> <br> <input
		type="submit" value="show Nothing">
</form>
</u:html>
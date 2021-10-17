<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <%@ taglib tagdir="/WEB-INF/tags" prefix="u"%> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>



<%-- <u:html title="Result Page"> --%>

<body>
<%-- <jsp:include page="Head.jsp"></jsp:include> --%>
<%@include file="head.jsp"%>

	JSP
	<p>id user ${ID}</p>
			<p>role user ${role}</p>
	
	<p><fmt:message key="result.head" /> </p>
	<c:url value="/jsp/Nothing.html" var="NothingUrl" />
	<form name="Nothing" action="${NothingUrl}" method="POST">
		<label for="show Nothing"><fmt:message key="result.head" /></label>
		 <br> <input
			type="submit" value="show Nothing">
	</form>
</body>
		<c:import url="/WEB-INF/jsp/fotter.jsp"/>

<%-- </u:html> --%>
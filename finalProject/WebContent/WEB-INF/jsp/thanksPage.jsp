<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
<title>How to reload/refresh a page using jQuery</title>
</head>
<body>
	<h2>Reloading a page using jQuery and JavaScript</h2>
	<button id="btn_reload">Reload from Server</button>
	<button id="btn_refresh">Reload from Browser's cache</button>
	<script src="//code.jquery.com/jquery-1.6.2.min.js"></script>
	<script> $(document).ready(function() { $("#btn_refresh").click(function(){ location.reload(false);}); 
	//loads from browser's cache }); 
	$("#btn_reload").click(function(){ location.reload(true);}); 
	//loads from server }); 
	}); </script>
</body>
</html>



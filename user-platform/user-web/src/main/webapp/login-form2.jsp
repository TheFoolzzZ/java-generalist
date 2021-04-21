<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>My Home Page</title>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body>
<button onclick="jump()">Gitee</button>
</body>
<script>
	var url = "https://gitee.com/oauth/authorize?"
			+"client_id="+"ee71083e915cc09faaf7beeda2d9c67f8665786f2197627ae89bd18974b4d755"
			+"&redirect_uri="+"http://127.0.0.1/callback"
			+"&response_type=code";
	function jump(){
		window.open(url);
	}
</script>
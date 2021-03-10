<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>注册页面</title>
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
	<div class="container">
		<form action="/register" method="post">
			<h1 class="h3 mb-3 font-weight-normal">注册页面</h1>
			<input type="text" name="userId" class="form-control" placeholder="请输入用户名" required>
			<input type="email" name="email" class="form-control" placeholder="请输入邮箱地址" required>
			<input type="text" name="phoneNum" class="form-control" placeholder="请输入11位中国电话号码" required>
			<input type="password" name="password" class="form-control" placeholder="请输入：6-23位密码" required>
			<input type="password" name="repeatPassword" class="form-control" placeholder="请再次输入密码" required>
			<div class="checkbox mb-3">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" name="submit " type="submit">注册</button>
		</form>
		<p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
	</div>
</body>
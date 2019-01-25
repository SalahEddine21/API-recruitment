<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<html>
<head>
	<title>Login</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href='<c:url value="/resources/css/bootstrap.css" />' rel="stylesheet">
</head>
<body data-ng-app="my-app"  data-ng-controller="Ctrl">

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	  <a class="navbar-brand" href="#">Home</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <div class="collapse navbar-collapse" id="navbarNav">
	    <ul class="navbar-nav">
	      <li class="nav-item active">
	        <a class="nav-link" href='<c:url value="/admin/login" />' >Login<span class="sr-only">(current)</span></a>
	      </li>
	    </ul>
	  </div>
	</nav>
	
	<div class="container">
		<div class="row" style="margin-top:170px">
			<div class="col-lg-3"></div>
			<div class="col-lg-6">
				<form name="loginForm" action='<c:url value="/home/login" />' method="post" novalidate="novalidate">
				
				  <div class="form-group">
				    <label for="CodeInput">Code</label>
				    <input type="text" name="code" data-ng-model="code" class="form-control" placeholder="Enter Login Code" data-ng-required="true" data-ng-change="unkown = false">
				    <div data-ng-show="loginForm.code.$touched && loginForm.code.$invalid">
						<small style="color: red;" class="form-text" >Enter a valid Code Please</small>
					</div>
				  </div>
				  
				  <div class="form-group">
				    <label for="InputPassword">Password</label>
				    <input type="password" name="password" data-ng-model="password" class="form-control" placeholder="Password" data-ng-required="true" data-ng-change="unkown = false">
				    <div data-ng-show="loginForm.password.$touched && loginForm.password.$invalid">
						<small style="color: red;" class="form-text" >Enter your password Please</small>
					</div>
				  </div>

				  <div class="form-group form-check">
				    <input type="checkbox" class="form-check-input" id="exampleCheck1">
				    <label class="form-check-label" for="exampleCheck1">Check me out</label>
				  </div>
				  <button type="submit" class="btn btn-primary" data-ng-disabled="loginForm.$invalid">Submit</button>
				</form>		
			</div>
		</div>
		<div data-ng-show="${unkown}">
			<div class="row">
				<div class="col-lg-3"></div>
				<div class="col-lg-6">
					<div class="alert alert-danger">
					 	 This data doese not figure in our database !
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src='<c:url value="/resources/js/angular.min.js" />' ></script>
	<script>
		var app = angular.module("my-app", []);
		app.controller("Ctrl", function($scope, $window) {
			
		});
	</script>
</body>
</html>

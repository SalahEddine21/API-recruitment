<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE>
<html>
<head>
	<title>Student/Profil</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-3.3.7.css" />' >
    <link rel="stylesheet" href='<c:url value="/resources/css/style.css" />' >
</head>
<body data-ng-app="my-app"  data-ng-controller="Ctrl">

        <div class="wrapper">
            <!-- Sidebar Holder -->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <h3>SideBar</h3>
                </div>

                <ul class="list-unstyled components">
                    <p> ${student.name} ${student.lastName}  </p>
                    <li class="active">
                        <a href='<c:url value="/student/profil" />'>DASHBOARD</a>
                    </li>
                    <li>
                        <a href='<c:url value="/student/absence" />'>Afficher l'absence</a>
                    </li>
                    <li>
                        <a href='<c:url value="/student/emploi" />'>Afficher l'emploi</a>
                    </li>
                </ul>

                <ul class="list-unstyled CTAs">
                    <li><a href='<c:url value="/student/logout" />' class="download">Deconnexion</a></li>
                </ul>
            </nav>

            <!-- Page Content Holder -->
            <div id="content" class="container">
                <nav class="navbar navbar-default">
                    <div class="container-fluid">

                        <div class="navbar-header">
                            <button type="button" id="sidebarCollapse" class="btn btn-info navbar-btn">
                                <i class="glyphicon glyphicon-align-left"></i>
                                <span>Toggle Sidebar</span>
                            </button>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav navbar-right">
                                <li><a href="#">Page</a></li>
                                <li><a href="#">Page</a></li>
                                <li><a href="#">Page</a></li>
                                <li><a href="#">Page</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>
                
                
                <p>Bonjour ${ student.name }</p>
				<br/>
				<p>Le nom : ${student.lastName }</p>
				<br/>
				<p>Code : ${student.code }</p>
				<br/>
				<p>Email: ${student.email }</p>
				<br/>
				<p>Filiere: ${student.groupe.filiere }</p>
				<br/>
				<p>Groupe : ${student.groupe.name }</p>
				<br/>
				<p>Année scolaire : ${student.groupe.annee_scolaire }</p>
				<br/>
				<p>notifications:</p>
				<br/>
				<p>les examens</p>
            </div>
        </div>
		
    
    <!-- /#wrapper -->
    <script src='<c:url value="/resources/js/jquery-3.2.1.js" />' ></script>
    <script src='<c:url value="/resources/js/bootstrap-3.3.7.js" />'></script>
    
	<!--angularJs -->
    <script src='<c:url value="/resources/js/angular.min.js" />' ></script>
    
    <script>
	    $('#sidebarCollapse').click(function (e) {
	        $('#sidebar').toggleClass('active');
        });
        
		var app = angular.module("my-app", []); // angular call
		app.controller("Ctrl", function($scope, $http) {
			$scope.filiers = [];
			$scope.not1A = false; // 1A by default
			$scope.year = '1A';
			$scope.week = "semaine1";
					
			$scope.getData = function(){	
				if( $scope.year != '1A' ){
					var year = {"year" : $scope.year};
					var response = $http.post('LoadFiliers', year);
					
					response.then(function(response) {
						$scope.filiers = []; // set to empty
						$scope.filiers = response.data;
						$scope.not1A = true;
						$scope.fl = $scope.filiers[0].name;
					}, function(error) {
						alert('cannot get data !');
						console.log(error);
					}, function(value) {
						
					});	
				}else $scope.not1A = false;	
			}

		});
    </script>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
	<title>Admin/Profil</title>
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
                    <p>Emploi du temps</p>
                    <li>
                        <a href='<c:url value="/admin/profil" />'>DASHBOARD</a>
                    </li>
                    <li  class="active">
                        <a href='<c:url value="/admin/gestion_seances" />'>Gestion des emplois</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/gestion_profs" />'>Gestion des professeurs</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/gestion_groupes" />'>Gestion des groupes</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/gestion_modules" />'>Gestion des modules</a>
                    </li>
                </ul>

                <ul class="list-unstyled CTAs">
                    <li><a href='<c:url value="/admin/logout" />' class="download">Deconnexion</a></li>
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
		       	<form class="row" action='<c:url value="/admin/gestion_seances" />' method="post" enctype="multipart/form-data">
					<div class="col-md-9 col-md-offset-1">
							<c:if test="${!empty result}">
								<c:choose>
									<c:when test="${result == 'done' }">
										<div class="alert alert-success alert-dismissible">
										  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										  <strong>Success!</strong> Opération bien fait.
										</div>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger alert-dismissible">
										  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										  <strong>Error!</strong>${result}.
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<div class="form-group">
								<legend>Emploi du temps</legend>
						    	<label for="year">Annèe</label>
								<select name="year" class="form-control" data-ng-model="year" data-ng-change="getFiliere()" >
									<option value="1A">1A</option>
									<option value="2A">2A</option>
									<option value="3A">3A</option>
								</select>							
							</div>
							<div class="form-group" data-ng-if=" not1A == true " >
								<select name="option" class="form-control" data-ng-model="fl">
									<option data-ng-repeat="filiere in filiers" >{{filiere.name}}</option>
								</select>
							</div>
							<div class="form-group">
						 		<label for="">Liste des Seances des {{year}} (yyyy-mm-dd, xh-yh(8h-10h Ex), Titre du module, Grp1/Grp2...)</label>
						 		<input type="file" name="emploi" class="form-control"  />							
							</div>
							<div class="form-group">
								<legend>Semaine</legend>
								<label>Label</label>
								<input type="text" name="semaine" class="form-control" placeholder="nom du semaine ex(semaine7)" >
							</div>
							<div class="form-group">
								<label>Date Debut</label>
								<input type="date" name="date_debut" class="form-control" >
							</div>
							<div class="form-group">
								<label>Date Fin</label>
								<input type="date" name="date_fin" class="form-control" >
							</div>
							<div class="form-groupe">
								 <button type="submit" class="btn btn-primary" >GO</button>
							</div>
					</div>	       		
		       	</form>                           
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
			$scope.year = "1A";
			$scope.not1A = false;
			$scope.filiere = [];

			$scope.getFiliere = function(){
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE>
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
                    <p> ${prof.name} ${prof.lastName} </p>
                    <li>
                        <a href='<c:url value="/prof/profil" />'>DASHBOARD</a>
                    </li>
                    <li class="active">
                        <a href='<c:url value="/prof/faireAbsence" />'>Faire l'absence</a>
                    </li>
                    <li>
                        <a href='<c:url value="/prof/mdfAbsence" />'>Modifier l'absence</a>
                    </li>
                    <li>
                        <a href='<c:url value="/prof/gestion_modules" />'>Gestion des modules</a>
                    </li>
                    <li>
                        <a href='<c:url value="/prof/gestion_groupes" />'>Gestion des groupes</a>
                    </li>
                </ul>

                <ul class="list-unstyled CTAs">
                    <li><a href='<c:url value="/prof/logout" />' class="download">Deconnexion</a></li>
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
				<div class="row">
	                <form class="col-md-12" name="absForm" action='<c:url value="/prof/absence" />' method="post" enctype="multipart/form-data">
						<div class="col-md-9 col-md-offset-1">
								<div class="form-group">
									<legend>Faire l'absence</legend>
							    	<label for="module">Module</label>
									<select name="module" class="form-control" data-ng-model="module" data-ng-required="true" data-ng-change="getSeances()" >
										<c:forEach items="${modules}" var="m">
											<option value="${m.id}" > ${m.titre} </option>
										</c:forEach>
									</select>					
								</div>
								<div class="form-group">
									<label for="seances">Liste des seances</label>
									<select name="seance" class="form-control" data-ng-model="seance" data-ng-required="true" >
										<option  data-ng-repeat="s in seances" value="{{s.id}}" > {{s.date}} à {{s.heure}} </option>
									</select>
								</div>
								<div class="form-groupe">
									 <button type="submit" class="btn btn-primary col-md-3" data-ng-disabled="absForm.$invalid" >GO</button>
								</div>
						</div>	                  	
	                </form>
				</div> 					
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
			$scope.sceances = [];
					
			$scope.getSeances = function(){	
				var md = {"module" : $scope.module};
				var response = $http.post('loadSeances', md);
					
				response.then(function(response) {
					$scope.seances = []; // set to empty
					$scope.seances = response.data;
				}, function(error) {
					alert('cannot get data !');
					console.log(error);
				}, function(value) {
					
				});	
			}

		});
    </script>
</body>
</html>
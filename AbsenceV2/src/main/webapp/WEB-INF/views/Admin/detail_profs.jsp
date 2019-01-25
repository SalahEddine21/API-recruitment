<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE>
<html>
<head>
	<title>Detail des professeurs</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-3.3.7.css" />' >
    <link rel="stylesheet" href='<c:url value="/resources/css/style.css" />' >
</head>
<body data-ng-app="my-app"  data-ng-controller="Ctrl">

        <div class="wrapper">
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
					<div class="col-md-7 col-md-offset-2">
						<legend>Informations Professeur</legend>
						<input type="text" data-ng-model="order" class="form-control" placeholder="Rechercher" >
						<div class="row" style="margin-top: 20px;">
								<div class="col-md-12" data-ng-if="profs.length > 0">
									<div class="row">
										<div class="col-md-7"><input type="text" data-ng-model="order" class="form-control" placeholder="Recherche: ce que vous voulez" ></div>
										<button class="btn btn-primary col-md-2" data-ng-click="getPrevious()" data-ng-disabled="startID == 5" >Précédent</button>
										<button class="btn btn-danger col-md-2" data-ng-click="getNext()" data-ng-disabled="startID >= ${limit} " >Suivant</button>
									</div>									
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>Nom</th>
												<th>Prenom</th>
												<th>Email</th>
												<th>Modules</th>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="prof in profs | filter:order" >
												<td> {{prof.nom}} </td>
												<td> {{prof.prenom}} </td>
												<td> {{prof.email}} </td>
												<td>
													<ul>
														<li data-ng-repeat="module in prof.modules" >
															{{module.titre}}
														</li>
													</ul>
												</td>
											</tr>
										</tbody>
									</table>
									<p class="col-md-8" >Total des professeurs: <strong  data-ng-bind="${limit}" ></strong></p>
									<a href='<c:url value="/admin/gestion_profs" />' class=" btn btn-default col-md-2" >Retour</a>
								</div>
								<div data-ng-if="profs.length == 0">
									<p>Aucun professeur n'est trouvé</p>
								</div>
						</div>
					</div>
				</div>                      
            </div>
            
         </div>
    <!-- /#wrapper -->
    <script src='<c:url value="/resources/js/jquery-3.3.1.js" />' ></script>
    <script src='<c:url value="/resources/js/bootstrap-3.3.7.js" />'></script>
    
	<!--angularJs -->
    <script src='<c:url value="/resources/js/angular.min.js" />' ></script>
    
    <script>
	    $('#sidebarCollapse').click(function (e) {
	        $('#sidebar').toggleClass('active');
        });
        
		var app = angular.module("my-app", []); // angular call
		app.controller("Ctrl", function($scope, $http) {
			$scope.profs = [];
			$scope.startID = 0;

			var ID = {"startID" : $scope.startID+""};
			var response = $http.post('LoadProfs', ID);
			
			response.then(function(response) {
				$scope.profs = []; // set to empty
				$scope.profs = response.data;
				$scope.startID = $scope.startID + 5;
			}, function(error) {
				alert('cannot get data ! '+error);
				consol.log(error);
			}, function(value) {
				
			});	

			$scope.getNext = function(){
					
				var ID = {"startID" : $scope.startID+""};
				var response = $http.post('LoadProfs', ID);
				
				response.then(function(response) {
					$scope.profs = []; // set to empty
					$scope.profs = response.data;
					$scope.startID = $scope.startID + 5;
				}, function(error) {
					alert('cannot get data ! '+error);
				}, function(value) {
					
				});	
			}			

			$scope.getPrevious = function(){
				$scope.startID = $scope.startID - 10;
					
				var ID = {"startID" : $scope.startID+""};
				var response = $http.post('LoadProfs', ID);
				
				response.then(function(response) {
					$scope.profs = []; // set to empty
					$scope.profs = response.data;
					$scope.startID = $scope.startID + 5;
				}, function(error) {
					alert('cannot get data ! '+error);
				}, function(value) {
					
				});	
			}		
		});
    </script>
</body>
</html>
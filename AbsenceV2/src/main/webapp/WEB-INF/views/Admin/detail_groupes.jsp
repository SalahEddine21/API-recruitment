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
						<legend>Informations Groupe</legend>
						<label>Nom du groupe</label>
						<select data-ng-model="groupe" class="form-control" data-ng-change="getData()">
							<c:forEach items="${grps}" var="groupe">
								<option value="${groupe.id}" >${groupe.name} - ${groupe.annee}</option>
							</c:forEach>
						</select>
						<div class="row" style="margin-top: 20px;">
							<div class="col-md-12">
								<p data-ng-if="students.length == 0" > Aucun étudiant pour ce groupe !</p>
								<div data-ng-if="students.length > 0">
									<input type="text" data-ng-model="order" class="form-control" placeholder="Rechercher" >
									<div style="height: 450px; overflow: scroll;">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>Code</th>
													<th>Nom</th>
													<th>Prènom</th>
													<th>Email</th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<tr data-ng-repeat="student in students | filter : order" >
													<td> {{student.code}} </td>
													<td> {{student.name}} </td>
													<td> {{student.lastname}} </td>
													<td> {{student.email}} </td>
													<td> <a href='<c:url value="/admin/detail_std?std_id={{student.id}}" />' class="btn btn-info btn-sm" target='_blank' >Detail</a> </td>
												</tr>
											</tbody>
										</table>
									</div>
									<p>Nombre: {{students.length}} </p>
								</div>
							</div>
							<div class="col-md-12">
								<a href='<c:url value="/admin/gestion_groupes" />' class="col-md-offset-9 col-md-3 btn btn-default" >Retour</a>
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
			$scope.students = [];
			
			$scope.getData = function(){	
					var grp = {"groupe" : $scope.groupe};
					var response = $http.post('LoadStds', grp);
					
					response.then(function(response) {
						$scope.students = []; // set to empty
						$scope.students = response.data;
					}, function(error) {
						alert('cannot get data ! '+error);
					}, function(value) {
						
					});	
					alert(start);
			}	
					
		});
    </script>
</body>
</html>
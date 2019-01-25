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
                    <li class="active">
                        <a href='<c:url value="/prof/profil" />'>DASHBOARD</a>
                    </li>
                    <li>
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
				<form class="row" action='<c:url value="/admin/profil" />' method="post"> 
						<div class="col-md-4">
							<select name="week" class="form-control" data-ng-model="week">
								<c:forEach items="${semaines}" var="semaine">
									<option value="${semaine.name}"> ${semaine.name} </option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2">
							<select name="year" class="form-control" data-ng-model="year" data-ng-change="getData()">
								<option value="1A">1A</option>
								<option value="2A">2A</option>
								<option value="3A">3A</option>
							</select>
						</div>
						<div class="col-md-2" data-ng-if=" not1A == true " >
							<select name="option" class="form-control" data-ng-model="fl">
								<option data-ng-repeat="filiere in filiers" >{{filiere.name}}</option>
							</select>
						</div>
						<div class="col-md-2">
							<button type="submit" class="btn btn-danger">GO</button>
						</div>
				</form>					
				<div class="row">
						<div class="col-md-12" style="margin-top:30px;">
							<c:choose>
								<c:when test="${!empty emplois}">
									<table class="table table-bordered">
									  <thead>
									    <tr>
									      <th>Date</th>
									      <th>8h-10h</th>
									      <th>10h-12h</th>
									      <th>14h-16h</th>
									      <th>16h-18h</th>
									    </tr>
									  </thead>
									  <tbody>
										<c:forEach items="${emplois}" var="day">
											<tr>
												<td>${day.date}</td>
												<c:forEach items="${times}" var="time">
													<c:choose>
														<c:when test="${!empty day.hashmap_module[time]}">
															<td>
																${day.hashmap_module[time]}<strong> | </strong>${day.hashmap_prof[time]}
																<br>
																<strong>Groupe: </strong>${day.hashmap_groupes[time]}
															</td>
														</c:when>
														<c:otherwise>
															<td></td>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</tr>
										</c:forEach>
									  </tbody>
									</table>							
								</c:when>
								<c:otherwise>
									<p>Emploi du temps non trouvé !</p>
								</c:otherwise>
							</c:choose>
					</div>
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
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
                    <li>
                        <a href='<c:url value="/prof/faireAbsence" />'>Faire l'absence</a>
                    </li>
                    <li>
                        <a href='<c:url value="/prof/mdfAbsence" />'>Modifier l'absence</a>
                    </li>
                    <li>
                        <a href='<c:url value="/prof/gestion_modules" />'>Gestion des modules</a>
                    </li>
                    <li class="active">
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
					<div class="col-md-12">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Groupe</th>
									<th>Niveau</th>
									<th>Filiere</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${groupes}" var="groupe">
									<tr>
										<td> ${groupe.name} </td>
										<td> ${groupe.annee} </td>
										<c:choose>
											<c:when test="${groupe.annee != '1A'}">
												<td> ${groupe.filiere} </td>
											</c:when>
											<c:otherwise>
												<td> --- </td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>								
							</tbody>
						</table>
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

		});
    </script>
</body>
</html>
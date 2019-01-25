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
                    <p>Emploi du temps</p>
                    <li>
                        <a href='<c:url value="/admin/profil" />'>DASHBOARD</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/gestion_seances" />'>Gestion des emplois</a>
                    </li>
                    <li class="active">
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
                <form class="row" name="profsForm" action='<c:url value="/admin/gestion_profs" />' method="post" enctype="multipart/form-data">
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
								<legend>Gestion des Professeurs</legend>
						    	<label for="file">Fichier (Cin, Nom, Prenom, Email, Mot de passe)</label>
								<input type="file" name="profs" class="form-control" data-ng-required="true" />					
							</div>
							<div class="form-groupe">
								 <button type="submit" class="btn btn-primary col-md-3">GO</button>
								 <a href='<c:url value="/admin/detail_profs" />' class="col-md-offset-6 col-md-3 btn btn-default" >Detail des professeurs</a>
							</div>
					</div>	                  	
                </form>
				                       
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
		app.controller("Ctrl", function($scope) {
			
		});
    </script>
</body>
</html>
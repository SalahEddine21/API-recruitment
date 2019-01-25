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
                    <li>
                        <a href='<c:url value="/admin/gestion_profs" />'>Gestion des professeurs</a>
                    </li>
                    <li class="active">
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
                <form class="row" name="groupeForm" action='<c:url value="/admin/gestion_groupes" />' method="post" novalidate="novalidate" enctype="multipart/form-data">
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
								<legend>Gestion des groupes</legend>
						    	<label for="year">Filière</label>
								<input type="text" name="filiere" class="form-control" placeholder="Filière" data-ng-model="filiere" data-ng-disabled="annee == '1A'">		
							</div>
							<div class="form-group">
								<label>Nom</label>
								<input type="text" name="name" class="form-control" data-ng-model="name" placeholder="Nom du groupe" data-ng-required="true">
							    <div data-ng-if="groupeForm.name.$touched && groupeForm.name.$invalid == true">
									<small style="color: red;" class="form-text" >Entrer le nom du groupe !</small>
								</div>
							</div>
							<div class="form-group">
								<label>Annèe Scolaire</label>
								<input type="text" name="scolaire" class="form-control" data-ng-model="scolaire" placeholder="l'annèe scolaire" data-ng-required="true" >
							    <div data-ng-if="groupeForm.scolaire.$touched && groupeForm.scolaire.$invalid == true">
									<small style="color: red;" class="form-text" >Entrer l'annèe scolaire !</small>
								</div>							
							</div>
							<div class="form-group">
						 		<label for="">Année</label>
								<select name="annee" class="form-control" data-ng-model="annee" data-ng-required="true">
									<option value="1A">1A</option>
									<option value="2A">2A</option>
									<option value="3A">3A</option>
								</select>	
							    <div data-ng-if="groupeForm.annee.$touched && groupeForm.annee.$invalid == true">
									<small style="color: red;" class="form-text" >Entrer l'annèe !</small>
								</div>							
							</div>
							
							<div class="form-group">
								<legend>Fichier des étudiants</legend>
								<label>List des étudiants(Code, Nom, Prènom, Email, Mot de passe)</label>
								<input type="file" name="etudiants" class="form-control" data-ng-required="true" >
							</div>
							<div class="form-groupe">
								 <button type="submit" class="btn btn-primary col-md-3" data-ng-disabled="groupeForm.$invalid" >GO</button>
								 <a href='<c:url value="/admin/detail_groupes" />' class="col-md-offset-6 col-md-3 btn btn-default" >Detail des groupes</a>
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
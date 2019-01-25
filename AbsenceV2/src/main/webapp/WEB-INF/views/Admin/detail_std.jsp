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
					<div class="col-md-5">
						<legend>Informations Etudiant</legend>
						<div class="row">
							<div class="col-sm-2">
								<label>Code</label>
							</div>
							<div class="col-sm-5">
								<label>${student.code}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-2">
								<label>Nom</label>
							</div>
							<div class="col-sm-5">
								<label>${student.name}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-2">
								<label>Prénom</label>
							</div>
							<div class="col-sm-5">
								<label>${student.lastName}</label>
							</div>
						</div>	
						<div class="row">
							<div class="col-sm-2">
								<label>Email</label>
							</div>
							<div class="col-sm-5">
								<label>${student.email}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-2">
								<label>Groupe</label>
							</div>
							<div class="col-sm-5">
								<label>${student.groupe.name}</label>
							</div>
						</div>	
						<div class="row">
							<div class="col-sm-2">
								<label>Année</label>
							</div>
							<div class="col-sm-5">
								<label>${student.groupe.annee}</label>
							</div>
						</div>	
						<c:if test="${student.groupe.annee != '1A'}">
							<div class="row">
								<div class="col-sm-2">
									<label>Filière</label>
								</div>
								<div class="col-sm-5">
									<label>${student.groupe.filiere}</label>
								</div>
							</div>							
						</c:if>				
					</div>
					<div class="col-md-6 col-md-offset-1">
						<legend>Absence/Presence Etudiant</legend>
						<c:choose>
							<c:when test="${!empty presences}">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>Date</th>
											<th>Heure</th>
											<th>Module</th>
											<th>Présent</th>
											<th>Justifié</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${presences}" var="presence">
											<tr>
												<td> ${presence.seance.date} </td>
												<td> ${presence.seance.heure} </td>
												<td> ${presence.seance.module.titre} </td>
												<c:choose>
													<c:when test="${presence.present}">
														<td style="color:green;" > ${presence.present} </td>
													</c:when>
													<c:otherwise>
														<td style="color:red;" > ${presence.present} </td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${presence.justified}">
														<td style="color:green;" > ${presence.justified} </td>
													</c:when>
													<c:otherwise>
														<td style="color:red;" > ${presence.justified} </td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</tbody>								
								</table>
							</c:when>
							<c:otherwise>
								<p>Information non disponible</p>
							</c:otherwise>
						</c:choose>						
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
        
		var app = angular.module("my-app", []); // angular call
		app.controller("Ctrl", function($scope, $http) {
					
		});
    </script>
</body>
</html>
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
				<form class="row" action='<c:url value="/prof/saveAbs" />' method="post"  >
					<c:forEach items="${groupes}" var="groupe">
						<c:forEach items="${groupe}" var="entry">
							<div class="col-md-12">
								<div class="col-md-6 col-md-offset-3">
									<p> <strong>Groupe: ${entry.key} </strong> </p>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>Etudiant</th>
												<th>Présent</th>
												<th>Justifié</th>
												<th>Detail</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${entry.value}" var="student">
												<tr>
													<td> ${student.name} ${student.lastName} </td>
													<td> <input type="checkbox" name="p${student.id}" > </td>
													<td> <input type="checkbox" name="j${student.id}" > </td>
													<td> <a href='<c:url value="/prof/detail_std?std_id=${student.id}" />' class="btn btn-info btn-sm" target='_blank' >Detail</a> </td>
												</tr>
											</c:forEach>
										</tbody>								
									</table>
								</div>
							</div>						
						</c:forEach>
					</c:forEach>
					<div class="col-md-12">
						<div class="col-md-6 col-md-offset-3">
							<button type="submit" class="btn btn-info pull-right" >Enregistrer</button>
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
		app.controller("Ctrl", function($scope, $http) {
		
		});
    </script>
</body>
</html>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<%@attribute name="head" fragment="true" %>

<html>
  <head>
        <jsp:invoke fragment="head" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"/>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link rel="stylesheet" type="text/css" href="/css/error-style.css">

  </head>
  <body>
    <header>
        <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
              <a class="navbar-brand" href="/"><spring:message code="app.title"/></a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                 aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                 <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarCollapse">
                 <ul class="navbar-nav mr-auto">
                    <li class="nav-item active"><a class="nav-link" href="/"><spring:message code="app.home"/> <span class="sr-only"></span></a></li>
                    <security:authorize access="isAnonymous()">
                        <li class="nav-item active">
                            <a class="nav-link" href="/registration">
                                <spring:message code="app.registration"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/users">
                                <spring:message code="usersList.pageTitle"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('USER')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/requests">
                                <spring:message code="requestsList.pageTitle"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('MANAGER')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/manager-requests">
                                <spring:message code="requestsList.pageTitle"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('WORKMAN')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/workman-requests">
                                <spring:message code="requestsList.pageTitle"/>
                            </a>
                        </li>
                    </security:authorize>
                 </ul>
              </div>

            <div class="navbar-nav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a href="?lang=en">
                            <spring:message code="app.lang.english"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="?lang=uk">
                            <spring:message code="app.lang.ukrainian"/>
                        </a>
                    </li>
                </ul>
            </div>

            <div>
            <div class="navbar-nav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <security:authorize access="isAnonymous()">
                            <div class="workshop-sessionInfo">
                                <spring:message code="subhead.welcome" />
                                <a href="${loginUrl}"><spring:message code="subhead.login" /></a>
                            </div>
            		    </security:authorize>
            		</li>
            		<li class="nav-item">
                        <security:authorize access="isAuthenticated()">
                            <div class="workshop-sessionInfo">
                                <security:authentication property="principal.username" />.
                                <a href="${logoutUrl}"><spring:message code="subhead.logout" /></a>
                            </div>
                        </security:authorize>
                    </li>
                 </ul>
            </div>
        </nav>
    </header>
    <div class="content">
        <jsp:doBody/>
    </div>
    <footer class="footer">
        <div class="container">
            <p class="text-center text-uppercase text-muted">&copy;1984, no monkey was injured</p>
        </div>
    </footer>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  </body>
</html>
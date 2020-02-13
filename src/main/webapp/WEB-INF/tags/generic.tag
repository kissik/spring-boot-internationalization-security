<%@tag description="Overall Page template" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<%@attribute name="head" fragment="true" %>

<html>
  <head>
        <jsp:invoke fragment="head" />
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"/>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="/js/pageable-utilities.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link rel="stylesheet" type="text/css" href="/css/error-style.css">
        <link href="https://fonts.googleapis.com/css?family=Pattaya&display=swap" rel="stylesheet">
  </head>
  <body>
    <header>
        <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
              <a class="navbar-brand" href="/" title="<spring:message code="app.title"/>">
                <spring:message code="app.title"/>
              </a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                 aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                 <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarCollapse">
                 <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/">
                            <spring:message code="app.home"/>
                        </a>
                    </li>
                    <security:authorize access="isAnonymous()">
                        <li class="nav-item active">
                            <a class="nav-link" href="/registration">
                                <spring:message code="app.registration"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/admin/page">
                                <spring:message code="app.pageTitle.admin"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('MANAGER')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/manager/page">
                                <spring:message code="app.pageTitle.manager"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('WORKMAN')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/workman/page">
                                <spring:message code="app.pageTitle.workman"/>
                            </a>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('USER')">
                        <li class="nav-item active">
                            <a class="nav-link" href="/user/page">
                                <spring:message code="app.pageTitle.user"/>
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
                                <a class="workshop-login-logout-btn" href="${loginUrl}"><spring:message code="subhead.login" /></a>
                            </div>
            		    </security:authorize>
            		</li>
            		<li class="nav-item">
                        <security:authorize access="isAuthenticated()">
                            <div class="workshop-sessionInfo">
                                <security:authentication property="principal.username" />.
                                <a class="workshop-login-logout-btn" href="${logoutUrl}"><spring:message code="subhead.logout" /></a>
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
            <p class="text-center text-uppercase text-muted">&copy;<spring:message code="app.footer" /></p>
        </div>
    </footer>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  </body>
</html>
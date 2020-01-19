<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="postLoginUrl" value="/perform_login" />

<t:generic>
    <jsp:attribute name="head">
    	<title><spring:message code="login.in.please" /></title>
    </jsp:attribute>
    <jsp:body>
    <div class="container main-content">
        <h1><spring:message code="login.in.please" /></h1>
        	<form action="${postLoginUrl}" method="post" >
    			<c:if test="${error}" >
    				<div class="alert alert-danger"><spring:message code="login.warning.alert" /></div>
    			</c:if>
    				<div class="form-group">
    				    <label for="username" class="workshop-field-label"><spring:message code="login.login" /></label>
    				    <input type="text" class="form-control" id="username" name="username" aria-describedby="loginHelp" placeholder="<spring:message code="login.login.input" />">
                        <small id="loginHelp" class="form-text text-muted"><spring:message code="login.never.share" /></small>
                    </div>
    				<div class="form-group">
    					<label for="password" class="workshop-field-label"><spring:message code="login.password" /></label>
                        <input type="password" class="form-control" id="password" name="password"  placeholder="<spring:message code="login.password.input" />">
    				</div>
    				<div class="form-group">
    					<div class="col-sm-2"><input class="btn btn-primary" type="submit" value="<spring:message code="login.in" />" /></div>
    				</div>
            </form>
        </div>
    </jsp:body>
</t:generic>

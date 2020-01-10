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
        	<form class="main" action="${postLoginUrl}" method="post" >
    			<c:if test="${param.error == true}">
    				<div class="alert alert-danger"><spring:message code="login.warning.alert" /></div>
    			</c:if>
    				<div class="form-group row">
    					<div class="col-sm-2 workshop-field-label"><spring:message code="login.login" /></div>
    					<div class="col-sm-6"><input type="text" name="username" class="short" /></div>
    				</div>
    				<div class="form-group row">
    					<div class="col-sm-2 workshop-field-label"><spring:message code="login.password" /></div>
    					<div class="col-sm-6"><input type="password" name="password" class="short" /></div>
    				</div>
    				<div class="form-group row">
    					<div class="col-sm-2 workshop-field-label"></div>
    					<div class="col-sm-6">
    					    <input type="checkbox" name="remember_me" />
    					    <spring:message code="login.remember.me" />
    					</div>
    				</div>
    				<div class="form-group row">
    					<div class="col-sm-2"><input class="btn btn-primary" type="submit" value="<spring:message code="login.in" />" /></div>
    				</div>
            </form>
        </div>
    </jsp:body>
</t:generic>

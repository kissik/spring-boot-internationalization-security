<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="newUserRegistration.pageTitle" /></title>
    </jsp:attribute>
    <jsp:body>
        <div class="container main-content">
        		<h1><spring:message code="newUserRegistration.pageTitle" /></h1>
        		<form:form cssClass="main" modelAttribute="account" method = "post">
        			<p> <spring:message code="newUserRegistration.message.allFieldsRequired" /></p>
        			<div class="form-group">
    				    <label for="username" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.username" />
    				    </label>
    				    <form:input path="username" type="text" class="form-control" id="username" />
                    	<c:set var="usernameErrors"><form:errors path="username"/></c:set>
                            <c:if test="${not empty usernameErrors}">
        					    <div class="alert alert-danger"><form:errors path="username"/></div>
        				    </c:if>
        		    </div>
        			<div class="form-group">
        				<label for="password" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.password" />
    				    </label>
    				    <form:password path="password" showPassword="false" class="form-control" id="password"/>
                    </div>
        			<div class="form-group">
        				<label for="confirmPassword" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.confirmPassword" />
    				    </label>
    				    <form:password path="confirmPassword" showPassword="false" class="form-control" id="confirmPassword"/>
                    	<c:set var="passwordErrors"><form:errors path="password"/></c:set>
                        <c:if test="${not empty passwordErrors}">
        				    <div class="alert alert-danger"><form:errors path="password"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        				<label for="firstName" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.firstName" />
    				    </label>
    				    <form:input path="firstName" type="text" class="form-control" id="firstName" />
                    	<c:set var="firstNameErrors"><form:errors path="firstName"/></c:set>
                        <c:if test="${not empty firstNameErrors}">
        				    <div class="alert alert-danger"><form:errors path="firstName"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        				<label for="lastName" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.lastName" />
    				    </label>
    				    <form:input path="lastName" type="text" class="form-control" id="lastName" />
                    	<c:set var="lastNameErrors"><form:errors path="lastName"/></c:set>
                        <c:if test="${not empty lastNameErrors}">
        				    <div class="alert alert-danger"><form:errors path="lastName"/></div>
        				</c:if>
        			</div>
                    <div class="form-group">
        				<label for="firstNameOrigin" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.firstNameOrigin" />
    				    </label>
    				    <form:input path="firstNameOrigin" type="text" class="form-control" id="firstNameOrigin" />
                    	<c:set var="firstNameOriginErrors"><form:errors path="firstNameOrigin"/></c:set>
                        <c:if test="${not empty firstNameOriginErrors}">
        				    <div class="alert alert-danger"><form:errors path="firstNameOrigin"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        				<label for="lastNameOrigin" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.lastNameOrigin" />
    				    </label>
    				    <form:input path="lastNameOrigin" type="text" class="form-control" id="lastNameOrigin" />
                    	<c:set var="lastNameOriginErrors"><form:errors path="lastNameOrigin"/></c:set>
                        <c:if test="${not empty lastNameOriginErrors}">
        				    <div class="alert alert-danger"><form:errors path="lastNameOrigin"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        				<label for="email" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.email" />
    				    </label>
    				    <form:input path="email" type="text" class="form-control" id="email" />
    				    <c:set var="emailErrors"><form:errors path="email"/></c:set>
                        <c:if test="${not empty emailErrors}">
        				    <div class="alert alert-danger"><form:errors path="email"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        				<label for="phone" class="workshop-field-label">
    				        <spring:message code="newUserRegistration.label.phone" />
    				    </label>
    				    <form:input path="phone" type="text" class="form-control" id="phone" />
    				    <c:set var="phoneErrors"><form:errors path="phone"/></c:set>
                        <c:if test="${not empty phoneErrors}">
        				    <div class="alert alert-danger"><form:errors path="phone"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        			    <input type="submit" class="btn btn-primary" value="<spring:message code="newUserRegistration.label.submit" />" />
        			</div>
        		</form:form>
        </div>
                 <script  type="text/javascript">
                        		$(function(){
                        			$('form').submit(function() {
                        				$(':submit',this).attr('disabled', 'disabled');
                        			});
                        		})
                 </script>
    </jsp:body>
</t:generic>
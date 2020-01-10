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
        		<form:form cssClass="main" modelAttribute="account" method = "${method}">
        			<p> <spring:message code="newUserRegistration.message.allFieldsRequired" /></p>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.username" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="username" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="username" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.password" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:password path="password" showPassword="false" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="password" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.confirmPassword" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:password path="confirmPassword" showPassword="false" cssClass="medium" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.firstName" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="firstName" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="firstName" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.lastName" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="lastName" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="lastName" htmlEscape="false" /></div>
        					</div>
        				</div>
                        <div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.firstNameOrigin" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="firstNameOrigin" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="firstNameOrigin" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.lastNameOrigin" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="lastNameOrigin" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="lastNameOrigin" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.email" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="email" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="email" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newUserRegistration.label.phone" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="phone" cssClass="medium" cssErrorClass="medium error" /></div>
        							<div class="alert alert-danger"><form:errors path="phone" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2">
        						<input type="submit" class="btn btn-primary" value="<spring:message code="newUserRegistration.label.submit" />" />
        					</div>
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
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:set var="requestTitleError" value="" />

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="newRequest.pageTitle" /></title>
    </jsp:attribute>
    <jsp:body>
        <div class="container main-content">
        		<h1><spring:message code="newRequest.pageTitle" /></h1>
        		<form:form cssClass="main" modelAttribute="request" method = "post">
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newRequest.label.title" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="title" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="title" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="newRequest.label.description" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:textarea path="description" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="description" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2">
        						<input type="submit" class="btn btn-primary" value="<spring:message code="newRequest.label.submit" />" />
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
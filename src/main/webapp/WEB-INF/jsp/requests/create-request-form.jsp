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
        			<div class="form-group">
        				<label for="title" class="workshop-field-label">
    				        <spring:message code="newRequest.label.title" />
    				    </label>
    				    <form:input path="title" type="text" class="form-control" id="title" />
    				    <c:set var="titleErrors"><form:errors path="title"/></c:set>
        				<c:if test="${not empty titleErrors}">
        				    <div class="alert alert-danger"><form:errors path="title"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        				<label for="description" class="workshop-field-label">
    				        <spring:message code="newRequest.label.description" />
    				    </label>
    				    <form:textarea path="description" class="form-control" id="description" />
        				<c:set var="descriptionErrors"><form:errors path="description"/></c:set>
        				<c:if test="${not empty descriptionErrors}">
        				    <div class="alert alert-danger"><form:errors path="description"/></div>
        				</c:if>
        			</div>
        			<div class="form-group">
        			    <input type="submit" class="btn btn-primary" value="<spring:message code="newRequest.label.submit" />" />
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
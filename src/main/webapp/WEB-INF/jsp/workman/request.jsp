<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="requestPath" value="/workman/requests/${request.id}" />
<c:url var="requestUrl" value="${requestPath}" />

<c:url var="editRequestUrl" value="${requestPath}/edit" />

<spring:message var="editRequest" code="request.edit" />

<t:generic>
    <jsp:attribute name="head">
        <title><c:out value="${request.title}" /></title>
    </jsp:attribute>
    <jsp:body>
    <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item"><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
        	<li class="breadcrumb-item active"><a href="${workmanUrl}"><spring:message code="requestsList.pageTitle" /></a></li>
        </ol>
        <c:if test="${param.saved == true}">
            <div class="info alert"><spring:message code="request.save" /></div>
        </c:if>
        <h1><c:out value="${request.title}" /></h1>
        <div>
			<div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="request.title" /></div>
					<div class="col-sm-6">${request.title}</div>
				</div>
			</div>
			<div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="request.description" /></div>
					<div class="col-sm-6">${request.description}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="request.author" /></div>
					<div class="col-sm-6">${request.author.fullName}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="request.status" /></div>
					<div class="col-sm-6">${request.status.code}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="request.dateCreated" /></div>
					<div class="col-sm-6">
						<span>${request.dateCreated}</span>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="request.closed" /></div>
					<div class="col-sm-6"><spring:message code="${request.getClosedLiteral()}" /></div>
				</div>
			</div>
		</div>
		<a href="${editRequestUrl}" class="btn btn-primary" title="${editRequest}"><spring:message code="request.edit" /></a>
    </div>
    </jsp:body>
</t:generic>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:url var="requestUrl" value="/manager/requests/${statuses.request.id}" />

<spring:message var="saveLabel" code="editRequest.label.submit" />

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="editRequest.pageTitle" /></title>
    </jsp:attribute>
    <jsp:body>
        <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item"><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
        	<li class="breadcrumb-item"><a href="${managerUrl}"><spring:message code="requestsList.pageTitle" /></a></li>
        	<li class="breadcrumb-item active"><a href="${requestUrl}">${statuses.request.title}</a></li>
        </ol>
        <h1><spring:message code="editRequest.pageTitle" />: ${statuses.request.title} </h1>

		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="editRequest.request.save" /><a href="${requestUrl}"><spring:message code="editRequest.request.view" /></a></div>
		</c:if>

        <form:form cssClass="main" modelAttribute="statuses" method="post">
            <div class="form-group">
        	    <label>
        		    ${statuses.request.title}
        		</label>
        	</div>
            <div class="form-group">
        	    <label>
        		    ${statuses.request.description}
        		</label>
        	</div>
            <div class="form-group">
                <label for="status" class="workshop-field-label">
                    <spring:message code="editRequest.label.status" />
                </label>
                <form:select path="status" class="form-control" id="status" >
                    <c:forEach items="${statuses.request.status.nextStatuses}" var="statuses">
                        <form:option value="${statuses.code}">${statuses.code}</form:option>
                    </c:forEach>
                </form:select>
                <c:set var="statusErrors"><form:errors path="status"/></c:set>
        		<c:if test="${not empty statusErrors}">
        		    <div class="alert alert-danger"><form:errors path="status"/></div>
        		</c:if>
        	</div>
        	<div class="form-group">
                <label for="price" class="workshop-field-label">
                    <spring:message code="editRequest.label.price" />
                </label>
                <form:input path="price" type="number" class="form-control" id="price" />
                <c:set var="priceErrors"><form:errors path="price"/></c:set>
        		<c:if test="${not empty priceErrors}">
        		    <div class="alert alert-danger"><form:errors path="price"/></div>
        		</c:if>
        	</div>
        	<div class="form-group">
                <label for="cause" class="workshop-field-label">
                    <spring:message code="editRequest.label.cause" />
                </label>
                <form:textarea path="cause" class="form-control" id="cause" />
        		<c:set var="causeErrors"><form:errors path="cause"/></c:set>
        		<c:if test="${not empty causeErrors}">
        		    <div class="alert alert-danger"><form:errors path="cause"/></div>
        		</c:if>
        	</div>
        	<div class="form-group">
        	    <input type="submit" class="btn btn-primary" value="<spring:message code="editRequest.label.submit" />" />
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
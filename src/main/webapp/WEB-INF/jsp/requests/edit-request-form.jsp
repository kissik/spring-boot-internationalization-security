<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:url var="requestUrl" value="/requests/${statuses.request.id}" />

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
        	<li class="breadcrumb-item"><a href="${requestsUrl}"><spring:message code="requestsList.pageTitle" /></a></li>
        	<li class="breadcrumb-item active"><a href="${requestUrl}">${statuses.request.title}</a></li>
        </ol>
        <h1><spring:message code="editRequest.pageTitle" />: ${statuses.request.title} </h1>

		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="editRequest.request.save" /><a href="${requestUrl}"><spring:message code="editRequest.request.view" /></a></div>
		</c:if>

        <form:form cssClass="main" modelAttribute="statuses">
            <input type="hidden" name="_method" value="POST" />
                        <div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
        					    ${statuses.request.title}
        					</div>
        			    </div>
                        <div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
        					    ${statuses.request.description}
        					</div>
        			    </div>
                        <div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="editRequest.label.status" />
        					</div>
        					<div class="col-sm-6">
        						<div>
        						<form:select path="status" cssClass="custom-select medium" cssErrorClass="medium error" >
        							<c:forEach items="${statuses.request.status.nextStatuses}" var="statuses">
        			 				<form:option value="${statuses.code}">${statuses.code}</form:option>
        							</c:forEach>
        			 			</form:select>
        						</div>
        						<div class="alert alert-danger"><form:errors path="status" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="editRequest.label.price" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input type="number" path="price" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="price" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="editRequest.label.cause" />
        					</div>
        					<div class="col-sm-6">
        						<div><form:input path="cause" cssClass="medium" cssErrorClass="medium error" /></div>
        						<div class="alert alert-danger"><form:errors path="cause" htmlEscape="false" /></div>
        					</div>
        				</div>
        				<div class="form-group row">
        					<div class="col-sm-2">
        						<input type="submit" class="btn btn-primary" value="<spring:message code="editRequest.label.submit" />" />
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
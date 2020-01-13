<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<spring:message var="createRequest" code="request.create" />

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="requestsList.pageTitle"/> </title>
    </jsp:attribute>
    <jsp:body>
    <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item active"><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
        </ol>
        <h1><spring:message code="requestsList.pageTitle" /></h1>
        		<c:choose>
        			<c:when test="${empty requestsList}">
        				<p><spring:message code="requestsList.empty" /></p>
        			</c:when>
        			<c:otherwise>
        			<div id="list">

        				<table id="requestList" class="sortable">
        					<thead>
        						<tr>
        							<th class="string"><spring:message code="requestsList.table.title" /></th>
        							<th class="string"><spring:message code="requestsList.table.status" /></th>
        							<th class="string"><spring:message code="requestsList.table.description" /></th>
        							<security:authorize access="hasRole('ADMIN')">
        							    <th class="string"><spring:message code="requestsList.table.author" /></th>
        							</security:authorize>
        						</tr>
        					</thead>
        					<tbody class="list">
        						<c:forEach var="request" items="${requestsList}">
        							<c:url var="requestUrl" value="/requests/${request.id}" />
        							<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
        							<tr>
        								<td class="title"><span class="request" style="white-space:nowrap"><a href="${requestUrl}"><c:out value="${request.title}" /></a></td>
        								<td class="status"><c:out value="${request.status.code}" /></td>
        								<td class="description"><c:out value="${request.description}" /></td>
        							</tr>
        						</c:forEach>
        					</tbody>
        				</table>
        			</div>
        			</c:otherwise>
        		</c:choose>
    </div>
    </jsp:body>
</t:generic>
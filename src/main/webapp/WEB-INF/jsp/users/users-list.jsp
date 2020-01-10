<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="usersList.pageTitle"/></title>
    </jsp:attribute>
    <jsp:body>
    <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item active"><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
        </ol>
        <h1><spring:message code="usersList.pageTitle" /></h1>
        		<c:choose>
        			<c:when test="${empty usersList}">
        				<p><spring:message code="usersList.empty" /></p>
        			</c:when>
        			<c:otherwise>
        			<div id="list">

        				<table id="userList" class="sortable">
        					<thead>
        						<tr>
        							<th class="string"><spring:message code="usersList.table.user" /></th>
        							<th class="string"><spring:message code="usersList.table.firstName" /></th>
        							<th class="string"><spring:message code="usersList.table.lastName" /></th>
        							<th class="string"><spring:message code="usersList.table.phone" /></th>
        						</tr>
        					</thead>
        					<tbody class="list">
        						<c:forEach var="user" items="${usersList}">
        							<c:url var="userUrl" value="/users/${user.id}" />
        							<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
        							<fmt:formatDate var="date" type="both" timeStyle="short" value="${u.dateCreated}" />
        							<tr>
        								<td class="username"><span class="user icon" style="white-space:nowrap"><a href="${userUrl}"><c:out value="${user.username}" /></a></td>
        								<td class="firstName"><c:out value="${user.firstName}" /></td>
        								<td class="lastName"><c:out value="${user.lastName}" /></td>
        								<td class="phone"><c:out value="${user.phone}" /></td>
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
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
        <h1><spring:message code="requestsHistoryList.pageTitle" /></h1>
        <input id="size" type="number" min=2 max=6>
        <table id="request-list-table" class="sortable">
            <thead>
        	    <tr>
        		    <th class="string"><spring:message code="requestsHistoryList.table.title" /></th>
        			<th class="string"><spring:message code="requestsHistoryList.table.status" /></th>
        			<th class="string"><spring:message code="requestsHistoryList.table.description" /></th>
        			<th class="string"><spring:message code="requestsHistoryList.table.user" /></th>
        		</tr>
        	</thead>
        	<tbody id="pageable-list">
        	</tbody>
        </table>
        <div id="page-navigation"></div>
        <script src="js/request-history-pageable.js"></script>
    </div>
    </jsp:body>
</t:generic>
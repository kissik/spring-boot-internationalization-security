<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:generic>
    <jsp:attribute name="head">
        <title>
            Workshop
        </title>
    </jsp:attribute>
    <jsp:body>
        <div class="container main-content">
             <ol class="breadcrumb">
                <li class="breadcrumb-item active"><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
             </ol>
             <c:if test="${param.updated == true}">
                <div class="info alert"><spring:message code="user.update" /></div>
             </c:if>
             <c:if test="${param.deleted == true}">
                <div class="info alert alert-danger"><spring:message code="user.delete.true" /></div>
             </c:if>
             <c:if test="${param.deleted == false}">
                <div class="info alert alert-danger"><spring:message code="user.delete.false" /></div>
             </c:if>
             <h1><spring:message code="usersList.pageTitle" /></h1>
             <input id="search" class="col-4 rounded border" type="texp" placeholder="<spring:message code="table.search" />">
             <input id="size" class="col-2 rounded border" type="number" min="2" max="6" value="5">
             <input name="sorting" class="hidden" type="radio" id="asc" value="asc" checked><label class="col-2" for="asc"><spring:message code="table.asc" /></label>
             <input name="sorting" class="hidden" type="radio" id="desc" value="desc"><label class="col-2" for="desc"><spring:message code="table.desc" /></label>
             <div id="page-navigation"></div>
             <table id="request-list-table" class="sortable">
                <thead>
                    <tr>
                        <th class="string"><spring:message code="usersList.table.user" /></th>
                     	<th class="string"><spring:message code="usersList.table.firstName" /></th>
                        <th class="string"><spring:message code="usersList.table.lastName" /></th>
                     	<th class="string"><spring:message code="usersList.table.phone" /></th>
                    </tr>
                </thead>
                <tbody id="pageable-list">
                </tbody>
             </table>
            <script src="/js/pageable-admin-accounts.js"></script>
        </div>
    </jsp:body>
</t:generic>
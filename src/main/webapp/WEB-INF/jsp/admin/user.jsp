<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<c:set var="email" value="${account.email}" />
<c:set var="userPath" value="/admin/accounts/${account.id}" />
<c:url var="userUrl" value="/${userPath}" />
<c:url var="editUserUrl" value="${userPath}/edit" />
<c:url var="deleteUserUrl" value="/admin/accounts/${account.id}/delete" />

<spring:message var="editUser" code="user.edit" />

<t:generic>
    <jsp:attribute name="head">
        <title><c:out value="${account.fullName}" /></title>
    </jsp:attribute>
    <jsp:body>
    <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item"><a href="/"><spring:message code="home.pageTitle" /></a></li>
        	<li class="breadcrumb-item active"><a href="/admin/page"><spring:message code="usersList.pageTitle" /></a></li>
        </ol>
        <c:if test="${param.update == true}">
            <div class="alert alert-success"><spring:message code="user.save" /></div>
        </c:if>
        <h1><c:out value="${account.fullName}" /></h1>
        <form action="${deleteUserUrl}" method="post">
		    <input type="text" name=id class="hidden" value=${account.id}>
		    <input type="submit" class="btn btn-danger" value=<spring:message code="user.delete" /> >
		</form>
		<div>
			<div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.login" /></div>
					<div class="col-sm-6">${account.username}</div>
				</div>
			</div>
			<div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.firstName" /></div>
					<div class="col-sm-6">${account.firstName}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.lastName" /></div>
					<div class="col-sm-6">${account.lastName}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.firstNameOrigin" /></div>
					<div class="col-sm-6">${account.firstNameOrigin}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.lastNameOrigin" /></div>
					<div class="col-sm-6">${account.lastNameOrigin}</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.email" /></div>
					<div class="col-sm-6">
						<a href="mailto:${email}">${email}</a>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.phone" /></div>
					<div class="col-sm-6">
						<span>${account.phone}</span>
					</div>
				</div>
			</div>
			<div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.enable" /></div>
					<div class="col-sm-6"><c:out value="${account.enabled}" /></div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 workshop-field-label"><spring:message code="user.roles" /></div>
					<div class="col-sm-6">
						<c:forEach var="role" items="${account.roles}">
							<c:out value="${role.name}" /><br />
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<a href="${editUserUrl}" class="btn btn-primary" title="${editUser}"><spring:message code="user.edit" /></a>
    </div>
    </jsp:body>
</t:generic>
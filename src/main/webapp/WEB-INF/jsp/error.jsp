<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="error.title"/></title>
    </jsp:attribute>
    <jsp:body>
    	<div class="workshop-error-body-class">
	        <div class="workshop-error-message-wrapper">
				<h1><spring:message code="error.title" /></h1>
				<p>${error}</p>
				<c:if test="${param.failedconnection == true}"><p><spring:message code="error.dbconnection" /></p></c:if>
				<p><spring:message code="error.contact.admin" /></p>
			</div>
		</div>
    </jsp:body>
</t:generic>

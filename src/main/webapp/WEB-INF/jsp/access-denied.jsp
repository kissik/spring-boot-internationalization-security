<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="access.denied.title"/></title>
    </jsp:attribute>
    <jsp:body>
        <h1><spring:message code="access.denied.title" /></h1>
        <p><spring:message code="access.denied.sorry" /></p>
        <p>${error}</p><p><spring:message code="home.welcome" /></p>
    </jsp:body>
</t:generic>

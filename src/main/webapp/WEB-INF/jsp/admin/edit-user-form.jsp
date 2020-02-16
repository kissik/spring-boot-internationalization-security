<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:url var="userUrl" value="/admin/accounts/${account.id}" />
<c:url var="adminPageUrl" value="/admin/page" />

<spring:message var="saveLabel" code="editUser.label.submit" />

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="editUser.pageTitle" /></title>
    </jsp:attribute>
    <jsp:body>
        <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item"><a href="/"><spring:message code="home.pageTitle" /></a></li>
        	<li class="breadcrumb-item"><a href="${adminPageUrl}"><spring:message code="usersList.pageTitle" /></a></li>
        	<li class="breadcrumb-item active"><a href="${userUrl}">${account.username}</a></li>
        </ol>
        <h1><spring:message code="editUser.pageTitle" />: ${account.username} </h1>

		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="editUser.user.save" /><a href="${userUrl}"><spring:message code="editUser.user.view" /></a></div>
		</c:if>

        <form:form cssClass="main" method="post" modelAttribute="roleForm">
            <div class="form-group">
        	    <label>
        		    ${account.firstName} ${account.lastName}
                </label>
        	</div>
        	<div class="form-group">
        		<label>
        		    ${account.firstNameOrigin} ${account.lastNameOrigin}
                </label>
        	</div>
        	<div class="form-group">
        	    <label>
        		    <a href="mailto:${account.email}">${account.email}</a>
                </label>
        	</div>
        	<div class="form-group">
        	    <label>
        	        ${account.phone}
                </label>
        	</div>
        	<div class="form-group">
                <label for="role" class="workshop-field-label">
                    <spring:message code="editUser.label.roles" />
                </label>
                <form:select multiple="true" path="role" class="form-control" id="role" >
                    <c:forEach items="${rolesList}" var="item">
                        <form:option value="${item.code}">${item.code}</form:option>
                    </c:forEach>
                </form:select>
                <c:set var="roleErrors"><form:errors path="role"/></c:set>
                <c:if test="${not empty roleErrors}">
                    <div class="alert alert-danger"><form:errors path="role"/></div>
                </c:if>
            </div>
        	<div class="form-group">
        	    <input type="submit" class="btn btn-primary" value="<spring:message code="editUser.label.submit" />" />
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
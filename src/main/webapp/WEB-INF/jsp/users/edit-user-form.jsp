<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:url var="userUrl" value="/users/${originalAccount.id}" />

<spring:message var="saveLabel" code="editUser.label.submit" />

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:generic>
    <jsp:attribute name="head">
        <title><spring:message code="editUser.pageTitle" /></title>
    </jsp:attribute>
    <jsp:body>
        <div class="container main-content">
        <ol class="breadcrumb">
        	<li class="breadcrumb-item"><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
        	<li class="breadcrumb-item"><a href="${usersUrl}"><spring:message code="usersList.pageTitle" /></a></li>
        	<li class="breadcrumb-item active"><a href="${userUrl}">${originalAccount.username}</a></li>
        </ol>
        <h1><spring:message code="editUser.pageTitle" />: ${account.username} </h1>

		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="editUser.user.save" /><a href="${userUrl}"><spring:message code="editUser.user.view" /></a></div>
		</c:if>

        	<form:form cssClass="main" modelAttribute="roles">
        	    <input type="hidden" name="_method" value="POST" />
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
        					    ${originalAccount.username}
        					</div>
        			    </div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
                                ${originalAccount.firstName} ${originalAccount.lastName}
                            </div>
        			    </div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
                                ${originalAccount.firstNameOrigin} ${originalAccount.lastNameOrigin}
                            </div>
        			    </div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
                                ${originalAccount.email}
                            </div>
        			    </div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
                                ${originalAccount.phone}
                            </div>
        			    </div>
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					</div>
        					<div class="col-sm-6">
                                ${originalAccount.dateCreated}
                            </div>
                        </div>
                        <security:authorize access="isAuthenticated()">
        				<div class="form-group row">
        					<div class="col-sm-2 workshop-field-label">
        					    <spring:message code="editUser.label.roles" />
        					</div>
        					<div class="col-sm-6">
        						<div>
        						<form:select multiple="true" path="role" cssClass="medium" cssErrorClass="medium error" >
        							<c:forEach items="${roleList}" var="roles">
        			 				<form:option value="${roles.code}">${roles.code}</form:option>
        							</c:forEach>
        			 			</form:select>
        						</div>
        						<div class="alert alert-danger"><form:errors path="role" htmlEscape="false" /></div>
        					</div>
        				</div>
        				</security:authorize>

        				<div class="form-group row">
        					<div class="col-sm-2">
        						<input type="submit" class="btn btn-primary" value="<spring:message code="editUser.label.submit" />" />
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
<%@ page import="com.edu.mvc.models.Page" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>

        <c:url value="/site/create/confirm" var="siteCreateConfirm"/>

        <div class="card mb-3">
            <div class="card-header">Добавить сайт</div>
            <div class="card-body">
                <springform:form cssClass="form-group" method="post" modelAttribute="newSite"
                                 action="${siteCreateConfirm}">
                    <label for="pageUrl">siteUrl</label>
                    <springform:input cssClass="form-control" id="pageUrl" path="url"/><br/>
                    <springform:button class="btn btn-primary">Создать</springform:button>
                </springform:form>
            </div>
        </div>
    </jsp:body>

</page:template>

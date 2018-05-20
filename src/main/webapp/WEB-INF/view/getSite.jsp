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

        <c:url value="/getSite/confirm" var="siteCreateConfirm"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }
        </style>
        <h1>getSitePage</h1>
        <p>Enter site URL:</p>
        <springform:form cssClass="form-group" method="post" modelAttribute="newSite" action="${siteCreateConfirm}">
            <label for="pageUrl">URL сайта:</label>
            <springform:input cssClass="form-control" id="pageUrl" path="url"/><br/>
            <springform:button class="btn btn-primary">Создать</springform:button>
        </springform:form>
    </jsp:body>

</page:template>

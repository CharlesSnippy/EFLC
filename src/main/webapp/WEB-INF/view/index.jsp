<%@ page import="com.edu.mvc.bean.EducationSitePage" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>
        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }
        </style>
        <h1>Blank</h1>
        <p>collectDocument</p>
        <textarea disabled rows="10" class="form-control code-area ">${collectDocument}</textarea>
        <p>hrefs</p>
        <textarea disabled rows="10" class="form-control code-area ">${hrefs}</textarea>
        <c:forEach items="${sitePages}" var="item">
            <p>SitePage</p>
            <textarea disabled rows="10" class="form-control code-area ">${item}</textarea>
        </c:forEach>
    </jsp:body>

</page:template>

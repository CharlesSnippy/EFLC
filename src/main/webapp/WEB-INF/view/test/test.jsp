<%@ page import="com.edu.services.parsing.ParsingServiceImpl" %>
<%@ page import="org.jsoup.nodes.Document" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>

        <c:url value="/site/delete/" var="siteDelete"/>
        <c:url value="/site/parse/" var="siteParse"/>
        <c:url value="/compare/" var="comparePages"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
                width: 100%;
            }

            .stripped {
                overflow-y: scroll;
                height: 550px;
                font-family: 'Courier New';
                font-size: 0.8em;
                margin: 10px;
                background-color: #e0e0e0;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Тестирование функций</div>
            <div class="card-body">

                <c:forEach items="${singles}" var="item">
                    <textarea class="code-area" disabled rows="5">${item}</textarea>
                </c:forEach>

                <c:forEach items="${differences}" var="compareResult">
                <div class="compare-result stripped">
                    <table>
                        <c:forEach items="${compareResult}" var="item">
                            <c:choose>
                                <c:when test="${item.type eq '+'.charAt(0)}">
                                    <tr class="table-success">
                                </c:when>
                                <c:when test="${item.type eq '-'.charAt(0)}">
                                    <tr class="table-danger">
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                </c:otherwise>
                            </c:choose>
                            <td>${item.firstIndex}</td>
                            <td>${item.secondIndex}</td>
                            <td>${item.row.replace("&","&amp;").replace("<","&lt;").replace(">", "&gt;")}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                </c:forEach>

            </div>
        </div>
    </jsp:body>

</page:template>

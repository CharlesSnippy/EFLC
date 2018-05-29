<%@ page import="com.edu.mvc.models.Site" %>
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
        <c:url value="/page/" var="pageView"/>
        <c:url value="/compare/" var="comparePages"/>
        <c:url value="/site/cut/" var="cutPages"/>
        <c:url value="/check/" var="checkSite"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }

            .pages-container {
                overflow-y: scroll;
                height: 500px;
                margin: 10px;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Информация о критериях:</div>
            <div class="card-body">
                <h4>Критерии в базе данных:</h4>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Название</th>
                        <th scope="col">Краткое описание</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${criteria}" var="item">
                        <c:choose>
                            <c:when test="${item.name.charAt(0) eq 'C'.charAt(0)}">
                                <tr class="table-success">
                            </c:when>
                            <c:when test="${item.name.charAt(0) eq 'S'.charAt(0)}">
                                <tr class="table-warning">
                            </c:when>
                            <c:when test="${item.name.charAt(0) eq 'D'.charAt(0)}">
                                <tr class="table-danger">
                            </c:when>
                            <c:otherwise>
                                <tr>
                            </c:otherwise>
                        </c:choose>
                        <td>
                                ${item.criteriaId}
                        </td>
                        <td>
                                ${item.name}
                        </td>
                        <td>
                                ${item.shortDescription}
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:body>

</page:template>

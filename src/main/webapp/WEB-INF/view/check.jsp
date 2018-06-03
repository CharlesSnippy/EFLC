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
        <c:url value="/compare/" var="comparePages"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Результат структурного анализа</div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Страница</th>
                        <th scope="col">Адрес</th>
                        <th scope="col">Результаты</th>
                    </tr>
                    </thead>
                    <c:forEach items="${pageRating}" var="item">
                        <c:if test="${item.value > 0}">
                        <tr>
                            <td>
                                ${item.key.pageId}
                            </td>
                            <td>
                                    ${item.key.title}
                            </td>
                            <td>
                                <a href="${item.key.url}" class="d-inline-block text-truncate" style="max-width: 400px;">${item.key.url}</a>
                            </td>
                            <td>
                                <c:forEach items="${results}" var="result">
                                    <c:if test="${result.page.pageId == item.key.pageId}">
                                        <p>${result.criterion.name} : ${result.answer}</p>
                                        <%--<p>${result.block}</p>--%>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </div>
    </jsp:body>

</page:template>

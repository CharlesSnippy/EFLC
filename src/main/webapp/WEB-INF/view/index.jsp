<%@ page import="com.edu.mvc.models.Site" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>

        <c:url value="/site/" var="siteView"/>
        <c:url value="/compare/" var="comparePages"/>
        <c:url value="/check/" var="checkSite"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Индекс сайтов</div>
            <div class="card-body">
                <c:choose>
                <c:when test="${not empty allSites}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Заголовок</th>
                        <th scope="col">Адрес</th>
                        <th scope="col">Статус</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${allSites}" var="item">
                        <tr>
                            <td>
                                    ${item.siteId}
                            </td>
                            <td>
                                <a href="${siteView}${item.siteId}">${item.title}</a>
                            </td>
                            <td>
                                <a href="${item.url}">${item.url}</a>
                            </td>
                            <td>
                                <span class="badge ${Site.STATE_CLASS.get(item.state)}">${Site.STATE_STRING.get(item.state)}</span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            </c:when>

            <c:otherwise>
                <p><em>No Site available</em></p>
            </c:otherwise>
            </c:choose>
        </div>
        </div>
    </jsp:body>

</page:template>

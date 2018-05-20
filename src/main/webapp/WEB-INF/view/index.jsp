<%@ page import="com.edu.mvc.models.Page" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>

        <c:url value="/site/delete/" var="siteDelete"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }
        </style>
        <h1>Sites index</h1>
        <c:choose>
            <c:when test="${not empty allSites}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">URL</th>
                        <th scope="col">Page count</th>
                        <th scope="col">Actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${allSites}" var="item">
                        <tr>
                            <td>
                                    ${item.siteId}
                            </td>
                            <td>
                                    <a href="${item.url}">${item.url}</a>
                            </td>
                            <td>
                                    ${item.pages.size()}
                            </td>
                            <td>
                                <ul class="nav nav-pills">
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Action</a>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="${siteDelete}${item.siteId}">Parse</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="${siteDelete}${item.siteId}">Remove</a>
                                        </div>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </c:when>

            <c:otherwise>
                <p><em>No Site available</em></p>
            </c:otherwise>
        </c:choose>
    </jsp:body>

</page:template>

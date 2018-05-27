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
        <c:url value="/check/" var="checkSite"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Sites index</div>
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
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${allSites}" var="item">
                                <tr>
                                    <td>
                                            ${item.siteId}
                                    </td>
                                    <td>
                                            ${item.title}
                                    </td>
                                    <td>
                                        <a href="${item.url}">${item.url}</a>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.state == Site.STATE_CREATED}">
                                                <span class="badge badge-info">Добавлен</span>
                                            </c:when>
                                            <c:when test="${item.state == Site.STATE_PARSED}">
                                                <span class="badge badge-info">Загружен</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-secondary">Неизвестно</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <ul class="nav nav-pills">
                                            <li class="nav-item dropdown">
                                                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
                                                   role="button" aria-haspopup="true" aria-expanded="false">Выбрать</a>
                                                <div class="dropdown-menu">

                                                    <a class="dropdown-item" href="${siteParse}${item.siteId}">Загрузить все страницы</a>
                                                    <a class="dropdown-item" href="${checkSite}${item.siteId}">Провести анализ страниц</a>
                                                    <a class="dropdown-item" href="${comparePages}${item.siteId}">Сравнить страницы</a>
                                                    <div class="dropdown-divider"></div>
                                                    <a class="dropdown-item"
                                                       href="${siteDelete}${item.siteId}">Удалить</a>
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
            </div>
        </div>
    </jsp:body>

</page:template>

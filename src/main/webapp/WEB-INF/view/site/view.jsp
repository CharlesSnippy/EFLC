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
            <div class="card-header">Информация о сайте:</div>
            <div class="card-body">
                <div class="row">

                    <div class="col-sm-6">
                        <h4>Общая информация</h4>
                        <div class="row">
                            <p class="col-sm-5">Заголовок:</p>
                            <p class="col-sm-7">${site.title}</p>
                        </div>
                        <div class="row">
                            <p class="col-sm-5">Адрес:</p>
                            <a class="col-sm-7" href="${site.url}">${site.url}</a>
                        </div>
                        <div class="row">
                            <p class="col-sm-5">ID:</p>
                            <p class="col-sm-7">${site.siteId}</p>
                        </div>
                        <div class="row">
                            <p class="col-sm-5">Статус:</p>
                            <p class="col-sm-7">
                                <span class="badge ${Site.STATE_CLASS.get(site.state)}">${Site.STATE_STRING.get(site.state)}</span>
                            </p>
                        </div>
                        <div class="row">
                            <p class="col-sm-5">Загружено страниц:</p>
                            <p class="col-sm-7">${site.pages.size()}</p>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <h4>Действия</h4>
                        <div class="list-group">
                            <a class="list-group-item list-group-item-action" href="${siteParse}${site.siteId}">Загрузить
                                страницы</a>
                            <a class="list-group-item list-group-item-action" href="${comparePages}${site.siteId}">Сравнить
                                страницы</a>
                            <a class="list-group-item list-group-item-action" href="${cutPages}${site.siteId}">Обработать
                                страницы</a>
                            <a class="list-group-item list-group-item-action"
                               href="${siteDelete}${site.siteId}">Удалить</a>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <h4>Анализ</h4>
                        <div class="list-group">
                            <a class="list-group-item list-group-item-action" href="${checkSite}${site.siteId}">Провести
                                анализ страниц на структурные критерии</a>
                        </div>
                    </div>
                </div>
                <h4>Информация о страницах</h4>
                <div class="pages-container">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Заголовок</th>
                            <th scope="col">Адрес</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${site.pages}" var="item">
                            <tr>
                                <td>
                                        ${item.pageId}
                                </td>
                                <td>
                                    <a href="${pageView}${item.pageId}">${item.title}</a>
                                </td>
                                <td>
                                    <a href="${item.url}">${item.url}</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>

</page:template>

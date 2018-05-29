<%@ page import="com.edu.mvc.models.Site" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>

        <c:url value="/compare/" var="comparePages"/>
        <c:url value="/site/cut/" var="cutPages"/>

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
            <div class="card-header">Обработать сайт ${site.title}:</div>
            <div class="card-body">
                <h4>Выберите страницу-образец</h4>
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
                                    <a href="${cutPages}${site.siteId}/${item.pageId}">${item.title}</a>
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

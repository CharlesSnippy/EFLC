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

        <c:url value="/compare/" var="compare"/>

        <style>
            .code-area {
                font-family: 'Courier New';
                font-size: 0.8em;
                width: 100%;
                resize: none;
            }

            .compare-result {
                overflow-y: scroll;
                height: 550px;
                font-family: 'Courier New';
                font-size: 0.8em;
                margin: 10px;
                background-color: #e0e0e0;
            }

            .height-set {
                height: 600px;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Сравнение страниц сайта</div>
            <div class="card-body">
                <div class="row height-set">
                    <c:set var="settings" value="settings"/>
                    <c:set var="code" value="code"/>
                    <c:set var="result" value="result"/>
                    <c:set var="onlyLeft" value="onlyLeft"/>
                    <c:set var="onlyRight" value="onlyRight"/>
                    <div class="col-2">
                        <div class="list-group" id="list-tab" role="tablist">
                            <a class="list-group-item list-group-item-action active" data-toggle="list" role="tab"
                               id="list-${settings}-list"
                               href="#list-${settings}"
                               aria-controls="${settings}">
                                Параметры сравнения
                            </a>
                            <a class="list-group-item list-group-item-action" data-toggle="list" role="tab"
                               id="list-${code}-list"
                               href="#list-${code}"
                               aria-controls="${code}">
                                Код сайтов
                            </a>
                            <a class="list-group-item list-group-item-action" data-toggle="list" role="tab"
                               id="list-${result}-list"
                               href="#list-${result}"
                               aria-controls="${result}">
                                Результат сравнения
                            </a>
                            <a class="list-group-item list-group-item-action" data-toggle="list" role="tab"
                               id="list-${onlyLeft}-list"
                               href="#list-${onlyLeft}"
                               aria-controls="${onlyLeft}">
                                Разница слева
                            </a>
                            <a class="list-group-item list-group-item-action" data-toggle="list" role="tab"
                               id="list-${onlyRight}-list"
                               href="#list-${onlyRight}"
                               aria-controls="${onlyRight}">
                                Разница справа
                            </a>
                        </div>
                    </div>
                    <div class="col-10">
                        <div class="tab-content" id="nav-tabContent">
                            <div class="tab-pane fade show active" id="list-${settings}" role="tabpanel"
                                 aria-labelledby="list-${settings}-list">
                                <h1>${site.url}</h1>
                                <springform:form cssClass="form-group" method="post" modelAttribute="diffResult"
                                                 action="${compare}${site.siteId}/getDiff">
                                    <div style="display:inline-block; width:40%;">
                                        <springform:select cssClass="form-control" id="pageUrl" path="firstIndex"><br/>
                                            <springform:options items="${pageOptions}"></springform:options>
                                        </springform:select>
                                    </div>
                                    <div style="display:inline-block; width:40%;">
                                        <springform:select cssClass="form-control" id="pageUrl" path="secondIndex"><br/>
                                            <springform:options items="${pageOptions}"></springform:options>
                                        </springform:select>
                                    </div>
                                    <div>
                                        <springform:button class="btn btn-primary">Compare</springform:button>
                                    </div>
                                </springform:form>
                            </div>
                            <div class="tab-pane fade" id="list-${code}" role="tabpanel"
                                 aria-labelledby="list-${code}-list">
                                <h1>${site.url}</h1>
                                <div>
                                    <a href="${firstPage.url}" class="badge badge-danger">${firstPage.url}</a>
                                    <a href="${secondPage.url}" class="badge badge-success">${secondPage.url}</a>
                                </div>
                                <div style="display:inline-block; width:40%;">
                                    <textarea class="code-area" disabled rows="15">${firstPage.document.toString().replace("&","&amp;").replace("<","&lt;").replace(">", "&gt;")}</textarea>
                                </div>
                                <div style="display:inline-block; width:40%;">
                                    <textarea class="code-area" disabled rows="15">${secondPage.document.toString().replace("&","&amp;").replace("<","&lt;").replace(">", "&gt;")}</textarea>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="list-${result}" role="tabpanel"
                                 aria-labelledby="list-${result}-list">
                                <div>
                                    <a href="${firstPage.url}" class="badge badge-danger">${firstPage.url}</a>
                                    <a href="${secondPage.url}" class="badge badge-success">${secondPage.url}</a>
                                </div>
                                <div class="compare-result">
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
                            </div>
                            <div class="tab-pane fade" id="list-${onlyLeft}" role="tabpanel"
                                 aria-labelledby="list-${onlyLeft}-list">
                                <a href="${firstPage.url}" class="badge badge-danger">${firstPage.url}</a>
                                <div class="compare-result">
                                    <table>
                                        <c:forEach items="${compareResult}" var="item">
                                            <c:if test="${item.type eq '-'.charAt(0)}">
                                                <tr class="table-danger">
                                                    <td>${item.firstIndex}</td>
                                                    <td>${item.secondIndex}</td>
                                                    <td>${item.row.replace("&","&amp;").replace("<","&lt;").replace(">", "&gt;")}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="list-${onlyRight}" role="tabpanel"
                                 aria-labelledby="list-${onlyRight}-list">
                                <a href="${secondPage.url}" class="badge badge-success">${secondPage.url}</a>
                                <div class="compare-result">
                                    <table>
                                        <c:forEach items="${compareResult}" var="item">
                                            <c:if test="${item.type eq '+'.charAt(0)}">
                                                <tr class="table-success">
                                                    <td>${item.firstIndex}</td>
                                                    <td>${item.secondIndex}</td>
                                                    <td>${item.row.replace("&","&amp;").replace("<","&lt;").replace(">", "&gt;")}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--diff-->

    </jsp:body>

</page:template>

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
            }
        </style>
        <h1>Page comparing</h1>

        <%--<div style="clear:both;" id="wrapper"><!--diff, вывод-->--%>
        <div><!--diff, вывод-->
            <style>
                .code-area {
                    font-family: 'Courier New';
                    font-size: 0.8em;
                    width: 100%;
                    resize: none;
                }

                .compare-result {
                    overflow-y: scroll;
                    height: 500px;
                    font-family: 'Courier New';
                    font-size: 0.8em;
                    margin: 10px;
                }
            </style>
            <h1>${site.url}</h1>
            <springform:form cssClass="form-group" method="post" modelAttribute="diffResult"
                             action="${compare}${site.siteId}/getDiff">
                <div style="display:inline-block; width:40%;">
                    <springform:select cssClass="form-control" id="pageUrl" path="firstIndex"><br/>
                        <springform:options items="${pageOptions}"></springform:options>
                    </springform:select>
                    <textarea class="code-area" disabled rows="15">${firstPage}</textarea>
                </div>
                <div style="display:inline-block; width:40%;">
                    <springform:select cssClass="form-control" id="pageUrl" path="secondIndex"><br/>
                        <springform:options items="${pageOptions}"></springform:options>
                    </springform:select>
                    <textarea class="code-area" disabled rows="15">${secondPage}</textarea>
                </div>
                <div>
                    <springform:button class="btn btn-primary">Compare</springform:button>
                </div>
            </springform:form>

                <%--<textarea class="code-area" disabled rows="15">${firstPage}</textarea>--%>
                <%--<textarea class="code-area" disabled rows="15">${secondPage}</textarea>--%>
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
        <!--diff-->

    </jsp:body>

</page:template>

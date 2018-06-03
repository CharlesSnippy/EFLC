<%@ page import="com.edu.services.parsing.ParsingServiceImpl" %>
<%@ page import="org.jsoup.nodes.Document" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<page:template>

    <jsp:attribute name="title">EFLC</jsp:attribute>

    <jsp:body>

        <c:url value="/site/delete/" var="siteDelete"/>
        <c:url value="/site/parse/" var="siteParse"/>
        <c:url value="/compare/" var="comparePages"/>

        <spring:url value="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.js" var="chartsJs"/>

        <!-- Bootstrap core JavaScript-->
        <script src="${chartsJs}"></script>


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
                <c:forEach items="${differencesPos}" var="compareResult">
                    <div class="compare-result stripped">
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
                </c:forEach>

                <c:forEach items="${differencesNeg}" var="compareResult">
                    <div class="compare-result stripped">
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
                </c:forEach>

                <c:forEach items="${differencesEq}" var="compareResult">
                    <div class="compare-result stripped">
                        <table>
                            <c:forEach items="${compareResult}" var="item">
                                <c:if test="${item.type eq '='.charAt(0)}">
                                    <tr>
                                        <td>${item.firstIndex}</td>
                                        <td>${item.secondIndex}</td>
                                        <td>${item.row.replace("&","&amp;").replace("<","&lt;").replace(">", "&gt;")}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>
                </c:forEach>
                <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                <script type="text/javascript">
                    google.charts.load('current', {'packages': ['corechart']});
                    google.charts.setOnLoadCallback(drawChart);

                    function drawChart() {
                        var data = google.visualization.arrayToDataTable([
                            ['Age', 'Weight']
                            <c:forEach var = "i" begin = "0" end = "1">
                            <c:forEach var = "j" begin = "1" end = "${fn:length(coordinates[0]) - 1}">
                            ,[${coordinates[0][j]}, ${coordinates[1][j]}]
                            </c:forEach>
                            </c:forEach>
                        ]);

                        var options = {
                            title: 'Age vs. Weight comparison',
                            hAxis: {title: 'Age', minValue: -1, maxValue: 1},
                            vAxis: {title: 'Weight', minValue: -1, maxValue: 1},
                            legend: 'none'
                        };

                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));

                        chart.draw(data, options);
                    }
                </script>
                <div id="chart_div" style="width: 1000px; height: 1000px;"></div>
                <canvas id="canvas" style="max-height:700px;"></canvas>
                <script>
                    var color = Chart.helpers.color;
                    var scatterChartData = {
                        datasets: [{
                            label: 'My First dataset',
                            borderColor: 'rgba(255,99,132,1)',
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            data: [
                                <c:forEach var = "i" begin = "0" end = "1">
                                <c:forEach var = "j" begin = "1" end = "${fn:length(coordinates[0]) - 1}">
                                {
                                x: ${coordinates[0][j]},
                                y: ${coordinates[1][j]},
                                },
                            </c:forEach>

                            </c:forEach>
                            ]
                        }, {
                            label: 'My Second dataset',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            data: [
                                <c:forEach var = "i" begin = "0" end = "1">
                                    <c:forEach var = "j" begin = "1" end = "${fn:length(coordinates[0]) - 1}">
                                {
                                    x: ${coordinates[0][j]},
                                    y: ${coordinates[2][j]},
                                },
                                    </c:forEach>
                                </c:forEach>
                            ]
                        }]
                    };
                    window.onload = function() {
                        var ctx = document.getElementById('canvas').getContext('2d');
                        window.myScatter = Chart.Scatter(ctx, {
                            data: scatterChartData,
                            options: {
                                title: {
                                    display: true,
                                    text: 'Chart.js Scatter Chart'
                                },
                            }
                        });
                    };
                    document.getElementById('randomizeData').addEventListener('click', function() {
                        scatterChartData.datasets.forEach(function(dataset) {
                            dataset.data = dataset.data.map(function() {
                                return {
                                    x: randomScalingFactor(),
                                    y: randomScalingFactor()
                                };
                            });
                        });
                        window.myScatter.update();
                    });
                </script>
            </div>
        </div>
    </jsp:body>

</page:template>

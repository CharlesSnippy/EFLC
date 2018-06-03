<%@ page import="com.edu.services.parsing.ParsingServiceImpl" %>
<%@ page import="org.jsoup.nodes.Document" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=windows-1251" pageEncoding="windows-1251" %>
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

            .pages-container {
                overflow-y: scroll;
                height: 500px;
                margin: 10px;
            }
        </style>

        <div class="card mb-3">
            <div class="card-header">Латентно-семантический анализ</div>
            <div class="card-body">
                <a href="${page.url}">${page.title}</a>
                <p>${criterion.name} ${criterion.shortDescription}</p>



                <div class="pages-container">
                    <h4>Частота слов в документах</h4>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>N</th>
                            <c:forEach items="${documents}" var="item">
                                <th scope="col">d${documents.indexOf(item)}</th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="i" begin="0" end="${fn:length(wordFreqMatrix) - 1}">
                            <tr>
                                <td>
                                        ${words.get(i)}
                                </td>
                                <c:forEach var="j" begin="0" end="${fn:length(wordFreqMatrix[i]) - 1}">
                                    <td>
                                            ${wordFreqMatrix[i][j]}
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>


                <div class="pages-container">
                    <h4>Отношения "документы-темы"</h4>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>N</th>
                            <c:forEach var="i" begin="0" end="${fn:length(docCoordinates) - 1}">
                                <th scope="col">t${i}</th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="i" begin="0" end="${fn:length(docCoordinates) - 1}">
                            <tr>
                                <td>
                                        d${i}
                                </td>
                                <c:forEach var="j" begin="0" end="${fn:length(docCoordinates[i]) - 1}">
                                    <td>
                                            ${docCoordinates[i][j]}
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>


                <canvas id="canvas" style="max-height:700px;"></canvas>


                <script>
                    var color = Chart.helpers.color;
                    var scatterChartData = {
                        labels: [
                            <c:forEach var="i" begin="0" end="${fn:length(docCoordinates) - 1}">
                            "${documents.get(i)}",
                            </c:forEach>
                        ],
                        datasets: [{
                            label: 'Координаты критерия',
                            borderColor: 'rgba(255,99,132,1)',
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            data: [
                                <c:forEach var = "j" begin = "0" end = "${sizeCrit - 1}">
                                {
                                    x: ${docCoordinates[j][0]},
                                    y: ${docCoordinates[j][criterion.criteriaId]},
                                },
                                </c:forEach>
                            ]
                        }, {
                            label: 'Координаты документов',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            data: [
                                <c:forEach var = "j" begin = "${sizeCrit}" end = "${fn:length(docCoordinates) - 1}">
                                {
                                x: ${docCoordinates[j][0]},
                                y: ${docCoordinates[j][criterion.criteriaId]},
                                },
                                </c:forEach>
                                <%--{--%>
                                <%--x: ${docCoordinates[0][0]},--%>
                                <%--y: ${docCoordinates[0][i]},--%>
                                <%--},--%>
                            ]
                        }]
                    };
                </script>


                <script>
                    window.onload = function() {
                        var ctx = document.getElementById('canvas').getContext('2d');
                        window.myScatter = Chart.Scatter(ctx, {
                            data: scatterChartData,
                            options: {
                                title: {
                                    display: true,
                                    text: 'Точечная диаграмма'
                                },
                                scales: {
                                    xAxes:[{
                                        scaleLabel: {
                                            display: true,
                                            labelString: "x",
                                            fontColor: "black"
                                        }
                                    }],
                                    yAxes:[{
                                        scaleLabel: {
                                            display: true,
                                            labelString: "y",
                                            fontColor: "black"
                                        }
                                    }],
                                },
                                tooltips: {
                                    callbacks: {
                                        label: function(tooltipItem, data) {
                                            var datasetLabel = data.datasets[tooltipItem.datasetIndex].label || 'Other';
                                            var label = data.labels[tooltipItem.index];
                                            return datasetLabel + ': ' + label;
                                        }
                                    }
                                }
                            }
                        });
                    };
                </script>


            </div>
        </div>
    </jsp:body>

</page:template>

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
        </style>

        <div class="card mb-3">
            <div class="card-header">Латентно-семантический анализ</div>
            <div class="card-body">
                <a href="${page.url}">${page.title}</a>

                <c:forEach var="i" begin="1" end="${fn:length(wordCoordinates[0]) - 1}">
                    <h4>Документ и ${criteria.get(i - 1).name} ${criteria.get(i - 1).shortDescription}</h4>
                    <%--<c:forEach var = "k" begin = "0" end = "${fn:length(wordCoordinates) - 1}">--%>
                        <%--<p>${words[k]} ${wordCoordinates[k][0]} ${wordCoordinates[k][i]}</p>--%>
                    <%--</c:forEach>--%>
                <canvas id="canvas${i}" style="max-height:700px;"></canvas>
                <script>
                    var color = Chart.helpers.color;
                    var scatterChartData${i} = {
                        labels: ["Label 1","Label 2","Label 3"],
                        datasets: [{
                            label: 'Координаты слов',
                            borderColor: 'rgba(255,99,132,1)',
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            data: [

                                <c:forEach var = "j" begin = "0" end = "${fn:length(wordCoordinates) - 1}">
                                {
                                x: ${wordCoordinates[j][0]},
                                y: ${wordCoordinates[j][i]},
                                },
                            </c:forEach>

                            ]
                        }, {
                            label: 'Координаты тем',
                            borderColor: 'rgba(54, 235, 162, 1)',
                            backgroundColor: 'rgba(54, 235, 162, 0.2)',
                            data: [
                                {
                                    x: ${docCoordinates[0][0]},
                                    y: ${docCoordinates[0][i]},
                                },
                            ]
                        }, {
                            label: 'Координаты документов',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            data: [
                                <%--<c:forEach var = "j" begin = "0" end = "${fn:length(docCoordinates) - 1}">--%>
                                <%--{--%>
                                    <%--x: ${docCoordinates[j][0]},--%>
                                    <%--y: ${docCoordinates[j][i]},--%>
                                <%--},--%>
                                <%--</c:forEach>--%>
                                <%--{--%>
                                <%--x: ${docCoordinates[0][0]},--%>
                                <%--y: ${docCoordinates[0][i]},--%>
                                <%--},--%>
                                {
                                x: ${docCoordinates[i][0]},
                                y: ${docCoordinates[i][i]},
                                },
                            ]
                        }]
                    };

                </script>
                </c:forEach>
                <script>
                    window.onload = function() {
                        <c:forEach var="i" begin="1" end="${fn:length(wordCoordinates[0]) - 1}">
                        var ctx${i} = document.getElementById('canvas${i}').getContext('2d');
                        window.myScatter${i} = Chart.Scatter(ctx${i}, {
                            data: scatterChartData${i},
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
                        </c:forEach>
                    };
                </script>
            </div>
        </div>
    </jsp:body>

</page:template>

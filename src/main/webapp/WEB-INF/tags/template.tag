<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="title" fragment="true" %>
<html>
<head>
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EFLC</title>

    <spring:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" var="bootstrap"/>
    <spring:url value="/resources/vendor/font-awesome/css/font-awesome.min.css" var="font_awesome"/>
    <spring:url value="/resources/css/sb-admin.css" var="sb_admin"/>

    <spring:url value="/resources/vendor/jquery/jquery.min.js" var="jquery_js"/>
    <spring:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" var="bootstrap_js"/>
    <spring:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" var="jquery_easing_js"/>
    <spring:url value="/resources/js/sb-admin.min.js" var="sbadmin_js"/>

    <!-- Bootstrap core JavaScript-->
    <script src="${jquery_js}"></script>
    <script src="${bootstrap_js}"></script>
    <!-- Core plugin JavaScript-->
    <script src="${jquery_easing_js}"></script>
    <!-- Custom scripts for all pages-->
    <script src="${sbadmin_js}"></script>

    <!-- Bootstrap core CSS-->
    <link href="${bootstrap}" rel="stylesheet" type="text/css">

    <!-- Custom fonts for this template-->
    <link href="${font_awesome}" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="${sb_admin}" rel="stylesheet" type="text/css">

    <c:url value="/" var="index"/>
    <c:url value="/criteria" var="criteriaIndex"/>
    <c:url value="/compare" var="check"/>
    <c:url value="/site/create" var="siteCreate"/>

</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="${index}">EFLC</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav navbar-sidenav">
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link" href="${siteCreate}">
                    <span class="nav-link-text">Добавить сайт</span>
                </a>
            </li>
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link" href="${criteriaIndex}">
                    <span class="nav-link-text">Просмотреть критерии</span>
                </a>
            </li>
            <%--<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">--%>
            <%--<a class="nav-link" href="${compare}">--%>
            <%--<span class="nav-link-text">Compare pages</span>--%>
            <%--</a>--%>
            <%--</li>--%>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
                    <i class="fa fa-fw fa-sign-out"></i>Logout</a>
            </li>
        </ul>
    </div>
</nav>
<div class="content-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <jsp:doBody/>
            </div>
        </div>
    </div>
    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <%--<footer class="sticky-footer">--%>
    <%--<div class="container">--%>
    <%--<div class="text-center">--%>
    <%--<small>Copyright © Your Website 2018</small>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</footer>--%>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fa fa-angle-up"></i>
    </a>
    <!-- Logout Modal-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>


    <%--<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')" var="isUSer"/>--%>


    <%--<c:if test="${not isUSer}">--%>
    <%--<li style="padding-top: 15px; padding-bottom: 15px; color: red">--%>
    <%--<c:if test="${empty param.error}">--%>
    <%--Вы не вошли в приложение--%>
    <%--</c:if>--%>
    <%--</li>--%>
    <%--<li><a style="color: Green;" href="<c:url value="/login.html"/>">Login</a></li>--%>
    <%--</c:if>--%>


    <%--<c:if test="${isUSer}">--%>
    <%--<li style="padding-top: 15px; padding-bottom: 15px; color: green">--%>
    <%--Вы вошли как:--%>
    <%--<security:authentication property="principal.username"/> с ролью:--%>
    <%--<b><security:authentication property="principal.authorities"/></b>--%>

    <%--</li>--%>
    <%--<li><a style="color: red;" href="<c:url value="/j_spring_security_logout"/>">Logout</a></li>--%>
    <%--</c:if>    --%>

</div>
</body>

</html>
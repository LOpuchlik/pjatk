
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.css" rel="stylesheet" media="screen">

</head>
<body>

<div align="center">
    <h2>Welcome to car parlour</h2>
    <form action="request" method="get">

        <div>
            Select car type:&nbsp;
            <select name="type">
                <option value="none"></option>
                <c:forEach items="${types}" var="type">
                    <option value="${type}" <c:if test='${param.type == type}'> selected </c:if>>
                            ${type}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>
        </div>


        <div>
            Select manufacturer:&nbsp;
            <select name="brand">
                <option value="none"></option>
                <c:forEach items="${manufacturers}" var="manufacturer">
                    <option value="${manufacturer}"  >
                            ${manufacturer}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>
        </div>


        <div>
            Select model:&nbsp;
            <select name="model">
                <option value="none"></option>
                <c:forEach items="${models}" var="model">
                    <option value="${model}" >
                            ${model}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>
        </div>


        <div>
            Select fuel type:&nbsp;
            <select name="engine">
                <option value="none"></option>
                <c:forEach items="${engines}" var="engine">
                    <option value="${engine}"  >
                            ${engine}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>
        </div>


        <input type="submit" value="SEND" />
        <input type="reset" value="CLEAR" />

    </form>

</div>

<div align="center">
<TABLE class="table">

    <thead>
    <tr>
        <td>type</td>
        <td>manufacturer</td>
        <td>model</td>
        <td>engine</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cars}" var="car">
    <tr>
        <td><c:out value="${car.type}"/></td>
        <td><c:out value="${car.manufacturer}"/></td>
        <td><c:out value="${car.model}"/></td>
        <td><c:out value="${car.engine}"/></td>
    </tr>
    </c:forEach>
    </tbody>
</TABLE>
</div>
</body>

</html>
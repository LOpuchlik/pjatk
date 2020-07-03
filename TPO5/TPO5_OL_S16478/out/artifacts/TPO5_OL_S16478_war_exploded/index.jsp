
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Index</title>
    <link rel='stylesheet' type='text/css' href='styles.css'>
</head>
<body>

<div align="center">
    <h2>Welcome to car parlour</h2>
    <form action="request" method="get">
        <fieldset>
            <legend>Type</legend>
        <div>
            <select name="type">
                <option value="none"></option>
                <c:forEach items="${types}" var="type">
                    <option value="${type}" >
                            ${type}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>
        </div>
        </fieldset>

        <fieldset>
            <legend>Manufacturer</legend>
        <div>
            <select name="manufacturer">
                <option value="none"></option>
                <c:forEach items="${manufacturers}" var="manufacturer">
                    <option value="${manufacturer}"  >
                            ${manufacturer}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>
        </div>
        </fieldset>

        <fieldset>
            <legend>Model</legend>
        <div>
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
        </fieldset>

        <fieldset>
            <legend>Fuel type</legend>
        <div>
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
        </fieldset>
        <br/><br/>


        <input type="submit" value="SEND" />
        <input type="reset" value="CLEAR" />


    </form>

</div>

<div style="overflow-x:auto;">
    <table>
        <thead>
        <tr>
            <td>TYPE</td>
            <td>MANUFACTURER</td>
            <td>MODEL</td>
            <td>ENGINE</td>
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
    </table>
</div>
</body>
</html>
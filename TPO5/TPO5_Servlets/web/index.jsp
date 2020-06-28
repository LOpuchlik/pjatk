<%--
  Created by IntelliJ IDEA.
  User: jagoodka
  Date: 27/06/2020
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <style>
      fieldset {
        display: inline-block;
      }
    </style>
  </head>
  <body>
<h2> </h2>
  <!-- ==========    form    ========= -->
  <h2>Choose 1 and only 1 sorting attribute !!!!!</h2>
<br>
  <form method="get" action="request">
    <fieldset>
      <legend>Type of car</legend>
      <select name = "type">
        <option value="null"></option>
        <option value="cabrio">cabrio</option>
        <option value="offroad">offroad car</option>
        <option value="passengerCar">passenger car</option>
        <option value="pickUpTruck">pickup truck</option>
    </select>
    </fieldset>


    <fieldset>
      <legend>Brand of car</legend>
      <select name = "brand">
        <option value="null"></option>
        <option value="audi">audi</option>
        <option value="bmw">bmw</option>
        <option value="chevrolet">chevrolet</option>
        <option value="ford">ford</option>
        <option value="honda">honda</option>
        <option value="jeep">jeep</option>
        <option value="landrover">land rover</option>
        <option value="mazda">mazda</option>
        <option value="mercedes">mercedes</option>
        <option value="mitsubishi">mitsubishi</option>
        <option value="toyota">toyota</option>
        <option value="vw">vw</option>
    </select>
    </fieldset>


    <fieldset>
      <legend>Model of car</legend>
      <select name = "model">
        <option value="null"></option>
        <option value="a5">a5</option>
        <option value="a6">a6</option>
        <option value="cherokee">cherokee</option>
        <option value="civic">civic</option>
        <option value="colorado">colorado</option>
        <option value="defender">defender</option>
        <option value="discovery">discovery</option>
        <option value="D-Max">D-Max</option>
        <option value="focus">focus</option>
        <option value="gclass">G-class</option>
        <option value="mondeo">mondeo</option>
        <option value="mustang">mustang</option>
        <option value="mx5">mx5</option>
        <option value="pajero">pajero</option>
        <option value="passat">passat</option>
        <option value="ranger">ranger</option>
        <option value="raptor">raptor</option>
        <option value="silverado">silverado</option>
        <option value="tacoma">tacoma</option>
        <option value="triton">triton</option>
        <option value="wrangler">wrangler</option>
        <option value="z4">z4</option>
    </select>
    </fieldset>


    <fieldset>
      <legend>Year of production</legend>
      <select name = "year">
        <option value="0"></option>
      <option value="1999">1999</option>
      <option value="2005">2005</option>
      <option value="2008">2008</option>
      <option value="2010">2010</option>
      <option value="2012">2012</option>
      <option value="2015">2015</option>
      <option value="2016">2016</option>
      <option value="2018">2018</option>
      <option value="2019">2019</option>
      <option value="2020">2020</option>
    </select>
    </fieldset>


    <fieldset>
      <legend>Type of engine</legend>
      <select name = "engine">
        <option value="null"></option>
        <option value="diesel">diesel</option>
        <option value="gasoline">gasoline</option>
      </select>
    </fieldset>


<!--
    <a href='/request'>BACK</a>
    -->
    <input type="submit" value="SEND" />
    <input type="reset" value="CLEAR" />
  </form>
  </body>
</html>

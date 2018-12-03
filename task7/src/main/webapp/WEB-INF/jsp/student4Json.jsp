<%@ page contentType="application/json;charset=UTF-8" language="java" isErrorPage="true" isELIgnored="false"
   import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<json:object escapeXml="false">
<json:property name="code" value="${code}"> </json:property>
<json:property name="message">
<spring:message code="${code}"/>
</json:property>
<json:array name="data" items="${student4List}" var="student4">
<json:object>
<json:property name ="name" value="${student4.name}"/>
<json:property name ="img" value="${student4.img}"/>
<json:property name ="resume" value="${student4.resume}"/>
<json:property name ="graduateAt" value="${student4.graduateAt}"/>
</json:object>
</json:array>
</json:object>


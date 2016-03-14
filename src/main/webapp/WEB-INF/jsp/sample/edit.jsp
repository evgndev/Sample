<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    String backURL = ParamUtil.getString(request, "backURL");

    String title = "edit";
%>
<liferay-ui:header title="<%= title %>" backURL="<%= backURL %>" showBackURL="true"/>

<portlet:actionURL name="saveCat" var="saveCatURL">
    <portlet:param name="action" value="saveCat"/>
</portlet:actionURL>

<aui:form name="cat"
          method="post"
          modelAttribute="cat"
          action="<%= saveCatURL %>"
>

    <aui:input name="name"
               placeholder="cat name"
               value=""
    />

    <aui:button-row>
        <aui:button type="submit"/>
    </aui:button-row>
</aui:form>
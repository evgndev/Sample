<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.domain.Cat" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    String backURL = ParamUtil.getString(request, "backURL");

    List<Cat> cats = (List<Cat>)request.getAttribute("cats");

    String title = "view";
%>
<liferay-ui:header title="<%= title %>" backURL="<%= backURL %>" showBackURL="true"/>

<portlet:renderURL var="editURL">
    <portlet:param name="mvcPath" value="sample/edit"/>
</portlet:renderURL>
<aui:a href="<%= editURL %>">edit</aui:a>

<%-- Order table --%>
<liferay-ui:search-container
        emptyResultsMessage="there-are-no-cats"
        delta="20"
>

    <liferay-ui:search-container-results>
        <%
            total = cats.size();
            results = cats;

            pageContext.setAttribute("results", results);
            pageContext.setAttribute("total", total);
        %>
    </liferay-ui:search-container-results>

    <liferay-ui:search-container-row
            className="org.evgndev.sample.domain.Cat"
            keyProperty="catId"
            modelVar="cat"
            rowVar="row"
    >

        <%-- Identificator--%>
        <liferay-ui:search-container-column-text
                name="id"
                value="<%= String.valueOf(cat.getCatId()) %>"
        />

        <%-- Name--%>
        <liferay-ui:search-container-column-text
                name="name"
                value="<%= String.valueOf(cat.getName()) %>"
        />
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator searchContainer="<%= searchContainer %>"/>
</liferay-ui:search-container>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.Form" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="com.google.common.base.Joiner" %>
<%@ page import="org.evgndev.sample.dto.FormDto" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    List<FormDto> forms = (List<FormDto>)request.getAttribute("forms");
    Long count = (Long)request.getAttribute("count");

    String orderByCol = ParamUtil.getString(request, ORDER_BY_COL, "formId");
    String orderByType = ParamUtil.getString(request, ORDER_BY_TYPE, "asc");

%>

<%-- table --%>
<liferay-ui:search-container
        emptyResultsMessage="there-are-no-forms"
        orderByCol="<%= orderByCol %>"
        orderByType="<%= orderByType %>"
>

    <liferay-ui:search-container-results>
        <%
            total = count.intValue();
            results = forms;

            pageContext.setAttribute("results", results);
            pageContext.setAttribute("total", total);
        %>
    </liferay-ui:search-container-results>

    <liferay-ui:search-container-row
            className="org.evgndev.sample.dto.FormDto"
            keyProperty="formId"
            modelVar="form"
            rowVar="row"
    >

        <%-- Identificator--%>
        <liferay-ui:search-container-column-text name="formId" orderable="true"/>

        <%-- Name --%>
        <liferay-ui:search-container-column-text name="name" orderable="true"/>

        <%-- Date --%>
        <liferay-ui:search-container-column-text name="updateDate" orderable="true"/>

        <%-- Type --%>
       <liferay-ui:search-container-column-text name="formTypeName"/>

        <%-- Category --%>
      <liferay-ui:search-container-column-text name="formCategoryNames"/>

    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator searchContainer="<%= searchContainer %>"/>
</liferay-ui:search-container>
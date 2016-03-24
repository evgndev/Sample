<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.Form" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="com.google.common.base.Joiner" %>
<%@ page import="org.evgndev.sample.dto.FormDto" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    List<FormDto> forms = (List<FormDto>) request.getAttribute("forms");
    Long count = (Long) request.getAttribute("count");

    String orderByCol = ParamUtil.getString(request, ORDER_BY_COL);
    String orderByType = ParamUtil.getString(request, ORDER_BY_TYPE);
%>

<%-- table --%>
<liferay-ui:search-container
        emptyResultsMessage="form.noForms"
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
        <%-- Name --%>
        <liferay-ui:search-container-column-text property="name"
                                                 orderableProperty="name"
                                                 name="form.table.name"
                                                 orderable="true"
        />

        <%-- Date --%>
        <liferay-ui:search-container-column-text property="formDate"
                                                 orderableProperty="formDate"
                                                 name="form.table.formDate"
                                                 orderable="true"
        />

        <%-- Type --%>
        <liferay-ui:search-container-column-text property="formTypeName"
                                                 orderableProperty="formTypeName"
                                                 name="form.table.formTypeName"
        />

        <%-- Category --%>
        <liferay-ui:search-container-column-text property="formCategoryNames"
                                                 orderableProperty="formCategoryNames"
                                                 name="form.table.formCategoryNames"
        />
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator searchContainer="<%= searchContainer %>"/>
</liferay-ui:search-container>
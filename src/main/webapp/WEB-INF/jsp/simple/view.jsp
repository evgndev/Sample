<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.Form" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="com.google.common.base.Joiner" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    String backURL = ParamUtil.getString(request, "backURL");

    List<Form> forms = (List<Form>)request.getAttribute("forms");

    String title = "view";
%>
<liferay-ui:header title="<%= title %>" backURL="<%= backURL %>" showBackURL="true"/>

<portlet:renderURL var="editURL">
    <portlet:param name="mvcPath" value="sample/edit"/>
</portlet:renderURL>
<aui:a href="<%= editURL %>">edit</aui:a>

<%-- table --%>
<liferay-ui:search-container
        emptyResultsMessage="there-are-no-forms"
        delta="20"
>

    <liferay-ui:search-container-results>
        <%
            total = forms.size();
            results = forms;

            pageContext.setAttribute("results", results);
            pageContext.setAttribute("total", total);
        %>
    </liferay-ui:search-container-results>

    <liferay-ui:search-container-row
            className="org.evgndev.sample.model.Form"
            keyProperty="formId"
            modelVar="form"
            rowVar="row"
    >

        <%-- Identificator--%>
        <liferay-ui:search-container-column-text
                name="id"
                value="<%= String.valueOf(form.getFormId()) %>"
        />

        <%-- Name--%>
        <liferay-ui:search-container-column-text name="name"/>

        <%-- Type --%>
        <%
            String formTypeName = "";
            if (form.getFormType() != null) {
                formTypeName = form.getFormType().getName();
            }
        %>
        <liferay-ui:search-container-column-text name="formType" value="<%= formTypeName %>"/>

        <%-- Category --%>
        <%

            String categoryNamesStr = "";
            Set<FormCategory> formCategories = form.getFormCategory();
            if (formCategories != null && !formCategories.isEmpty()) {
                Set<String> categoryNames = new HashSet<String>();

                for (FormCategory category : formCategories) {
                    categoryNames.add(category.getName());
                }

                categoryNamesStr = Joiner.on(", ").join(categoryNames);
            }
        %>
        <liferay-ui:search-container-column-text name="category" value="<%= categoryNamesStr %>"/>

    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator searchContainer="<%= searchContainer %>"/>
</liferay-ui:search-container>
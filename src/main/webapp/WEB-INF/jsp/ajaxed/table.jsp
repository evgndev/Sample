<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.Form" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="com.google.common.base.Joiner" %>
<%@ page import="org.evgndev.sample.dto.FormDto" %>
<%@ page import="org.evgndev.sample.controller.PortletViewController" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    List<FormDto> forms = (List<FormDto>) request.getAttribute("forms");
    Long count = (Long) request.getAttribute("count");

    int delta = ParamUtil.getInteger(request, DELTA, 10);
    String orderByCol = ParamUtil.getString(request, ORDER_BY_COL, "formId");
    String orderByType = ParamUtil.getString(request, ORDER_BY_TYPE, "desc");
%>

<%-- table --%>
<liferay-ui:search-container
        id="formTableSearchContainer"
        emptyResultsMessage="form.noForms"
        delta="<%= delta %>"
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

        <%-- Action --%>
        <liferay-ui:search-container-column-text name="action">
            <portlet:renderURL var="editURL">
                <portlet:param name="mvcPath" value='<%= PortletViewController.JSP_EDIT %>'/>
                <portlet:param name="<%= PortletViewController.FORM_ID %>" value='<%= form.getFormId() %>'/>
            </portlet:renderURL>
            <aui:a href="<%= editURL %>"><liferay-ui:message key="form.table.edit"/></aui:a>
            <br>
            <portlet:actionURL var="removeURL" name="removeForm">
                <portlet:param name="<%= PortletViewController.FORM_ID %>" value='<%= form.getFormId() %>'/>
                <portlet:param name="action" value="removeForm"/>
            </portlet:actionURL>
            <aui:a href="<%= removeURL %>"><liferay-ui:message key="form.table.remove"/></aui:a>
        </liferay-ui:search-container-column-text>
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator searchContainer="<%= searchContainer %>"/>
</liferay-ui:search-container>

<script type="text/javascript">


    AUI().ready('liferay-portlet-url', function () {
        tableJSP.updateLinks();
    });

    var tableJSP = (function () {
        var my = {};

        function _updateLink(a) {
            var url = a.href;

            var delta = getParameterByName(getNamespace() + 'delta', url);
            var cur = getParameterByName(getNamespace() + 'cur', url);
            var orderByCol = getParameterByName(getNamespace() + 'orderByCol', url);
            var orderByType = getParameterByName(getNamespace() + 'orderByType', url);

            if(!orderByCol || orderByCol == 'null') {
                orderByCol = '';
            }

            if(!orderByType || orderByType == 'null') {
                orderByType = '';
            }

            a.href = "javascript:view.reloadData(" + delta + ", " + cur + ", '" + orderByCol + "','" + orderByType + "');";
        }

        my.updateLinks = function () {
            var $searchContainerDiv = $(viewJSP.getPlaceholderSelector());
                                                     // ignore row actions
            var links = $searchContainerDiv.find("a").not("tbody tr td a");

            links.each(function (index, a) {
                _updateLink(a);
            });
        };

        return my;
    }());
</script>
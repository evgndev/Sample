<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.FormType" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="org.evgndev.sample.controller.PortletViewController" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%--Filter panel--%>
<aui:form name="filterForm" cssClass="form-inline withBottomSpacer5">

    <%-- Name --%>
    <aui:input name="<%= PortletViewController.FILTER_NAME %>"
               type="text"
               label=""
               cssClass="w200 withoutBottomSpacer"
               inlineField="true"
               placeholder="form.name"
               value=""
    />

    <%--Form type--%>
    <aui:select name="<%= PortletViewController.FILTER_FORM_TYPE_ID %>"
                inlineField="true"
                label=""
    >
        <aui:option label="form.formTypeName" value="0"/>
        <%
            List<FormType> formTypes = (List<FormType>)renderRequest.getAttribute("formTypes");
            for (FormType formType : formTypes) {
        %>
        <aui:option label="<%= formType.getName() %>" value="<%= formType.getFormTypeId() %>"/>
        <%
            }
        %>
    </aui:select>

    <%--Form category--%>
    <aui:select name="<%= PortletViewController.FILTER_FORM_CATEGORY_ID %>"
                inlineField="true"
                label=""
    >
        <aui:option label="form.formCategoryNames" value="0"/>
        <%
            List<FormCategory> formCategories = (List<FormCategory>)renderRequest.getAttribute("formCategories");
            for (FormCategory category : formCategories) {
        %>
        <aui:option label="<%= category.getName() %>" value="<%= category.getFormCategoryId() %>"/>
        <%
            }
        %>
    </aui:select>

    <%-- Filter btn --%>
    <aui:button name="applyFilter"
                value="search"
                inlineField="true"
                cssClass="inlineBtn btn-primary"
                onClick="javascript:view.applyFilter();"
    />
    <aui:button name="clear"
                value="clear"
                cssClass="inlineBtn"
                onClick="javascript:view.clearFilters();"
    />
</aui:form>


<script type="text/javascript">

    AUI().ready('liferay-portlet-url', function () {
        view.loadData();
    });

    /**
     *  filters, creation, table
     */
    var view = (function () {
        var view = {};

        function _td() {

        }

        /**
         * Public methods
         */
        view.loadData = function () {
            var renderUrl = createResourceURL(getPortletName(), getPlid());

            load(renderUrl, '<%= PortletViewController.FILTER_NAME %>', true);
            load(renderUrl, '<%= PortletViewController.FILTER_FORM_TYPE_ID %>');
            load(renderUrl, '<%= PortletViewController.FILTER_FORM_CATEGORY_ID %>');

            renderUrl.setParameter("delta", viewJSP.getDelta());
            renderUrl.setParameter("cur", viewJSP.getCur());
            renderUrl.setParameter("orderByCol", viewJSP.getOrderByCol());
            renderUrl.setParameter("orderByType", viewJSP.getOrderByType());

            include(viewJSP.getPlaceholderSelector(), renderUrl, viewJSP.getTableCmd());
        };

        view.applyFilter = function() {
            showMask(viewJSP.getPlaceholderSelector());

            var renderUrl = createResourceURL(getPortletName(), getPlid());

            store(renderUrl, '<%= PortletViewController.FILTER_NAME %>', true);
            store(renderUrl, '<%= PortletViewController.FILTER_FORM_TYPE_ID %>');
            store(renderUrl, '<%= PortletViewController.FILTER_FORM_CATEGORY_ID %>');

            renderUrl.setParameter("delta", viewJSP.getDelta());

            include(viewJSP.getPlaceholderSelector(), renderUrl, viewJSP.getTableCmd());
        };

        view.clearFilters = function() {
            showMask(viewJSP.getPlaceholderSelector());

            var renderUrl = createResourceURL(getPortletName(), getPlid());

            clear(renderUrl, '<%= PortletViewController.FILTER_NAME %>', true);
            clear(renderUrl, '<%= PortletViewController.FILTER_FORM_TYPE_ID %>');
            clear(renderUrl, '<%= PortletViewController.FILTER_FORM_CATEGORY_ID %>');

            renderUrl.setParameter("delta", viewJSP.getDelta());

            include(viewJSP.getPlaceholderSelector(), renderUrl, viewJSP.getTableCmd());
        };

        return view;
    }());
</script>
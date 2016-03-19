<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.Form" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="com.google.common.base.Joiner" %>
<%@ page import="org.evgndev.sample.controller.PortletViewController" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    String backURL = ParamUtil.getString(request, "backURL");
    String title = "view";

    long delta = ParamUtil.getLong(request, DELTA, 20);
    long cur = ParamUtil.getLong(request, CUR, 1);
%>
<liferay-ui:header title="<%= title %>" backURL="<%= backURL %>" showBackURL="true"/>

<portlet:renderURL var="editURL">
    <portlet:param name="mvcPath" value='<%= PortletViewController.JSP_EDIT %>'/>
</portlet:renderURL>
<aui:a href="<%= editURL %>">edit</aui:a>

<%-- table.jsp include here--%>
<div class="tablePlaceholder" id="<portlet:namespace/>tablePlaceholder">
    <div class="loading-animation"></div>
</div>

<script type="text/javascript">

    AUI().ready('liferay-portlet-url', function () {
        view.loadData();
    });

    /**
     *  Provide fields and constants. Only getters.
     *  NOTE: Define js behavior in /js/
     */
    var viewJSP = (function () {
        var my = {};

        my.getPlaceholderSelector = function () {
            return '#<portlet:namespace/>tablePlaceholder';
        };

        my.getTableCmd = function () {
            <%--return '<%= PortletViewController.CMD_TABLE %>';--%>
            return 'getTableJSP';
        };

        my.getDelta = function () {
            return '<%= delta %>';
        };

        my.getCur = function () {
            return '<%= cur %>';
        };

        return my;
    }());

    /**
     * Order view: filters, creation, table
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

            renderUrl.setParameter("delta", viewJSP.getDelta());
            renderUrl.setParameter("cur", viewJSP.getCur());

            include(viewJSP.getPlaceholderSelector(), renderUrl, viewJSP.getTableCmd());
        };

        view.applyFilter = function() {
            showMask(viewJSP.getPlaceholderSelector());

            var renderUrl = createResourceURL(getPortletName(), getPlid());

            renderUrl.setParameter("delta", viewJSP.getDelta());

            include(viewJSP.getPlaceholderSelector(), renderUrl, viewJSP.getTableCmd());
        };

        view.clearFilters = function() {
            showMask(viewJSP.getPlaceholderSelector());

            var renderUrl = createResourceURL(getPortletName(), getPlid());

            renderUrl.setParameter("delta", viewJSP.getDelta());

            include(viewJSP.getPlaceholderSelector(), renderUrl, viewJSP.getTableCmd());
        };

        return view;
    }());
</script>
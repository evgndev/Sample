<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.controller.PortletViewController" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    long delta = ParamUtil.getLong(request, DELTA, 20);
    long cur = ParamUtil.getLong(request, CUR, 1);
    String getOrderByCol = ParamUtil.getString(request, ORDER_BY_COL, "formId");
    String getOrderByType = ParamUtil.getString(request, ORDER_BY_TYPE, "desc");
%>

<portlet:renderURL var="editURL">
    <portlet:param name="mvcPath" value='<%= PortletViewController.JSP_EDIT %>'/>
</portlet:renderURL>
<div class="withBottomSpacer15">
    <aui:a href="<%= editURL %>"><liferay-ui:message key="form.createForm"/></aui:a>
</div>

<liferay-util:include page="/WEB-INF/jsp/ajaxed/filter.jsp" servletContext="<%= session.getServletContext() %>"/>

<%-- table.jsp include here--%>
<div class="tablePlaceholder" id="<portlet:namespace/>tablePlaceholder">
    <div class="loading-animation"></div>
</div>

<script type="text/javascript">

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

        my.getOrderByCol = function () {
            return '<%= getOrderByCol %>';
        };

        my.getOrderByType = function () {
            return '<%= getOrderByType %>';
        };

        return my;
    }());
</script>
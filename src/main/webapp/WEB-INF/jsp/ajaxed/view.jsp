<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.controller.PortletViewController" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

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
            return my.delta;
        };

        my.getCur = function () {
            return my.cur;
        };

        my.getOrderByCol = function () {
            return my.orderByCol;
        };

        my.getOrderByType = function () {
            return my.orderByType;
        };

        my.setDelta = function (delta) {
            my.delta = delta;
        };

        my.setCur = function (cur) {
            my.cur = cur;
        };

        my.setOrderByCol = function (orderByCol) {
            my.orderByCol = orderByCol;
        };

        my.setOrderByType = function (orderByType) {
            my.orderByType = orderByType;
        };

        return my;
    }());
</script>
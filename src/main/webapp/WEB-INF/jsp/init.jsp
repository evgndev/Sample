<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="javax.portlet.*" %>
<%@ page import="com.liferay.util.mail.*" %>
<%@ page import="com.liferay.mail.service.*" %>
<%@ page import="com.liferay.portlet.*" %>
<%@ page import="com.liferay.portal.util.*" %>
<%@ page import="com.liferay.portal.model.*" %>
<%@ page import="com.liferay.portal.theme.*" %>
<%@ page import="com.liferay.portal.kernel.util.*" %>
<%@ page import="com.liferay.portal.kernel.dao.orm.*"%>
<%@ page import="com.liferay.portal.kernel.dao.search.*" %>
<%@ page import="com.liferay.portal.kernel.portlet.*" %>
<%@ page import="com.liferay.portal.kernel.servlet.*" %>
<%@ page import="com.liferay.portal.kernel.workflow.*" %>
<%@ page import="com.liferay.portal.kernel.repository.model.*" %>
<%@ page import="com.liferay.portal.service.*" %>
<%@ page import="com.liferay.portal.service.persistence.*" %>
<%@ page import="com.liferay.portal.security.permission.*" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.*" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.*" %>
<%@ page import="com.liferay.portal.kernel.log.Log" %>
<%@ page import="com.liferay.portal.kernel.log.LogFactoryUtil" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%--<script src="<c:url value="/js/jquery-1.8.3.js"/>" type="text/javascript"></script>--%>
<script src="<c:url value="/js/main.js"/>" type="text/javascript"></script>

<%!
    static final String CUR = "cur";
    static final String DELTA = "delta";
    static final String ORDER_BY_TYPE = "orderByType";
    static final String ORDER_BY_COL = "orderByCol";
    static final String JSP = "jspPage";
    static final String IS_VIEW = "isView";
    static final String AJAX_JSP = "ajaxJsp";
    static final String BACK_URL = "backURL";
%>
<%
    final String portletFullName = portletDisplay.getNamespace()
            .substring(1, portletDisplay.getNamespace().length() - 1);

    String jspPath = request.getPathInfo();
    final Log log = LogFactoryUtil.getLog(jspPath.replace(".jsp", "_jsp"));
%>

<%-- General js with jsp tags--%>
<script type="text/javascript">

    /**
     * Provide portlet namespace
     * @returns string portlet:namespace
     */
    function getNamespace() {
        return "<portlet:namespace/>";
    }

    function getPortletName() {
        return "<%= portletFullName %>";
    }

    function getPlid() {
        return "<%= plid %>";
    }
</script>

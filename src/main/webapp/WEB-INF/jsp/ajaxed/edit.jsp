<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="org.evgndev.sample.model.FormType" %>
<%@ page import="org.evgndev.sample.model.FormCategory" %>
<%@ page import="org.evgndev.sample.*" %>
<%@ page import="org.evgndev.sample.DateUtil" %>

<%@include file="/WEB-INF/jsp/init.jsp" %>

<%
    String backURL = ParamUtil.getString(request, "backURL", "ajaxed/view");
%>
<liferay-ui:header title="form.creation.title" backURL="<%= backURL %>" showBackURL="true"/>

<portlet:actionURL name="saveForm" var="saveFormURL">
    <portlet:param name="action" value="saveForm"/>
</portlet:actionURL>

<aui:form name="formForm"
          method="post"
          modelAttribute="form"
          action="<%= saveFormURL %>"
>

    <aui:input name="name"
               label="form.name"
               placeholder=""
               required="true"
               value=""
    />

    <aui:input name="description"
               type="textarea"
               label="form.description"
               placeholder=""
               value=""
    />

    <aui:input name="formDate"
               label="form.formDate"
               placeholder=""
               value="<%= DateUtil.toViewDateFormatString(new Date()) %>"
               type="text"
               cssClass="date"
    />

    <aui:select name="formType.formTypeId" label="form.formTypeName">
        <%
            List<FormType> formTypes = (List<FormType>)renderRequest.getAttribute("formTypes");
            for (FormType formType : formTypes) {
        %>
        <aui:option label="<%= formType.getName() %>" value="<%= formType.getFormTypeId() %>"/>
        <%
            }
        %>
    </aui:select>

    <aui:select name="formCategory" multiple="true" label="form.formCategoryNames">
        <%
            List<FormCategory> formCategories = (List<FormCategory>)renderRequest.getAttribute("formCategories");
            for (FormCategory category : formCategories) {
        %>
        <aui:option label="<%= category.getName() %>" value="<%= category.getFormCategoryId() %>"/>
        <%
            }
        %>
    </aui:select>

    <aui:button-row>
        <aui:button type="submit" value="save"/>
    </aui:button-row>
</aui:form>
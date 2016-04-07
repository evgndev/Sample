/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.evgndev.sample.controller;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.liferay.portal.kernel.util.ParamUtil;
import org.evgndev.sample.DateUtil;
import org.evgndev.sample.dto.FormDto;
import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;
import org.evgndev.sample.model.FormType;
import org.evgndev.sample.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

    private static final Logger log = LoggerFactory.getLogger(PortletViewController.class.getName());

    public static final String CMD_TABLE = "cmdTable";
    public static final String JSP_VIEW = "ajaxed/view";
    public static final String JSP_TABLE = "ajaxed/table";
    public static final String JSP_EDIT = "ajaxed/edit";

    public static final String FORM_ID = "formId";
    public static final String FORM = "form";

    public static final String FILTER_NAME = "filterName";
    public static final String FILTER_FORM_TYPE_ID = "filterFormTypeId";
    public static final String FILTER_FORM_CATEGORY_ID = "filterFormCategoryId";

    @Autowired
    private FormService formService;

    @RenderMapping
    public String question(RenderRequest request, Model model) {
        String page = ParamUtil.getString(request, "mvcPath", JSP_VIEW);

        long formId = ParamUtil.getLong(request, PortletViewController.FORM_ID);
        model.addAttribute(FORM, formService.getForm(formId));

        List<FormType> formTypes = formService.getFormTypes();
        model.addAttribute("formTypes", formTypes);

        List<FormCategory> formCategories = formService.getFormCategories();
        model.addAttribute("formCategories", formCategories);

        return page;
    }

    @ResourceMapping
    public String getTableJSP(ResourceRequest request, ModelMap model) {

        int cur = ParamUtil.getInteger(request, "cur", 1);
        int delta = ParamUtil.getInteger(request, "delta", 10);
        String getOrderByCol = ParamUtil.getString(request, "orderByCol", "formId");
        String getOrderByType = ParamUtil.getString(request, "orderByType", "desc");

        String filterName = ParamUtil.getString(request, FILTER_NAME);
        Long filterFormTypeId = ParamUtil.getLong(request, FILTER_FORM_TYPE_ID);
        Long filterFormCategoryId = ParamUtil.getLong(request, FILTER_FORM_CATEGORY_ID);

        List<FormDto> forms = formService.getFormDtoList(
                filterName,
                filterFormTypeId,
                filterFormCategoryId,
                cur, delta,
                getOrderByCol, getOrderByType
        );

        long count = formService.getFormsCount(
                filterName,
                filterFormTypeId,
                filterFormCategoryId
        );

        model.addAttribute("forms", forms);
        model.addAttribute("count", count);

        model.addAttribute("cur", cur);
        model.addAttribute("delta", delta);
        model.addAttribute("orderByCol", getOrderByCol);
        model.addAttribute("orderByType", getOrderByType);

        return JSP_TABLE;
    }

    @ActionMapping(params = "action=saveForm")
    public void saveForm(ActionRequest actionRequest,
                         ActionResponse actionResponse,
                         Model model,
                         @ModelAttribute("form") Form form) throws Exception {

        form.setRemoved(false);
        formService.updateForm(form);
    }


    @ActionMapping(params = "action=removeForm")
    public void removeForm(ActionRequest request,
                           ActionResponse response,
                           Model model) throws Exception {

        long formId = ParamUtil.getLong(request, PortletViewController.FORM_ID);
        Form form = formService.getForm(formId);
        form.setRemoved(true);
        formService.updateForm(form);

    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(
                Set.class,
                "formCategory",
                new CustomCollectionEditor(Set.class) {
                    protected Object convertElement(Object element) {
                        if (element instanceof String) {
                            return formService.getFormCategory(Long.parseLong(element.toString()));
                        }
                        return null;
                    }
                });

        DateFormat dateFormat = DateUtil.getViewDateFormat();
        binder.registerCustomEditor(
                Date.class,
                "formDate",
                new CustomDateEditor(dateFormat, false) {
                    @Override
                    public void setAsText(String text) throws IllegalArgumentException {
                        if (!Strings.isNullOrEmpty(text) && text.contains(",")) {
                            // text example: "123123123, 30.12.2011"
                            text = Splitter.on(",").splitToList(text).get(1);
                        }
                        super.setAsText(text);
                    }
                }
        );
    }
}
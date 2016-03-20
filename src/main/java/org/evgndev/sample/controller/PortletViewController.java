/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.evgndev.sample.controller;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import org.evgndev.sample.dto.FormDto;
import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;
import org.evgndev.sample.model.FormType;
import org.evgndev.sample.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

	private static final Logger log = LoggerFactory.getLogger(PortletViewController.class.getName());
	
	public static final String CMD_TABLE = "cmdTable";
	public static final String JSP_VIEW = "ajaxed/view";
	public static final String JSP_TABLE = "ajaxed/table";
	public static final String JSP_EDIT= "ajaxed/edit";


	@Autowired
	private FormService formService;

	@RenderMapping
	public String question(RenderRequest request, Model model) {

		String page = ParamUtil.getString(request,"mvcPath", JSP_VIEW);

		if (page.equals(JSP_VIEW)) {
			int cur = ParamUtil.getInteger(request,"cur", 0);
			int delta = ParamUtil.getInteger(request,"delta", 20);

//			List<Form> forms = formService.getFormDtoList(cur, delta);
//			model.addAttribute("forms", forms);
		} else if (page.equals(JSP_EDIT)) {
			List<FormType> formTypes = formService.getFormTypes();
			model.addAttribute("formTypes", formTypes);

			List<FormCategory> formCategories = formService.getFormCategories();
			model.addAttribute("formCategories", formCategories);
		}

		model.addAttribute("releaseInfo", ReleaseInfo.getReleaseInfo());
		return page;
	}


	@ResourceMapping
	public String getTableJSP(ResourceRequest request, ModelMap model) {

		int cur = ParamUtil.getInteger(request,"cur", 1);
		int delta = ParamUtil.getInteger(request,"delta", 20);
		String getOrderByCol = ParamUtil.getString(request, "orderByCol", "formId");
		String getOrderByType = ParamUtil.getString(request, "orderByType", "asc");

		List<FormDto> forms = formService.getFormDtoList(cur, delta,getOrderByCol, getOrderByType);
		long count = formService.getFormsCount();

		model.addAttribute("forms", forms);
		model.addAttribute("count", count);

		return JSP_TABLE;
	}

	@ActionMapping(params = "action=saveForm")
	public void saveForm(ActionRequest actionRequest,
						 ActionResponse actionResponse,
						 Model model,
						 @ModelAttribute("form") Form form,
						 BindingResult result) throws Exception {

		log.info("form: " + form.getName());
		formService.addForm(form);
		log.info("form added");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception{
		binder.registerCustomEditor(Set.class,"formCategory", new CustomCollectionEditor(Set.class){
			protected Object convertElement(Object element){
				if (element instanceof String) {
					FormCategory formCategory = formService.getFormCategory(Long.parseLong(element.toString()));

					return formCategory;
				}
				return null;
			}
		});
	}

}
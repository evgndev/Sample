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
import org.evgndev.sample.domain.Cat;
import org.evgndev.sample.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

	private static final Logger log = LoggerFactory.getLogger(PortletViewController.class.getName());

	@Autowired
	private CatService catService;

	@RenderMapping
	public String question(RenderRequest request, Model model) {

		String page = ParamUtil.getString(request,"mvcPath", "sample/view");

		if (page.equals("sample/view")) {
			List<Cat> cats = new ArrayList<Cat>();

			Cat cat1 = new Cat();
			cat1.setCatId(1L);
			cat1.setName("Catty");
			cats.add(cat1);

			Cat cat2 = new Cat();
			cat2.setCatId(2L);
			cat2.setName("Natty");
			cats.add(cat2);

			model.addAttribute("cats", cats);
		}

		model.addAttribute("releaseInfo", ReleaseInfo.getReleaseInfo());
		return page;
	}

	@ActionMapping(params = "action=saveCat")
	public void saveCat(ActionRequest actionRequest,
						 ActionResponse actionResponse,
						 Model model,
						 @ModelAttribute("cat") Cat cat,
						 BindingResult result) throws Exception {

		log.info("cat: " + cat.getName());
		catService.addCat(cat);
		log.info("cat added");
	}
}
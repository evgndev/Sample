package org.evgndev.sample.service;

import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;
import org.evgndev.sample.model.FormType;
import org.evgndev.sample.repository.FormCategoryRepository;
import org.evgndev.sample.repository.FormRepository;
import org.evgndev.sample.repository.FormTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("formService")
@Transactional
public class FormServiceImpl implements FormService {

    private static final Logger log = LoggerFactory.getLogger(FormServiceImpl.class.getName());

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormTypeRepository formTypeRepository;

    @Autowired
    private FormCategoryRepository formCategoryRepository;

    /**
     * Add a new form
     */
    public void addForm(Form form) {
        formRepository.save(form);
    }

    public List<Form> getForms() {
        return formRepository.findAll();
    }

    public List<FormType> getFormTypes(){
        return formTypeRepository.findAll();
    }

    public List<FormCategory> getFormCategories() {
        return formCategoryRepository.findAll();
    }

    public FormCategory getFormCategory(long formCategoryId) {
        return formCategoryRepository.findByFormCategoryId(formCategoryId);
    }
}

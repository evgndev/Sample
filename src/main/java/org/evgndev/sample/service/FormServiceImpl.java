package org.evgndev.sample.service;

import org.evgndev.sample.dao.FormRepository;
import org.evgndev.sample.domain.Form;
import org.evgndev.sample.domain.FormCategory;
import org.evgndev.sample.domain.FormType;
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

    /**
     * Add a new form
     */
    public void addForm(Form form) {
        formRepository.addForm(form);
    }

    public List<Form> getForms() {
        return formRepository.getForms();
    }

    public List<FormType> getFormTypes(){
        return formRepository.getFormTypes();
    }

    public List<FormCategory> getFormCategories() {
        return formRepository.getFormCategories();
    }

    public FormCategory getFormCategory(long formCategoryId) {
        return formRepository.getFormCategory(formCategoryId);
    }
}

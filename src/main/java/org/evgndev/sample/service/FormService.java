package org.evgndev.sample.service;

import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;
import org.evgndev.sample.model.FormType;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormService {
    void addForm(Form form);
    List<Form> getForms();
    List<FormType> getFormTypes();
    List<FormCategory> getFormCategories();
    FormCategory getFormCategory(long formCategoryId);
}

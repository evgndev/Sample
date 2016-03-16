package org.evgndev.sample.dao;

import org.evgndev.sample.domain.Form;
import org.evgndev.sample.domain.FormCategory;
import org.evgndev.sample.domain.FormType;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormRepository {
    void addForm(Form form);
    List<Form> getForms();
    List<FormType> getFormTypes();
    List<FormCategory> getFormCategories();
    FormCategory getFormCategory(long formCategoryId);
}

package org.evgndev.sample.service;

import org.evgndev.sample.dto.FormDto;
import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;
import org.evgndev.sample.model.FormType;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormService {
    void addForm(Form form);
    List<FormDto> getFormDtoList(int start, int size, String getOrderByCol, String getOrderByType);
    List<FormType> getFormTypes();
    List<FormCategory> getFormCategories();
    FormCategory getFormCategory(long formCategoryId);
    long getFormsCount();
}

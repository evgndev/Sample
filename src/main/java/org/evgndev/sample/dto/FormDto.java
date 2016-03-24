package org.evgndev.sample.dto;

import com.google.common.base.Joiner;
import org.evgndev.sample.DateUtil;
import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;

import java.util.*;

/**
 * @author Evgeny Krysenko
 */
public class FormDto {
    private String formId;
    private String name;
    private String description;
    private String formDate;
    private String formTypeName;
    private String formCategoryNames;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String updateDate) {
        this.formDate = updateDate;
    }

    public String getFormTypeName() {
        return formTypeName;
    }

    public void setFormTypeName(String formTypeName) {
        this.formTypeName = formTypeName;
    }

    public String getFormCategoryNames() {
        return formCategoryNames;
    }

    public void setFormCategoryNames(String formCategoryNames) {
        this.formCategoryNames = formCategoryNames;
    }

    public static List<FormDto> getFormDtoList(List<Form> formList)  {
        List<FormDto> formDtoList = new ArrayList<FormDto>(formList.size());

        for (Form form : formList) {
            FormDto formDto = new FormDto();

            formDto.setFormId(String.valueOf(form.getFormId()));
            formDto.setName(form.getName());
            formDto.setFormTypeName(form.getFormType().getName());

            Date formDate = form.getFormDate();
            if (formDate != null) {
                formDto.setFormDate(DateUtil.toViewDateFormatString(formDate));
            }

            String categoryNamesStr = "";
            Set<FormCategory> formCategories = form.getFormCategory();
            if (formCategories != null && !formCategories.isEmpty()) {
                Set<String> categoryNames = new HashSet<String>();
                for (FormCategory category : formCategories) {
                    categoryNames.add(category.getName());
                }
                categoryNamesStr = Joiner.on(", ").join(categoryNames);
            }
            formDto.setFormCategoryNames(categoryNamesStr);

            formDtoList.add(formDto);
        }

        return formDtoList;
    }
}

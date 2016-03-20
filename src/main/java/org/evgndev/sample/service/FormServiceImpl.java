package org.evgndev.sample.service;

import org.evgndev.sample.dto.FormDto;
import org.evgndev.sample.model.Form;
import org.evgndev.sample.model.FormCategory;
import org.evgndev.sample.model.FormType;
import org.evgndev.sample.repository.FormCategoryRepository;
import org.evgndev.sample.repository.FormRepository;
import org.evgndev.sample.repository.FormTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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

        form.setCreateDate(new Date());
        form.setUpdateDate(new Date());

        formRepository.saveAndFlush(form);
    }

    public List<FormDto> getFormDtoList(int start, int size, String orderByCol, String orderByType) {

        PageRequest pageRequest = new PageRequest(
                start - 1,
                size,
                Sort.Direction.fromStringOrNull(orderByType),
                orderByCol
        );

        List<Form> formList = formRepository.findAll(pageRequest).getContent();

        return FormDto.getFormDtoList(formList);
    }

    @Override
    public long getFormsCount() {
        return formRepository.count();
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

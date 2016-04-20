package org.evgndev.sample.service;

import com.google.common.base.Strings;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import org.evgndev.sample.FileUtil;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.*;

@Service("formService")
@Transactional
public class FormService {

    private static final Logger log = LoggerFactory.getLogger(FormService.class.getName());

    public static final String FOLDER_MESSAGE_ATTACHMENT = "sample_form_attachments";

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormTypeRepository formTypeRepository;

    @Autowired
    private FormCategoryRepository formCategoryRepository;

    /**
     * Add a new form
     * @param form
     * @param files null to ignore
     */
    public void updateForm(Form form, Map<String, FileEntry> files, List<String> errors) throws Exception {

        form.setCreateDate(new Date());
        form.setUpdateDate(new Date());

        if (files != null) {
            updateFiles(files, form, errors);
        }

        formRepository.saveAndFlush(form);
    }

    private static void updateFiles(Map<String, FileEntry> files,
                                    Form form,
                                    List<String> errors) throws Exception {

        Set<Long> fileIds = new HashSet<Long>(files.size());
        for (String fileName : files.keySet()) {
            try {
                FileEntry fileEntry = files.get(fileName);

                InputStream fileAsStream = DLFileEntryLocalServiceUtil.getFileAsStream(
                        fileEntry.getUserId(),
                        fileEntry.getFileEntryId(),
                        fileEntry.getVersion()
                );

                Long size = fileEntry.getSize();
                String rndFileName = UUID.randomUUID().toString() + "_" + fileName;
                String entryName = String.valueOf(form.getFormId());

                long fileId = FileUtil.addFileToLibrary(
                        fileAsStream,
                        size.intValue(),
                        rndFileName,
                        fileName,
                        entryName,
                        FOLDER_MESSAGE_ATTACHMENT
                );

                fileIds.add(fileId);

            } catch (Exception e) {
                errors.add("messaging.error.cannotSaveFile$" + fileName);
                throw new Exception("Cannot save attached file " + fileName, e);
            }
        }

        form.setFileEntryIds(fileIds);
    }


    public Form getForm(long formId) {
        return formRepository.findOne(formId);
    }

    public List<FormDto> getFormDtoList(String filterName,
                                        long filterFormTypeId,
                                        long filterFormCategoryId,
                                        int start, int size, String orderByCol, String orderByType) {

        PageRequest pageRequest = new PageRequest(
                start - 1,
                size,
                Sort.Direction.fromStringOrNull(orderByType),
                orderByCol
        );

        Specification<Form> specification = getFormSpecification(
                filterName,
                filterFormTypeId,
                filterFormCategoryId
        );

        List<Form> formList = formRepository.findAll(specification, pageRequest).getContent();

        return FormDto.getFormDtoList(formList);
    }


    public long getFormsCount(String filterName,
                              long filterFormTypeId,
                              long filterFormCategoryId) {

        Specification<Form> specification = getFormSpecification(
                filterName,
                filterFormTypeId,
                filterFormCategoryId
        );
        return formRepository.count(specification);
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

    private Specification<Form> getFormSpecification(final String filterName,
                                                     final long filterFormTypeId,
                                                     final long filterFormCategoryId) {

        return new Specification<Form>() {
            @Override
            public Predicate toPredicate(Root<Form> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                Predicate predicate = builder.conjunction();

                if (!Strings.isNullOrEmpty(filterName)) {

                    Predicate namePredicate = builder.like(
                            builder.lower(root.<String>get("name")),
                            "%" + filterName.toLowerCase() + "%"
                    );
                    predicate = builder.and(predicate, namePredicate);
                }

                if (filterFormTypeId > 0) {

                    Predicate formTypePredicate = builder.equal(
                            root.<FormType>get("formType").get("formTypeId"),
                            filterFormTypeId
                    );
                    predicate = builder.and(predicate, formTypePredicate);
                }

                if (filterFormCategoryId > 0) {
                    Join join = root.join("formCategory");
                    Predicate formCategoryPredicate = join.get("formCategoryId").in(filterFormCategoryId);
                    predicate = builder.and(predicate, formCategoryPredicate);
                }

                predicate = builder.and(predicate, builder.isFalse(root.<Boolean>get("removed")));

                return predicate;
            }
        };
    }
}

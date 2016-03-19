package org.evgndev.sample.repository;

import org.evgndev.sample.model.FormCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormCategoryRepository extends  CrudRepository<FormCategory, Long> {
    List<FormCategory> findAll();
    FormCategory findByFormCategoryId(long formCategoryId);
}

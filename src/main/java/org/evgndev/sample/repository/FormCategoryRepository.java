package org.evgndev.sample.repository;

import org.evgndev.sample.model.FormCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormCategoryRepository extends JpaRepository<FormCategory, Long> {
    List<FormCategory> findAll();
    FormCategory findByFormCategoryId(long formCategoryId);
}

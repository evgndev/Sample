package org.evgndev.sample.repository;

import org.evgndev.sample.model.FormType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormTypeRepository extends  CrudRepository<FormType, Long> {
    List<FormType> findAll();
}

package org.evgndev.sample.repository;

import org.evgndev.sample.model.Form;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormRepository extends  CrudRepository<Form, Long> {
    List<Form> findAll();
}

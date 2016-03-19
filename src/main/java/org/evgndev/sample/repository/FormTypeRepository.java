package org.evgndev.sample.repository;

import org.evgndev.sample.model.FormType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
public interface FormTypeRepository extends JpaRepository<FormType, Long> {
    List<FormType> findAll();
}

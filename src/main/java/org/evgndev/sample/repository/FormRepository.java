package org.evgndev.sample.repository;

import org.evgndev.sample.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Evgeny Krysenko
 */
public interface FormRepository extends JpaRepository<Form, Long>, JpaSpecificationExecutor<Form> {

}

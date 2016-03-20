package org.evgndev.sample.repository;

import org.evgndev.sample.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Evgeny Krysenko
 */
public interface FormRepository extends JpaRepository<Form, Long> {
   // List<Form> findAll(Pageable pageable);
}

package org.evgndev.sample.model;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Evgeny Krysenko
 */

@Entity
@Table(name = "sample_formType")
public class FormType implements Serializable {

    private static final long serialVersionUID = -3061644797237282851L;

    @Id
    @GeneratedValue
    private Long formTypeId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(Long formTypeId) {
        this.formTypeId = formTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormType formType = (FormType) o;
        return Objects.equal(formTypeId, formType.formTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(formTypeId);
    }
}



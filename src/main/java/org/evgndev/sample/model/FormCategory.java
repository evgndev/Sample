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
@Table(name = "sample_formCategory")
public class FormCategory implements Serializable {

    private static final long serialVersionUID = -7795322906059784136L;

    @Id
    @GeneratedValue
    private Long formCategoryId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFormCategoryId() {
        return formCategoryId;
    }

    public void setFormCategoryId(Long formTypeId) {
        this.formCategoryId = formTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormCategory that = (FormCategory) o;
        return Objects.equal(formCategoryId, that.formCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(formCategoryId);
    }
}



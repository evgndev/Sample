package org.evgndev.sample.model;

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
}



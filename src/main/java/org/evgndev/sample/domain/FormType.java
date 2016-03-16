package org.evgndev.sample.domain;

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
}



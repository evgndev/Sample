package org.evgndev.sample.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Evgeny Krysenko
 */

@Entity
@Table(name = "sample_form")
public class Form implements Serializable {

    private static final long serialVersionUID = 664165945401274625L;

    @Id
    @GeneratedValue
    private Long formId;

    private String name;

    private Date formDate;

    @ManyToOne
    @JoinColumn(name = "formTypeId")
    private FormType formType;

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name="sample_form_formCategory",
            joinColumns={@JoinColumn(name="FORM_ID")},
            inverseJoinColumns={@JoinColumn(name="FORM_CATEGORY_ID")})

    private Set<FormCategory> formCategory;

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public Set<FormCategory> getFormCategory() {
        return formCategory;
    }

    public void setFormCategory(Set<FormCategory> formCategory) {
        this.formCategory = formCategory;
    }

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }
}



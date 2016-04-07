package org.evgndev.sample.model;

import com.google.common.base.Objects;

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
    private String description;

    @Temporal(TemporalType.DATE)
    private Date formDate;

    @ManyToOne
    @JoinColumn(name = "formTypeId")
    private FormType formType;

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name="sample_form_formCategory",
            joinColumns={@JoinColumn(name="FORM_ID")},
            inverseJoinColumns={@JoinColumn(name="FORM_CATEGORY_ID")})

    private Set<FormCategory> formCategory;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private Boolean removed;

    /*
    Getter and setter
     */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return Objects.equal(formId, form.formId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(formId);
    }
}



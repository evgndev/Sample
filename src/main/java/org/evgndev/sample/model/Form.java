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

    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "formTypeId")
    private FormType formType;

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name="sample_form_formCategory",
            joinColumns={@JoinColumn(name="FORM_ID")},
            inverseJoinColumns={@JoinColumn(name="FORM_CATEGORY_ID")})

    private Set<FormCategory> formCategory;

    /*
    Getter and setter
     */
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
}



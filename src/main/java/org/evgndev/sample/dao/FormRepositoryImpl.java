package org.evgndev.sample.dao;

import org.evgndev.sample.domain.Form;
import org.evgndev.sample.domain.FormCategory;
import org.evgndev.sample.domain.FormType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Evgeny Krysenko
 */
@Repository("formRepository")
public class FormRepositoryImpl implements FormRepository {

    private static final Logger log = LoggerFactory.getLogger(FormRepositoryImpl.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    public void addForm(Form form) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Save
        session.save(form);
    }

    public List<Form> getForms() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Form").list();
    }

    public List<FormType> getFormTypes() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from FormType").list();
    }

    @Override
    public List<FormCategory> getFormCategories() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from FormCategory").list();
    }

    public FormCategory getFormCategory(long formCategoryId) {
        Session session = sessionFactory.getCurrentSession();

        List<FormCategory> list = session.createQuery("from FormCategory fc where fc.formCategoryId = :formCategoryId")
                .setParameter("formCategoryId", formCategoryId).list();

        return list.size() > 0 ? (FormCategory) list.get(0) : null;
    }


}

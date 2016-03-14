package org.evgndev.sample.dao;

import org.evgndev.sample.domain.Cat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Evgeny Krysenko
 */
@Repository("catRepository")
public class CatRepositoryImpl implements CatRepository {

    private static final Logger log = LoggerFactory.getLogger(CatRepositoryImpl.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    public void addCat(Cat cat){

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Save
        session.save(cat);
    }

    public List<Cat> getCats(Cat cat){
        Session session = sessionFactory.getCurrentSession();
        //session.qet

        return null;
    }
}

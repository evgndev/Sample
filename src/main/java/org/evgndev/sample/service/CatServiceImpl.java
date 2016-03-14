package org.evgndev.sample.service;

import org.evgndev.sample.dao.CatRepository;
import org.evgndev.sample.domain.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catService")
@Transactional
public class CatServiceImpl implements CatService {

    private static final Logger log = LoggerFactory.getLogger(CatServiceImpl.class.getName());

    @Autowired
    private CatRepository catRepository;

    /**
     * Adds a new cat
     */
    public void addCat(Cat cat) {
        catRepository.addCat(cat);
    }
}

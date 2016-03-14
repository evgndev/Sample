package org.evgndev.sample.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Evgeny Krysenko
 */

@Entity
@Table(name = "sample_cat")
public class Cat implements Serializable {

    private static final long serialVersionUID = -7985925024881791648L;

    @Id
    @GeneratedValue
    private Long catId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }
}



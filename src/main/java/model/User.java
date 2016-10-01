package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by MSI on 2016-10-01.
 */
@Entity(name = "User")
public class User implements Serializable {

    private static final long serialVersionUID = 2215718172665511717L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;

    public User() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


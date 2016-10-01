package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MSI on 2016-10-01.
 */
@Entity(name = "Application")
public class Application implements Serializable {

    private static final long serialVersionUID = 5504646324911838309L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "seconds")
    Double seconds;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    User userId;
    @Column(name = "title")
    private String title;

    public Application() { }

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

    public Double getSeconds() {
        return seconds;
    }

    public void setSeconds(Double seconds) {
        this.seconds = seconds;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

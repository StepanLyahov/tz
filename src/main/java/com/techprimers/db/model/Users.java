package com.techprimers.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "secondname")
    private String secondname;


    @Column(name = "email")
    private String email;

    @Column(name = "uriImage")
    private String uriImage;

    @Column(name = "status")
    private String status;

    @Column(name = "timeChange")
    private Date timeChange;

    public Users() { }

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

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(Date timeChange) {
        this.timeChange = timeChange;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondname='" + secondname + '\'' +
                ", email='" + email + '\'' +
                ", uriImage='" + uriImage + '\'' +
                ", status='" + status + '\'' +
                ", timeChange=" + timeChange;
    }

}

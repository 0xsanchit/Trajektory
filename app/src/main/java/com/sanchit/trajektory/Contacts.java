package com.sanchit.trajektory;

public class Contacts {

    public String name,email,image,isAdmin,isMentor,location,uid,standard;

    public Contacts() {
    }

    public Contacts(String name, String email, String image, String isAdmin, String isMentor, String location, String uid, String standard) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.isAdmin = isAdmin;
        this.isMentor = isMentor;
        this.location = location;
        this.uid = uid;
        this.standard = standard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsMentor() {
        return isMentor;
    }

    public void setIsMentor(String isMentor) {
        this.isMentor = isMentor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}

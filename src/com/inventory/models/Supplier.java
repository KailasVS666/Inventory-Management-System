package com.inventory.models;

import java.io.Serializable;

public class Supplier implements Serializable {
    private String id;
    private String name;
    private String contactInfo;
    
    public Supplier(String id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    @Override
    public String toString() {
        return String.format("Supplier{id='%s', name='%s', contactInfo='%s'}", 
                           id, name, contactInfo);
    }
}

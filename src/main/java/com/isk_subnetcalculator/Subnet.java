/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isk_subnetcalculator;

import java.util.Objects;
import javafx.beans.property.*;

/**
 *
 * @author rekombinator
 */
public class Subnet implements Comparable<Subnet>{
    
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty neededSize = new SimpleIntegerProperty();
    private SimpleIntegerProperty allocatedSize = new SimpleIntegerProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleStringProperty mask = new SimpleStringProperty();
    private SimpleStringProperty decMask = new SimpleStringProperty();
    private SimpleStringProperty range = new SimpleStringProperty();
    private SimpleStringProperty broadcast = new SimpleStringProperty();
    private SimpleStringProperty capacity = new SimpleStringProperty();
    
    public Subnet(){
        
    }
    
    public Subnet(String name, Integer neededSize) {
        this.name.setValue(name);
        this.neededSize.setValue(neededSize);
    }
    

    public Subnet(SimpleStringProperty name, 
            SimpleIntegerProperty neededSize, 
            SimpleIntegerProperty allocatedSize, 
            SimpleStringProperty address, 
            SimpleStringProperty mask, 
            SimpleStringProperty decMask, 
            SimpleStringProperty range, 
            SimpleStringProperty broadcast) {
        this.name = name;
        this.neededSize = neededSize;
        this.allocatedSize = allocatedSize;
        this.address = address;
        this.mask = mask;
        this.decMask = decMask;
        this.range = range;
        this.broadcast = broadcast;
    }
    
    
        
    public String getName() {
        return name.getValue();
    }

    public int getNeededSize() {
        return neededSize.getValue();
    }

    public int getAllocatedSize() {
        return allocatedSize.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getMask() {
        return mask.getValue();
    }

    public String getDecMask() {
        return decMask.getValue();
    }

    public String getRange() {
        return range.getValue();
    }

    public String getBroadcast() {
        return broadcast.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setNeededSize(int neededSize) {
        this.neededSize.setValue(neededSize);
    }

    public void setAllocatedSize(int allocatedSize) {
        this.allocatedSize.setValue(allocatedSize);
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    public void setMask(String mask) {
        this.mask.setValue(mask);
    }

    public void setDecMask(String decMask) {
        this.decMask.setValue(decMask);
    }

    public void setRange(String range) {
        this.range.setValue(range);
    }

    public void setBroadcast(String broadcast) {
        this.broadcast.setValue(broadcast);
    }
    
    public void setCapacity(String capacity) {
        this.capacity.setValue(capacity);
    }

    public String getCapacity() {
        return capacity.getValue();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subnet other = (Subnet) obj;
        if (!Objects.equals(this.name.getValue(), other.name.getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Subnet t) {
        return this.neededSize.getValue().compareTo(t.neededSize.getValue());
    }
    
        
    
    }

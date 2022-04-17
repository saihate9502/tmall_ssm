package com.saihate.tmall.service;

import java.util.List;

import com.saihate.tmall.pojo.Product;
import com.saihate.tmall.pojo.PropertyValue;

public interface PropertyValueService {
    void init(Product p);
    void update(PropertyValue pv);
 
    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}
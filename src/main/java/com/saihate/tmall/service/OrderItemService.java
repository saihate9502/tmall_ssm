package com.saihate.tmall.service;
  
import java.util.List;
 
import com.saihate.tmall.pojo.Order;
import com.saihate.tmall.pojo.OrderItem;
 
public interface OrderItemService {
      
    void add(OrderItem c); 
    void delete(int id);
    void update(OrderItem c);
    OrderItem get(int id);
    List list();
    void fill(List<Order> os);
    void fill(Order o);    
    int getSaleCount(int  pid);
    List<OrderItem> listByUser(int uid);
 
}
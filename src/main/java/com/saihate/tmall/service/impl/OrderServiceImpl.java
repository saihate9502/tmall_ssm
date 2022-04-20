package com.saihate.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.saihate.tmall.mapper.OrderMapper;
import com.saihate.tmall.pojo.Order;
import com.saihate.tmall.pojo.OrderExample;
import com.saihate.tmall.pojo.OrderItem;
import com.saihate.tmall.pojo.User;
import com.saihate.tmall.service.OrderItemService;
import com.saihate.tmall.service.OrderService;
import com.saihate.tmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper; 
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
 
    @Override
    public void add(Order c) {
        orderMapper.insert(c);
    }
 
    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }
 
    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }
 
    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }
 
    public List<Order> list(){
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result =orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }
    public void setUser(List<Order> os){
        for (Order o : os)
            setUser(o);
    }
    public void setUser(Order o){
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
 
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);
        for (OrderItem oi: ois) {
            oi.setOid(o.getId());
			orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

    @Override
    public List list(int uid, String excludedStatus) {
        OrderExample example =new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }
}
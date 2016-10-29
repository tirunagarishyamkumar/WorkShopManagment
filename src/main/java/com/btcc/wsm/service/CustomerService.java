package com.btcc.wsm.service;

import com.btcc.wsm.model.Customer;

import java.util.List;

/**
 * Created by siva on 29/9/2016.
 */
public interface CustomerService {

    Customer create(Customer customer);
    Customer update(Customer customer);
    Customer findSingle(int id);
    List<Customer> findAll();
    Customer delete(Customer customer);

}

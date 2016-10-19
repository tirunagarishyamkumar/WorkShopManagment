package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.Customer;
import com.btcc.wsm.repository.CustomerRepository;
import com.btcc.wsm.service.CustomerService;
import com.btcc.wsm.web.managedbean.LoginAuthenticationBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by siva on 29/9/2016.
 */


public class CustomerServiceImpl implements CustomerService {

    final static Logger logger = LogManager.getLogger(CustomerServiceImpl.class);


    @Resource
    private CustomerRepository customerRepository;


    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findSingle(int id) {
        return customerRepository.findOne(id);
    }

    public List<Customer> findAll() {
        return (List<Customer> )customerRepository.findAll();
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }
}

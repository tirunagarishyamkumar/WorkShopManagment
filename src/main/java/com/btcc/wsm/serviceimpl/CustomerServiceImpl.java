package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.Customer;
import com.btcc.wsm.model.Users;
import com.btcc.wsm.repository.CustomerRepository;
import com.btcc.wsm.service.CustomerService;
import com.btcc.wsm.util.FacesUtil;
import com.btcc.wsm.web.managedbean.LoginAuthenticationBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by siva on 29/9/2016.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    final static Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Resource
    private CustomerRepository customerRepository;


    public Customer create(Customer customer) {
        logger.info("Entred Into CustomerServiceImpl.create() "+customer);
        customer.setCreationTime(new Date());
        Customer customerFromDb=customerRepository.save(customer);
        logger.info("Exit From CustomerServiceImpl.create() "+customerFromDb);
        return customerFromDb;
    }

    public Customer update(Customer customer) {
        logger.info("Entred Into CustomerServiceImpl.update() "+customer);
        customer.setUpdateTime(new Date());
        Customer customerFromDb=customerRepository.save(customer);
        logger.info("Exit From CustomerServiceImpl.update() "+customerFromDb);
        return customerFromDb;
    }

    public Customer findSingle(int id) {
        return customerRepository.findOne(id);
    }

    public List<Customer> findAll() {
        return (List<Customer> )customerRepository.findAllByIsDeletedOrderByCreationTimeDesc(false);
    }

    public Customer delete(Customer customer) {
        logger.info("Entred Into CustomerServiceImpl.delete() "+customer);
        customer.setUpdateTime(new Date());
        customer.setDeleted(true);
        Customer customerFromDb=customerRepository.save(customer);
        logger.info("Entred Into CustomerServiceImpl.delete() "+customer);
        return  customerFromDb;
    }
}

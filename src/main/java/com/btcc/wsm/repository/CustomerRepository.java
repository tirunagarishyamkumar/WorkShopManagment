package com.btcc.wsm.repository;

import com.btcc.wsm.model.Customer;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by siva on 1/10/2016.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}

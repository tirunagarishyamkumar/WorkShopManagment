package com.btcc.wsm.web.managedbean;

import com.btcc.wsm.model.Customer;
import com.btcc.wsm.model.SystemAuditTrail;
import com.btcc.wsm.service.CustomerService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.serviceimpl.CustomerServiceImpl;
import com.btcc.wsm.web.datamodel.CustomerDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */


@ManagedBean
@Component
@ViewScoped
public class CustomerManagedBean implements MainManagedBean {

    final static Logger logger = LogManager.getLogger(CustomerManagedBean.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SystemAuditTrailRecordService auditTrailRecordService;

    private Customer customer = new Customer();
    private List<Customer> customerList;
    private Customer selectedCustomer = new Customer();
    private CustomerDataModel customerDataModel;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        if(customerList == null){
            customerList = customerService.findAll();
        }
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public CustomerDataModel getCustomerDataModel() {
        return new CustomerDataModel(getCustomerList());
    }

    public void setCustomerDataModel(CustomerDataModel customerDataModel) {
        this.customerDataModel = customerDataModel;
    }

    public void create() {

    }

    public void update() {

    }

    public void delete() {

    }
}

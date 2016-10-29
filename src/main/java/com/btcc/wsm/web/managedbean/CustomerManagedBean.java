package com.btcc.wsm.web.managedbean;

import com.btcc.wsm.model.Customer;
import com.btcc.wsm.model.Item;
import com.btcc.wsm.model.SystemAuditTrail;
import com.btcc.wsm.model.Users;
import com.btcc.wsm.service.CustomerService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.serviceimpl.CustomerServiceImpl;
import com.btcc.wsm.util.FacesUtil;
import com.btcc.wsm.web.datamodel.CustomerDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */


@ManagedBean
@Component
@ViewScoped
public class CustomerManagedBean implements Serializable {

    final static Logger logger = LogManager.getLogger(CustomerManagedBean.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SystemAuditTrailRecordService auditTrailRecordService;
    private Users loggedInUser;
    private Customer newCustomer = new Customer();
    private List<Customer> customerList;
    private Customer selectedCustomer = new Customer();
    private CustomerDataModel customerDataModel;

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer customer) {
        this.newCustomer = customer;
    }

    public List<Customer> getCustomerList() {
        //if(customerList == null){
        customerList = customerService.findAll();
        //}
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
    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Users loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public String create() {
        Users loggedInUser = (Users) FacesUtil
                .getSessionMapValue("LOGGEDIN_USER");
        newCustomer.setCreatedBy(loggedInUser.getUsername());
        Customer customer=customerService.create(newCustomer);
        if(customer!=null){
            newCustomer=new Customer();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : New Customer Created Successfully"));

        }else{
            newCustomer=new Customer();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR : Customer Not Created Successfully"));
        }
        return "listcustomer";
    }

    public void update() {
        Users loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
        selectedCustomer.setUpdatedBy(loggedInUser.getUsername());
        Customer customer=customerService.update(selectedCustomer);
        if(customer!=null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : Customer "+customer.getName()+" Has Been Updated Successfully"));
        }
    }

    public void delete() {
        Users loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
        selectedCustomer.setUpdatedBy(loggedInUser.getUsername());
        Customer customer=customerService.delete(selectedCustomer);
        if(customer!=null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : Customer "+customer.getName()+" Has Been Deleted Successfully"));
        }
    }

    public void showDialogue() {

        RequestContext.getCurrentInstance().execute(
                "PF('newCustomerDialog').show()");
        return;

    }

    public void onRowSelect(SelectEvent event) {
        setSelectedCustomer((Customer) event.getObject());
        RequestContext.getCurrentInstance().execute("PF('customerDialog').show()");
    }

    public void reset(){
        newCustomer=new Customer();
        selectedCustomer=new Customer();
    }

    public void navigateToCreateCustomer(){

        ConfigurableNavigationHandler nh = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        nh.performNavigation("createcustomer?faces-redirect=true");

    }
}

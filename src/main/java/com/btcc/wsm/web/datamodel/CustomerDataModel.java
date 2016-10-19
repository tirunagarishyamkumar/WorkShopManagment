package com.btcc.wsm.web.datamodel;

import com.btcc.wsm.model.AccessRights;
import com.btcc.wsm.model.Customer;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */
public class CustomerDataModel extends ListDataModel<Customer> implements
        SelectableDataModel<Customer> {

    public CustomerDataModel() {
    }

    public CustomerDataModel(List<Customer> list) {
        super(list);
    }

    public Object getRowKey(Customer object) {

        return object.getId();
    }
    @SuppressWarnings("unchecked")
    public Customer getRowData(String rowKey) {
        List<Customer> customerList = (List<Customer>) getWrappedData();
        Integer rowKeyInt = Integer.parseInt(rowKey);
        for (Customer customer : customerList) {
            if (customer.getId() == rowKeyInt) {
                return customer;
            }
        }
        return null;
    }


}

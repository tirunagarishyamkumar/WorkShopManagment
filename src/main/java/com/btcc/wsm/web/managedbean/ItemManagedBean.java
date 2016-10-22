package com.btcc.wsm.web.managedbean;

import com.btcc.wsm.model.Item;
import com.btcc.wsm.service.ItemService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.web.datamodel.ItemDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
public class ItemManagedBean implements MainManagedBean{


    final static Logger logger = LogManager.getLogger(ItemManagedBean.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private SystemAuditTrailRecordService auditTrailRecordService;

    private Item newItem = new Item();
    private Item selectedItem = new Item();
    private List<Item> itemList;
    private ItemDataModel itemDataModel;

    public Item getNewItem() {
        return newItem;
    }

    public void setNewItem(Item newItem) {
        this.newItem = newItem;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getItemList() {
        if(itemList == null){
            itemService.findAll();
        }
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public ItemDataModel getItemDataModel() {
        return new ItemDataModel(getItemList());
    }

    public void setItemDataModel(ItemDataModel itemDataModel) {
        this.itemDataModel = itemDataModel;
    }

    public void create() {

    }

    public void update() {

    }

    public void delete() {

    }
}

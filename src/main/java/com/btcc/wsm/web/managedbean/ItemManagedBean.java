package com.btcc.wsm.web.managedbean;

import com.btcc.wsm.model.Item;
import com.btcc.wsm.model.Users;
import com.btcc.wsm.service.ItemService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.util.FacesUtil;
import com.btcc.wsm.web.datamodel.ItemDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

@Component
@ViewScoped
public class ItemManagedBean implements Serializable{

    private static final long serialVersionUID = 1L;
    final static Logger logger = LogManager.getLogger(ItemManagedBean.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private SystemAuditTrailRecordService auditTrailRecordService;

    private Users loggedInUser;

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
        //if(itemList == null){
        itemList= itemService.findAll();
        //}
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

    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Users loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public void create() {
        loggedInUser = (Users) FacesUtil
                .getSessionMapValue("LOGGEDIN_USER");
        newItem.setCreatedBy(loggedInUser.getUsername());
        Item item=itemService.create(newItem);
        if(item!=null){
            newItem=new Item();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : New Item Created Successfully"));
        }
    }

    public void update() {
        loggedInUser = (Users) FacesUtil
                .getSessionMapValue("LOGGEDIN_USER");
        selectedItem.setLastModifiedBy(loggedInUser.getUsername());
        selectedItem.setLastModifiedTime(new Date());
        Item item=itemService.update(selectedItem);
        if(item!=null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : Item "+item.getItemName()+" Has Been Updated Successfully"));
        }
    }

    public void delete() {
        loggedInUser = (Users) FacesUtil
                .getSessionMapValue("LOGGEDIN_USER");
        selectedItem.setLastModifiedBy(loggedInUser.getUsername());
        selectedItem.setLastModifiedTime(new Date());
        Item item=itemService.delete(selectedItem);
        if(item!=null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : Item "+item.getItemName()+" Has Been Deleted Successfully"));
        }
    }


    public void showDialogue() {

        RequestContext.getCurrentInstance().execute(
                "PF('newItemDialog').show()");
        return;

    }
    public void onRowSelect(SelectEvent event) {
        setSelectedItem((Item) event.getObject());
        RequestContext.getCurrentInstance().execute("PF('itemDialog').show()");
    }

    public void reset(){
        newItem=new Item();
        selectedItem=new Item();
    }
}

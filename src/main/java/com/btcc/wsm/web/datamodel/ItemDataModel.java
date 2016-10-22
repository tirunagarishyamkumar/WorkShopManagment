package com.btcc.wsm.web.datamodel;

import com.btcc.wsm.model.Item;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */
public class ItemDataModel extends ListDataModel<Item> implements
        SelectableDataModel<Item> {

    public ItemDataModel() {
    }

    public ItemDataModel(List<Item> list) {
        super(list);
    }

    public Object getRowKey(Item object) {

        return object.getId();
    }
    @SuppressWarnings("unchecked")
    public Item getRowData(String rowKey) {
        List<Item> itemList = (List<Item>) getWrappedData();
        Integer rowKeyInt = Integer.parseInt(rowKey);
        for (Item item : itemList) {
            if (item.getId() == rowKeyInt) {
                return item;
            }
        }
        return null;
    }
}

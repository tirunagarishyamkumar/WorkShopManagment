package com.btcc.wsm.service;

import com.btcc.wsm.model.Item;

import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */
public interface ItemService {

    Item create(Item item);
    Item update(Item item);
    Item findSingle(int id);
    List<Item> findAll();
    Item delete(Item item);
}

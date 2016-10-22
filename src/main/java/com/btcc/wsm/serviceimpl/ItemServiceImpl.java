package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.Item;
import com.btcc.wsm.repository.ItemRepository;
import com.btcc.wsm.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */
public class ItemServiceImpl implements ItemService{

    final static Logger logger = LogManager.getLogger(ItemServiceImpl.class);

    @Resource
    private ItemRepository itemRepository;

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public Item findSingle(int id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findAll() {
        return (List<Item>)itemRepository.findAll();
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }
}

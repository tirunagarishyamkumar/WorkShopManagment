package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.Item;
import com.btcc.wsm.repository.ItemRepository;
import com.btcc.wsm.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */
@Service
public class ItemServiceImpl implements ItemService{

    final static Logger logger = LogManager.getLogger(ItemServiceImpl.class);


    @Resource
    private ItemRepository itemRepository;

    public Item create(Item item) {
        item.setCreationTime(new Date());
        return itemRepository.save(item);
    }

    public Item update(Item item) {

        return itemRepository.save(item);
    }

    public Item findSingle(int id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findAll() {

        return itemRepository.findAllByIsDeletedOrderByCreationTimeDesc(false);
    }

    public Item delete(Item item) {

        item.setDeleted(true);
        item.setLastModifiedTime(new Date());
        return itemRepository.save(item);
    }
}

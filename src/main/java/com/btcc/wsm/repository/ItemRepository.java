package com.btcc.wsm.repository;


import com.btcc.wsm.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by siva on 1/10/2016.
 */
public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findAllByIsDeletedOrderByCreationTimeDesc(boolean isDeleted);
}

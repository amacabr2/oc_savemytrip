package com.openclassrooms.savemytrip.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.savemytrip.database.dao.ItemDao;
import com.openclassrooms.savemytrip.models.Item;

import java.util.List;

public class ItemDataRepository {

    private final ItemDao itemDao;

    public ItemDataRepository(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public LiveData<List<Item>> getItems(long userId){
        return this.itemDao.getItems(userId);
    }

    public void createItem(Item item){
        itemDao.insertItem(item);
    }

    public void deleteItem(long itemId){
        itemDao.deleteItem(itemId);
    }

    public void updateItem(Item item){
        itemDao.updateItem(item);
    }
}

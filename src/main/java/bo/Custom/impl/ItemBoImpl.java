package bo.Custom.impl;

import bo.Custom.ItemBo;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;
import dto.CustomerDto;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo<ItemDto> {

    ItemDao itemDao = new ItemDaoImpl();


    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(
                new Item(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQty()
                )

        );
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.update(
                new Item(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQty()
                )

        );
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> allItem() throws SQLException, ClassNotFoundException {
        List<Item> itemList = itemDao.GetAll();
        List<ItemDto> dtoList = new ArrayList<>();
        for (Item item:itemList  ){
            dtoList.add(
                    new ItemDto(
                            item.getCode(),
                            item.getDesc(),
                            item.getUnitPrice(),
                            item.getQty()
                    )
            );
        }
        return dtoList;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDao.getItem(code);
        return new ItemDto(
                item.getCode(),
                item.getDesc(),
                item.getUnitPrice(),
                item.getQty()
        );
    }

}


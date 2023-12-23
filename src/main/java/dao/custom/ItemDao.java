package dao.custom;

import dao.CrudDao;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao extends CrudDao<Item> {

    ItemDto searchItem(String code);
   Item getItem(String code) throws SQLException, ClassNotFoundException;
}

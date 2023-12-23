package bo.Custom;

import dto.CustomerDto;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo<T> {
    boolean saveItem(T dto) throws SQLException, ClassNotFoundException ;
    boolean updateItem(T dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    List<T> allItem() throws SQLException, ClassNotFoundException;

    ItemDto getItem(String code) throws SQLException, ClassNotFoundException;
}

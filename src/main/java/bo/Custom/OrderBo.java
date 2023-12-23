package bo.Custom;

import dto.OrderDto;
import dto.SecondOrderDto;
import entity.Order;

import java.sql.SQLException;

public interface OrderBo {
     SecondOrderDto lastOrder() throws SQLException, ClassNotFoundException;
     boolean saveOrder(OrderDto order) throws SQLException, ClassNotFoundException;

}

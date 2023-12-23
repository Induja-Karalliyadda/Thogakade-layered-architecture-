package bo.Custom.impl;

import bo.Custom.OrderBo;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.custom.impl.OrderDaoImpl;
import dao.custom.impl.OrderDetailsDaoImpl;
import dto.OrderDto;
import dto.SecondOrderDto;
import entity.Order;

import java.sql.SQLException;


public class OrderBoImpl implements OrderBo {
private OrderDao orderDao = new OrderDaoImpl() ;
private OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();
    @Override
    public SecondOrderDto lastOrder() throws SQLException, ClassNotFoundException {
        Order order =orderDao.lastOrder() ;
        return new SecondOrderDto(
                order.getId(),
                order.getDate(),
                order.getCusId()
        );
    }

    @Override
    public boolean saveOrder(OrderDto order) throws SQLException, ClassNotFoundException {
       boolean isSaved = orderDao.save(
                new Order(
                        order.getCustId(),
                        order.getDate(),
                        order.getCustId()
                )

        );

        if (isSaved){
            boolean isSavedDetails = orderDetailsDao.saveOrderDetails(order.getDto());
            if (isSavedDetails){
                return true;
            }
        }
        return false;
    }
}

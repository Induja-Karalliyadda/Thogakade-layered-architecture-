package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import db.DBConnection;
import dto.OrderDto;
import entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();


    @Override
    public Order lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "select * from orders order by id desc limit 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            );
        }
        return null;
    }

    @Override
    public boolean save(Order entity) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into orders values(?,?,?)";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setString(1,entity.getId());
            prstm.setString(2,entity.getDate());
            prstm.setString(3,entity.getCusId());
           return (prstm.executeUpdate()>0);
        } catch (SQLException | ClassNotFoundException ex){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public boolean delete(String Value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Order> GetAll() throws SQLException, ClassNotFoundException {
        List <Order> entnityList = new ArrayList<>();
        String SQL = "SELECT * FROM orders";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            entnityList.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)

            ));
        }
    return entnityList;
    }

}

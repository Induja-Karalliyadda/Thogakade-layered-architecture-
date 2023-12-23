package dao.custom.impl;

import dao.custom.CustomerDao;
import db.DBConnection;
import dto.CustomerDto;
import entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {



    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "insert into customer value(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,entity.getId());
        pstm.setString(2,entity.getName());
        pstm.setString(3,entity.getAddress());
        pstm.setDouble(4,entity.getSalary());

        int result = pstm.executeUpdate();
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "update from customer set name=? address=? salary=? where id=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,entity.getName());
        prstm.setString(2,entity.getAddress());
        prstm.setDouble(3,entity.getSalary());
        prstm.setString(4,entity.getId());
        int result = prstm.executeUpdate();
        if(result>0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(String Value) throws SQLException, ClassNotFoundException {
        String sql = "delete from customer where id=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,Value);
        int result = prstm.executeUpdate();
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> GetAll() throws SQLException, ClassNotFoundException {
        List<Customer> customerDtos = new ArrayList<>();
        String sql = "select * from customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        while(rst.next()){
            customerDtos.add(new Customer(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4)
                    )
            );
        }
        return customerDtos;
    }
}

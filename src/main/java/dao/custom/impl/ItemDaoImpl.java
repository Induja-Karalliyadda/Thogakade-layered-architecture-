package dao.custom.impl;
import dao.custom.ItemDao;
import db.DBConnection;
import dto.ItemDto;
import entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {






    @Override
    public ItemDto searchItem(String code) {
        return null;
    }

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "select * from item where code=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,code);
        ResultSet result = prstm.executeQuery();
        if(result.next()){
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return null;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {

        String sql = "Insert into item values(?,?,?,?)";
        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prst.setString(1, entity.getCode());
        prst.setString(2,entity.getDesc());
        prst.setInt(3,entity.getQty());
        prst.setDouble(4,entity.getUnitPrice());
        int result = prst.executeUpdate();
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "update from item set description=? unitPrice=? qtyOnHand=? where code=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,entity.getDesc());;
        prstm.setDouble(2,entity.getUnitPrice());
        prstm.setInt(3,entity.getQty());
        prstm.setString(4,entity.getCode());
        int result= prstm.executeUpdate();
        if(result>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean delete(String Value) throws SQLException, ClassNotFoundException {

        String sql = "delete from item where code=?";
        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prst.setString(1,Value);
        int rst = prst.executeUpdate();
        if(rst>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Item> GetAll() throws SQLException, ClassNotFoundException {
        List<Item> dtos = new ArrayList<>();
        String sql = "select * from item";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            dtos.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            ));
        }
        return dtos;

    }
}

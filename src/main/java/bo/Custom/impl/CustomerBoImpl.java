package bo.Custom.impl;

import bo.Custom.CustomerBo;
import dao.custom.CustomerDao;
import dao.custom.impl.CustomerDaoImpl;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo<CustomerDto> {

    private CustomerDao customerDao = new CustomerDaoImpl();
    
    
    
    
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
            ));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException{
        return customerDao.update(
                (new Customer(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary()
                )));

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    @Override
    public List<CustomerDto> allCustomer() throws SQLException, ClassNotFoundException {
      List<Customer> customerList= customerDao.GetAll();
       List<CustomerDto> dtoList= new ArrayList<>();

        for (Customer customer:customerList ) {
            dtoList.add(
                    new CustomerDto(customer.getId(),
                            customer.getName(),
                            customer.getAddress(),
                            customer.getSalary()
                    )
            );
        }
        return dtoList;
    }
}

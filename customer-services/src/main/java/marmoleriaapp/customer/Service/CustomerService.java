package marmoleriaapp.customer.Service;

import java.util.List;

import marmoleriaapp.customer.Dto.DtoCustomer;
import marmoleriaapp.customer.Entity.Customer;
import marmoleriaapp.customer.Entity.Region;

public interface CustomerService {

    public List<DtoCustomer> listAllProduct();
    public List<DtoCustomer> findByRegion(Region region);
    public DtoCustomer getCustomer(Long id);
    public DtoCustomer getCustomerByDni(String dni);
    public DtoCustomer createCustomer(String transactionId, Customer product);
    public DtoCustomer updateCustomer(Customer product);
    public Customer deleteCustomer(Long id);
    public Customer getOriginalCustomer(Long id);

}
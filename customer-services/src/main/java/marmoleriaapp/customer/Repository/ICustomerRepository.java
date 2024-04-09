package marmoleriaapp.customer.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import marmoleriaapp.customer.Entity.Customer;
import marmoleriaapp.customer.Entity.Region;

public interface ICustomerRepository extends JpaRepository<Customer, Long>{
    public Customer findByDni(String dni);
    public List<Customer> findByLastName(String lastName);
    public Customer findByEmail(String email);
    public Customer findByTransactionId(String transactionId);
    public List<Customer> findByRegion(Region region);
}

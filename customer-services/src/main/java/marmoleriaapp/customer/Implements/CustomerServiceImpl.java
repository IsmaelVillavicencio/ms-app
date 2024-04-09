package marmoleriaapp.customer.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import marmoleriaapp.customer.Dto.DtoCustomer;
import marmoleriaapp.customer.Entity.Customer;
import marmoleriaapp.customer.Entity.Region;
import marmoleriaapp.customer.Repository.ICustomerRepository;
import marmoleriaapp.customer.Service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ICustomerRepository customerRepository;

    @Autowired()
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DtoCustomer> listAllProduct() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DtoCustomer getCustomer(Long id) {
        Customer customer = this.getOriginalCustomer(id);
        if (customer == null) {
            return null;
        }
        return convertToDto(customer);
    }

    @Override
    @Transactional()
    public DtoCustomer createCustomer(String transactionId, Customer customer) {
        Customer customerExist = customerRepository.findByTransactionId(transactionId);
        if (customerExist != null) {
            return convertToDto(customerExist);
        }
        customer.setDni(customer.getDni().toUpperCase());
        customer.setTransactionId(transactionId);
        customerRepository.save(customer);
        return convertToDto(customer);
    }

    @Override
    public DtoCustomer updateCustomer(Customer customer) {
        Customer customerExist = this.getOriginalCustomer(customer.getId());
        if (customerExist == null) {
            return null;
        }
        customerExist.setDni(customer.getDni().toUpperCase());
        customerExist.setName(customer.getName());
        customerExist.setLastName(customer.getLastName());
        customerExist.setEmail(customer.getEmail());
        customerExist.setRegion(customer.getRegion());
        customerRepository.save(customerExist);
        return convertToDto(customerExist);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer customer = this.getOriginalCustomer(id);
        if (customer == null) {
            return null;
        }
        customer.setStatus(false);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getOriginalCustomer(Long id) {
       return customerRepository.findById(id).orElse(null);
    }

    private DtoCustomer convertToDto(Customer customer) {
        DtoCustomer dtoCustomer = modelMapper.map(customer, DtoCustomer.class);
        return dtoCustomer;
    }

    @Override
    @Transactional(readOnly = true)
    public DtoCustomer getCustomerByDni(String dni) {
        Customer customer = customerRepository.findByDni(dni);
        if (customer == null) {
            return null;
        }
        return convertToDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoCustomer> findByRegion(Region region) {
        List<Customer> customers = customerRepository.findByRegion(region);
        return customers.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
}

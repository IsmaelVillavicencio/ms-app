package marmoleriaapp.customer.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import marmoleriaapp.customer.Dto.DtoApiResponse;
import marmoleriaapp.customer.Dto.DtoCustomer;
import marmoleriaapp.customer.Dto.DtoFieldResponseError;
import marmoleriaapp.customer.Entity.Customer;
import marmoleriaapp.customer.Entity.Region;
import marmoleriaapp.customer.Exception.ResourceNotFoundException;
import marmoleriaapp.customer.Service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> listAllCustomers(
        @RequestParam(name = "regionId", required = false) Long regionId ) {
            List<DtoCustomer> customers;
            if (regionId == null) {
                customers = customerService.listAllProduct();
            } else {
                customers = customerService.findByRegion(Region.builder().id(regionId).build());
            }
        return buildResponse(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable(name = "id") Long id) {
        DtoCustomer customer = customerService.getCustomer(id);
        if (customer == null) {
           throw new ResourceNotFoundException("No se encontro el recurso con el id: " + id);
        }
        return buildResponse(customerService.getCustomer(id));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestHeader(name = "transactionId") String transactionId, @Valid @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, getErrorMessage(result));
        }
        DtoCustomer customerResponse = customerService.createCustomer(transactionId, customer);
        return buildResponse(customerResponse);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> getCustomerByDni(@PathVariable(name = "dni") String dni) {
        DtoCustomer customer = customerService.getCustomerByDni(dni);
        if (customer == null) {
           throw new ResourceNotFoundException("No se encontro el recurso con el dni: " + dni);
        }
        return buildResponse(customerService.getCustomerByDni(dni));
    }



    private String getErrorMessage(BindingResult result) {
        ObjectMapper mapper = new ObjectMapper();
        List<DtoFieldResponseError> fieldErrors = result.getFieldErrors().stream()
                .map(error -> new DtoFieldResponseError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        try {
            System.out.println(mapper.writeValueAsString(fieldErrors));
            return mapper.writeValueAsString(fieldErrors);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; 
        }
    }

    private ResponseEntity<DtoApiResponse<?>> buildResponse(Object data) {
        return ResponseEntity.ok(new DtoApiResponse<>("success", data));
    }
}

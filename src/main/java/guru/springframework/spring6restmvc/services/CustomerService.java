package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

//    CustomerDTO getCustomerById(UUID uuid);
    Optional<CustomerDTO> getCustomerById(UUID uuid);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    void updatedById(UUID id, CustomerDTO customer);

    void deleteById(UUID id);

    void patchBeerById(UUID id, CustomerDTO customer);
}

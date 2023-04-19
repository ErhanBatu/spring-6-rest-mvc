package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {

        customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(2)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
    }

    @Override
    public CustomerDTO getCustomerById(UUID uuid) {
        return customerMap.get(uuid);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .version(customer.getVersion())
                .name(customer.getName())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updatedById(UUID id, CustomerDTO customer) {

        CustomerDTO updatedCustomer = customerMap.get(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setVersion(customer.getVersion());
        updatedCustomer.setCreatedDate(LocalDateTime.now());
        updatedCustomer.setUpdateDate(LocalDateTime.now());

        //You don't have to use this below, just in case
//        customerMap.put(updatedCustomer.getId(), updatedCustomer);

    }

    @Override
    public void deleteById(UUID id) {

        customerMap.remove(id);
    }

    @Override
    public void patchBeerById(UUID id, CustomerDTO customer) {

        CustomerDTO existing = customerMap.get(id);

        //if it has a text
        if (StringUtils.hasText(customer.getName())){
            existing.setName(customer.getName());
        }

        if (existing.getVersion() != null){
            existing.setVersion(customer.getVersion());
        }

    }
}

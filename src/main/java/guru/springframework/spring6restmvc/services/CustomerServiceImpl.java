package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {

        customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
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
    public Customer getCustomerById(UUID uuid) {
        return customerMap.get(uuid);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {

        Customer savedCustomer = Customer.builder()
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
    public void updatedById(UUID id, Customer customer) {

        Customer updatedCustomer = customerMap.get(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setVersion(customer.getVersion());
        updatedCustomer.setCreatedDate(LocalDateTime.now());
        updatedCustomer.setUpdateDate(LocalDateTime.now());

        //You don't have to use this below, just in case
//        customerMap.put(updatedCustomer.getId(), updatedCustomer);

    }
}

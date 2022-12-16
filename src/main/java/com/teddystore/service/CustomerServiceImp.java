package com.teddystore.service;

import com.teddystore.controller.CustomerController;
import com.teddystore.exception.CustomerNotFoundException;
import com.teddystore.model.Customer;
import com.teddystore.model.WebAppUser;
import com.teddystore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * @author Alberto Villalpando <br><br>
 * Esta clase se encargará de ser una mediadora entre el cliente y la persistencia, implementará las reglas de negocio y traducirá la información que intercambian estas dos capas. <br><br>
 * <font color="#85ba6a"><strong>{@code @Service}</strong></font>: Indica la responsabilidad de la clase con <a href="https://livebook.manning.com/book/spring-quickly/chapter-4/128">Stereotype Annotations</a>. <br><br>
 * <font color="#85ba6a"><strong>{@code @Transactional}</strong></font>: Se asegura de que la información se obtenga correctamente, en caso de fallar, los cambios serán revertidos. <br><br>
 * <font color="#85ba6a"><strong>{@code @Autowired}</strong></font>: Obtiene el Bean del <a href="https://livebook.manning.com/book/spring-quickly/chapter-2/45">Spring Context</a> y lo inyecta en la clase. <br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Customer}
 *              -> {@link CustomerRepository  CustomerRepository}
 *              -> {@link CustomerService}
 *              -> {@link CustomerServiceImp}
 *              -> {@link CustomerController}
 * </strong><br><br>
 * @see com.teddystore.exception.CustomerNotFoundException
 * @see com.teddystore.exception.handler.CustomerTrackerGlobalExceptionHandler CostumerTrackerGlobalExceptionHandler
 * @see <a href="https://livebook.manning.com/book/spring-quickly/chapter-13/">Spring Start Here: 13 Using transactions in Spring apps</a>
 * */
@Service
@Transactional
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public CustomerServiceImp(CustomerRepository customerRepository, PasswordEncoder encoder) {
        this.customerRepository = customerRepository;
        this.encoder = encoder;
    }

    @Override
    public <T extends WebAppUser> Customer registerCustomer(T customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepository.save((Customer) customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer with id %s found", id))));
    }

    @Override
    public Optional<Customer> getByUsername(String username) {
        return Optional.ofNullable(customerRepository.getByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer with username %s found", username))));
    }

    @Override
    public Optional<Iterable<Customer>> getCustomers() {
        return Optional.ofNullable(Optional.of(customerRepository.findAll())
                .orElseThrow(() -> new CustomerNotFoundException("No customers found")));
    }

    @Override
    public Customer updateCustomerDetails(Long id, Customer newCustomerDetails) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer with id %s found", id)));

        existingCustomer.updateUserDetails(newCustomerDetails);

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        if(customerExists(id)) customerRepository.deleteById(id);
    }

    @Override
    public void deleteCustomers() {
        customerRepository.deleteAll();
    }

    private boolean customerExists(Long id) {
        if(customerRepository.existsById(id)) {
            return true;
        }else {
            throw new CustomerNotFoundException(String.format("No customer with id %s found", id));
        }
    }
}

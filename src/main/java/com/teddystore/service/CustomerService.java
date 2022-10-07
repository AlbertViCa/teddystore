package com.teddystore.service;

import com.teddystore.controller.CustomerController;
import com.teddystore.model.Customer;
import com.teddystore.repository.CustomerRepository;

import java.util.Optional;
/**
 * @author Alberto Villalpando <br><br>
 * Interface que establece las reglas/requerimientos de negocio.<br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Customer}
 *              -> {@link CustomerRepository  CustomerRepository}
 *              -> {@link CustomerService}
 *              -> {@link CustomerServiceImp}
 *              -> {@link CustomerController}
 * </strong>
 * */
public interface CustomerService {

    Customer registerCustomer(Customer customer);

    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> getByUsername(String username);

    Optional<Iterable<Customer>> getCustomers();

    Customer updateCustomerDetails(Long id, Customer customer);

    void deleteCustomerById(Long id);

    void deleteCustomers();
}

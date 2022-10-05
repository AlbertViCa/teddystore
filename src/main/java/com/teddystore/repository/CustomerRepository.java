package com.teddystore.repository;

import com.teddystore.controller.CustomerController;
import com.teddystore.model.Customer;
import com.teddystore.service.CustomerService;
import com.teddystore.service.CustomerServiceImp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * @author Alberto Villalpando <br><br>
 * Clase en la que establecemos las reglas dobtención y almacenamiento de información en la base de datos. <br><br>
 * <font color="#e6b737"><strong>{@code CrudRepository<Customer, Long>}</strong></font>: <strong>Customer</strong> es el objeto a guardar, <strong>Long</strong> es el tipo de dato correspondiente al identificador único del objeto. <br><br>
 * <font color="#85ba6a"><strong>{@code @Repository}</strong></font>: Indica la responsabilidad de la clase con <a href="https://livebook.manning.com/book/spring-quickly/chapter-4/128">Stereotype Annotations</a> de que se encargará de la función de persistir información. <br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Customer}
 *              -> {@link CustomerRepository  CustomerRepository}
 *              -> {@link CustomerService}
 *              -> {@link CustomerServiceImp CustomerServiceImp}
 *              -> {@link CustomerController}
 * </strong>
 * @see <a href="https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html">Interface CrudRepository</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods">Query Methods</a>
 * @see <a href="https://livebook.manning.com/book/java-persistence-with-hibernate-third-edition/chapter-2/v-13/">Java Persistence with Spring Data and Hibernate: 2 Starting a project</a>
 * */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> getByUsername(String username);
}

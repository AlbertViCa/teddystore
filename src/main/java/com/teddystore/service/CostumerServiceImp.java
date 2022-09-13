package com.teddystore.service;

import com.teddystore.controller.CostumerController;
import com.teddystore.exception.CostumerNotFoundException;
import com.teddystore.model.Costumer;
import com.teddystore.repository.CostumerRepository;
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
 *              -> {@link Costumer}
 *              -> {@link com.teddystore.repository.CostumerRepository  CostumerRepository}
 *              -> {@link CostumerService}
 *              -> {@link CostumerServiceImp}
 *              -> {@link CostumerController}
 * </strong><br><br>
 * @see com.teddystore.exception.CostumerNotFoundException
 * @see com.teddystore.exception.handler.CostumerTrackerGlobalExceptionHandler CostumerTrackerGlobalExceptionHandler
 * @see <a href="https://livebook.manning.com/book/spring-quickly/chapter-13/">Spring Start Here: 13 Using transactions in Spring apps</a>
 * */
@Service
@Transactional
public class CostumerServiceImp implements CostumerService {

    private final CostumerRepository costumerRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public CostumerServiceImp(CostumerRepository costumerRepository, PasswordEncoder encoder) {
        this.costumerRepository = costumerRepository;
        this.encoder = encoder;
    }

    @Override
    public Costumer registerCostumer(Costumer costumer) {
        costumer.setPassword(encoder.encode(costumer.getPassword()));
        return costumerRepository.save(costumer);
    }

    @Override
    public Optional<Costumer> getCostumerById(Long id) {
        return Optional.ofNullable(costumerRepository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with id %s found", id))));
    }

    @Override
    public Optional<Costumer> getByUsername(String username) {
        return Optional.ofNullable(costumerRepository.getByUsername(username)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with username %s found", username))));
    }

    @Override
    public Iterable<Costumer> getCostumers() {
        return costumerRepository.findAll();
    }

    @Override
    public Costumer updateCostumerDetails(Long id, Costumer newCostumerDetails) {
        Costumer existingCostumer = costumerRepository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with id %s found", id)));

        existingCostumer.updateCostumerDetails(newCostumerDetails);

        return costumerRepository.save(existingCostumer);
    }

    @Override
    public void deleteCostumerById(Long id) {
        costumerRepository.deleteById(id);
    }

    @Override
    public void deleteCostumers() {
        costumerRepository.deleteAll();
    }
}

package com.teddystore.service;

import com.teddystore.controller.CostumerController;
import com.teddystore.model.Costumer;

import java.util.Optional;
/**
 * @author Alberto Villalpando <br><br>
 * Interface que establece las reglas/requerimientos de negocio.<br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Costumer}
 *              -> {@link com.teddystore.repository.CostumerRepository  CostumerRepository}
 *              -> {@link CostumerService}
 *              -> {@link CostumerServiceImp}
 *              -> {@link CostumerController}
 * </strong>
 * */
public interface CostumerService {

    Costumer registerCostumer(Costumer costumer);

    Optional<Costumer> getCostumerById(Long id);

    Optional<Costumer> getByUsername(String username);

    Iterable<Costumer> getCostumers();

    Costumer updateCostumerDetails(Long id, Costumer costumer);

    void deleteCostumerById(Long id);

    void deleteCostumers();
}

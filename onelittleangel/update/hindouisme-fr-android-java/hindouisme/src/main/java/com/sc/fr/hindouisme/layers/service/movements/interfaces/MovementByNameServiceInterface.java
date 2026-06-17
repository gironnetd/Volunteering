package com.sc.fr.hindouisme.layers.service.movements.interfaces;

import com.sc.fr.hindouisme.transverse.orms.realm.models.Movement;
import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

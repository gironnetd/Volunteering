package com.sc.fr.philosophie.layers.service.movements.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

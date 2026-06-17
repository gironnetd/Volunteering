package com.sc.fr.islam.layers.service.movements.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

package com.sc.fr.christianisme.layers.service.movements.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.christianisme.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

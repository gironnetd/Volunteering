package com.sc.en.christianism.layers.service.movements.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.christianism.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

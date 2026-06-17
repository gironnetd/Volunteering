package com.sc.en.philosophy.layers.service.movements.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;
import com.sc.en.philosophy.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

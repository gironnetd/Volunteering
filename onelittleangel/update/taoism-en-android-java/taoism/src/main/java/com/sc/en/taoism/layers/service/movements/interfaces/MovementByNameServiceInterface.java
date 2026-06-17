package com.sc.en.taoism.layers.service.movements.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.taoism.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

package com.sc.en.onelittleangel.layers.service.movements.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Movement;

import io.reactivex.Observable;

public interface MovementByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<Movement> loadMovementByNameAsync(String name);
}

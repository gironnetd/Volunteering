package com.sc.fr.philosophie.injector.dao;

import com.sc.fr.philosophie.layers.dao.movements.MovementsDaoInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Movement;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class MovementsDaoMocked implements MovementsDaoInterface {
  @Override
  public Movement findMovementByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public Movement findMovementByName(String name) {
    return null;
  }

  @Override
  public List<Movement> findMovementsByIdParent(int idParent) {
    return null;
  }

  @Override
  public List<Movement> findAllMovements() {
    return null;
  }

  /**
   * @return
   */
  @Override
  public List<Movement> findMovementsWithAuthors() {
    return null;
  }

  /**
   * @return
   */
  @Override
  public List<Movement> findMovementsWithBooks() {
    return null;
  }

  /**
   * @return
   */
  @Override
  public List<Movement> findMovementsWithMovements() {
    return null;
  }
}

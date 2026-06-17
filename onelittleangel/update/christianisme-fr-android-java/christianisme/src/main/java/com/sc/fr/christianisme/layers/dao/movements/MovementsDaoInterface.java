package com.sc.fr.christianisme.layers.dao.movements;

import com.sc.fr.christianisme.transverse.orms.realm.models.Movement;

import java.util.List;

public interface MovementsDaoInterface {

  /**
   *
   * @param idMovement
   * @return
   */
  Movement findMovementByIdMovement(int idMovement);

  /**
   *
   * @param name
   * @return
   */
  Movement findMovementByName(String name);

  /**
   *
   * @param idParent
   * @return
   */
  List<Movement> findMovementsByIdParent(int idParent);

  /**
   *
   * @return
   */
  List<Movement> findAllMovements();

  /**
   *
   * @return
   */
  List<Movement> findMovementsWithAuthors();

  /**
   *
   * @return
   */
  List<Movement> findMovementsWithBooks();

  /**
   *
   * @return
   */
  List<Movement> findMovementsWithMovements();
}

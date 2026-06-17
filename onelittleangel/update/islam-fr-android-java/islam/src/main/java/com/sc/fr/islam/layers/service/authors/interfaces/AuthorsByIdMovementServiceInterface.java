package com.sc.fr.islam.layers.service.authors.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;

public interface AuthorsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find authors that match the id movement in an asynchronous way
   * @param idMovement The id movement of authors searched
   */
  void loadAuthorsByIdMovementAsync(int idMovement);
}

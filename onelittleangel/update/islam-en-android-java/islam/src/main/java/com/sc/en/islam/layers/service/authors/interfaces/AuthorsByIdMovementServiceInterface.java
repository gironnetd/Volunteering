package com.sc.en.islam.layers.service.authors.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;

public interface AuthorsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find authors that match the id movement in an asynchronous way
   * @param idMovement The id movement of authors searched
   */
  void loadAuthorsByIdMovementAsync(int idMovement);
}

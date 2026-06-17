package com.sc.en.hindouism.layers.service.books.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface BooksByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find authors that match the id movement in an asynchronous way
   * @param idMovement The id movement of authors searched
   */
  void loadBooksByIdMovementAsync(int idMovement);
}

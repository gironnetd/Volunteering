package com.sc.en.islam.layers.service.books.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;

public interface BookByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author id in an asynchronous way
   * @param idBook The name of the author searched
   */
  void loadBookByIdBookAsync(int idBook);
}

package com.sc.en.taoism.layers.mvp.tablecontents.fragments;

import com.sc.en.taoism.layers.mvp.tablecontents.models.BaseEntity;

import java.util.List;

public interface PresenterInterface {

  /**
   *
   */
  void loadMovements();

  /**
   *
   * @return
   */
  List<? extends BaseEntity> getMovements();

  void removeMovementsOfFragment();
}

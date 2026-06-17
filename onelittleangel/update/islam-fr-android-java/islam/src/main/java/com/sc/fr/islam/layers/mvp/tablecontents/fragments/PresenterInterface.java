package com.sc.fr.islam.layers.mvp.tablecontents.fragments;

import com.sc.fr.islam.layers.mvp.tablecontents.models.BaseEntity;

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

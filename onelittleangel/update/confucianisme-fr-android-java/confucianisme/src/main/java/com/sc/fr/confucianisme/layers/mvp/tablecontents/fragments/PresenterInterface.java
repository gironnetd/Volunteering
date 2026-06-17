package com.sc.fr.confucianisme.layers.mvp.tablecontents.fragments;

import com.sc.fr.confucianisme.layers.mvp.tablecontents.models.BaseEntity;

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

package com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments;

import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.BaseEntity;

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

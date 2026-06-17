package com.sc.fr.onelittleangel.injector.dao;

import com.sc.fr.onelittleangel.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Presentation;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class PresentationsDaoMocked implements PresentationsDaoInterface {
  @Override
  public Presentation findPresentationByIdPresentation(int idPresentation) {
    return null;
  }

  @Override
  public Presentation findPresentationByIdAuthor(int idAuthor) {
    return null;
  }

  @Override
  public Presentation findPresentationByIdBook(int idBook) {
    return null;
  }

  @Override
  public Presentation findPresentationByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public List<Presentation> findAllPresentations() {
    return null;
  }
}

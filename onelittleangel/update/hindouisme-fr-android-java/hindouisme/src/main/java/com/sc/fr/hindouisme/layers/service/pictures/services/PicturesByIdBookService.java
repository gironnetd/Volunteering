package com.sc.fr.hindouisme.layers.service.pictures.services;

import android.util.SparseArray;

import com.sc.fr.hindouisme.OnelittleAngelApplication;
import com.sc.fr.hindouisme.transverse.eventbus.events.pictures.PicturesByIdBookLoadedEvent;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Picture;
import com.sc.fr.hindouisme.injector.Injector;
import com.sc.fr.hindouisme.layers.dao.pictures.PicturesDaoInterface;
import com.sc.fr.hindouisme.layers.service.MotherBusinessService;
import com.sc.fr.hindouisme.layers.service.ServiceManagerInterface;
import com.sc.fr.hindouisme.layers.service.pictures.interfaces.PicturesByIdBookServiceInterface;
import com.sc.fr.hindouisme.transverse.eventbus.models.PictureEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PicturesByIdBookService extends MotherBusinessService implements PicturesByIdBookServiceInterface {

  private static final String TAG = "PicutresByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Picture>> picturesByIdBookList = null;

  /**
   *
   */
  private PicturesByIdBookLoadedEvent picturesByIdBookLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesByIdBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    picturesByIdBookList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idBook
   */
  @Override
  public void loadPicturesByIdBookAsync(int idBook) {
    //  Log.e(TAG, "loadPicturesByIdBookAsync() called with: " + "cityId = [" + idBook + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (picturesByIdBookList.get(idBook)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPictureByIdBookDataLoadedEvent(picturesByIdBookList.get(idBook),idBook);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new PicturesByIdBookService.DaoPictureByIdBookLoadRunnable(idBook));
    }
  }

  private void loadPicturesByIdBookSync(int idBook){
    // Log.e(TAG, "loadPicturesByIdBookSync() called with: " + "idBook = [" + idBook + "]");
    // Load data from database
    /*

   */
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    List<Picture> picturesByIdBook = picturesDaoInterface.findPicturesByIdBook(idBook);
    picturesDaoInterface = null;
    //update your own cache
    picturesByIdBookList.put(idBook,picturesByIdBook);
    //send send back the answer using eventBus
    postPictureByIdBookDataLoadedEvent(picturesByIdBook,idBook);
  }

  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPictureByIdBookLoadRunnable implements Runnable {
    final int idBook;

    public DaoPictureByIdBookLoadRunnable(int idBook) {
      this.idBook = idBook;
    }

    @Override
    public void run() {
      loadPicturesByIdBookSync(idBook);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPictureByIdBookDataLoadedEvent(List<Picture> picturesByIdBook,int idBook) {
    //  Log.e(TAG, "postPictureByIdBookDataLoadedEvent() called as found Picture with " +
    //    pictureByIdBookLoadedEvent.getPictureByIdBook().getQuotes().size() + " elements and idBook="+idBook);
    List<PictureEventBus> pictures = new ArrayList<>();

    for(Picture p : picturesByIdBook){
      pictures.add(new PictureEventBus(p));
    }

    if(picturesByIdBookLoadedEvent==null){
      picturesByIdBookLoadedEvent= new PicturesByIdBookLoadedEvent(pictures, idBook);
    }else{
      picturesByIdBookLoadedEvent.setPicturesByIdBook(pictures);
      picturesByIdBookLoadedEvent.setIdBook(idBook);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + pictureByIdBookLoadedEvent.getPictureByIdBook() + " elements and idBook ="+pictureByIdBookLoadedEvent.getIdBook());
    EventBus.getDefault().post(picturesByIdBookLoadedEvent);
  }

}

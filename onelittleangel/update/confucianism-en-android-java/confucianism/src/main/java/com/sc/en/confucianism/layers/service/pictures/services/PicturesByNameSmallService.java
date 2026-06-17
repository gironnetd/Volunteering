package com.sc.en.confucianism.layers.service.pictures.services;

import android.support.v4.util.ArrayMap;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.dao.pictures.PicturesDaoInterface;
import com.sc.en.confucianism.layers.service.MotherBusinessService;
import com.sc.en.confucianism.layers.service.ServiceManagerInterface;
import com.sc.en.confucianism.transverse.eventbus.events.pictures.PicturesByNameSmallLoadedEvent;
import com.sc.en.confucianism.transverse.eventbus.models.PictureEventBus;
import com.sc.en.confucianism.transverse.orms.realm.models.Picture;
import com.sc.en.confucianism.layers.service.pictures.interfaces.PicturesByNameSmallServiceInterface;
import com.sc.en.confucianism.injector.Injector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PicturesByNameSmallService extends MotherBusinessService implements PicturesByNameSmallServiceInterface {

  private static final String TAG = "PictureByIdPictureNameSmallService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String,List<Picture>> picturesByNameSmallList = null;

  /**
   *
   */
  private PicturesByNameSmallLoadedEvent picturesByNameSmallLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesByNameSmallService(ServiceManagerInterface srvManager) {
    super(srvManager);
    picturesByNameSmallList = new ArrayMap<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param nameSmall
   */
  @Override
  public void loadPicturesByNameSmallAsync(String nameSmall) {
    //  Log.e(TAG, "loadPictureByNameSmallAsync() called with: " + "cityId = [" + nameSmall + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (picturesByNameSmallList.get(nameSmall)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPictureByNameSmallDataLoadedEvent(picturesByNameSmallList.get(nameSmall),nameSmall);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoPictureByNameSmallLoadRunnable(nameSmall));
    }
  }

  private void loadPictureByNameSmallSync(String nameSmall){
    // Log.e(TAG, "loadPictureByNameSmallSync() called with: " + "nameSmall = [" + nameSmall + "]");
    // Load data from database
    /*

   */
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    List<Picture> pictureByNameSmall = picturesDaoInterface.findPicturesByNameSmall(nameSmall);
    picturesDaoInterface = null;
    //update your own cache
    picturesByNameSmallList.put(nameSmall,pictureByNameSmall);

    //send send back the answer using eventBus
    postPictureByNameSmallDataLoadedEvent(pictureByNameSmall,nameSmall);
  }

  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPictureByNameSmallLoadRunnable implements Runnable {
    final String nameSmall;

    public DaoPictureByNameSmallLoadRunnable(String nameSmall) {
      this.nameSmall = nameSmall;
    }

    @Override
    public void run() {
      loadPictureByNameSmallSync(nameSmall);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPictureByNameSmallDataLoadedEvent(List<Picture> picturesByNameSmall,String nameSmall) {
    //  Log.e(TAG, "postPictureByNameSmallDataLoadedEvent() called as found Picture with " +
    //    pictureByNameSmallLoadedEvent.getPictureByNameSmall().getQuotes().size() + " elements and nameSmall="+nameSmall);
    List<PictureEventBus> pictures = new ArrayList<>();

    for(int i = 0; i < picturesByNameSmall.size(); i++){
      pictures.add(new PictureEventBus(picturesByNameSmall.get(i)));
    }


    if(picturesByNameSmallLoadedEvent==null){
      picturesByNameSmallLoadedEvent= new PicturesByNameSmallLoadedEvent(pictures, nameSmall);
    }else{
      picturesByNameSmallLoadedEvent.setPicturesByNameSmall(pictures);
      picturesByNameSmallLoadedEvent.setNameSmall(nameSmall);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + pictureByNameSmallLoadedEvent.getPictureByNameSmall() + " elements and nameSmall ="+pictureByNameSmallLoadedEvent.getNameSmall());
    EventBus.getDefault().post(picturesByNameSmallLoadedEvent);
  }

}

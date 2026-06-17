package com.sc.fr.taoisme.layers.service.pictures.services;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.sc.fr.taoisme.OnelittleAngelApplication;
import com.sc.fr.taoisme.R;
import com.sc.fr.taoisme.injector.Injector;
import com.sc.fr.taoisme.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.taoisme.layers.dao.books.BooksDaoInterface;
import com.sc.fr.taoisme.layers.dao.movements.MovementsDaoInterface;
import com.sc.fr.taoisme.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.taoisme.layers.service.MotherBusinessService;
import com.sc.fr.taoisme.layers.service.ServiceManagerInterface;
import com.sc.fr.taoisme.layers.service.pictures.interfaces.PicturesForCarouselInterface;
import com.sc.fr.taoisme.transverse.orms.realm.models.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.Observable;

public class PicturesForCarousel extends MotherBusinessService implements PicturesForCarouselInterface {

  /**
   *
   */
  private ArrayMap<String, List<Bitmap>> bitmapsForCarousel;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesForCarousel(ServiceManagerInterface srvManager) {
    super(srvManager);
    bitmapsForCarousel = new ArrayMap<>();
  }

//  @Override
//  public void onDestroy(ServiceManagerInterface srvManager) {
//    super.onDestroy(srvManager);
//    bitmapsForCarousel = null;
//  }

  @Override
  public void onDestroy() {
    bitmapsForCarousel = null;
  }

  @Override
  public Observable<List<Bitmap>> loadBitmapsForCarouselAsync(String type, String name) {

    boolean reload = false;

    if(bitmapsForCarousel == null) {
      bitmapsForCarousel = new ArrayMap<>();
    }

    if(bitmapsForCarousel.get(name) != null) {
      reload = true;
    }

    if(reload) {
      return Observable.just(bitmapsForCarousel.get(name));
    } else {

      Future<List<Bitmap>> future = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoLoadPictureLoadRunnable(type, name));

      try {
        return Observable.just(future.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
//      return Observable.just(loadBitmapsForCarouselSync(type, name));
    if (bitmapsForCarousel.get(name) == null) return Observable.empty();
     else return Observable.just(bitmapsForCarousel.get(name));
  }

  @Override
  public void setBitmapsForCarousel() {
    if(bitmapsForCarousel == null) {
      for(int i = 0 ; i < this.bitmapsForCarousel.size(); i++) {
        this.bitmapsForCarousel.remove(this.bitmapsForCarousel.get(i));
      }
    }
  //  this.bitmapsForCarousel = bitmapsForCarousel;
  }


  private List<Bitmap> loadBitmapsForCarouselSync(String type, String name) {

    setBitmapsForCarousel();
    List<Picture> pictures = new ArrayList<>();
    List<Bitmap> bitmaps = new ArrayList<>();

    if(type.equals(OnelittleAngelApplication.instance.getString(R.string.authors))) {
      AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
      pictures = authorsDaoInterface.findAuthorByName(name).getPictures();
      authorsDaoInterface = null;
    } else if(type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.books))) {
      BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
      pictures = booksDaoInterface.findBookByName(name).getPictures();
      booksDaoInterface = null;
    } else if(type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.movements))) {
      MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
      pictures = movementsDaoInterface.findMovementByName(name).getPictures();
      movementsDaoInterface = null;
    } else if(type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.themes))) {
      ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();
      pictures = themesDaoInterface.findThemeByName(name).getPictures();
      themesDaoInterface = null;
    }

      for(Picture picture : pictures) {

        Bitmap bitmap = null;
        String pictureName = picture.getNameSmall() + "_" + picture.getIdPicture() + "." + picture.getExtension();
        //  Log.v(TAG, "OnelittleAngelWebSite == null");
      //  Call<ResponseBody> responseBodyCall = service.loadPicture(pictureName);

        ANRequest request = AndroidNetworking.get("http://www.onelittleangel.com/common/images/auteur/" + pictureName)
          .setTag("imageRequestTag")
          .setPriority(Priority.IMMEDIATE)
          .build();

        ANResponse response = request.executeForBitmap();
        //  try {

        bitmap = (Bitmap) response.getResult();


        //try {

        //  ResponseBody responseBody = responseBodyCall.execute().body();

        //  bitmap = BitmapFactory.decodeStream(responseBody.byteStream());

//          ImagePanel imagePanel = new ImagePanel(OnelittleAngelApplication.instance.getApplicationContext());
//          //imagePanel.setImageResId(R.drawable.iron_man);
//          imagePanel.setImageBitmap(bitmap);
//          views.add(imagePanel);
        //  imagePanel =  null;
          bitmaps.add(bitmap);
//        } catch (IOException e) {
//          e.printStackTrace();
//        } finally {
//          bitmap = null;
//        }
      }
      pictures = null;

    bitmapsForCarousel.put(name, bitmaps);
//    for(int i = 0 ; i < bitmaps.size(); i++) {
//      bitmaps.remove(bitmaps.get(i));
//    }
    return bitmapsForCarousel.get(name);
  }

  private class DaoLoadPictureLoadRunnable implements Callable<List<Bitmap>> {

    final String type;
    final String name;

    public DaoLoadPictureLoadRunnable(String type, String name) {
      this.type = type;
      this.name = name;
    }

    @Override
    public List<Bitmap> call() throws Exception {
      return loadBitmapsForCarouselSync(type, name);
    }
  }
}

package com.sc.fr.islam.layers.mvp.biography;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.sc.fr.islam.transverse.orms.realm.models.Book;
import com.sc.fr.islam.transverse.orms.realm.models.Movement;
import com.sc.fr.islam.transverse.orms.realm.models.Author;

import java.util.List;

public interface BiographyPresenterInterface {

    /**
     *
     * @param author
     */
    void loadAuthor(String author);

    /**
     *
     * @return
     */
    Author getAuthor();

    /**
     *
     * @param author
     */
    void setAuthor(Author author);

    /**
     *
     */
    void loadAuthorPictures(String author);

    /**
     *
     * @param book
     */
    void loadBook(String book);

    /**
     *
     * @return
     */
    Book getBook();

    /**
     *
     * @param book
     */
    void setBook(Book book);

    /**
     *
     */
    void loadBookPictures(String book);

    /**
     *
     * @param movement
     */
    void loadMovement(String movement);

    /**
     *
     * @return
     */
    Movement getMovement();

    /**
     *
     * @param movement
     */
    void setMovement(Movement movement);

    /**
     *
     */
    void loadMovementPictures(String movement);


    /**
     *
     * @return
     */
    ArrayMap<Integer, ArrayMap<String, String>> getPresentations();

    /**
     *
     * @param presentations
     */
    void setPresentations(ArrayMap<Integer, ArrayMap<String, String>> presentations);

    /**
     *
     * @param biographyViewInterface
     */
    void setBiographyViewInterface(BiographyViewInterface biographyViewInterface);

    /**
     *
     * @return
     */
    List<View> getCarouselView();

    /**
     *
     * @param carouselView
     */
    void setCarouselView(List<View> carouselView);

    /**
     *
     * @return
     */
    List<Bitmap> getBitmapsView();

    /**
     *
     * @param bitmapsView
     */
    void setBitmapsView(List<Bitmap> bitmapsView);

    /**
     *
     */
    void releaseBitmapsCarousel();

    /**
     *
     * @return
     */
    boolean picturesAlreadyLoaded();

}

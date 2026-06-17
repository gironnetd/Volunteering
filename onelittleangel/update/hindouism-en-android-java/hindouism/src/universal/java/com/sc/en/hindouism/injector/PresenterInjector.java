/**
 * <ul>
 * <li>PresenterInjector</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.injector</li>
 * <li>11/04/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.sc.en.hindouism.injector;

//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityPresenter;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityViewIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.WeatherPresenter;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.WeatherPresenterIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.WeatherViewIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment.WeatherCityPresenter;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment.WeatherCityViewIntf;

import com.sc.en.hindouism.layers.mvp.biography.BiographyPresenter;
import com.sc.en.hindouism.layers.mvp.biography.BiographyPresenterInterface;
import com.sc.en.hindouism.layers.mvp.biography.BiographyViewInterface;
import com.sc.en.hindouism.layers.mvp.biography.fragments.PresentationPresenter;
import com.sc.en.hindouism.layers.mvp.biography.fragments.PresentationPresenterInterface;
import com.sc.en.hindouism.layers.mvp.biography.fragments.PresentationViewInterface;
import com.sc.en.hindouism.layers.mvp.contents.ContentsPresenter;
import com.sc.en.hindouism.layers.mvp.contents.ContentsPresenterInterface;
import com.sc.en.hindouism.layers.mvp.contents.ContentsViewInterface;
import com.sc.en.hindouism.layers.mvp.favorites.FavoritesPresenter;
import com.sc.en.hindouism.layers.mvp.favorites.FavoritesPresenterInterface;
import com.sc.en.hindouism.layers.mvp.favorites.FavoritesViewInterface;
import com.sc.en.hindouism.layers.mvp.settings.SettingsPresenter;
import com.sc.en.hindouism.layers.mvp.settings.SettingsPresenterInterface;
import com.sc.en.hindouism.layers.mvp.settings.SettingsViewInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.mails.MailPresenter;
import com.sc.en.hindouism.layers.mvp.settings.fragments.mails.MailPresenterInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.mails.MailViewInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.movements.MovementsPresenterInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.movements.MovementsViewInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.notifications.NotificationsPresenter;
import com.sc.en.hindouism.layers.mvp.settings.fragments.notifications.NotificationsPresenterInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.notifications.NotificationsViewInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.tips.TipsPresenter;
import com.sc.en.hindouism.layers.mvp.settings.fragments.tips.TipsPresenterInterface;
import com.sc.en.hindouism.layers.mvp.settings.fragments.tips.TipsViewInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsPresenter;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsPresenterInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsViewInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.ViewInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.authors.AuthorsPresenter;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.books.BooksPresenter;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.homepage.fragments.HomePagePresenter;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.homepage.fragments.HomePagePresenterInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.homepage.fragments.HomePageViewInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.movements.MovementsPresenter;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.themes.ThemesPresenter;

/**
 * Created by Mathias Seguy - Android2EE on 11/04/2016.
 * Because you wantto inject MockPresenter to your view
 * So you need to have a presenterInjection for them
 * Else you're screwed.
 */
public class PresenterInjector {
    public static TableContentsPresenterInterface getTableContentsPresenter(TableContentsViewInterface view) {
        return new TableContentsPresenter(view);
    }

    public static ContentsPresenterInterface getContentsPresenter(ContentsViewInterface view) {
        return new ContentsPresenter(view);
    }

    public static FavoritesPresenterInterface getFavoritesPresenter(FavoritesViewInterface view) {
        return new FavoritesPresenter(view);
    }

    public static PresenterInterface getAuthorsPresenter(ViewInterface view) {
        return new AuthorsPresenter(view);
    }

    public static PresenterInterface getBooksPresenter(ViewInterface view) {
        return new BooksPresenter(view);
    }

    public static PresenterInterface getMovementsPresenter(ViewInterface view) {
        return new MovementsPresenter(view);
    }

    public static PresenterInterface getThemesPresenter(ViewInterface view) {
        return new ThemesPresenter(view);
    }

    public static MovementsPresenterInterface getMovementsPresenter(MovementsViewInterface view) {
        return new com.sc.en.hindouism.layers.mvp.settings.fragments.movements.MovementsPresenter(view);
    }

    public static NotificationsPresenterInterface getNotificationsPresenter(NotificationsViewInterface view){
        return new NotificationsPresenter(view);
    }

    public static TipsPresenterInterface getTipsPresenter(TipsViewInterface view){
        return new TipsPresenter(view);
    }

    public static BiographyPresenterInterface getBiographyPresenter(BiographyViewInterface view) {
        return new BiographyPresenter(view);
    }

    public static SettingsPresenterInterface getSettingsPresenter(SettingsViewInterface view) {
        return new SettingsPresenter(view);
    }

    public static MailPresenterInterface getMailPresenter(MailViewInterface view) {
        return new MailPresenter(view);
    }

    public static HomePagePresenterInterface getHomePagePresenter(HomePageViewInterface view) {
        return new HomePagePresenter(view);
    }

    public static PresentationPresenterInterface getPresentationInterface(PresentationViewInterface view) {
        return new PresentationPresenter(view);
    }
}

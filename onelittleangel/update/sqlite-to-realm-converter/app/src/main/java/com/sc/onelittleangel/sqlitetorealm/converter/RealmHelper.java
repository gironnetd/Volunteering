package com.sc.onelittleangel.sqlitetorealm.converter;


import android.content.Context;
import android.util.Log;

import com.sc.onelittleangel.sqlitetorealm.converter.models.Account;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Author;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Book;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Century;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Favorite;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Movement;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Picture;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Presentation;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Quote;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Theme;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Url;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmHelper {
    private static  Context mContext;
    private static RealmHelper mInstance;
    //private RealmList<Word> words;
    public static RealmHelper createInstance(Context context){
        return mInstance  = new RealmHelper(context);
    }
    public static RealmHelper getIntance(){
        return mInstance;
    }
    private RealmHelper(Context context){
        mContext = context;
    }

    public void createAccounts() {
        //        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        Account account = new Account();

        realm.copyToRealm(account);
        realm.commitTransaction();

    }


    public void loadAuthors(List<Author> allAuthors) {
        //        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
        realm.copyToRealm(allAuthors);

        List<List<String>> records = SqliteHelper.getInstance().getRecords("aut_picture");
        //List<Author> authors = new ArrayList<>();
        //Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records){
            // realm.beginTransaction();
            Author author = realm.where(Author.class).equalTo("idAuthor", Integer.valueOf(record.get(1))).findFirst();
            if (author != null) {
                author.getPictures().add(realm.where(Picture.class).equalTo("idPicture", Integer.valueOf(record.get(2))).findFirst());
                realm.copyToRealmOrUpdate(author);

            }
        }

        records = SqliteHelper.getInstance().getRecords("author_default");
//        List<Author> authors = new ArrayList<>();
//        Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records) {
            Author author = realm.where(Author.class).equalTo("idAuthor", Integer.parseInt(record.get(0))).findFirst();
            Movement movement = realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(8))).findFirst();
            if (author != null) {
                movement.getAuthors().add(author);
                realm.copyToRealmOrUpdate(movement);
            }

            if (!record.get(9).equals("") || !record.get(14).equals("") || !record.get(16).equals("") || !record.get(18).equals("") || !record.get(20).equals("")) {
                RealmResults<Presentation> realmResults = realm.where(Presentation.class).findAll();
                Presentation presentation = realm.createObject(Presentation.class, realmResults.size() + 1);
                //presentation.setIdPresentation(realmResults.size() + 1);
                presentation.setPresentation(android.text.Html.fromHtml(record.get(9)).toString());

                presentation.setPresentationTitle1(android.text.Html.fromHtml(record.get(13)).toString());
                presentation.setPresentation1(android.text.Html.fromHtml(record.get(14)).toString());

                presentation.setPresentationTitle2(android.text.Html.fromHtml(record.get(15)).toString());
                presentation.setPresentation2(android.text.Html.fromHtml(record.get(16)).toString());

                presentation.setPresentationTitle3(android.text.Html.fromHtml(record.get(17)).toString());
                presentation.setPresentation3(android.text.Html.fromHtml(record.get(18)).toString());

                presentation.setPresentationTitle4(android.text.Html.fromHtml(record.get(19)).toString());
                presentation.setPresentation4(android.text.Html.fromHtml(record.get(20)).toString());

                presentation.setSourcePresentation(android.text.Html.fromHtml(record.get(23)).toString());
                //realm.copyToRealmOrUpdate(presentation);

                author.setPresentation(presentation);
            }

            //realm.copyToRealmOrUpdate(author);

        }
        realm.commitTransaction();
    }

    public void loadBooks(List<Book> allBooks) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }



        realm.copyToRealm(allBooks);

        for(Book book : allBooks) {
            Movement movement = book.getMovement();
            movement.getBooks().add(book);
            realm.copyToRealmOrUpdate(movement);
        }

        List<List<String>> records = SqliteHelper.getInstance().getRecords("aut_picture");
        //List<Author> authors = new ArrayList<>();
        //Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records) {
            // realm.beginTransaction();
            Book book = realm.where(Book.class).equalTo("idBook", Integer.valueOf(record.get(1))).findFirst();



            if (book != null) {
                //if (book.getPictures() == null) book.getPictures()
                book.getPictures().add(realm.where(Picture.class).equalTo("idPicture", Integer.valueOf(record.get(2))).findFirst());
                realm.copyToRealmOrUpdate(book);
            }
        }

        records = SqliteHelper.getInstance().getRecords("book");
//        List<Author> authors = new ArrayList<>();
//        Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records) {
//            Book book = realm.where(Book.class).equalTo("idBook", Integer.parseInt(record.get(0))).findFirst();
//
//            for (int i = 0; i < allBooks.size(); i++) {
//                if(allBooks.get(i).getIdBook() == Long.parseLong(record.get(0))) book = allBooks.get(i);
//            }

//            Movement movement = realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(8))).findFirst();
        //    if (record.get(5).equals("1")) {
//                movement.getBooks().add(book);
//                realm.copyToRealmOrUpdate(movement);

            if (!record.get(9).equals("") || !record.get(14).equals("") || !record.get(16).equals("") || !record.get(18).equals("") || !record.get(20).equals("")) {

                Book book = realm.where(Book.class).equalTo("idBook", Integer.parseInt(record.get(0))).findFirst();

                RealmResults<Presentation> realmResults = realm.where(Presentation.class).findAll();
                //Presentation presentation = new Presentation();
                Presentation presentation = realm.createObject(Presentation.class, realmResults.size() + 1);
                //presentation.setIdPresentation(realmResults.size() + 1);
                //presentation.setIdPresentation(realmResults.size() + 1);
                presentation.setPresentation(android.text.Html.fromHtml(record.get(9)).toString());

                presentation.setPresentationTitle1(android.text.Html.fromHtml(record.get(13)).toString());
                presentation.setPresentation1(android.text.Html.fromHtml(record.get(14)).toString());

                presentation.setPresentationTitle2(android.text.Html.fromHtml(record.get(15)).toString());
                presentation.setPresentation2(android.text.Html.fromHtml(record.get(16)).toString());

                presentation.setPresentationTitle3(android.text.Html.fromHtml(record.get(17)).toString());
                presentation.setPresentation3(android.text.Html.fromHtml(record.get(18)).toString());

                presentation.setPresentationTitle4(android.text.Html.fromHtml(record.get(19)).toString());
                presentation.setPresentation4(android.text.Html.fromHtml(record.get(20)).toString());

                presentation.setSourcePresentation(android.text.Html.fromHtml(record.get(23)).toString());
                //realm.copyToRealmOrUpdate(presentation);

                book.setPresentation(presentation);
                //realm.copyToRealmOrUpdate(book);
            }

        //    }
        }

        realm.commitTransaction();
    }

    public void loadCenturies(List<Century> allCenturies) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allCenturies);
        realm.commitTransaction();

    }

    public void createFavorites() {
        //        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        Favorite favorite = new Favorite();
        realm.copyToRealm(favorite);
        realm.commitTransaction();
    }

    public void loadFavorites(List<Favorite> allFavorites) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allFavorites);
        realm.commitTransaction();

    }

    public void loadMovements(List<Movement> allMovements, Boolean isFirstInit) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        if (isFirstInit) realm.copyToRealm(allMovements);
        else {

            List<List<String>> records = SqliteHelper.getInstance().getRecords("cou_picture");
            //List<Author> authors = new ArrayList<>();
            //Realm realm = Realm.getDefaultInstance();
            for (List<String> record : records) {
                // realm.beginTransaction();
                Movement movement = realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(1))).findFirst();
                if (movement != null) {
                    Picture picture = realm.where(Picture.class).equalTo("idPicture", Integer.valueOf(record.get(2))).findFirst();
                    if (picture != null) {
                        movement.getPictures().add(picture);
                        realm.copyToRealmOrUpdate(movement);
                    }
                }
            }

            records = SqliteHelper.getInstance().getRecords("tab_courant_default");
            //  List<Movement> movements = new ArrayList<>();
            for (List<String> record : records) {

                Movement movement = realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(0))).findFirst();
                if (!record.get(1).equals("")) {
                    Movement parentMovement = realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(1))).findFirst();

                    if (!record.get(9).equals("") || !record.get(11).equals("") || !record.get(13).equals("") || !record.get(15).equals("") || !record.get(17).equals("")) {
                        RealmResults<Presentation> realmResults = realm.where(Presentation.class).findAll();
                        Presentation presentation = realm.createObject(Presentation.class, realmResults.size() + 1);
                        //presentation.setIdPresentation(realmResults.size() + 1);
                        presentation.setPresentation(android.text.Html.fromHtml(record.get(9)).toString());

                        presentation.setPresentationTitle1(android.text.Html.fromHtml(record.get(10)).toString());
                        presentation.setPresentation1(android.text.Html.fromHtml(record.get(11)).toString());

                        presentation.setPresentationTitle2(android.text.Html.fromHtml(record.get(12)).toString());
                        presentation.setPresentation2(android.text.Html.fromHtml(record.get(13)).toString());

                        presentation.setPresentationTitle3(android.text.Html.fromHtml(record.get(14)).toString());
                        presentation.setPresentation3(android.text.Html.fromHtml(record.get(15)).toString());

                        presentation.setPresentationTitle4(android.text.Html.fromHtml(record.get(16)).toString());
                        presentation.setPresentation4(android.text.Html.fromHtml(record.get(17)).toString());

                        presentation.setSourcePresentation(android.text.Html.fromHtml(record.get(18)).toString());
                        //realm.copyToRealmOrUpdate(presentation);

                        movement.setPresentation(presentation);
                    }

                    movement.setParentMovement(parentMovement);
                    parentMovement.getMovements().add(movement);


                    realm.copyToRealmOrUpdate(movement);
                    realm.copyToRealmOrUpdate(parentMovement);
                }
            }

            records = SqliteHelper.getInstance().getRecords("tab_courant_default");
            //  List<Movement> movements = new ArrayList<>();
            for (List<String> record : records) {

                Movement movement = realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(0))).findFirst();

                movement.setNbAuthors(movement.getAuthors().size());


                int nbAuthorsQuotes = 0;

                for (Author author : movement.getAuthors()) {
                    nbAuthorsQuotes += author.getQuotes().size();
                }
                movement.setNbAuthorsQuotes(nbAuthorsQuotes);

                movement.setNbBooks(movement.getBooks().size());

                int nbBooksQuotes = 0;

                for (Book book : movement.getBooks()) {
                    nbBooksQuotes += book.getQuotes().size();
                }

                movement.setNbBooksQuotes(nbBooksQuotes);

                movement.setNbQuotes(nbAuthorsQuotes + nbBooksQuotes);

                int nbTotalAuthors = 0;
                int nbAuthorsSubCourants = 0;
                int nbSubCourants = 0;
                int nbTotalQuotes = 0;

                if (movement.getAuthors() != null && movement.getAuthors().size() != 0) {

                    nbTotalAuthors += movement.getAuthors().size();
                    nbAuthorsSubCourants++;

                    for (Author author : movement.getAuthors()) {
                        nbTotalQuotes += author.getQuotes().size();
                    }
                }

                int nbBooksSubCourants = 0;
                int nbTotalBooks = 0;

                if (movement.getBooks() != null && movement.getBooks().size() != 0) {

                    nbTotalBooks += movement.getBooks().size();
                    nbBooksSubCourants++;

                    for (Book book : movement.getBooks()) {
                        nbTotalQuotes += book.getQuotes().size();
                    }
                }

                if (movement.getMovements() != null && movement.getMovements().size() != 0) {

                    nbSubCourants++;

                    for (Movement subCourants : movement.getMovements()) {

                        nbSubCourants++;

                        if (subCourants.getAuthors() != null && subCourants.getAuthors().size() != 0) {

                            nbTotalAuthors += subCourants.getAuthors().size();
                            nbAuthorsSubCourants++;

                            for (Author author : subCourants.getAuthors()) {
                                nbTotalQuotes += author.getQuotes().size();
                            }
                        }

                        if (subCourants.getBooks() != null && subCourants.getBooks().size() != 0) {

                            nbTotalBooks += subCourants.getBooks().size();
                            nbBooksSubCourants++;

                            for (Book book : subCourants.getBooks()) {
                                nbTotalQuotes += book.getQuotes().size();
                            }
                        }

                        if (subCourants.getMovements() != null && subCourants.getMovements().size() != 0) {

                            for (Movement subSubCourants : subCourants.getMovements()) {

                                nbSubCourants++;

                                if (subSubCourants.getAuthors() != null && subSubCourants.getAuthors().size() != 0) {

                                    nbTotalAuthors += subSubCourants.getAuthors().size();
                                    nbAuthorsSubCourants++;

                                    for (Author author : subSubCourants.getAuthors()) {
                                        nbTotalQuotes += author.getQuotes().size();
                                    }
                                }

                                if (subSubCourants.getBooks() != null && subSubCourants.getBooks().size() != 0) {

                                    nbTotalBooks += subSubCourants.getBooks().size();
                                    nbBooksSubCourants++;

                                    for (Book book : subSubCourants.getBooks()) {
                                        nbTotalQuotes += book.getQuotes().size();
                                    }
                                }
                            }
                        }
                    }
                }
                movement.setNbTotalQuotes(nbTotalQuotes);

                movement.setNbTotalAuthors(nbTotalAuthors);
                movement.setNbAuthorsSubcourants(nbAuthorsSubCourants);

                movement.setNbTotalBooks(nbTotalBooks);
                movement.setNbBooksSubcourants(nbBooksSubCourants);

                movement.setNbSubcourants(nbSubCourants);

                //  realm.copyToRealmOrUpdate(movement);

                realm.copyToRealmOrUpdate(movement);
            }
        }
        realm.commitTransaction();

        //if (!isFirstInit) updateMovements(allMovements);
    }

    public void updateMovements(List<Movement> allMovements) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (Movement courant : allMovements) {

            int nbTotalAuthors = 0;
            int nbAuthorsSubCourants = 0;
            int nbSubCourants = 0;
            int nbTotalQuotes = 0;

            if (courant.getAuthors() != null && courant.getAuthors().size() != 0) {

                nbTotalAuthors += courant.getAuthors().size();
                nbAuthorsSubCourants++;

                for (Author author : courant.getAuthors()) {
                    nbTotalQuotes += author.getQuotes().size();
                }
            }

            int nbBooksSubCourants = 0;
            int nbTotalBooks = 0;

            if (courant.getBooks() != null && courant.getBooks().size() != 0) {

                nbTotalBooks += courant.getBooks().size();
                nbBooksSubCourants++;

                for (Book book : courant.getBooks()) {
                    nbTotalQuotes += book.getQuotes().size();
                }
            }

            if (courant.getMovements() != null && courant.getMovements().size() != 0) {

                nbSubCourants++;

                for (Movement subCourants : courant.getMovements()) {

                    nbSubCourants++;

                    if (subCourants.getAuthors() != null && subCourants.getAuthors().size() != 0) {

                        nbTotalAuthors += subCourants.getAuthors().size();
                        nbAuthorsSubCourants++;

                        for (Author author : subCourants.getAuthors()) {
                            nbTotalQuotes += author.getQuotes().size();
                        }
                    }

                    if (subCourants.getBooks() != null && subCourants.getBooks().size() != 0) {

                        nbTotalBooks += subCourants.getBooks().size();
                        nbBooksSubCourants++;

                        for (Book book : subCourants.getBooks()) {
                            nbTotalQuotes += book.getQuotes().size();
                        }
                    }

                    if (subCourants.getMovements() != null && subCourants.getMovements().size() != 0) {

                        for (Movement subSubCourants : subCourants.getMovements()) {

                            nbSubCourants++;

                            if (subSubCourants.getAuthors() != null && subSubCourants.getAuthors().size() != 0) {

                                nbTotalAuthors += subSubCourants.getAuthors().size();
                                nbAuthorsSubCourants++;

                                for (Author author : subSubCourants.getAuthors()) {
                                    nbTotalQuotes += author.getQuotes().size();
                                }
                            }

                            if (subSubCourants.getBooks() != null && subSubCourants.getBooks().size() != 0) {

                                nbTotalBooks += subSubCourants.getBooks().size();
                                nbBooksSubCourants++;

                                for (Book book : subSubCourants.getBooks()) {
                                    nbTotalQuotes += book.getQuotes().size();
                                }
                            }
                        }
                    }
                }
            }
            courant.setNbTotalQuotes(nbTotalQuotes);

            courant.setNbTotalAuthors(nbTotalAuthors);
            courant.setNbAuthorsSubcourants(nbAuthorsSubCourants);

            courant.setNbTotalBooks(nbTotalBooks);
            courant.setNbBooksSubcourants(nbBooksSubCourants);

            courant.setNbSubcourants(nbSubCourants);

            realm.copyToRealmOrUpdate(courant);
        }
        realm.commitTransaction();
    }

    public void loadPictures(List<Picture> allPictures) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allPictures);
        realm.commitTransaction();

    }

    public void loadPresentations(List<Presentation> allPresentations) {
//        this.words =
        // Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allPresentations);
        realm.commitTransaction();

    }

    public void loadQuotes(List<Quote> allQuotes) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allQuotes);

        for(Quote quote : allQuotes) {

            if (quote.getAuthor() != null) {
                quote.getAuthor().getQuotes().add(quote);
                realm.copyToRealmOrUpdate(quote.getAuthor());
            }

            if (quote.getBook() != null) {
                quote.getBook().getQuotes().add(quote);
                realm.copyToRealmOrUpdate(quote.getBook());
            }
        }
        realm.commitTransaction();
    }

    public void loadThemes(List<Theme> allThemes) {
//        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allThemes);

        List<List<String>> records = SqliteHelper.getInstance().getRecords("cat_picture");
        //  List<Author> authors = new ArrayList<>();
        //  Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records){
            // realm.beginTransaction();
            Theme theme = realm.where(Theme.class).equalTo("idTheme", Integer.valueOf(record.get(1))).findFirst();
            if (theme != null) {
                Picture picture = realm.where(Picture.class).equalTo("idPicture", Integer.valueOf(record.get(2))).findFirst();
                if (picture != null) {
                    theme.getPictures().add(picture);
                    realm.copyToRealmOrUpdate(theme);
                }
            }
        }

        records = SqliteHelper.getInstance().getRecords("category_default");

        for(List<String> record : records){

            if(!record.get(1).equals("")) {

                Theme theme = realm.where(Theme.class).equalTo("idTheme", Integer.valueOf(record.get(0))).findFirst();
                Theme parentTheme = realm.where(Theme.class).equalTo("idTheme", Integer.valueOf(record.get(1))).findFirst();

                if (parentTheme != null) {
                    theme.setParentTheme(parentTheme);
                    parentTheme.getThemes().add(theme);

                    realm.copyToRealmOrUpdate(theme);
                    realm.copyToRealmOrUpdate(parentTheme);
                }
            }
        }

        records = SqliteHelper.getInstance().getRecords("quo_category");

        for(List<String> record : records){

            if (!record.get(1).equals("") && !record.get(2).equals("")) {

            Theme theme = realm.where(Theme.class).equalTo("idTheme", Integer.parseInt(record.get(2))).findFirst();

            Quote quote =  realm.where(Quote.class).equalTo("idQuote", Integer.parseInt(record.get(1))).findFirst();
            theme.getQuotes().add(quote);

            if (quote.getAuthor() != null) {
                if (theme.getAuthors() == null) theme.setAuthors(new RealmList<Author>());
                theme.getAuthors().add(quote.getAuthor());
            }

            if (quote.getBook() != null) {
                if (theme.getBooks() == null) theme.setBooks(new RealmList<Book>());
                theme.getBooks().add(quote.getBook());
            }

            realm.copyToRealmOrUpdate(theme);
            }
        }
        realm.commitTransaction();
    }

    public void loadUrls(List<Url> allUrls) {

        //        this.words =
        //Log.d("8888888888",mContext.toString());
        //Realm.ddeleteRealmFile(mContext);
        Realm realm = Realm.getDefaultInstance();
//        words = new RealmList<>();
        realm.beginTransaction();
//        for (Word word : allWords){
//            words.add(realm.copyToRealm())
//        }

        realm.copyToRealm(allUrls);
        realm.commitTransaction();

    }


//    public void loadWords(List<Word> allWords){
////        this.words =
//        Log.d("8888888888",mContext.toString());
//        //Realm.ddeleteRealmFile(mContext);
//        Realm realm = Realm.getDefaultInstance();
////        words = new RealmList<>();
//        realm.beginTransaction();
////        for (Word word : allWords){
////            words.add(realm.copyToRealm())
////        }
//
//        realm.copyToRealm(allWords);
//        realm.commitTransaction();
//
//    }
//    public void addSynonymToWords(List<IntPair> synonyms){
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        for(IntPair pair : synonyms){
//            if(pair.getFirstVal() > 5000 || pair.getSocondVal() > 5000)
//                continue;
//            RealmList<Word> wordSynonyms =  realm.where(Word.class).equalTo("id",pair.getFirstVal()).findFirst().getSynonyms();
//            Log.d("8888888888888",pair.toString());
//            wordSynonyms.add(realm.where(Word.class).equalTo("id",pair.getSocondVal()).findFirst());
//        }
//        realm.commitTransaction();
//    }
//
//    public Word getWord(int id){
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(Word.class).equalTo("id",id).findFirst();
//    }
}

package com.sc.onelittleangel.sqlitetorealm.converter;

import android.content.Context;

import com.sc.onelittleangel.sqlitetorealm.converter.models.Author;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Book;
import com.sc.onelittleangel.sqlitetorealm.converter.models.Century;
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

public class SqliteToRealm {
    private static SqliteToRealm mInstance;
    private static Context mContext;

    public static SqliteToRealm createInstance(Context context){
        return mInstance = new SqliteToRealm(context);
    }
    public static SqliteToRealm getInstance() {
        return mInstance;
    }

    private SqliteToRealm(Context context) {
        this.mContext =context;
    }

//    public List<Word> getAllRawWords(){
//        List<List<String>> records = SqliteHelper.getInstance().getRecords("word");
//        List<Word> words = new ArrayList<>();
//        for(List<String> record : records){
//            Word word = new Word();
//            word.setId(Integer.parseInt(record.get(0)));
//            word.setFavorite(false);
//            word.setText(record.get(1));
//            word.setDefinition(record.get(2));
////            word.setImage(record.get(2));
//            word.setLevel(Integer.parseInt(record.get(5)));
//            words.add(word);
//        }
//        return words;
//    }

    public List<Author> getAllRawAuthors(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("author_default");
        List<Author> authors = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records) {
            Author author = new Author();
            author.setIdAuthor(Integer.parseInt(record.get(0)));
            if (!record.get(1).equals("")) author.setCentury(realm.where(Century.class).equalTo("idCentury", Integer.valueOf(record.get(1))).findFirst());
            author.setMovement(realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(8))).findFirst());
            author.setName(record.get(2));
            author.setSurname(record.get(3));
            author.setDetails(record.get(6));
            author.setPeriod(record.get(7));
            author.setBibliographie(record.get(10));
            if (!record.get(25).equals("")) author.setMainPicture(Integer.parseInt(record.get(25)));
            //author.setMcc1(record.get(30));

            authors.add(author);
        }
        realm.close();
        return authors;
    }

    public void loadAuthorsPictures() {
        List<List<String>> records = SqliteHelper.getInstance().getRecords("aut_picture");
        //List<Author> authors = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records){
                //realm.beginTransaction();
                Author author = realm.where(Author.class).equalTo("idAuthor", Integer.valueOf(record.get(1))).findFirst();
            if (author != null) {
                author.getPictures().add(realm.where(Picture.class).equalTo("idPicture", Integer.valueOf(record.get(2))).findFirst());
                realm.copyToRealmOrUpdate(author);
                //realm.commitTransaction();
            }
        }
    }

    public List<Book> getAllRawBooks(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("book");
        List<Book> books = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();

        for(List<String> record : records){
            Book book = new Book();
            book.setIdBook(Integer.parseInt(record.get(0)));
//           Century century = realm.where(Century.class).equalTo("idCentury", Integer.parseInt(record.get(1))).findFirst();
//            book.setCentury(century);
//
//            Movement movement = realm.where(Movement.class).equalTo("idMovement", Integer.parseInt(record.get(8))).findFirst();
//            book.setMovement(movement);
            if (!record.get(1).equals(""))  book.setCentury(realm.where(Century.class).equalTo("idCentury", Integer.valueOf(record.get(1))).findFirst());
            book.setMovement(realm.where(Movement.class).equalTo("idMovement", Integer.valueOf(record.get(8))).findFirst());

            book.setName(record.get(2));
            //book.setSurname(record.get(3));
            book.setDetails(record.get(6));
            book.setPeriod(record.get(7));
            //book.setBibliographie(record.get(10));
            //book.setMainPicture(Integer.parseInt(record.get(25)));
            book.setMcc1(record.get(30));
//            word.setId(Integer.parseInt(record.get(0)));
//            word.setFavorite(false);
//            word.setText(record.get(1));
//            word.setDefinition(record.get(2));
////            word.setImage(record.get(2));
//            word.setLevel(Integer.parseInt(record.get(5)));
            books.add(book);
        }
        realm.close();
        return books;
    }

    public List<Century> getAllRawCenturies(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("tab_century");
        List<Century> centuries = new ArrayList<>();
        for(List<String> record : records){
            Century century = new Century();
            century.setIdCentury(Integer.parseInt(record.get(0)));
            century.setCentury(record.get(1));
            century.setPresentation(record.get(2));
//            word.setId(Integer.parseInt(record.get(0)));
//            word.setFavorite(false);
//            word.setText(record.get(1));
//            word.setDefinition(record.get(2));
////            word.setImage(record.get(2));
//            word.setLevel(Integer.parseInt(record.get(5)));
            centuries.add(century);
        }
        return centuries;
    }

    public List<Movement> getAllRawMovements(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("tab_courant_default");
        List<Movement> movements = new ArrayList<>();
        for(List<String> record : records){
            Movement movement = new Movement();
            movement.setIdMovement(Integer.parseInt(record.get(0)));
            movement.setMovement(record.get(2));
            movement.setMcc1(record.get(3));
            movement.setMcc2(record.get(4));
            movement.setMcc3(record.get(25));
            movements.add(movement);
        }
        return movements;
    }

    public List<Picture> getAllRawPictures(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("pictures");
        List<Picture> pictures = new ArrayList<>();
        for(List<String> record : records){
            Picture picture = new Picture();
            picture.setIdPicture(Integer.parseInt(record.get(0)));
            picture.setNameSmall(record.get(1));
            picture.setExtension(record.get(2));
            picture.setComment(record.get(3));
            picture.setWidth(Integer.parseInt(record.get(5)));
            picture.setHeight(Integer.parseInt(record.get(6)));
            picture.setPortrait(Integer.parseInt(record.get(7)));
            pictures.add(picture);
        }
        return pictures;
    }

//    public List<Presentation> getAllRawPresentations(){
//        List<List<String>> records = SqliteHelper.getInstance().getRecords("presentation");
//        List<Presentation> presentations = new ArrayList<>();
//        for(List<String> record : records){
//            Presentation presentation = new Presentation();
//
////            word.setId(Integer.parseInt(record.get(0)));
////            word.setFavorite(false);
////            word.setText(record.get(1));
////            word.setDefinition(record.get(2));
//////            word.setImage(record.get(2));
////            word.setLevel(Integer.parseInt(record.get(5)));
//            presentations.add(presentation);
//        }
//        return presentations;
//    }

    public List<Quote> getAllRawQuotes(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("quote_default");
        List<Quote> quotes = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for(List<String> record : records){
            Quote quote = new Quote();
            quote.setIdQuote(Integer.parseInt(record.get(0)));
            quote.setQuote(record.get(2));
            quote.setSource(record.get(3));

            Author author = realm.where(Author.class).equalTo("idAuthor", Integer.valueOf(record.get(1))).findFirst();
            if (author != null) quote.setAuthor(author);
            else {
                Book book = realm.where(Book.class).equalTo("idBook", Integer.valueOf(record.get(1))).findFirst();
                quote.setBook(book);
            }
            quotes.add(quote);
        }
        return quotes;
    }

    public List<Theme> getAllRawThemes(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("category_default");
        List<Theme> themes = new ArrayList<>();
        for(List<String> record : records){
            Theme theme = new Theme();
            theme.setIdTheme(Integer.parseInt(record.get(0)));
            theme.setTheme(record.get(2));
            themes.add(theme);
        }
        return themes;
    }

    public List<Url> getAllRawUrls(){
        List<List<String>> records = SqliteHelper.getInstance().getRecords("url");
        List<Url> urls = new ArrayList<>();
        for(List<String> record : records){
            Url url = new Url();
            urls.add(url);
        }
        return urls;
    }

    public void SqliteToRealm(){
//        SqliteHelper dbs = SqliteHelper.getInstance();
        RealmHelper dbr = RealmHelper.getIntance();

        //dbr.createAccounts();

        List<Picture> pictures = getAllRawPictures();
        dbr.loadPictures(pictures);

        List<Century> centuries = getAllRawCenturies();
        dbr.loadCenturies(centuries);

        //dbr.createFavorites();

        List<Movement> movements = getAllRawMovements();
        dbr.loadMovements(movements, true);


        List<Author> authors = getAllRawAuthors();
        dbr.loadAuthors(authors);

        List<Book> books = getAllRawBooks();
        dbr.loadBooks(books);


        //List<Presentation> presentations = getAllRawPresentations();
        //dbr.loadPresentations(presentations);

        List<Quote> quotes = getAllRawQuotes();
        dbr.loadQuotes(quotes);

        dbr.loadMovements(movements, false);

        List<Theme> themes = getAllRawThemes();
        dbr.loadThemes(themes);

        //List<Url> urls = getAllRawUrls();
        //dbr.loadUrls(urls);
        //List<Author> =

    }



}

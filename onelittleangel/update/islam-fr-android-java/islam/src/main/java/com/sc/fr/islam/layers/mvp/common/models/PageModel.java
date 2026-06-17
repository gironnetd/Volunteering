package com.sc.fr.islam.layers.mvp.common.models;

import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;

import com.sc.fr.islam.transverse.orms.realm.models.Quote;
import com.sc.fr.islam.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;
import com.sc.fr.islam.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.fr.islam.layers.mvp.common.layouts.linearlayouts.PersonalLinearLayout;

import java.util.List;


public class PageModel {

    public ContentTextViewNativeBiography textView;
    public LinearLayout llTxtView;
    public TextViewNative authorBookNameTxtView;
    public TextViewNative detailsTxtView;
    public ContentTextViewNativeBiography sourceTxtView;
    public CardView cardView;
    public PersonalLinearLayout layout;
    public List<ContentTextViewNativeBiography> contentTextViewNatives;


    private int index;
    private String text;
    private String authorBookNameText;
    private String quoteText;
    private String detailText;
    private String sourceText;
    private Quote quote;
    private int idQuote;
    private int color;
    public boolean isAuthor ;
    public boolean isPresentationOrBibliography = false;
    public boolean isFavorites = false;
    public Typeface typeface;

    public PageModel(int index, Quote quote) {

        this.index = index;
        this.text =  quote.getQuote();
        this.quoteText = quote.getQuote();
        if(quote.getAuthor() != null) {
            this.isAuthor = true;
            this.authorBookNameText = quote.getAuthor().getName();
            this.detailText = quote.getAuthor().getDetails();

            this.isPresentationOrBibliography = quote.getAuthor().getPresentation() != null;
        } else if(quote.getBook() != null) {
            this.isAuthor = false;
            this.authorBookNameText = quote.getBook().getName();
            this.detailText = quote.getBook().getDetails();

            this.isPresentationOrBibliography = quote.getBook().getPresentation() != null;
        }


        this.sourceText = quote.getSource();

        this.quote = quote;
        setIndex(index, quote);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index, Quote quote) {
        this.index = index;
        setText(index, quote);
    }

    public String getText() {
        String s = text.trim();
        return "    " + s;
    }

    private void setText(int index, Quote quote) {
        this.text =  quote.getQuote();
        this.quoteText = quote.getQuote();
        if(quote.getAuthor() != null) {
            this.isAuthor = true;
            this.authorBookNameText = quote.getAuthor().getName();
            this.detailText = quote.getAuthor().getDetails();

            this.isPresentationOrBibliography = quote.getAuthor().getPresentation() != null;
        } else if(quote.getBook() != null) {
            this.isAuthor = false;
            this.authorBookNameText = quote.getBook().getName();
            this.detailText = quote.getBook().getDetails();

            this.isPresentationOrBibliography = quote.getBook().getPresentation() != null;
        }
        this.sourceText = quote.getSource();
    }

    public int getColor() {
        return color;
    }

    private void setColor(int color) {
        this.color = color;
    }

    public String getQuoteText() {

        String s = quoteText.trim();
        return "    " + s;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public String getSourceText() {
        return "source : " + sourceText;
    }

    public String getAuthorBookNameText() {
        return authorBookNameText;
    }

    public void setAuthorBookNameText(String authorBookNameText) {
        this.authorBookNameText = authorBookNameText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        //textView.setTypeface(typeface);
        for(ContentTextViewNativeBiography nativeBiography : contentTextViewNatives)
            nativeBiography.setTypeface(typeface);
        sourceTxtView.setTypeface(typeface);
    }
}

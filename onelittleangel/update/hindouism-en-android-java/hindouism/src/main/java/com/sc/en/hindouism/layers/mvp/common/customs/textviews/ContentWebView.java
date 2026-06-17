package com.sc.en.hindouism.layers.mvp.common.customs.textviews;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ContentWebView extends WebView {
  public ContentWebView(Context context) {
    super(context);
  }

  public ContentWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ContentWebView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

//  public ContentWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//    super(context, attrs, defStyleAttr, defStyleRes);
//  }

  public ContentWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
    super(context, attrs, defStyleAttr, privateBrowsing);
  }

  @Override
  public void loadData(String data, String mimeType, String encoding) {
    super.loadData(data, mimeType, encoding);
  }


}

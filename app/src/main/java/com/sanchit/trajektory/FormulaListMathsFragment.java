package com.sanchit.trajektory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class FormulaListMathsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formula_list_maths, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webview3);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/(969)-maths-gyaan-sutra-jee-main.pdf?alt=media&token=8703ab14-fe50-41ff-a4db-64dad935d17d";
        String finalURL = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if(newProgress==100)
                {progressBar.setVisibility(View.GONE);
                  }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(finalURL);
        return view;
    }
}
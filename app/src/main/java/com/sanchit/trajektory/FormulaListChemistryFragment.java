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

public class FormulaListChemistryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formula_list_chemistry, container, false);


        WebView webView = (WebView) view.findViewById(R.id.webview2);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar3);

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/chemistry%20formulas.pdf?alt=media&token=4945f251-395d-4c86-a8f0-ced91241270c";
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
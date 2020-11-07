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

public class FormulaListPhysicsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formula_list_physics, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webview1);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/Formula_List.pdf?alt=media&token=d2b96a55-11e1-49af-b717-136014de5f28";
        String finalURL = "https://docs.google.com/gview?embedded=true&url="+url;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                //getSupportActionBar().setTitle("Loading...");
                if(newProgress==100)
                {progressBar.setVisibility(View.GONE);
                    //getSupportActionBar().setTitle(R.string.app_name);
                    }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(finalURL);

        return view;
    }
}
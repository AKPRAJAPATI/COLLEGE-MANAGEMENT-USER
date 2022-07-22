package com.genral_now_ledge.collegemanagementusers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.genral_now_ledge.collegemanagementusers.R;

public class websitesFragment extends Fragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_websites, container, false);
        webView = view.findViewById(R.id.webViewWebsite);
        webView.getSettings().setJavaScriptEnabled(true);

        //Handling Page Navigation
        webView.setWebViewClient(new WebViewClient());

        //Load a URL on WebView
        webView.loadUrl("https://gcmtaligarh.com/");
        return view;
    }

}
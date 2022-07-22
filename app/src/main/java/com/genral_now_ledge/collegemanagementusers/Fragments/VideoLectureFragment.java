package com.genral_now_ledge.collegemanagementusers.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.genral_now_ledge.collegemanagementusers.R;

public class VideoLectureFragment extends Fragment {
    WebView web_view;
    String url = "https://youtube.com/channel/UCRruemFhUBYOSfFY0hPpF9A";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_lecture, container, false);
        web_view = view.findViewById(R.id.videoYoutube);

        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
progressDialog.show();
        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setGeolocationEnabled(true);
        web_view.setSoundEffectsEnabled(true);

        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });
 web_view.loadUrl(url);

        return view;
    }


}
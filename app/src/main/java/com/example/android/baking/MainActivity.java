package com.example.android.baking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }








    private void displayLicensesAlertDialog() {
        WebView view = (WebView) LayoutInflater.from(this).inflate(R.layout.dialog_license, null);
        view.loadUrl("file:///android_asset/open_source_licenses.html");
        AlertDialog mAlertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(getString(R.string.action_licenses))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}

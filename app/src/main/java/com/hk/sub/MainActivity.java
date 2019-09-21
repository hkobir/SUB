package com.hk.sub;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private ProgressBar progressBar;
private WebView webView;
FrameLayout frameLayout;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //execute load function
        load();
    }






    ////implements option menu activity


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:                  //implement actionBar back arrow button at menu bar
                if (webView.canGoBack()) {

                    webView.goBack();
                }
                else{
                    //exit confirmation Dialog create when web view has no previous content

                    AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Exit Application?");
                    alertDialogBuilder.setMessage("Click Yes to Exit!").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }).setNegativeButton("No",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
                    AlertDialog alertDialog=alertDialogBuilder.create();
                    alertDialog.show();
                }
                return true;
            case R.id.about:
                //push a notification
                NotificationCompat.Builder mBuilder=(NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.mipmap.profile).setContentTitle("Configured by").setContentText("Md. Humayunn Kobir(Dept. of CSE)");
                NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0,mBuilder.build());
                Intent intent=new Intent(this,About_Activity.class);
                this.startActivity(intent);
                return true;
            case R.id.feedback:
                //send feedback
                Intent fintent=new Intent(getApplicationContext(),Feedback.class);
                startActivity(fintent);
                return true;
            case R.id.share:
                //share this apps
                Intent sintent = new Intent(Intent.ACTION_SEND);
                sintent.setType("text/plain");
                String subject="SUB apps";
                String body="This apps is veru useful for SUB faculty and students for visiting SUB official WEB platform.";
                sintent.putExtra(Intent.EXTRA_SUBJECT,subject);
                sintent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(sintent, "Share with"));
                return true;
            case R.id.exit:
                //exit confirmation Dialog create

                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Exit Application?");
                alertDialogBuilder.setMessage("Click Yes to Exit!").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }).setNegativeButton("No",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }







    //loading page
    public void load(){
        frameLayout=(FrameLayout) findViewById(R.id.framelayout);
        progressBar=(ProgressBar)findViewById(R.id.progressID);
        progressBar.setMax(100);
        webView=(WebView)findViewById(R.id.webView);
        WebSettings settings=webView.getSettings();

        //prgress with activity  when new activity started by clicking any link page
        webView.setWebViewClient(new HelpClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                setTitle("Loading");
                if(progress==100){
                    frameLayout.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view, progress);
            }
        });







        //multitouch zoom
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("http://www.stamforduniversity.edu.bd/");
        progressBar.setProgress(0);


//create download file listener
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });





    }


    //back button define
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {

            webView.goBack();
        }
        else {
            //exit confirmation Dialog create

            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder.setMessage("Click Yes to Exit!").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int id) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            }).setNegativeButton("No",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();

                }
            });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
    }







//webclient
    private class HelpClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        frameLayout.setVisibility(View.VISIBLE);
        return true;
    }





    //implement when recieve error
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
    }
}



}

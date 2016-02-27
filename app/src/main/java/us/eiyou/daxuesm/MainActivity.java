package us.eiyou.daxuesm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    ImageView iv_launch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        iv_launch = (ImageView) findViewById(R.id.iv_launch);
        wv = (WebView) findViewById(R.id.wv);
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setBuiltInZoomControls(true);
        ws.setSupportZoom(true);
        ws.setDomStorageEnabled(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setDefaultTextEncodingName("utf-8");
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(-1);
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
            // 重写此方法可以让webview处理https请求
                handler.proceed();
            }
        });
        wv.loadUrl("http://m.daxuesm.com/");
        AnimationUtils.showLaunchAnimation(iv_launch, wv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.getUrl().equals("http://m.daxuesm.com/")) {
                new AlertDialog.Builder(this).setTitle("确认退出吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                Intent home = new Intent(Intent.ACTION_MAIN);
                                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                home.addCategory(Intent.CATEGORY_HOME);
                                startActivity(home);
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
            } else {
                wv.goBack();
            }
        }
        return true;
    }




}

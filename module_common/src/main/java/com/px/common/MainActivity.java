package com.px.common;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.px.common.databinding.ModuleCommonActivityMainBinding;
import com.px.common.retrofit.ResultListener;
import com.px.common.retrofit.RetrofitMaster;
import com.px.common.utils.Logger;
import com.px.common.utils.RxBus;


import java.util.HashMap;

import javax.xml.transform.Result;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private ModuleCommonActivityMainBinding binding;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.module_common_activity_main);
        binding.setOnEvent(new OnEventListener());
        disposable= RxBus.getDefault().subscribe(TestEvent.class).subscribe(new Consumer<TestEvent>() {
            @Override
            public void accept(TestEvent testEvent) throws Exception {
                binding.tvTest.setText(testEvent.getContent());
            }
        });
    }

    public class OnEventListener{
        public void onClick(View view) {
            if(R.id.btStart == view.getId()){
                RetrofitMaster.getInstance("http://ldservice.v1.ldlegacy.com/")
                        .get("ldservice/bvision/channel_type/2/9B67E88314F416F2092AB8ECA6A7C8EDCCE3D6D85A816E6E6F9F919B2E6C277D", new HashMap<String, String>(), ChannelInfo.class)
                        .listener(new ResultListener<ChannelInfo>() {
                            @Override
                            public void onSuccess(Boolean execute, ChannelInfo channelInfo) {
                                Logger.d(channelInfo.toString());
                            }

                            @Override
                            public void onFailure(int code, String message) {
                                Logger.d(message);
                            }
                        });

            }else if(R.id.btUpload == view.getId()){
                verifyStoragePermissions(MainActivity.this);
            }else if(R.id.btReport == view.getId()){
                Logger.d("");
            }else{
                Logger.d("");
            }
        }
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return ;
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }else{
            Logger.d("have permission");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean writeAccepted = false;
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length == 1){
                    writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                }
                break;
            default:
                break;
        }
        if (writeAccepted){
            Logger.d("access");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }
}

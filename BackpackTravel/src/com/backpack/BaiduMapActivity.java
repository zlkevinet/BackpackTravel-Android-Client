
package com.backpack;

import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKOfflineMap;
import com.baidu.mapapi.MKOfflineMapListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;

public class BaiduMapActivity extends MapActivity {
    /** Called when the activity is first created. */
    BMapManager mBMapMan = null;
    MKOfflineMap mOffline = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidu_main);

        mBMapMan = new BMapManager(getApplication());
        mBMapMan.init("08743B0A01C2BAB64F0C5E2FAAD6EC954AD66851", null);
        super.initMapActivity(mBMapMan);

        MapView mMapView = (MapView) findViewById(R.id.baidu_map);
        mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件

        MapController mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
                (int) (116.404 * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        mMapController.setCenter(point); // 设置地图中心点
        mMapController.setZoom(12); // 设置地图zoom级别

        mOffline = new MKOfflineMap();
        mOffline.init(mBMapMan, new MKOfflineMapListener() {
            @Override
            public void onGetOfflineMapState(int type, int state) {
                switch (type) {
                    case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                        // MKOLUpdateElement update =
                        // mOffline.getUpdateInfo(state);
                        // mText.setText(String.format("%s : %d%%",
                        // update.cityName,
                        // update.ratio));
                        break;
                    }
                    case MKOfflineMap.TYPE_NEW_OFFLINE:
                    Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                    break;
                case MKOfflineMap.TYPE_VER_UPDATE:
                    Log.d("OfflineDemo", String.format("new offlinemap ver"));
                    break;
            }
        }
        }
                );

        //扫描离线包
//        int num = mOffline.scan();
    }

    @Override
    protected void onDestroy() {
        if (mBMapMan != null) {
            mBMapMan.destroy();
            mBMapMan = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mBMapMan != null) {
            mBMapMan.stop();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBMapMan != null) {
            mBMapMan.start();
        }
        super.onResume();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}

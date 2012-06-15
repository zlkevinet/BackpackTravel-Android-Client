
package com.backpack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class GoogleMapActivity extends MapActivity {
    /** Called when the activity is first created. */

    // 地图显示控制相关变量定义
    private MapView map = null;
    private MapController mapCon;

    // 菜单项
    final private int menuMode = Menu.FIRST;
    final private int menuExit = Menu.FIRST + 1;
    private int chooseItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_main);

        // 获取MapView
        map = (MapView) findViewById(R.id.google_map);
        // 设置显示模式
        map.setTraffic(true);
        map.setSatellite(false);
        map.setStreetView(true);
        // 设置可以缩放
        map.setBuiltInZoomControls(true);

        // 设置初始地图的中心位置
        GeoPoint geoBeijing = new GeoPoint((int) (39.95 * 1000000), (int) (116.37 * 1000000));
        mapCon = map.getController();
        mapCon.setCenter(geoBeijing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 建立菜单
        menu.add(0, menuMode, 0, "地图模式");
        menu.add(0, menuExit, 1, "退出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case menuExit:
                finish();
                break;
            case menuMode:
                Dialog dMode = new AlertDialog.Builder(this)
                        .setTitle("地图模式设置")
                        .setSingleChoiceItems(R.array.MapMode, chooseItem,
                                new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseItem = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                map.setSatellite(true);
                                // map.setTraffic(false);
                                // map.setStreetView(false);
                                break;
                            case 1:
                                // map.setSatellite(false);
                                map.setTraffic(true);
                                // map.setStreetView(false);
                                break;
                            case 2:
                                // map.setSatellite(false);
                                // map.setTraffic(false);
                                map.setStreetView(true);
                                break;
                            default:
                                break;
                        }
                    }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dMode.show();
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}

package com.karthik.corecommon.Views;

/**
 * Created by karthikrk on 24/09/17.
 */

public interface DashBoardManagedView {
    void loadImage(String url);
    void setDashBoardTitle(String date);
    void setForeCastInfo(String foreCastInfo);
    boolean isLocationPermGranted();
    void askLocationPermission();
}

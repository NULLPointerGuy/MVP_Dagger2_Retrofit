package com.karthik.corecommon.Db;

/**
 * Created by karthikrk on 24/09/17.
 */

public interface CacheManagedView {
    boolean isCachePresent(String date);
    String getFromCache(String date);
    void saveInCache(String date,String json);
}

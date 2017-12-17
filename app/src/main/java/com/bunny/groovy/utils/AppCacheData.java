package com.bunny.groovy.utils;

import com.bunny.groovy.model.PerformerUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * app运行时数据缓存
 * <p>
 * Created by Administrator on 2017/12/13.
 */

public class AppCacheData {
    private static PerformerUserModel userModel = new PerformerUserModel();

    public static PerformerUserModel getPerformerUserModel() {
        return userModel;
    }

    public static void setPerformerUserModel(PerformerUserModel performerUserModel) {
        userModel = performerUserModel;
    }

    private static Map<String, String> fileMap;

    public static Map<String, String> getFileMap() {
        if (fileMap == null) fileMap = new HashMap<>();
        return fileMap;
    }
}
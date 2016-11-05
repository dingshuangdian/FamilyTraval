package com.familytraval.common;

import android.os.Build;

/**
 * Created by dings on 2016/10/25.
 */

public class SdkVersionHelper {

    private SdkVersionHelper() {
    }

    public static int getSdkInt() {
        if (Build.VERSION.RELEASE.startsWith("1.5")) {
            return 3;
        }

        return HelperInternal.getSdkIntInternal();
    }

    private static class HelperInternal {
        private static int getSdkIntInternal() {
            return Build.VERSION.SDK_INT;
        }
    }

}
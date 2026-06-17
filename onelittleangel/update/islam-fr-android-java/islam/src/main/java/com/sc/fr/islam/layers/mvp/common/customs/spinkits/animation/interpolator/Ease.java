package com.sc.fr.islam.layers.mvp.common.customs.spinkits.animation.interpolator;

//import android.view.animation.Interpolator;

//import android.view.animation.Interpolator;

import android.view.animation.Interpolator;

/**
 * Created by ybq.
 */
class Ease {
    public static Interpolator inOut() {
        return PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f);
    }
}

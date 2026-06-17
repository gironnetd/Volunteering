package com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.style;

//import android.animation.ValueAnimator;
//
//import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
//import com.github.ybq.android.spinkit.animation.interpolator.KeyFrameInterpolator;
//import com.github.ybq.android.spinkit.sprite.RingSprite;

import com.nineoldandroids.animation.ValueAnimator;
import com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.animation.SpriteAnimatorBuilder;
import com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.animation.interpolator.KeyFrameInterpolator;
import com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.sprite.RingSprite;

/**
 * Created by ybq.
 */
public class PulseRing extends RingSprite {

    public PulseRing() {
        setScale(0f);
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 0.7f, 1f};
        return new SpriteAnimatorBuilder(this).
                scale(fractions, 0f, 1f, 1f).
                alpha(fractions, 255, (int) (255 * 0.7), 0).
                duration(1000).
                interpolator(KeyFrameInterpolator.pathInterpolator(fractions)).
                build();
    }
}

package com.sc.en.christianism.layers.mvp.common.customs.spinkits.style;

//import android.animation.ValueAnimator;
//
//import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
//import com.github.ybq.android.spinkit.sprite.CircleSprite;

import com.nineoldandroids.animation.ValueAnimator;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.animation.SpriteAnimatorBuilder;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.sprite.CircleSprite;

public class RotatingCircle extends CircleSprite {

    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 0.5f, 1f};
        return new SpriteAnimatorBuilder(this).
                rotateX(fractions, 0, -180, -180).
                rotateY(fractions, 0, 0, -180).
                duration(1200).
                easeInOut(fractions)
                .build();
    }
}

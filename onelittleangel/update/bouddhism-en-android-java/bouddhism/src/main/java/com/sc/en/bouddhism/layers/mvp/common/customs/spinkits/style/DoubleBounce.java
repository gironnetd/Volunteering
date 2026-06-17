package com.sc.en.bouddhism.layers.mvp.common.customs.spinkits.style;

//import android.animation.ValueAnimator;
import android.os.Build;

import com.nineoldandroids.animation.ValueAnimator;
import com.sc.en.bouddhism.layers.mvp.common.customs.spinkits.sprite.CircleSprite;
import com.sc.en.bouddhism.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.bouddhism.layers.mvp.common.customs.spinkits.sprite.SpriteContainer;
import com.sc.en.bouddhism.layers.mvp.common.customs.spinkits.animation.SpriteAnimatorBuilder;

//import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
//import com.github.ybq.android.spinkit.sprite.CircleSprite;
//import com.github.ybq.android.spinkit.sprite.Sprite;
//import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/**
 * Created by ybq.
 */
public class DoubleBounce extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Bounce(), new Bounce()
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sprites[1].setAnimationDelay(1000);
        } else {
            sprites[1].setAnimationDelay(-1000);
        }
    }

    private class Bounce extends CircleSprite {

        Bounce() {
            setAlpha(153);
            setScale(0f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.5f, 1f};
            return new SpriteAnimatorBuilder(this).scale(fractions, 0f, 1f, 0f).
                    duration(2000).
                    easeInOut(fractions)
                    .build();
        }
    }
}

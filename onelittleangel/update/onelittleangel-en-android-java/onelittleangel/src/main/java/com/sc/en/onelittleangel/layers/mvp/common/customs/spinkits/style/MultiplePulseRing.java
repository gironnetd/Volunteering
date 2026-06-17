package com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.style;

//import com.github.ybq.android.spinkit.sprite.Sprite;
//import com.github.ybq.android.spinkit.sprite.SpriteContainer;

import com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.onelittleangel.layers.mvp.common.customs.spinkits.sprite.SpriteContainer;

/**
 * Created by ybq.
 */
public class MultiplePulseRing extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new PulseRing(),
                new PulseRing(),
                new PulseRing(),
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].setAnimationDelay(200 * (i + 1));
        }
    }
}

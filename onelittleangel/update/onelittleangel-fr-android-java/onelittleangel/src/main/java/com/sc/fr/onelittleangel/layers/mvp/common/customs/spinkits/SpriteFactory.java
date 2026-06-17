package com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits;


import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.ChasingDots;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.Circle;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.CubeGrid;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.DoubleBounce;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.FadingCircle;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.FoldingCube;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.MultiplePulse;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.MultiplePulseRing;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.Pulse;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.PulseRing;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.RotatingCircle;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.RotatingPlane;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.ThreeBounce;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.WanderingCubes;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.spinkits.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}

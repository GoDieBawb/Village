package mygame;

import com.jme3.input.event.TouchEvent;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.extras.android.Joystick;
import tonegod.gui.core.ElementManager;
import tonegod.gui.listeners.TouchListener;

public abstract class MyJoystick extends Joystick implements TouchListener {
 
    public MyJoystick(ElementManager screen, Vector2f position, int size) {
        super(screen, position, size);
    }
 
    @Override
    public void onTouchDown(TouchEvent evt) {
        evt.setConsumed();
    }
 
    @Override
    public void onTouchMove(TouchEvent evt) {
        evt.setConsumed();
    }
 
    @Override
    public void onTouchUp(TouchEvent evt) {
        evt.setConsumed();
    }
}



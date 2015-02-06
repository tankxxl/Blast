package com.blast.app.events;

/**
 * Created by rgz on 2014/7/14.
 */
public class DrawerNavigationItemClickedEvent {

    public String section;
    public int pos;

    public DrawerNavigationItemClickedEvent(int pos, String section) {
        this.pos = pos;
        this.section = section;
    }
}

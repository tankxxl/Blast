package com.blast.app.models;

/**
 * Created by rgz on 2014/7/14.
 */
public class DrawerItem {

    private int drawerIcon;
    private String drawerText;

    public DrawerItem(String drawerText, int drawerIcon) {
        this.drawerText = drawerText;
        this.drawerIcon = drawerIcon;
    }

    public int getDrawerIcon() {
        return drawerIcon;
    }

    public void setDrawerIcon(int drawerIcon) {
        this.drawerIcon = drawerIcon;
    }

    public String getDrawerText() {
        return drawerText;
    }

    public void setDrawerText(String drawerText) {
        this.drawerText = drawerText;
    }
}

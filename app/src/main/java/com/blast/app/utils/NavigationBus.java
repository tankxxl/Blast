package com.blast.app.utils;

import com.squareup.otto.Bus;

/**
 * Created by rgz on 2014/7/14.
 */
public class NavigationBus extends Bus {
    private static final NavigationBus navigationBus = new NavigationBus();

    public static NavigationBus getInstance() {
        return navigationBus;
    }

    private NavigationBus() {

    }
}

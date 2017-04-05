package com.raphaellevy.midi2.controller;

import com.raphaellevy.midi2.view.SequencerView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Manages the behavior of the SequencerView
 */
public class SequencerController {
    
    /**
     * The view controlled by this controller
     */
    private SequencerView view = null;
    
    /**
     * Make a new controller
     */
    public SequencerController() {
    
    }
    
    public void display() {
        view = SequencerView.open(this);
    }
}

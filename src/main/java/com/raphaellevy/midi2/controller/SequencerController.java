package com.raphaellevy.midi2.controller;

import com.raphaellevy.midi2.view.SequencerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/**
 * Manages the behavior of the SequencerView
 */
public class SequencerController implements ActionListener{
    
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

    /**
     * ActionEvent listener for the SequencerView
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.contains("noteButton")) {
            System.out.println(String.format("Note %s pressed!", command.charAt(0)));
        }
    }
}

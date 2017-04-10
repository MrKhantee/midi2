package com.raphaellevy.midi2.view;

import com.raphaellevy.midi2.controller.MenuBarController;
import com.raphaellevy.midi2.controller.SequencerController;

import javax.swing.*;
import java.awt.*;

/**
 * The menu bar for the application
 */
class MMenuBar extends JMenuBar {
    
    /**
     * The file menu
     */
    private JMenu fileMenu;
    
    /**
     * The "Sequence" menu
     */
    private JMenu sequenceMenu;
    
    /**
     * The help menu
     */
    private JMenu helpMenu;
    
    private final MenuBarController controller;
    
    MMenuBar(SequencerController scontroller) {
        super();
        controller = new MenuBarController(scontroller);
        addFileMenu();
        addSequenceMenu();
        addHelpMenu();
    }
    
    private void addFileMenu() {
        fileMenu = new JMenu("File");
        add(fileMenu);
    }
    
    private void addSequenceMenu() {
        sequenceMenu = new JMenu("Sequence");
        add(sequenceMenu);
    }
    
    private void addHelpMenu() {
        helpMenu = new JMenu("Help");
        add(helpMenu);
    }
}

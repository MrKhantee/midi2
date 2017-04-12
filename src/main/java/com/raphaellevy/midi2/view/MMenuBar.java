package com.raphaellevy.midi2.view;

import com.raphaellevy.midi2.controller.MenuBarController;
import com.raphaellevy.midi2.controller.SequencerController;

import javax.swing.*;
import java.awt.event.ActionListener;

import static java.awt.event.InputEvent.META_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.*;

/**
 * The menu bar for the application
 */
class MMenuBar extends JMenuBar {

    private final MenuBarController controller;
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

    MMenuBar(SequencerController scontroller) {
        super();
        controller = new MenuBarController(scontroller);
        addFileMenu();
        addSequenceMenu();
        addHelpMenu();
    }

    private void addFileMenu() {
        fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(VK_N, META_DOWN_MASK));
        newItem.addActionListener(e -> controller.newItem());
        fileMenu.add(newItem);

        fileMenu.addSeparator();

        addMenuItem(fileMenu, "Open", KeyStroke.getKeyStroke(VK_O, META_DOWN_MASK), e -> controller.openItem());

        fileMenu.addSeparator();

        addMenuItem(fileMenu, "Save", KeyStroke.getKeyStroke(VK_S, META_DOWN_MASK), e -> controller.saveItem());

        add(fileMenu);
    }

    private void addSequenceMenu() {
        sequenceMenu = new JMenu("Sequence");

        addMenuItem(sequenceMenu, "Clear", KeyStroke.getKeyStroke(VK_D, META_DOWN_MASK | SHIFT_DOWN_MASK), e -> controller.clearItem());
        addMenuItem(sequenceMenu, "Delete", KeyStroke.getKeyStroke(VK_D, META_DOWN_MASK), e -> controller.deleteItem());

        sequenceMenu.addSeparator();

        addMenuItem(sequenceMenu, "Play", KeyStroke.getKeyStroke(VK_P, META_DOWN_MASK), e -> controller.playItem());
        addMenuItem(sequenceMenu, "Stop", KeyStroke.getKeyStroke(VK_T, META_DOWN_MASK), e -> controller.stopItem());

        sequenceMenu.addSeparator();

        addMenuItem(sequenceMenu, "Add Rest", KeyStroke.getKeyStroke(VK_R, META_DOWN_MASK), e -> controller.addRestItem());
        addMenuItem(sequenceMenu, "Continue Previous Note", KeyStroke.getKeyStroke(VK_C, META_DOWN_MASK), e -> controller.continueItem());

        add(sequenceMenu);
    }

    private void addHelpMenu() {
        helpMenu = new JMenu("Help");

        addMenuItem(helpMenu, "Show Instructions", KeyStroke.getKeyStroke(VK_I,META_DOWN_MASK), e -> controller.instructionsItem());

        add(helpMenu);
    }

    private void addMenuItem(JMenu menu, String text, KeyStroke keyStroke, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setAccelerator(keyStroke);
        item.addActionListener(listener);
        menu.add(item);
    }

    ;
}

package com.raphaellevy.midi2.controller;

/**
 * Handles menu events
 */
public class MenuBarController {

    /**
     * The sequencer controller
     */
    private final SequencerController parent;
    
    public MenuBarController(SequencerController parent) {
        this.parent = parent;
    }

    public void newItem() {
        System.out.println("NEW Chosen");
    }

    public void openItem() {
        System.out.println("OPEN Chosen");
    }

    public void saveItem() {
        System.out.println("SAVE Chosen");
    }

    public void saveAsItem() {
        System.out.println("SAVE AS Chosen");
    }

    public void deleteItem() {

    }

    public void clearItem() {

    }

    public void playItem() {

    }

    public void stopItem() {

    }

}
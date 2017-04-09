package com.raphaellevy.midi2.controller;

import com.raphaellevy.midi2.Midi2;
import com.raphaellevy.midi2.midi.EasySeq;
import com.raphaellevy.midi2.view.SequencerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages the behavior of the SequencerView, handles events
 */
public class SequencerController implements ActionListener {
    
    /**
     * The application
     */
    private final Midi2 app;
    
    /**
     * The view controlled by this controller
     */
    private SequencerView view = null;
    
    /**
     * The sequencer
     */
    private EasySeq seq;
    
    /**
     * Make a new controller
     */
    public SequencerController(Midi2 app) {
        this.app = app;
    }
    
    /**
     * Open a sequencer view
     */
    public void display() {
        view = SequencerView.open(this);
    }
    
    /**
     * Sets the MIDI sequencer
     *
     * @param seq the sequencer
     */
    public void setSequencer(EasySeq seq) {
        this.seq = seq;
    }
    
    /**
     * ActionEvent listener for the SequencerView; handles events
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.contains("noteButton")) {
            System.out.println(String.format("Note %s pressed!", command.charAt(0)));
            SequencerView.NoteButton b = (SequencerView.NoteButton) e.getSource();
            app.notePressed(b.note);
        }
    }
}

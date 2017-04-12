package com.raphaellevy.midi2.controller;

import com.raphaellevy.midi2.Midi2;
import com.raphaellevy.midi2.MidiSequence;
import com.raphaellevy.midi2.NoteUtil;
import com.raphaellevy.midi2.midi.EasySeq;
import com.raphaellevy.midi2.view.SequencerView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

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
     * Set the displayed MIDI sequence
     */
    public void setMidiSequence(MidiSequence sequence) {
        for (int i = 0; i < 5; i++) {
            int note = sequence.getNote(i);
            view.setNoteText(i, NoteUtil.asString(note));
        }
    }

    /**
     * Set the selected note
     *
     * @param index the index of the note to select
     */
    public void setSelected(int index) {
        unselect();
        view.setNoteColor(index, new Color(0xFFEBE6));
    }

    /**
     * Unselects the selected note
     */
    public void unselect() {
        view.resetNoteColors();
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

    Midi2 getApp() {
        return app;
    }

    /**
     * Listens for key presses
     */
    public final class MKeyListener implements KeyListener {

        private boolean meta = false;
        private boolean shift = false;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() >= VK_A && e.getKeyCode() <= VK_G) {
                if (!shift && !meta) {
                    app.notePressed(NoteUtil.getNote(String.valueOf(Character.toUpperCase(e.getKeyChar()))));
                }
            } else if (e.getKeyCode() == VK_BACK_SPACE) {
                app.deleteNote();
            } else if (e.getKeyCode() == VK_SPACE) {
                app.addRest();
            } else if (e.getKeyCode() == VK_META) {
                meta = true;
            } else if (e.getKeyCode() == VK_SHIFT) {
                shift = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == VK_META) {
                meta = false;
            } else if (e.getKeyCode() == VK_SHIFT) {
                shift = false;
            }
        }
    }
}

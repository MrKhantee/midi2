package com.raphaellevy.midi2;

import com.raphaellevy.midi2.controller.SequencerController;
import com.raphaellevy.midi2.midi.EasySeq;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static com.raphaellevy.midi2.MidiSequence.CONTINUE;
import static com.raphaellevy.midi2.MidiSequence.REST;

/**
 * The main class for the application.
 * <p>
 * My project uses a Model-View-Controller (MVC) pattern. The View represents the actual components displayed
 * on the screen. This is implemented in SequencerView. The Controller (SequencerController)
 * provides an interface between the Model and the View and handles events. The Model, which is everything not
 * in the controller or view package, is the actual application code, which does the MIDI and file IO stuff.
 */
public class Midi2 {
    public static final boolean MUTED = false;

    /**
     * The 12 point Lato font
     */
    @SuppressWarnings("WeakerAccess")
    public static Font lato12;

    /**
     * The sequencer
     */
    private EasySeq seq;

    /**
     * The SequencerController
     */
    private SequencerController controller;

    /**
     * The position of the note currently being edited
     */
    private int cursor = 0;

    private MidiSequence sequence = new MidiSequence();

    /**
     * Main method
     *
     * @param args the command line args.
     */
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        Midi2 app = new Midi2();
        app.start();
    }

    /**
     * Start the application.
     */
    private void start() {
        loadFonts();

        //Create the controller, which will create the window.
        controller = new SequencerController(this);
        controller.display();
        controller.setSelected(cursor);

        //Set up midi
        seq = new EasySeq();
        controller.setSequencer(seq);
    }

    /**
     * Set up fonts
     */
    private void loadFonts() {
        try {
            Path dir = Paths.get(System.getProperty("user.home")).resolve("Library/Application Support/Midi2");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Path latoPath = dir.resolve("lato.ttf");
            Files.copy(getClass().getResourceAsStream("/fonts/lato.ttf"), latoPath, StandardCopyOption.REPLACE_EXISTING);
            lato12 = Font.createFont(Font.TRUETYPE_FONT, latoPath.toFile());
        } catch (Exception e) {
            System.out.println("PROBLEM LOADING FONTS!!!!!! UH OH!!!!!!!");
            e.printStackTrace();
        }
    }

    /**
     * Called when a note is inputted
     */
    public void notePressed(int note) {
        seq.stop();
        seq.addNote(note, 200);

        sequence = sequence.withNote(note, cursor);
        controller.setMidiSequence(sequence);

        if (cursor < 4) {
            cursor++;
            controller.setSelected(cursor);
        }
    }

    /**
     * Called when the sequence should be played
     */
    public void playSequence() {
        sequence.play(seq);
    }

    /**
     * Called when the user has asked for
     * the sequence to stop playing.
     */
    public void stopPlaying() {
        seq.stop();
    }

    /**
     * Called when a rest should be added
     */
    public void addRest() {
        notePressed(REST);
    }

    /**
     * Called when the previous note should be continued
     */
    public void addContinue() {
        if (cursor == 0) {
            JOptionPane.showMessageDialog(null, "There's no note to continue!", "Invalid Continue", JOptionPane.WARNING_MESSAGE);
        } else {
            notePressed(CONTINUE);
        }
    }

    /**
     * Called when all notes should be cleared
     */
    public void clearNotes() {
        cursor = 0;
        controller.setSelected(cursor);

        sequence = new MidiSequence();
        controller.setMidiSequence(sequence);
    }

    /**
     * Called when a note should be deleted
     */
    public void deleteNote() {
        if (cursor == 0) {
            JOptionPane.showMessageDialog(null, "There's no note to delete!", "Invalid Delete", JOptionPane.WARNING_MESSAGE);
        } else {
            if (sequence.getNote(cursor) == REST) {
                cursor--;
                controller.setSelected(cursor);

                sequence = sequence.withNote(REST, cursor);
                controller.setMidiSequence(sequence);
            } else {
                sequence = sequence.withNote(REST, cursor);
                controller.setMidiSequence(sequence);
            }
        }
    }

    /**
     * Called when the file should be saved.
     */
    public void save() {
        MidiFileIO.saveMIDIFile(sequence);
    }

    public void open() {
        Optional<MidiSequence> sequenceOptional = MidiFileIO.loadMIDIFile();
        if (sequenceOptional.isPresent()) sequenceOptional.ifPresent(inSequence -> {
            cursor = 0;
            controller.setSelected(cursor);

            sequence = inSequence;
            controller.setMidiSequence(sequence);
        });
        else {
            JOptionPane.showMessageDialog(null, "Failed to open file!", "OH NO!!!", JOptionPane.WARNING_MESSAGE);
        }

    }
}

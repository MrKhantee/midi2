package com.raphaellevy.midi2.midi;

import com.raphaellevy.midi2.Midi2;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Midi wrapper; MidiLatte wasn't working well for me so I made my own version.
 */
public class EasySeq {
    
    /**
     * The thread on which notes happen
     */
    private final Thread t;
    /**
     * The synthesizer being used to play MIDI
     */
    private Synthesizer synth;
    /**
     * The midi channels
     */
    private MidiChannel[] channels;
    /**
     * The notes to be played
     */
    private LinkedBlockingQueue<Note> notes = new LinkedBlockingQueue<>();
    
    /**
     * Make a new easy sequencer
     */
    public EasySeq() {
        try {
            //Get the default midi synth
            synth = MidiSystem.getSynthesizer();
            
            //Open it up
            if (!synth.isOpen() && !Midi2.MUTED) synth.open();
            
            //Get the channels
            channels = synth.getChannels();
            
            //Load instruments
            synth.loadAllInstruments(synth.getDefaultSoundbank());
        } catch (MidiUnavailableException e) {
            System.err.println("MIDI UNAVAILABLE!!!!!!!!!!");
            e.printStackTrace();
            System.exit(1);
        }
        t = new Thread(() -> {
            while (true) {
                try {
                    Note note = notes.take();
                    if (note.note >= 0) {
                        channels[0].noteOn(note.note, note.velocity);
                        System.out.println("On");
                        Thread.sleep(note.length);
                        System.out.println("Off");
                        channels[0].noteOff(note.note);
                    } else {
                        Thread.sleep(note.length);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupt");
                    synchronized (this) {
                        this.notifyAll();
                    }
                }
            }
        });
        t.start();
    }
    
    /**
     * Add a note to the queue of notes to be played
     *
     * @param note the note to play
     * @param mils how long
     */
    public synchronized void addNote(int note, int mils) {
        notes.add(new Note(note, mils));
    }
    
    /**
     * Add a rest
     *
     * @param mils how long
     */
    public synchronized void addRest(int mils) {
        notes.add(new Note(-1, mils));
    }
    
    /**
     * Stop everything
     */
    public synchronized void stop() {
        channels[0].allSoundOff();
        notes.clear();
        t.interrupt();
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Represents a note to be played
     */
    private static final class Note {
        final int note;
        final int length;
        final int velocity;
        
        /**
         * Make a new note of given length. A negative note value represents a rest.
         *
         * @param note   Which note
         * @param length How long
         */
        private Note(int note, int length) {
            this(note, length, 100);
        }
        
        /**
         * Make a new note of given length and velocity. A negative note value represents a rest.
         *
         * @param note     Which note
         * @param length   How long
         * @param velocity How loud
         */
        private Note(int note, int length, int velocity) {
            this.note = note;
            this.length = length;
            this.velocity = velocity;
        }
    }
}

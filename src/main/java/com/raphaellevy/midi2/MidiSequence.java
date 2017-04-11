package com.raphaellevy.midi2;

import com.raphaellevy.midi2.midi.EasySeq;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Represents a sequence of MIDI Notes. This class is immutable.
 */
public final class MidiSequence {

    public static final int NOTE_LENGTH = 400;

    /**
     * Represents a rest
     */
    public static final int REST = -1;

    /**
     * Represents a note held over from the last beat
     */
    public static final int CONTINUE = -2;

    /**
     * The notes in this sequence; initialized to a series of rests
     */
    private final int[] notes;

    /**
     * Get a new MidiSequence consisting of rests (-1).
     */
    public MidiSequence() {
        this(new int[]{REST, REST, REST, REST, REST});
    }

    /**
     * Get a new MidiSequence with the given notes. REST represents a rest while CONTINUE represents a continued note
     * @param notes
     */
    public MidiSequence(int[] notes) {
        if (notes.length != 5) {
            throw new UnsupportedOperationException("Array must be length 5");
        }
        this.notes = notes;

    }

    /**
     * @return An array containing the contents of this sequence
     */
    public int[] getNotes() {
        return Arrays.copyOf(notes, notes.length);
    }

    /**
     * Returns a new MidiSequence like this one, but with the given note at the given index.
     *
     * @param note  The note to set it to
     * @param index The index to set
     */
    public MidiSequence withNote(int note, int index) {
        if (note == CONTINUE && index == 0) throw new IllegalArgumentException("Cannot start with continue");
        int[] newNotes = getNotes();
        newNotes[index] = note;
        return new MidiSequence(newNotes);
    }

    /**
     * Stops the currently playing notes on sequencer and plays the contents of this {@link MidiSequence}.
     * @param sequencer The sequencer to play on.
     */
    public void play(EasySeq sequencer) {
        sequencer.stop();
        playLater(sequencer);
    }

    /**
     * Plays the contents of this MidiSequence on the given {@link EasySeq}, after the currently playing notes finish.
     * @param sequencer The sequencer to play on.
     */
    public void playLater(EasySeq sequencer) {
        for (int i = 0; i < 5;) {
            if (notes[i] == REST) {
                int restlength = 1;
                for (int j = i + 1; j < 5 && notes[j] == CONTINUE; j++) {
                    restlength++;
                    System.out.println("Continued rest");
                }
                sequencer.addRest(NOTE_LENGTH * restlength);
                i += restlength;
            } else if (notes[i] == CONTINUE) {
                throw new IllegalStateException(String.format("There shouldn't be a continue here -- at position %d",i));
            } else {
                int notelength = 1;
                for (int j = i + 1; j < 5 && notes[j] == CONTINUE; j++) {
                    notelength++;
                    System.out.println("Continued note");
                }
                sequencer.addNote(notes[i], NOTE_LENGTH*notelength);
                i += notelength;
            }
        }
    }
}

package com.raphaellevy.midi2;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import static com.raphaellevy.midi2.MidiSequence.REST;
import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Deals with saving and loading files
 */
public final class MidiFileIO {
    public static void saveMIDIFile(MidiSequence sequence) {
        try {
            JFileChooser fc = new JFileChooser();
            int status = fc.showSaveDialog(null);
            if (status == APPROVE_OPTION) {
                Path p = fc.getSelectedFile().toPath();
                Files.deleteIfExists(p);
                try (BufferedWriter bw = Files.newBufferedWriter(p)) {
                    for (int note : sequence.getNotes()) {
                        bw.write(String.valueOf(note) + " ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<MidiSequence> loadMIDIFile() {
        try {
            JFileChooser fc = new JFileChooser();
            int status = fc.showOpenDialog(null);
            if (status == APPROVE_OPTION) {
                Path p = fc.getSelectedFile().toPath();
                try (Scanner scan = new Scanner(Files.newInputStream(p))) {
                    int[] notes = new int[5];
                    for (int i = 0; i < 5; i++) {
                        try {
                            notes[i] = scan.nextInt();
                        } catch (NoSuchElementException e) {
                            notes[i] = REST;
                        }
                    }
                    return Optional.of(new MidiSequence(notes));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

package com.raphaellevy.midi2;

import com.raphaellevy.midi2.controller.SequencerController;
import com.raphaellevy.midi2.midi.EasySeq;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        SequencerController controller = new SequencerController(this);
        controller.display();
        
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
    }
}

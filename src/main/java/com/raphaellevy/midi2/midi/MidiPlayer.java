package com.raphaellevy.midi2.midi;

import lattelib.MidiLatte;

import javax.sound.midi.Sequencer;
import java.lang.reflect.Field;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * Responsible for dealing with the thread stuff needed to use the midilatte with Swing.
 */
public final class MidiPlayer {

    /**
     * Holds scheduled tasks
     */
    private final LinkedBlockingQueue<Consumer<MidiLatte>> tasks = new LinkedBlockingQueue<>();
    /**
     * The midilatte
     */
    private volatile MidiLatte midiLatte;
    /**
     * The thread on which tasks are run
     */
    private Thread thread;

    /**
     * Whether currently playing sequences should be stopped
     */
    private volatile boolean shouldStop = false;

    /**
     * Get a new midiplayer, with a new midilatte and thread.
     */
    public MidiPlayer() {
        thread = new MPThread();
        thread.start();
    }

    /**
     * Schedule a midi action
     *
     * @param action the action to schedule
     */
    public void addAction(Consumer<MidiLatte> action) {
        tasks.add(action);
    }

    /**
     * Cancel all pending actions and stop currently running midi
     */
    public void cancelPending() {
        tasks.clear();
        shouldStop = true;
        thread.interrupt();
    }

    /**
     * The thread on which midi actions are run
     */
    private final class MPThread extends Thread {

        /**
         * Run the thread
         */
        @Override
        public void run() {
            midiLatte = new MidiLatte();
            //This loop waits for tasks to become available
            while (true) {
                if (shouldStop) {
                    stopMidi();
                    shouldStop = false;
                }
                try {
                    tasks.take().accept(midiLatte);
                } catch (InterruptedException e) {
                    System.out.println("MPThread interrupted");
                }
            }
        }
        private void stopMidi() {
            try {
                Field f = MidiLatte.class.getDeclaredField("player");
                f.setAccessible(true);
                Sequencer s = (Sequencer) f.get(midiLatte);
                s.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

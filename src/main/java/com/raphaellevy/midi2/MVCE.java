package com.raphaellevy.midi2;

import com.raphaellevy.midi2.midi.EasySeq;
import lattelib.MidiLatte;

import javax.sound.midi.*;
import java.lang.reflect.Field;

/**
 * Created by student on 4/6/17.
 */
public class MVCE {
    public static void old(String args[]) {
        MidiLatte midiLatte = new MidiLatte();

        for (int i = 60; i <= 72; i++) {
            midiLatte.addNote(i, 4);
        }
        midiLatte.playAndRemove();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Field f = MidiLatte.class.getDeclaredField("player");
            f.setAccessible(true);
            Sequencer s = (Sequencer) f.get(midiLatte);
            s.stop();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (int i = 48; i <= 60; i++) {
            midiLatte.addNote(i, 4);
        }
        midiLatte.playAndRemove();
    }
    public static void main(String args[]) throws MidiUnavailableException, InterruptedException {
        EasySeq s = new EasySeq();
        for (int i = 60; i<=72; i++) {
            s.addNote(i, 400);
            s.addRest(400);
            s.addNote(i, 400);
            s.addNote(i, 400);
        }

    }

}

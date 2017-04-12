package com.raphaellevy.midi2;

import static com.raphaellevy.midi2.MidiSequence.CONTINUE;
import static com.raphaellevy.midi2.MidiSequence.REST;

/**
 * Just makes it easier to get given notes
 */
public final class NoteUtil {

    public static final int C = 60;
    public static final int D = 62;
    public static final int E = 64;
    public static final int F = 65;
    public static final int G = 67;
    public static final int A = 69;
    public static final int B = 71;
    public static final int C5 = 72;

    private NoteUtil() {
    }

    public static int getNote(String note) {
        switch (note) {
            case "C":
                return C;
            case "D":
                return D;
            case "E":
                return E;
            case "F":
                return F;
            case "G":
                return G;
            case "A":
                return A;
            case "B":
                return B;
            case "C5":
                return C5;
            case "—":
                return CONTINUE;
            case "":
                return REST;
            default:
                throw new InvalidNoteException(note);
        }
    }

    public static String asString(int note) {
        switch (note) {
            case C:
                return "C";
            case D:
                return "D";
            case E:
                return "E";
            case F:
                return "F";
            case G:
                return "G";
            case A:
                return "A";
            case B:
                return "B";
            case C5:
                return "C5";
            case CONTINUE:
                return "—";
            case REST:
                return "";
            default:
                throw new InvalidNoteException(note);
        }
    }

    private static final class InvalidNoteException extends RuntimeException {
        private InvalidNoteException(String note) {
            super("Invalid note String: " + note);
        }

        private InvalidNoteException(int note) {
            super("Invalid note value: " + note);
        }
    }
}

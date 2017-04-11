import com.raphaellevy.midi2.MidiSequence;
import com.raphaellevy.midi2.midi.EasySeq;
import org.junit.Test;

import static com.raphaellevy.midi2.MidiSequence.CONTINUE;
import static com.raphaellevy.midi2.MidiSequence.REST;

/**
 * Tests the MidiSequence class
 */
public class MidiSequenceTest {

    @Test
    public void testMidiSequence() throws InterruptedException {
        EasySeq sequencer = new EasySeq();
        MidiSequence seq = new MidiSequence(new int[]{60,CONTINUE,60,REST,CONTINUE});
        seq.play(sequencer);

        MidiSequence aba = new MidiSequence();
        aba.withNote(60,0).withNote(72,1).withNote(CONTINUE, 2).withNote(48,4).playLater(sequencer);

        Thread.sleep(5000);
    }
}

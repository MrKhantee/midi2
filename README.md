# Description

My project for the MIDI assignment. This is a MIDI sequencer
which allows for the input and playback of five notes and the
saving of those notes to a file which can be later loaded.

# Building

### With Maven
If you have Maven, do:

    mvn install
    
You will find the JAR in the `target` directory. You can build JavaDocs
as follows:

    mvn javadoc:javadoc
    
The JavaDoc will be in `target/site/apidocs`

### With Eclipse
From the *File* menu, choose *Import...*. In the dialog that pops up,
expand the *Maven* submenu and choose *Existing Maven Projects*. For the
"Root Directory" option, choose the project directory.
package com.apprentice.rpg.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.Logger;

/**
 * Courtesy of "oliver"
 * http://odoepner.wordpress.com/2013/07/19/play-mp3-or-ogg-using-javax-sound-sampled-mp3spi-vorbisspi/
 * 
 * @author theoklitos
 * 
 */
public class AudioPlayer {

	private static Logger LOG = Logger.getLogger(SoundManager.class);
	
	private AudioFormat getOutFormat(final AudioFormat inFormat) {
		final int ch = inFormat.getChannels();
		final float rate = inFormat.getSampleRate();
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
	}

	public void play(final URL fileLocation) {
		LOG.debug("Playing audio file " + fileLocation);
		try (final AudioInputStream in = AudioSystem.getAudioInputStream(fileLocation)) {

			final AudioFormat outFormat = getOutFormat(in.getFormat());
			final Info info = new Info(SourceDataLine.class, outFormat);

			try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {

				if (line != null) {
					line.open(outFormat);
					line.start();
					stream(AudioSystem.getAudioInputStream(outFormat, in), line);
					line.drain();
					line.stop();
				}
			}

		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void stream(final AudioInputStream in, final SourceDataLine line) throws IOException {
		final byte[] buffer = new byte[4096];
		for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
			line.write(buffer, 0, n);
		}
	}
}

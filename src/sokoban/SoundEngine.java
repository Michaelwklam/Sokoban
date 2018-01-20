package sokoban;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

/**
 * The class that handles the sound
 * @author slamDunk
 *
 */
public class SoundEngine {
	private Clip music;
	private Clip menuBop;
	private Clip gameBop;
	
	/**
	 * Creates a new sound Engine
	 */
	public SoundEngine() {
		try {
			File musicFile = new File(System.getProperty("user.dir") + java.io.File.separator + 
					"sound" + java.io.File.separator + "music.wav");
			File bopFile = new File(System.getProperty("user.dir") + java.io.File.separator + 
					"sound" + java.io.File.separator + "menuBop.wav");
			File gBopFile = new File(System.getProperty("user.dir") + java.io.File.separator + 
					"sound" + java.io.File.separator + "gameBop.wav");
			AudioInputStream musicIn = AudioSystem.getAudioInputStream(musicFile);
			AudioInputStream bopIn  = AudioSystem.getAudioInputStream(bopFile);
			AudioInputStream gbopIn = AudioSystem.getAudioInputStream(gBopFile);

			music = AudioSystem.getClip();	
			menuBop = AudioSystem.getClip();
			gameBop = AudioSystem.getClip();
			
			music.open(musicIn);
			menuBop.open(bopIn);
			gameBop.open(gbopIn);
			
		} catch (LineUnavailableException e) {
			music = null;
			menuBop = null;
			gameBop = null;
		} catch (IOException e) {
			music = null;
			menuBop = null;
			gameBop = null;
		} catch (UnsupportedAudioFileException e) {
			music = null;
			menuBop = null;
			gameBop = null;
		}	
	}
	
	/**
	 * @return
	 * Returns the music file
	 */
	public Clip getMusic() {
		return music;
	}

	/**
	 *  @return
	 *  Returns menu sound effect
	 */
	public Clip getMenuBop() {
		return menuBop;
	}
	
	/**
	 *  @return
	 *  Returns game sound effect
	 */
	public Clip getGameBop() {
		return gameBop;
	}
}
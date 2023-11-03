package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound 
{
 
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound()
	{
		soundURL[0] = getClass().getResource("/sound/mainsong.wav");
		
		soundURL[1] = getClass().getResource("/sound/item.wav");
		
		soundURL[2] = getClass().getResource("/sound/dead.wav");
		
		soundURL[3] = getClass().getResource("/sound/fire.wav");
		
		soundURL[4] = getClass().getResource("/sound/hit.wav");
		
		soundURL[5] = getClass().getResource("/sound/unlock.wav");
		
		soundURL[6] = getClass().getResource("/sound/talk.wav");
		
		soundURL[7] = getClass().getResource("/sound/attack.wav");
		
		soundURL[8] = getClass().getResource("/sound/select.wav");
		
		//soundURL[0] = getClass().getResource("/sound/MainSound.wav");
	}
	
	public void setFile(int i)
	{
		try 
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch(Exception e)
		{
			
		}
	}
	public void Play()
	{
		clip.start();
	}
	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	public void stop()
	{
		clip.stop();
	}
	
}













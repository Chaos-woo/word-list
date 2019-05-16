package main.src.utils.main.system;

import javazoom.jl.player.Player;
import main.src.common.Semaphore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class PlayAudio implements Runnable{
	private String word;

	public PlayAudio(String word) {
		this.word = word;
	}

	@Override
	public void run() {
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(new File(Semaphore.getAudioPath()+"/"+word+".mp3")));
			Player player = new Player(bis);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

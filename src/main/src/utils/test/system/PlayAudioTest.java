package main.src.utils.test.system;

import main.src.utils.main.system.PlayAudio;
import org.junit.jupiter.api.Test;

class PlayAudioTest {
	@Test
	void test(){
		new Thread(new PlayAudio("word")).start();
	}
}

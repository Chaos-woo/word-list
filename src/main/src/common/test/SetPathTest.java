package main.src.common.test;

import main.src.common.Container;
import main.src.common.Semaphore;
import org.junit.jupiter.api.Test;

public class SetPathTest {
	@Test
	void setPathTest(){
		System.out.println(Semaphore.getAudioPath());
		Semaphore.setAudioPath("H:/JavaPro/wordlist/src/main/resource/audio");
	}
}

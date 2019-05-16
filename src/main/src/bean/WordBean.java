package main.src.bean;

import main.src.annotation.Column;
import main.src.annotation.Table;

@Table("wordlist")
public class WordBean {
	@Column("id")
	private int id;
	@Column("english")
	private String english;
	@Column("chinese")
	private String chinese;
	@Column("pronunciation")
	private String pronunciation;
	@Column("sound")
	private String sound;
	@Column("selectedCount")
	private int selectedCount;
	@Column("correctCount")
	private int correctCount;

	public WordBean(int id, String english, String chinese, String pronunciation, String sound, int selectedCount, int correctCount) {
		this.id = id;
		this.english = english;
		this.chinese = chinese;
		this.pronunciation = pronunciation;
		this.sound = sound;
		this.selectedCount = selectedCount;
		this.correctCount = correctCount;
	}

	public WordBean(String english){
		this.english = english;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public int getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(int correctCount) {
		this.correctCount = correctCount;
	}
}

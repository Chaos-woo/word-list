package main.src.bean;

import main.src.annotation.Column;
import main.src.annotation.Table;

@Table("record")
public class RecordBean {
	@Column("start_time")
	private String startTime;
	@Column("end_time")
	private String endTime;
	@Column("test_word_count")
	private int testWordCount;
	@Column("user_name")
	private String userName;

	public RecordBean(String startTime, String endTime, int testWordCount, String userName) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.testWordCount = testWordCount;
		this.userName = userName;
	}

	public RecordBean(String userName) {
		this.userName = userName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getTestWordCount() {
		return testWordCount;
	}

	public void setTestWordCount(int testWordCount) {
		this.testWordCount = testWordCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

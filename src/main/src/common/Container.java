package main.src.common;

import main.src.bean.WordBean;

import java.util.ArrayList;

public class Container {
	public static ArrayList<Object> oldWordList = new ArrayList<>();
	public static ArrayList<WordBean> newWordList = new ArrayList<>();

	public static ArrayList<Object> wordCatch = new ArrayList<>();

	public static void clearWordList(){
		oldWordList.clear();
		newWordList.clear();
	}

	public static void clearCatch(){
		wordCatch.clear();
	}
}

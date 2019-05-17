package main.src.utils.main.system;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SystemTools {
	/**
	 * clear all text in console
	 * this operation through simulating shortcut operation in IDEA,
	 * in this program, my shortcut (Console Clear All) is "CTRL+ALT+R",
	 * you can find (setting->keymap->other->clear all) and set it,
	 * finally, you need to modify Java code in this function
	 */
	public static void clear(){
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_R);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_ALT);
			r.keyRelease(KeyEvent.VK_R);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * print a string "count" times in console
	 * @param s will be print string
	 * @param count
	 */
	public static void print(String s,int count){
		if(count<=0)
			return;
		if (count==1){
			System.out.print(s);
			return;
		}
		for(int i=0;i<count;i++){
			System.out.print(s);
		}
	}

	/**
	 * return a Integer array list and no same number in this list
	 * @param count
	 * @param max upper limit
	 */
	public static ArrayList<Integer> randomNumberList(int count, int max){
		ArrayList<Integer> numList = new ArrayList<>();
		for(int i=0;i<count;){
			int randomNum = (int)(Math.random()*max)+1;
			if(!numList.contains(randomNum)){
				numList.add(randomNum);
				i++;
			}
		}
		return numList;
	}

	/**
	 * print a card including a word that has english,chinese and pronunciation
	 * @param english
	 * @param chinese
	 * @param pronunciation
	 */
	public static void printWordCard(String english,String chinese,String pronunciation){
		print("*",35);
		print("\n",1);
		print("*",1);
		print(" ",5);
		print(english,1);
		print(" ",2);
		print(pronunciation,1);
		print("\n",1);
		print("*",1);
		print(" ",2);
		print(chinese,1);
		print("\n",1);
		print("*",35);
	}

	/**
	 * print main panel to help user
	 */
	public static void printWelcomePanel(String title,String[] contexts){
		print("*",40);
		print("\n",1);
		print("*",1);
		int titleGap = (40-2-title.length())/2;
		print(" ",titleGap);
		System.out.print(title);
		print(" ",40-2-title.length()-titleGap);
		print("*",1);
		print("\n",1);
		print("*",40);
		print("\n",1);
		for(String context:contexts){
			print("*",1);
			int contextGap = (40-2-context.length())/2;
			print(" ",contextGap);
			System.out.print(context);
			print(" ",40-2-context.length()-contextGap);
			print("*",1);
			print("\n",1);
		}
		print("*",40);
	}

}

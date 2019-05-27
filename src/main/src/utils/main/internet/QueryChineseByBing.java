package main.src.utils.main.internet;

import main.src.bean.WordBean;
import main.src.common.Container;
import main.src.common.ExecutePoolServ;
import main.src.common.Semaphore;
import main.src.db.main.DatabaseOperateDAO;
import main.src.utils.main.db.DatabaseQueryTool;
import main.src.utils.main.system.SystemTools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryChineseByBing implements Runnable {
	private WordBean word;
	private StringBuilder sb;
	private static StringBuilder newWord = new StringBuilder();
	private static final String url = "http://cn.bing.com/dict/search?q=";
	private static final String regexChinese = "class=\"def\"><span>.*?</";
	private static final String regexPronunciation = "英\\[.*?，";
	private static int count;

	QueryChineseByBing(){}

	public QueryChineseByBing(int n){
		int maxCount = DatabaseOperateDAO.getCountOfWords();
		setCount(Math.min(n,maxCount));
		ArrayList<Integer> randomList = SystemTools.randomNumberList(count,maxCount);
		ArrayList<Object> list = new ArrayList<>(randomList);
		ResultSet rs = DatabaseQueryTool.query(list);
		try{
			assert rs != null;
			while (rs.next()){
				Container.oldWordList.add(new WordBean(
						rs.getInt("id"),rs.getString("english"),
						rs.getString("chinese"),rs.getString("pronunciation"),
						rs.getString("sound"),rs.getInt("selected_count"),
						rs.getInt("correct_count")));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (Semaphore.isRunning()){
			if(Container.oldWordList.size()<=0){
				Semaphore.setRunning(false);
			}
			while (Container.wordCatch.size()<count && Semaphore.isSearchByInternetFlag()
					&& Container.oldWordList.size()>0){
				word = (WordBean) Container.oldWordList.remove(0);
				if((word.getSound()!=null) && (word.getChinese()!=null)){
					getHttpContext(word.getEnglish());
					downloadPronunciation();
					Container.wordCatch.add(word);
					continue;
				}
				word.setSound(word.getEnglish()+".mp3");
				word.setSelectedCount(word.getSelectedCount()+1);
				getHttpContext(word.getEnglish());
				downloadChinese();
				downloadPronunciation();
				Container.wordCatch.add(word);
			}
		}
	}

	/**
	 * make up a url to get html file
	 * use regex to get word's chinese and pronunciation
	 * maybe this method is slow and take some time
	 *
	 * if you have a official querying word api such as Baidu or Youdao,
	 * you can modify this method to get chinese and maybe it will be faster than before
	 */
	private void downloadChinese(){
		ExecutePoolServ.getExecutorService().execute(
				new DownloadEnglishAudioByShanbay(newWord.toString()));
		newWord.delete(0,newWord.length());
		Pattern pChinese = Pattern.compile(regexChinese);

		Matcher mChinese = pChinese.matcher(sb);

		if(mChinese.find()){
			String ch = mChinese.group();
			ch = ch.substring(15);
			int startIndex = ch.indexOf(">")+1;
			int endIndex = ch.indexOf("/")-1;
			String chinese = ch.substring(startIndex,endIndex);
			if(chinese.length()>13){
				chinese = chinese.substring(0,13);
			}
			word.setChinese(chinese);
		}
	}

	private void downloadPronunciation(){
		Pattern pPronunciation = Pattern.compile(regexPronunciation);
		Matcher mPronunciation = pPronunciation.matcher(sb);
		if(mPronunciation.find()){
			String pro = mPronunciation.group();
			String pronunciation = pro.substring(0,pro.length()-1);
			word.setPronunciation(pronunciation);
		}
	}

	private void getHttpContext(String w){
		if(w.contains(" ")){
			String[] strings = w.split(" ");
			for(int i=0;i<strings.length-1;i++){
				newWord.append(strings[i]).append("%20");
			}
			newWord.append(strings[strings.length-1]);
		}else {
			newWord.append(w);
		}
		sb = new StringBuilder();
		try {
			InputStream is = new URL(url + newWord).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while((s=br.readLine())!=null){
				sb.append(s);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCount(int n){
		count = n;
	}
}

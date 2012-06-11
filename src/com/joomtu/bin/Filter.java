package com.joomtu.bin;

import com.joomtu.datrie.AbstractDoubleArrayTrie;
import com.joomtu.datrie.DoubleArrayTrieImpl;
import com.joomtu.datrie.SearchResult;
import com.joomtu.datrie.store.IntegerArrayList;
import com.joomtu.datrie.store.IntegerList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Filter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Filter ft = new Filter();
		AbstractDoubleArrayTrie trie = new DoubleArrayTrieImpl(100);
		ft.readFile(trie, "./res/words");
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			trie.replaceTextFromHead("afittbnima你妈b国家的失身吗的怒哈tian天安门哈哈哈啊哈胡牛贝多芬底部大幅度发达地方打啊热特人的分割大幅度算法第三方打算dsfgsdf斯蒂芬斯蒂芬dd");
		}
		System.out
				.println(trie
						.replaceTextFromHead("afittbnima你妈b国家的失身吗的怒哈tian天安门哈哈哈啊哈特人的分割大幅度算法第三方打算dsfgsdf斯蒂芬斯蒂芬dd"));
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
	}

	/**
	 * 
	 * @param trie
	 * @param word
	 * @return
	 */
	public boolean addWord(AbstractDoubleArrayTrie trie, String word) {
		char[] c = word.toCharArray();
		int wordLen = c.length;
		IntegerList list = new IntegerArrayList(wordLen);
		for (int i = 0; i < wordLen; i++) {
			list.add((int) c[i]);
		}
		return trie.addToTrie(list);
	}

	/**
	 * 
	 * @param trie
	 * @param word
	 * @return
	 */
	public SearchResult searchWord(AbstractDoubleArrayTrie trie, String word) {
		char[] c = word.toCharArray();
		int wordLen = c.length;
		IntegerList list = new IntegerArrayList(wordLen);
		for (int i = 0; i < wordLen; i++) {
			list.add((int) c[i]);
		}
		return trie.containsPrefix(list);
	}

	public SearchResult replaceWord(AbstractDoubleArrayTrie trie, String word) {
		char[] c = word.toCharArray();
		int wordLen = c.length;
		IntegerList list = new IntegerArrayList(wordLen);
		for (int i = 0; i < wordLen; i++) {
			list.add((int) c[i]);
		}
		return trie.containsPrefix(list);
	}

	/**
	 * 
	 * @param trie
	 * @param fileName
	 */
	public void readFile(AbstractDoubleArrayTrie trie, String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		int id = 1;
		try {
			String tempString = null;
			reader = new BufferedReader(new FileReader(file));
			while ((tempString = reader.readLine()) != null) {
				String[] word = tempString.split("-");
				if (word.length == 2) {
					Words words = new Words();
					words.setOriStr(word[0]);
					words.setReplaceStr(word[1]);
					this.addWord(trie, words.getOriStr());
					id = id + 1;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

}

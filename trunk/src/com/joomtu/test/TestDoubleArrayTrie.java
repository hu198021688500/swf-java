package com.joomtu.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.joomtu.datrie.AbstractDoubleArrayTrie;
import com.joomtu.datrie.CountingTrie;
import com.joomtu.datrie.DoubleArrayTrieImpl;
import com.joomtu.datrie.SearchResult;
import com.joomtu.datrie.store.IntegerArrayList;
import com.joomtu.datrie.store.IntegerList;

public class TestDoubleArrayTrie {

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRobustnessUnderStress() {

		final int ALPHABET_SIZE = 120;
		final int NUMBER_OF_STRINGS = 100000;
		final int MAXIMUM_STRING_SIZE = 100;

		List<IntegerList> data = new ArrayList<IntegerList>(NUMBER_OF_STRINGS);
		AbstractDoubleArrayTrie trie = new DoubleArrayTrieImpl(ALPHABET_SIZE);
		Random rng = new Random();

		for (int i = 0; i < NUMBER_OF_STRINGS; i++) {
			IntegerList toAdd = new IntegerArrayList(MAXIMUM_STRING_SIZE);
			for (int j = 0; j < MAXIMUM_STRING_SIZE; j++) {
				toAdd.add(rng.nextInt(ALPHABET_SIZE));
			}
			data.add(toAdd);
		}

		for (IntegerList list : data) {
			assertTrue(trie.addToTrie(list));
			assertFalse(trie.addToTrie(list));
		}

		for (IntegerList list : data) {
			assertEquals(SearchResult.PERFECT_MATCH, trie.containsPrefix(list));
		}

		for (IntegerList list : data) {
			int removeSize = rng.nextInt(list.size()) + 1;
			for (; removeSize > 0; removeSize--) {
				list.remove(list.size() - 1);
			}
			assertEquals(SearchResult.PURE_PREFIX, trie.containsPrefix(list));
		}

		// TODO
		// Insert a NOT_FOUND test here that is on the one hand random and
		// on the other succeeds always regardless of the generated strings.
	}

	@Test
	public void testMarginCases() {
		AbstractDoubleArrayTrie trie = new DoubleArrayTrieImpl(3);

		IntegerList empty = new IntegerArrayList();
		assertEquals(SearchResult.PURE_PREFIX, trie.containsPrefix(empty));

		IntegerList notIn = new IntegerArrayList();
		notIn.add(1);
		notIn.add(2);
		assertEquals(SearchResult.NOT_FOUND, trie.containsPrefix(notIn));
		assertEquals(SearchResult.PURE_PREFIX, trie.containsPrefix(empty));

		IntegerList one = new IntegerArrayList();
		one.add(2);

		assertFalse(trie.addToTrie(empty));
		assertTrue(trie.addToTrie(one));
		assertFalse(trie.addToTrie(empty));
		assertEquals(SearchResult.PURE_PREFIX, trie.containsPrefix(empty));

		assertEquals(SearchResult.PERFECT_MATCH, trie.containsPrefix(one));
		assertEquals(SearchResult.PURE_PREFIX, trie.containsPrefix(empty));

		one.add(1);
		assertEquals(SearchResult.NOT_FOUND, trie.containsPrefix(one));
	}

	@Test
	public void testCountingTrie() {
		CountingTrie trie = new CountingTrie(4);
		IntegerList string1 = new IntegerArrayList();
		string1.add(0);
		string1.add(1);
		string1.add(2);
		string1.add(3);
		IntegerList string2 = new IntegerArrayList();
		string2.add(1);
		string2.add(2);
		string2.add(3);
		trie.addToTrie(string1);
		trie.addToTrie(string2);
		assertEquals(SearchResult.PERFECT_MATCH, trie.containsPrefix(string1));
		assertEquals(SearchResult.PERFECT_MATCH, trie.containsPrefix(string2));
		assertEquals(2, trie.getSearchCountFor(string1));
		assertEquals(3, trie.getSearchCountFor(string1));
		assertEquals(2, trie.getSearchCountFor(string2));
		assertEquals(3, trie.getSearchCountFor(string2));

		IntegerList string3 = new IntegerArrayList();
		string3.add(1);
		string3.add(2);
		assertEquals(0, trie.getSearchCountFor(string3));
	}

}

package aisd9.huffmanTree;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

import aisd9.comparators.HuffmanTreeNodesQuantComparator;
import aisd9.comparators.HuffmanTreeNodesQuantReversedComparator;
import aisd9.exceptions.TooFewCharactersException;

public class HuffmanTree {

	HuffmanTreeNode root;
	File f;
	Set<HuffmanTreeNode> nodesSet;
	HashMap<Character, String> charMapping;

	public HuffmanTree(File f) throws Exception {
		this.f = f;
		createTreeFromFile(f);
	}

	public void printTree() {
		PriorityQueue<HuffmanTreeNode> queue = new PriorityQueue<HuffmanTreeNode>(10, new HuffmanTreeNodesQuantReversedComparator());
		queue.addAll(nodesSet);
		while (!queue.isEmpty()) {
			HuffmanTreeNode node = queue.poll();
			char ch = node.getCh();
			int quant = node.getQuant();
			String charCode = charMapping.get(ch);
			String charType = ch == ' ' ? "spacja" : Character.toString(ch);
			System.out.printf("%-6s -%d %s%n", charType, quant, charCode);
		}
	}

	public void encrypt(File output) throws IOException {
		FileReader in = new FileReader(f);
		FileWriter out = new FileWriter(output);
		int ch = in.read();
		while (ch != -1) {
			char c = (char) ch;
			String code = charMapping.get(c);
			out.write(code);
			ch = in.read();
		}
		in.close();
		out.close();
	}

	private void createTreeFromFile(File f) throws Exception {
		nodesSet = createNodeSetFromFile(f);
		root = transformSetIntoTree(nodesSet);
		if (root.getLeftSon() == null && root.getRightSon() == null) {
			throw new TooFewCharactersException();
		} else {
			charMapping = createCharMapping();
		}

	}

	private Set<HuffmanTreeNode> createNodeSetFromFile(File f)
			throws IOException {
		HashSet<HuffmanTreeNode> set = new HashSet<HuffmanTreeNode>();
		FileReader fr = new FileReader(f);
		int ch = fr.read();
		while (ch != -1) {
			char c = (char) ch;
			boolean found = false;
			for (HuffmanTreeNode node : set) {
				if (node.getCh() == c)
				{
					node.incrementQuant();
					found = true;
				}
			}
			if(!found)
				set.add(new HuffmanTreeNode(c, 1));
			ch = fr.read();
		}
		fr.close();
		return set;
	}

	private HuffmanTreeNode transformSetIntoTree(Set<HuffmanTreeNode> set) {
		PriorityQueue<HuffmanTreeNode> queue = new PriorityQueue<HuffmanTreeNode>(10, new HuffmanTreeNodesQuantComparator());
		queue.addAll(set);
		while (queue.size() > 1) {
			HuffmanTreeNode left = queue.poll();
			HuffmanTreeNode right = queue.poll();
			HuffmanTreeNode merge = new HuffmanTreeNode(left.getQuant()
					+ right.getQuant());
			merge.linkLeft(left);
			merge.linkRight(right);
			queue.add(merge);
		}
		return queue.poll();
	}

	private HashMap<Character, String> createCharMapping() throws Exception {
		HashMap<Character, String> map = new HashMap<Character, String>();
		for (HuffmanTreeNode node : nodesSet) {
			if (!node.isOnlyValue()) {
				char ch = node.getCh();
				map.put(ch, getCharCode(ch));

			}
		}
		return map;
	}

	private String getCharCode(char c) throws Exception {
		LinkedList<HuffmanTreeNode> queue = new LinkedList<HuffmanTreeNode>();
		queue.add(root);
		while (!queue.isEmpty()) {
			HuffmanTreeNode node = queue.poll();
			if (!node.isOnlyValue() && node.getCh() == c) {
				return tracePathToNode(node);
			}
			if (node.getLeftSon() != null)
				queue.add(node.getLeftSon());
			if (node.getRightSon() != null)
				queue.add(node.getRightSon());
		}
		return null;
	}

	private String tracePathToNode(HuffmanTreeNode node) throws Exception {
		LinkedList<HuffmanTreeNode> path = new LinkedList<HuffmanTreeNode>();
		HuffmanTreeNode cur = node;
		path.add(cur);
		while (cur != root) {
			path.addFirst(cur.getParent());
			cur = cur.getParent();
		}
		if (path.peek() != root) {
			System.err.println("Klops");
			throw new Exception();
		}
		cur = path.poll();
		HuffmanTreeNode next;
		StringBuffer result = new StringBuffer();
		while (!path.isEmpty()) {
			next = path.poll();
			if (cur.getLeftSon() == next)
				result.append('0');
			else if (cur.getRightSon() == next)
				result.append('1');
			else {
				System.err.println("Klops");
				throw new Exception();
			}
			cur = next;

		}
		String temp = result.toString();
		return temp;
	}

}

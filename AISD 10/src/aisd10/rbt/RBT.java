package aisd10.rbt;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class RBT<T extends Comparable<T>,K> {

	RBTNode<T,K> root;
	Set<T> dataSet;

	
	public K bFS (T key)
	{
		LinkedList<RBTNode<T,K>> queue = new LinkedList<RBTNode<T,K>>();
		queue.push(root);
		while(!queue.isEmpty())
		{
			RBTNode<T,K> cur = queue.poll();
			if(cur.getKey().compareTo(key)==0)
				return cur.getValue();
			if(cur.getLeftSon().getValue()!=null)
				queue.push(cur.getLeftSon());
			if(cur.getRightSon().getValue()!=null)
				queue.push(cur.getRightSon());
		}
		return null;
	}
	
	public K get (T key)
	{
		RBTNode<T,K> node = getNode(key);
		if(!dataSet.contains(key))
			System.err.println("Not in data set");
		if(node == null)
			return null;
		else
			return node.getValue();
	}
	
	public void putAll (Map<? extends T, ? extends K> map)
	{
		for(T k : map.keySet())
		{
			put(k,map.get(k));
		}
	}
	
	public boolean contains(T key)
	{
		return getNode(key)==null ? false : true;
	}
	

	public boolean put(T key,K val) {
		RBTNode<T,K> nodeToAdd = new RBTNode<T,K>(key,val);
		if(root == null)
		{
			root = nodeToAdd;
			root.setColor(RBTColor.BLACK);
			dataSet = new HashSet<T>();
			dataSet.add(root.getKey());
			return true;
		}
		RBTNode<T,K> cur = root;
		for (T k : dataSet) {
			if (k.compareTo(key) == 0)
			{
				replace(key, val);
				return true;
			}
		}
		dataSet.add(key);
		while (cur.getKey() != null) {
			if (cur.compareTo(nodeToAdd) == 0)
				return false;
			else if (cur.compareTo(nodeToAdd) > 0)
				cur = cur.getLeftSon();
			else
				cur = cur.getRightSon();
		}
		RBTNode<T,K> par = cur.getParent();
		if (par.getLeftSon() == cur)
			par.linkLeft(nodeToAdd);
		else
			par.linkRight(nodeToAdd);
		if (par.getColor() == RBTColor.RED)
			fixAfterAdd(par);
		return true;
	}
	
	private RBTNode<T,K> getNode (T key)
	{
		RBTNode<T,K> cur = root;
		if(cur==null)
			return null;
		while (cur.getKey()!=null)
		{
			if(cur.getKey().compareTo(key)==0)
				return cur;
			else if (cur.getKey().compareTo(key) > 0)
				cur = cur.getLeftSon();
			else
				cur = cur.getRightSon();
		}
		return null;
	}
	
	private void replace (T key, K newVal)
	{
		RBTNode <T,K> node = getNode(key);
		if(node!=null)
			node.setValue(newVal);
	}

	private void fixAfterAdd(RBTNode<T,K> node) {
		boolean isNodeLeftSon = node.getParent().getLeftSon() == node ? true : false;
		RBTNode<T,K> uncle = isNodeLeftSon ? node.getParent().getRightSon() : node.getParent().getLeftSon();
		if (uncle.getColor() == RBTColor.RED) {
			node.setColor(RBTColor.BLACK);
			uncle.setColor(RBTColor.BLACK);
			node.getParent().setColor(RBTColor.RED);
			if (node.getParent().getParent() == null)
				node.getParent().setColor(RBTColor.BLACK);
			else if (node.getParent().getParent().getColor() == RBTColor.RED)
				fixAfterAdd(node.getParent().getParent());
		} else if (isNodeLeftSon) {
			if (node.getRightSon().getColor() == RBTColor.RED) {
				node.rotateLeft();
				node = node.getParent();
			}
			if (node.getLeftSon().getColor() == RBTColor.RED) {
				node.getParent().rotateRight();
				if(node.getParent() == null)
					root = node;
				node.setColor(RBTColor.BLACK);
				node.getRightSon().setColor(RBTColor.RED);
			}

		} else {
			if (node.getLeftSon().getColor() == RBTColor.RED) {
				node.rotateRight();
				node = node.getParent();
			}
			if (node.getRightSon().getColor() == RBTColor.RED) {
				node.getParent().rotateLeft();
				if(node.getParent() == null)
					root = node;
				node.setColor(RBTColor.BLACK);
				node.getLeftSon().setColor(RBTColor.RED);
			}

		}
	}
	
	public boolean remove(T data)
	{
		return true;
	}

}

import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
	}

	@Override
	public boolean add(T newEntry) {
		if (isEmpty()) {
			return super.add(newEntry);
		} else {
			return addEntryHelperNonRecursive(newEntry);
		}
	}

	// IMPLEMENT THIS METHOD; THIS METHOD CANNOT BE RECURSIVE
	private boolean addEntryHelperNonRecursive(T newEntry) {
		BinaryNode<T> currentNode = root;
		BinaryNode<T> newNodeToAdd = new BinaryNode<T>(newEntry);
		boolean addToTree = false;
		while(!addToTree) {
			int comparison = newEntry.compareTo(currentNode.getData());
			if(comparison<0) {
				if(currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				} else {
					currentNode.setLeftChild(newNodeToAdd);
					addToTree = true;
				}
			} else if(comparison>0) {
				if(currentNode.hasRightChild()) {
					currentNode = currentNode.getRightChild();
				} else {
					currentNode.setRightChild(newNodeToAdd);
					addToTree = true;
				}
			} else { // comparison == 0
				if(currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				} else {
					currentNode.setLeftChild(newNodeToAdd);
					addToTree = true;
				}
			}
		} return true;
	}

	// THIS METHOD CANNOT BE RECURSIVE.
	// Make sure to take advantage of the sorted nature of the BST!
	public int countIterative(T target) {
		int count = 0;
		BinaryNode<T> currentNode = root;
		while(currentNode!=null) {
			int comparison = target.compareTo(currentNode.getData());
			if(comparison==0) {
				count++;
				if(currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				} else {
					currentNode = null;
				}
			} else if(comparison<0) {
				if(currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				} else {
					currentNode = null;
				}
			} else { // comparison > 0
				if(currentNode.hasRightChild()) {
					currentNode = currentNode.getRightChild();
				} else {
					currentNode = null;
				}
			}
		} return count;
	}

	// THIS METHOD MUST BE RECURSIVE! 
	// You are allowed to create a private helper.
	// Make sure to take advantage of the sorted nature of the BST!
	public int countGreaterRecursive(T target) {
		if(!isEmpty()) {
			return countGreaterRecursiveHelper(root,target);
		} else {
			return 0;
		}
	}
	
	private int countGreaterRecursiveHelper(BinaryNode<T> currentNode, T target) {
		if(currentNode==null) {
			return 0;
		} else {
			int count = 0, comparison = currentNode.getData().compareTo(target);
			if(comparison>0) {
				count++;
			}
			int leftCount = countGreaterRecursiveHelper(currentNode.getLeftChild(),target);
			int rightCount = countGreaterRecursiveHelper(currentNode.getRightChild(),target);
			count += leftCount + rightCount;
			return count;
		}
	}
	
	
	

	// THIS METHOD CANNOT BE RECURSIVE.
	// Hint: use a stack!
	// Make sure to take advantage of the sorted nature of the BST!
	public int countGreaterIterative(T target) {
		int count = 0;
		BinaryNode<T> rootNode = root;
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();
		nodeStack.push(rootNode);
		while(!nodeStack.isEmpty()) {
			BinaryNode<T> currentNode = nodeStack.pop();
			int comparison = currentNode.getData().compareTo(target);
			if(comparison>0) {
				count++;
				if(currentNode.hasLeftChild()) {
					nodeStack.push(currentNode.getLeftChild());
				}
				if(currentNode.hasRightChild()) {
					nodeStack.push(currentNode.getRightChild());
				}
			} else { // comparison == 0 || comparison < 0
				if(currentNode.hasRightChild()) {
					nodeStack.push(currentNode.getRightChild());
				}
			}
		} return count;
	}
			
	
	// For full credit, the method should be O(n).
	// You are allowed to use a helper method.
	// The method can be iterative or recursive.
	// If you make the method recursive, you might need to comment out the call to the method in Part B.
	public int countUniqueValues() {
		int count = 0;
		if(!isEmpty()) {
			BinaryNode<T> currentNode = root;
			Stack<BinaryNode<T>> nodeStack = new Stack<>();
			T previous = null;
			while(!nodeStack.isEmpty() || (currentNode!=null)) {
				while(currentNode != null) {
					nodeStack.push(currentNode);
					currentNode = currentNode.getLeftChild();
				}
				if(!nodeStack.isEmpty()) {
					BinaryNode<T> nextNode = nodeStack.pop();
					if(previous==null || !previous.equals(nextNode.getData())) {
						count++;
					}
					previous = nextNode.getData();
					currentNode = nextNode.getRightChild();
				}
			} return count;
		} else {
			return count;
		}
	}

}
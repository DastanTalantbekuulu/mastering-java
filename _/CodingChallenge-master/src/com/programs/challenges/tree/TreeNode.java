/**
 * @author pranshu.shrivastava
 * @date Oct 11, 2019
 */
package com.programs.challenges.tree;


public class TreeNode {
	
	public TreeNode left, right;

	public int data;
	
	TreeNode(int data){
		this.data = data;
	}

	public void insert(int value) {
		if(value <= data) {
			if(left == null) {
				left = new TreeNode(value);
			} else {
				left.insert(value);
			}
		} else {
			if(right == null) {
				right = new TreeNode(value);
			} else {
				right.insert(value);
			}
			
		}
	}
	
	public boolean contains(int value) {

		if(this.data == value ) {
			return true;
		}
		
		if(value <= this.data) {
			if(left != null) {
				return left.contains(value);
			}
		} else {
			if(right != null) {
				return right.contains(value);
			}
		}
		return false;
	}
	

	public void printTree(){

	}

}

package com.changhf.domain.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author changhf
 *
 */
public class TreeNode implements Serializable {
	private static final long serialVersionUID = -8106976619910367159L;

	/** 部门ID. */
	private Integer id;

	/** 部门名称. */
	private String text;

	/** 父部门ID. */
	private Integer parentId;
	/**
	 * 深度
	 */
	private Integer depth;
	/** 子部门. */
	List<TreeNode> children;

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
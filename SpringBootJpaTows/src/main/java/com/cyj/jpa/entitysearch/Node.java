package com.cyj.jpa.entitysearch;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @Description:
 * @author Mashuai
 * @Date 2018-5-16 下午10:50:07
 * @Email 1119616605@qq.com 树控件数据格式化 每个节点都具备以下属性：
 * 
 *        id：节点ID，对加载远程数据很重要。 text：显示节点文本。 state：节点状态，'open' 或
 *        'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。 checked：表示该节点是否被选中。
 *        attributes: 被添加到节点的自定义属性。 children: 一个节点数组声明了若干节点。
 */
@JsonInclude(Include.NON_NULL)
public class Node implements Serializable {

	private Integer id;// 节点ID，对加载远程数据很重要。
	private String text;// 显示节点文本。
//	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp permissionLastUpdateTime;
	private String state;// 节点状态，'open' 或
							// 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点
	private Boolean checked;// 表示该节点是否被选中。
	private Map<String, Object> attributes;// 被添加到节点的自定义属性。
	private List<Node> children;// 一个节点数组声明了若干节点。

	@JsonIgnore
	private NodeInterf nodeInterf;

	public Node() {

	}

	public Node(NodeInterf nodeInterf) {
		this.nodeInterf = nodeInterf;
	}

	public Integer getId() {
		return nodeInterf.getId();
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return nodeInterf.getText();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> list) {
		this.children = list;
	}

	public Timestamp getPermissionLastUpdateTime() {
		return nodeInterf.getPermissionLastUpdateTime();
	}

	public void setPermissionLastUpdateTime(Timestamp permissionLastUpdateTime) {
		this.permissionLastUpdateTime = permissionLastUpdateTime;
	}

}

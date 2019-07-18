package com.cyj.jpa.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 自动所有参数的构造方法方法
@NoArgsConstructor // 自动无参的构造方法方法
@Builder // 使用建造模型
@Entity
@Table(name="tb_modules")
public class Modules implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // 自动增长列
	@OrderBy // 数据加载顺序
	@Column(columnDefinition = "int unsigned comment '备注:模块自动增长主键'  ")
	private int modules_id;
	@Column(unique = true, columnDefinition = "varchar(50) comment '备注:模块名称'  ")
	@JsonProperty(value ="text")
	private String name;
	@Column(columnDefinition = "int unsigned NOT NULL comment '备注:父模块编号'  ")
	private int parentId;
	@Column(columnDefinition = "varchar(120) comment '备注:模块路径'  ")
	private String path;
	@Column(columnDefinition = "int unsigned comment '备注:权重'  ")
	private int weight;
	@Column(columnDefinition = "datetime comment '备注:模块创建时间'  ")
	private Date createTime;
	@Column(columnDefinition = "TIMESTAMP comment '备注:模块最后一次修改时间'  ", nullable = false, updatable = false, insertable = false)
	private Timestamp lastUpdateTime;
	@Column(columnDefinition = "varchar(50) comment '备注:创建人'  ")
	private String founder;
	@Column(columnDefinition = "varchar(50) comment '备注:修改人'  ")
	private String updateMan;

	// 关联角色
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // 指定多对多关系 //默认懒加载,只有调用getter方法时才加载数据
	@JoinTable(name = "tb_rolemodules", // 指定第三张中间表名称
			joinColumns = { @JoinColumn(name = "modules_id") }, // 本表主键userId与第三张中间表user_role_tb的外键user_role_tb_user_id对应
			inverseJoinColumns = { @JoinColumn(name = "role_id") }) // 多对多关系另一张表与第三张中间表表的外键的对应关系
	@NotFound(action = NotFoundAction.IGNORE) // NotFound : 意思是找不到引用的外键数据时忽略，NotFound默认是exception
	private Set<Roles> roleSet = new HashSet<Roles>();// 用户所拥有的角色集合
	
	@Transient
	private boolean checked;
	@Transient
	private Map<String, Object> attributes;

	@JsonInclude(Include.NON_NULL)
	// 如果该属性为null则不参与序列化
	@Transient
	private List<Modules> children;

	public Map<String, Object> getAttributes() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", this.path);
		return map;
	}

}

package com.cyj.jpa.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 角色实体表
 * 
 * @author Chenyongjia 2018-11-12 下午 17:40
 *
 */
@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor // 自动所有参数的构造方法方法
@NoArgsConstructor // 自动无参的构造方法方法
@Builder // 使用建造模型
@Entity
@Table(name = "tb_roles")//声明主键生成策略
public class Roles implements Serializable {

	@Id // 实体类的主键
	@GeneratedValue(strategy = GenerationType.AUTO)	//自动增长列
	@OrderBy // 数据加载顺序
	@Column(columnDefinition = "int unsigned comment '备注:角色自动增长主键'  ")
	private Integer roleId;
	@Column(unique=true, columnDefinition = "varchar(20) comment '备注:角色名称'  ") // 字符长度20
	private String name;
	@Column(columnDefinition = "varchar(100) comment '备注:角色XXXXX'  ") // 字符长度20
	private String explains;
	@Column(columnDefinition = "datetime comment '备注:角色创建时间'  ")
	private Date createTime;
	@Column(columnDefinition = "TIMESTAMP comment '备注:最后一次操作时间'  ", nullable = false, updatable = false, insertable = false)
	private Timestamp lastUpdateTime;

	//关联角色
	@JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)                                      //多对多关系  
    @JoinTable(name="tb_userroles",                       //指定第三张中间表名称
                joinColumns={@JoinColumn(name="role_id")},   //本表主键roleId与第三张中间表user_role_tb的外键user_role_tb_role_id对应.本表与中间表的外键对应关系
                    inverseJoinColumns={@JoinColumn(name="user_id")}) //另一张表与中间表的外键的对应关系
    @NotFound(action = NotFoundAction.IGNORE)	//NotFound : 意思是找不到引用的外键数据时忽略，NotFound默认是exception
    private Set<User>  userSet = new HashSet<User>();//拥有该角色的所有用户集合
	
	//关联权限
	@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)                                      //指定多对多关系    //默认懒加载,只有调用getter方法时才加载数据
    @JoinTable(name="tb_rolepermission",                       //指定第三张中间表名称
                joinColumns={@JoinColumn(name="role_id")}, //本表主键userId与第三张中间表user_role_tb的外键user_role_tb_user_id对应
                    inverseJoinColumns={@JoinColumn(name="permission_id")})  //多对多关系另一张表与第三张中间表表的外键的对应关系
    @NotFound(action = NotFoundAction.IGNORE)	//NotFound : 意思是找不到引用的外键数据时忽略，NotFound默认是exception
    private Set<Permission> roleSet = new HashSet<Permission>();//用户所拥有的角色集合
	
	//关联模块
	@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)                                      //指定多对多关系    //默认懒加载,只有调用getter方法时才加载数据
    @JoinTable(name="tb_rolemodules",                       //指定第三张中间表名称
                joinColumns={@JoinColumn(name="role_id")}, //本表主键userId与第三张中间表user_role_tb的外键user_role_tb_user_id对应
                    inverseJoinColumns={@JoinColumn(name="modules_id")})  //多对多关系另一张表与第三张中间表表的外键的对应关系
    @NotFound(action = NotFoundAction.IGNORE)	//NotFound : 意思是找不到引用的外键数据时忽略，NotFound默认是exception
    private Set<Modules> modulesSet = new HashSet<Modules>();//用户所拥有的角色集合
	
	//----------------------分页参数
	@Transient
	private int page=0;
	@Transient
	private int rows=10;
	
}

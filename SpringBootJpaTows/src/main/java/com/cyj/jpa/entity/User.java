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
 * 用户实体表
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
@Table(name = "tb_user")//声明主键生成策略
public class User implements Serializable {

	@Id // 实体类的主键
	@GeneratedValue(strategy = GenerationType.AUTO) // 自动增长列
	@OrderBy // 数据加载顺序
	@Column(columnDefinition = "int unsigned comment '备注:用户自动增长主键'  ")
	private Integer userId;
	@Column(unique=true, columnDefinition = "varchar(20) comment '备注:用户登录名称'  ") // 字符长度20
	private String loginName;
	@Column(columnDefinition = "varchar(50) comment '备注:用户密码'  ") // 字符长度20
	private String passWords;
	@Column(columnDefinition = "char(1) DEFAULT '否' comment '备注:用户是否锁定'  ")
	private String isLookout;
	@Column(columnDefinition = "TIMESTAMP comment '备注:用户最后一次登陆时间'  ", nullable = false, updatable = false, insertable = false)
	private Timestamp lastLoginTime;
	@Column(columnDefinition = "datetime comment '备注:用户创建时间'  ")
	private Date createTime;
	@Column(columnDefinition = "tinyint(3) unsigned DEFAULT 0 comment '备注:密码错误次数'  ")
	private Integer psdWrongTime;
	@Column(columnDefinition = "datetime comment '备注:用户被锁定时间'  ")
	private Date lockTime;
	@Column(columnDefinition = "varchar(50) comment '备注:用户密保邮箱'  ")
	private String protectEmail;
	@Column(columnDefinition = "varchar(11) comment '备注:用户密保手机号'  ")
	private String protectMtel;
	@Column(columnDefinition = "varchar(20) DEFAULT '否' comment '备注:是否可以进行请假'  ")
	private String leaveState;
	@Column(columnDefinition = "varchar(20) DEFAULT '是' comment '备注:是否可以再次申请请假'  ")
	private String leaveBoolean;
	@Column(columnDefinition = "int unsigned DEFAULT 0 comment '备注:迟到次数'   ")
	private Integer leaveChi;
	@Column(columnDefinition = "int unsigned DEFAULT 0 comment '备注:早退次数'   ")
	private Integer leaveZao;
	@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)                                      //指定多对多关系    //默认懒加载,只有调用getter方法时才加载数据
    @JoinTable(name="tb_userroles",                       //指定第三张中间表名称
                joinColumns={@JoinColumn(name="user_id")}, //本表主键userId与第三张中间表user_role_tb的外键user_role_tb_user_id对应
                    inverseJoinColumns={@JoinColumn(name="role_id")})  //多对多关系另一张表与第三张中间表表的外键的对应关系
    @NotFound(action = NotFoundAction.IGNORE)	//NotFound : 意思是找不到引用的外键数据时忽略，NotFound默认是exception
    private Set<Roles> roleSet = new HashSet<Roles>();//用户所拥有的角色集合
	
	@Transient
	private String Pass;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginName=" + loginName + ", passWords=" + passWords + ", isLookout="
				+ isLookout + ", lastLoginTime=" + lastLoginTime + ", createTime=" + createTime + ", psdWrongTime="
				+ psdWrongTime + ", lockTime=" + lockTime + ", protectEmail=" + protectEmail + ", protectMtel="
				+ protectMtel + ", leaveState=" + leaveState + ", leaveBoolean=" + leaveBoolean + ", leaveChi="
				+ leaveChi + ", leaveZao=" + leaveZao + "]";
	}
	
}

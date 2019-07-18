package com.cyj.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @Description: 权限实体类
 * @author Chenyongjia
 * @Date 2018-11-13 下午5:42:22
 * @Email chen867647213@163.com
 * 
 */
@Getter
@Setter
@AllArgsConstructor // 自动所有参数的构造方法方法
@NoArgsConstructor // 自动无参的构造方法方法
@Builder // 使用建造模型
@Entity
@Table(name = "tb_answersheet")
public class AnswerSheet implements Serializable {
	
	private int answer_sheet_key;// 答题表主键id
	private int answer_sheet_userkey;// 答题人id
	private Date answer_sheet_createTime;// 答题表创建时间
	private Date answer_sheet_updateTime;// 答题表修改时间
	
}

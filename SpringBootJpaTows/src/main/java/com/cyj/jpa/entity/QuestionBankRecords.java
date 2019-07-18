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
@Table(name = "tb_questionbankrecords")
public class QuestionBankRecords implements Serializable  {
	
	private int question_bank_records_key;// 题表记录主键id
	private int answer_sheet_userkey;// 答题表id(外键)
	private int question_bank_records_tihao;// 题库里面随机十五道题的对应id一共生成十五条记录
	private String question_bank_records_topic;// 题型
	private Date question_bank_records_createTime;// 题表记录创建时间
	private Date question_bank_records_updateTime;// 题表记录修改时间
	
}

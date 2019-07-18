package com.cyj.jpa.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cyj.jpa.entity.User;

/**
 * 
 * @Description: 用户Dao接口
 * @ClassName: UserRepository.java
 * @author Chenyongjia
 * @Date 2018年11月12日 下午22:01
 * @Email 867647213@qq.com
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	/**
	 * 根据姓名查询
	 * 
	 * @param loginName
	 * @return
	 */
	@Query("FROM User u WHERE u.loginName= :loginName")
	public User findsLoginName(String loginName);

	/**
	 * 批量删除用户信息
	 * 
	 * @param stuList
	 * @return
	 */
	@Query(value = "DELETE FROM tb_user WHERE user_id IN (:stuList)", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer deleteBatch(@Param(value = "stuList") List<String> stuList);

	/**
	 * 根据ID查询用户角色
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT role_id FROM tb_userroles WHERE user_id=:id ", nativeQuery = true)
	public List<Integer> getUserRole(@Param(value = "id") Integer id);

	/**
	 * 用户登录
	 * 
	 * @param passWords
	 * @param loginName
	 * @return
	 */
	@Query(value = "SELECT user_id,create_time,is_lookout,last_login_time,leave_boolean,leave_chi,leave_state,leave_zao,lock_time,login_name,pass_words,protect_email,protect_mtel,psd_wrong_time FROM tb_user WHERE pass_words=:passWords AND login_name=:loginName ", nativeQuery = true)
	public User loginUser(@Param(value = "passWords") String passWords, @Param(value = "loginName") String loginName);

	/**
	 * 修改密码错误次数
	 * 
	 * @param lastLoginTime
	 * @param passwords
	 * @param id
	 * @return
	 */
	@Query(value = "UPDATE tb_user SET is_lookout=:IsLookout,last_login_time=:lastLoginTime,psd_wrong_time =:psdWrongTime WHERE user_id =:id", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer updUserByPsdWrongTime(@Param(value = "IsLookout") String IsLookout,
			@Param(value = "lastLoginTime") Date lastLoginTime, @Param(value = "psdWrongTime") Integer psdWrongTime,
			@Param(value = "id") Integer id);

	/**
	 * 查询用户权限
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT permission_value FROM tb_permission WHERE permission_id IN(SELECT permission_id FROM tb_rolepermission WHERE role_id IN(SELECT role_id FROM tb_userroles WHERE user_id =:id)) ", nativeQuery = true)
	public List<String> queryPermissionValueByUserId(Integer id);

}

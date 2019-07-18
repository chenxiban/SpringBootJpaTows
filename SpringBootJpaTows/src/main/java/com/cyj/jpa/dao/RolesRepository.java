package com.cyj.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cyj.jpa.entity.Roles;

/**
 * 
 * @Description: 用户Dao接口
 * @ClassName: RolesRepository.java
 * @author Chenyongjia
 * @Date 2018年11月12日 下午22:01
 * @Email 867647213@qq.com
 */
public interface RolesRepository extends JpaRepository<Roles, Integer>,JpaSpecificationExecutor<Roles> {
	
	/**
	 * 根据姓名查询
	 * 
	 * @param name
	 * @return
	 */
	@Query("FROM Roles r WHERE r.name= :name")
	public Roles getAllRoles(String name);
	
	/**
	 * 根据id获取用户表未被设置的角色信息
	 * @param id
	 * @return
	 */
	@Query(value="SELECT role_id,create_time,explains,last_update_time,NAME FROM tb_roles WHERE role_id NOT IN (:ids)",nativeQuery=true)
	public List<Roles> getRolesList(@Param("ids") List<Integer> id);
	
	/**
	 * 根据用户id查询该用户拥有的角色
	 * @param id
	 * @return
	 */
	@Query(value="SELECT * FROM tb_roles r LEFT JOIN tb_userroles u ON u.role_id=r.role_id WHERE u.user_id=:uid",nativeQuery=true)
	public List<Roles> getUserRolesByUserId(@Param("uid")Integer id);
	
	/**
	 * 批量删除角色信息
	 * 
	 * @param stuList
	 * @return
	 */
	@Query(value = "DELETE FROM tb_roles WHERE role_id IN (:stuList)", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer deleteBatch(@Param(value = "stuList") List<String> stuList);
	
	/**
	 * 添加角色
	 * 
	 * @param stuList
	 * @return
	 */
	@Query(value = "INSERT INTO tb_userroles (role_id,user_id) VALUES(:roleId,:userId)", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer addByRole(@Param(value = "roleId") Integer roleId,@Param(value = "userId") Integer userId);
	
	/**
	 * 删除角色
	 * 
	 * @param stuList
	 * @return
	 */
	@Query(value = "DELETE FROM tb_userroles WHERE role_id=:roleId AND user_id=:userId", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer deleteByRoleId(@Param(value = "roleId") Integer roleId,@Param(value = "userId") Integer userId);
	
	/**
	 * 设置模块
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@Query(value = "INSERT INTO tb_rolemodules(role_id,modules_id) VALUES(:roleId,:moduleId)", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer setRoleModule(@Param(value = "roleId") Integer roleId,@Param(value = "moduleId") Integer moduleIds);

	/**
	 * 移除角色模块
	 * @param roleId
	 * @return
	 */
	@Query(value = "DELETE FROM tb_rolemodules WHERE role_id=:roleId", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer  delRoleModule(@Param(value = "roleId") Integer roleId);
	
}

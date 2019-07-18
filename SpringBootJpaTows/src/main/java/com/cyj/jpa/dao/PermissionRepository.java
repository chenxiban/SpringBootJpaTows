package com.cyj.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cyj.jpa.entity.Permission;

/**
 * 
 * @Description: 模块Dao接口
 * @ClassName: PermissionRepository.java
 * @author Chenyongjia
 * @Date 2018年11月12日 下午22:01
 * @Email 867647213@qq.com
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>, JpaSpecificationExecutor<Permission> {
	
	/**
	 * 根据角色id查询权限id
	 * @param roleId
	 * @return
	 */
	@Query(value="SELECT permission_id FROM tb_rolepermission WHERE role_id IN (:roleId)",nativeQuery=true)
	public List<Integer> queryPermissionIdsByRoleIds(@Param(value="roleId")List<Integer> roleId);
	
	/**
	 * 查询所有权限
	 * @return
	 */
	@Query(value="SELECT * FROM tb_permission GROUP BY permission_module ORDER BY permission_id",nativeQuery=true)
	public List<Permission> findsBy();
	
	/**
	 * 根据text名称查询所属孩子节点
	 * 使用自定义工具类转换时原生SQL结果集
	 * @param permissionModule
	 * @return
	 */
	@Query(value="SELECT permission_id,permission_name,permission_last_update_time,permission_value,permission_module FROM tb_permission WHERE permission_module=:permissionModule",nativeQuery=true)
	public List<Permission> findsByPermissionModule(@Param(value="permissionModule")String permissionModule);
	
	/**
	 * 设置权限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	@Query(value="INSERT INTO tb_rolepermission (role_id,permission_id) VALUES(:roleId,:permissionIds)",nativeQuery=true)
	@Modifying
	@Transactional
	public Integer setRolePermission(@Param("roleId")Integer roleId,@Param("permissionIds") Integer permissionIds);
	
	/**
	 * 移除角色权限
	 * @param roleId
	 * @return
	 */
	@Query(value = "DELETE FROM tb_rolepermission WHERE role_id=:roleId", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer deletePermission(@Param(value = "roleId") Integer roleId);
	
}

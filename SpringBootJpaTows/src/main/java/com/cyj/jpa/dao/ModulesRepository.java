package com.cyj.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cyj.jpa.entity.Modules;

/**
 * 
 * @Description: 模块Dao接口
 * @ClassName: ModulesRepository.java
 * @author Chenyongjia
 * @Date 2018年11月12日 下午22:01
 * @Email 867647213@qq.com
 */
public interface ModulesRepository extends JpaRepository<Modules, Integer>, JpaSpecificationExecutor<Modules> {
	
	/**
	 * 批量删除模块信息
	 * 
	 * @param stuList
	 * @return
	 */
	@Query(value = "DELETE FROM tb_modules WHERE modules_id IN (:stuList)", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer deleteBatch(@Param(value = "stuList") List<String> stuList);
	
	/**
	 * 左侧树结构
	 */
	@Query(value = "SELECT modules_id FROM tb_rolemodules WHERE role_id IN (:roleId)", nativeQuery = true)
	public List<Integer> getModulesRole(@Param(value = "roleId") List<Integer> roleId);

	/**
	 * 根据id查询子菜单
	 * 
	 * @param id
	 * @param moduleId
	 * @return
	 */
	@Query(value = "SELECT modules_id,create_time,founder,last_update_time,name,parent_id,path,update_man,weight FROM tb_modules WHERE parent_id=:parentId AND modules_id IN(:moduleId)", nativeQuery = true)
	public List<Modules> queryChildrenById(@Param("parentId") int id, @Param("moduleId") List<Integer> moduleId);
	
	/**
	 * 根据根节点查询
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT modules_id,create_time,founder,last_update_time,name,parent_id,path,update_man,weight FROM tb_modules WHERE parent_id=:parentId", nativeQuery = true)
	public List<Modules> queryChildren(@Param("parentId") Integer parentId);
	
	/**
	 * 根据父id查询子菜单
	 * @param parentId
	 * @return
	 */
	@Query(value = "SELECT modules_id FROM tb_modules WHERE parent_id IN(:parentId)", nativeQuery = true)
	public List<String> getChildrenByParentId(@Param("parentId") List<String> parentId);
	
	/**
	 * 根据父id查询孙子菜单
	 * @param parentId
	 * @return
	 */
	@Query(value = "SELECT modules_id FROM tb_modules WHERE parent_id IN(SELECT modules_id FROM tb_modules WHERE parent_id IN (:parentId))", nativeQuery = true)
	public List<String> getChildrenByParentIds(@Param("parentId") List<String> parentId);
	
}

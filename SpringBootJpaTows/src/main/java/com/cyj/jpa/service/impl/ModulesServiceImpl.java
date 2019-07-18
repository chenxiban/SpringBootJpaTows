package com.cyj.jpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyj.jpa.dao.ModulesRepository;
import com.cyj.jpa.entity.Modules;
import com.cyj.jpa.service.ModulesService;

@Service
public class ModulesServiceImpl implements ModulesService {

	@Autowired
	private ModulesRepository modulesRepository;

	@Override
	public List<Modules> getModulesRoles(List<Integer> roleId) {
		// 根据角色查询模块ids
		List<Integer> moduleId = modulesRepository.getModulesRole(roleId);
		
		System.out.println("拥有的模块id   >=" + moduleId);

		List<Modules> rootList = modulesRepository.queryChildrenById(0, moduleId);
		// 递归设置子菜单
		this.setChildren(rootList, moduleId);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^最终得到的菜单列表=>" + rootList);
		return rootList;
	}

	/**
	 * 给模块设置孩子
	 * 
	 * @param parentList
	 * @param moduleId
	 */
	public void setChildren(List<Modules> parentList, List<Integer> moduleId) {
		for (Modules m : parentList) {
			// 查询出子菜单
			List<Modules> childrenList = this.queryChildrenById(m.getModules_id(), moduleId);
			// 如果没有子菜单则递归结束
			if (childrenList != null && !childrenList.isEmpty()) {// 有子菜单
				// 设置子菜单
				m.setChildren(childrenList);
				// 如果有子菜单则继续递归设置子菜单
				this.setChildren(childrenList, moduleId);
			}
		}
	}

	public List<Modules> queryChildrenById(int parentList, List<Integer> moduleId) {
		return modulesRepository.queryChildrenById(parentList, moduleId);
	}

	/**
	 * 查询所有Modules模块信息
	 */
	@Override
	public List<Modules> showModules() {
		// 查询出所有根菜单
		List<Modules> rootList = modulesRepository.queryChildren(0);
		// 递归设置子菜单
		this.setChildrens(rootList);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^最终得到的菜单列表=>" + rootList);
		return rootList;
	}
	
	/**
	 * 给菜单设置子菜单
	 * 
	 * @param parentList
	 */
	private void setChildrens(List<Modules> parentList) {
		for (Modules m : parentList) {
			// 查询出子菜单
			List<Modules> childrenList = this.queryChildren(m.getModules_id());
			System.out
					.println("*****************************************************设置子菜单=>"
							+ m.getName());
			// 如果没有子菜单则递归结束
			if (childrenList != null && !childrenList.isEmpty()) {// 有子菜单
				// 设置子菜单
				System.out.println("设置的子菜单是=>" + childrenList);
				m.setChildren(childrenList);
				// 如果有子菜单则继续递归设置子菜单
				this.setChildrens(childrenList);
			}
		}
	}
	
	/**
	 * 查询孩子菜单
	 */
	
	public List<Modules> queryChildren(Integer parentId) {
		return modulesRepository.queryChildren(parentId);
	}

	@Override
	public boolean addModules(Modules m) {
		try {
			modulesRepository.save(m);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean updModules(Modules m) {
		try {
			modulesRepository.save(m);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Modules getById(Integer id) {
		return modulesRepository.getOne(id);
	}

	@Override
	public boolean delModules(List<String> id) {
		try {
			modulesRepository.deleteBatch(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<String> getChildrenByParentId(List<String> parentId) {
		return modulesRepository.getChildrenByParentId(parentId);
	}
	
	@Override
	public List<String> getChildrenByParentIds(List<String> parentId) {
		return modulesRepository.getChildrenByParentIds(parentId);
	}

	/**
	 * RoleSetModuleTree 查询模块 RoleSetModuleTree 树形数据表格
	 */
	
	public List<Modules> queryRoleSetModuleTree(List<Integer> roleId) {
		// 根据角色查询模块ids
		List<Integer> moduleId = modulesRepository.getModulesRole(roleId);
		System.out.println(moduleId);

		//
		List<Modules> rootList = modulesRepository.queryChildren(0);
		// 递归设置子菜单
		this.setRoleSetModuleTreeChildrens(rootList, moduleId);
		System.out
				.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^最终得到的菜单列表=>"
						+ rootList);
		return rootList;
	}
	
	/**
	 * RoleSetModuleTree 给菜单模块 设置孩子
	 * 
	 * @param parentList
	 */
	private void setRoleSetModuleTreeChildrens(List<Modules> parentList,
			List<Integer> moduleId) {
		for (Modules m : parentList) {
			// 查询出子菜单
			List<Modules> childrenList = this.queryChildrenByIds(m.getModules_id());
			// 如果没有子菜单则递归结束
			if (childrenList != null && !childrenList.isEmpty()) {// 有子菜单
				// 设置子菜单
				m.setChildren(childrenList);
				// 如果有子菜单则继续递归设置子菜单
				this.setRoleSetModuleTreeChildrens(childrenList, moduleId);
			} else {// 没有孩子;是叶子节点,且角色有用该模块,则选中.
					// tree控件默认级联选中:选中父节点则默认选中全部子节点,选中一个子节点则默认选中父节点.所以只选中叶子节点即可.
					// 设置是否选中,把角色拥有的模块选中
				if (moduleId.contains(m.getModules_id()))
					m.setChecked(true);
			}
		}
	}
	
	public List<Modules> queryChildrenByIds(int parentList) {
		return modulesRepository.queryChildren(parentList);
	}

}

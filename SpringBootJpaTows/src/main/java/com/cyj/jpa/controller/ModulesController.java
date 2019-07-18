package com.cyj.jpa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyj.jpa.entity.Modules;
import com.cyj.jpa.entity.Result;
import com.cyj.jpa.service.ModulesService;
import com.cyj.jpa.util.IsEmptyUtils;

@RestController
@RequestMapping(value = "/modules", name = "菜单模块")
public class ModulesController {

	@Autowired
	private ModulesService modulesService;

	/**
	 * 查询左侧菜单树 MenuTree
	 * http://localhost:8080/SpringBootJpaTows/modules/queryMenuTree
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryMenuTree", name = "查询左侧菜单树")
	public Object queryModuleTree(Integer[] roleId) {
		List<Integer> roleIds = new ArrayList<Integer>();
		for (int i = 0; i < roleId.length; i++) {
			roleIds.add(roleId[i]);
		}
		System.out.println("roleId=>" + roleIds);
		return modulesService.getModulesRoles(roleIds);
	}

	/**
	 * http://localhost:8080/SpringBootJpaTows/modules/getModules
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getModules", name = "查询模块")
	public Object getModules() {
		return modulesService.showModules();
	}

	/**
	 * 添加模块 http://localhost:8080/SpringBootJpaTows/modules/addModules
	 * @param modules
	 * @return
	 */
	@RequestMapping(value = "/addModules", name = "添加模块")
	public Object addModules(Modules modules) {
		modules.setCreateTime(new Date());
		if (modulesService.addModules(modules)) {
			return new Result(true, "模块添加成功");
		} else {
			return new Result(false, "模块名称重复,添加失败");
		}
	}

	/**
	 * 修改模块 http://localhost:8080/SpringBootJpaTows/modules/updModules
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updModules", name = "修改模块")
	public Object updModules(Modules modules) {
		Modules modules2 = modulesService.getById(modules.getModules_id());
		System.out.println("modules2===============>" + modules.getUpdateMan());
		if (!IsEmptyUtils.isEmpty(modules.getName())) {
			modules2.setName(modules.getName());
		}
		if (!IsEmptyUtils.isEmpty(modules.getWeight())) {
			modules2.setWeight(modules.getWeight());
		}
		if (!IsEmptyUtils.isEmpty(modules.getPath())) {
			modules2.setPath(modules.getPath());
		}
		if (!IsEmptyUtils.isEmpty(modules.getUpdateMan())) {
			modules2.setUpdateMan(modules.getUpdateMan());
		}

		if (modulesService.updModules(modules2)) {
			return new Result(true, "模块修改成功");
		} else {
			return new Result(false, "模块名称重复,修改失败");
		}
	}

	/**
	 * http://localhost:8080/SpringBootJpaTows/modules/delModules
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delModules", name = "删除模块")
	public Object delModules(@RequestParam(value = "id[]") String[] id) {
		List<String> list = new ArrayList<String>();
		for (String dids : id) {
			list.add(dids);
		}

		List<String> moduleId = modulesService.getChildrenByParentId(list);
		List<String> modulePid = modulesService.getChildrenByParentIds(list);
		if (IsEmptyUtils.isEmpty(moduleId) || moduleId.size() == 0) {
			if (modulesService.delModules(list)) {
				return new Result(true, "模块删除成功");
			} else {
				return new Result(false, "有角色拥有当前模块,删除失败");
			}
		}

		if (IsEmptyUtils.isEmpty(modulePid) || modulePid.size() == 0) {
			for (String dids : moduleId) {
				list.add(dids);
			}

			if (modulesService.delModules(list)) {
				return new Result(true, "模块删除成功");
			} else {
				return new Result(false, "有角色拥有当前模块,删除失败");
			}
		} else {
			for (String dids : moduleId) {
				list.add(dids);
			}

			for (String dids : modulePid) {
				list.add(dids);
			}

			if (modulesService.delModules(list)) {
				return new Result(true, "模块删除成功");
			} else {
				return new Result(false, "有角色拥有当前模块,删除失败");
			}
		}

	}
	
	/**
	 * 查询角色设置模块 RoleSetModuleTree
	 * http://localhost:8080/SpringBootJpaTows/modules/queryRoleSetModuleTree
	 * @return
	 */
	@RequestMapping(value="/queryRoleSetModuleTree",name="查询角色设置模块")
	public Object queryRoleSetModuleTree(Integer[] roleId){
		List<Integer> roleIds = new ArrayList<Integer>();
		for (int i = 0; i < roleId.length; i++) {
			roleIds.add(roleId[i]);
		}
		System.out.println("roleId=>" + roleIds);
		return modulesService.queryRoleSetModuleTree(roleIds);
	}

}

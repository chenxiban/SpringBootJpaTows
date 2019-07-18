package com.cyj.jpa.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cyj.jpa.dao.RolesRepository;
import com.cyj.jpa.entity.Roles;
import com.cyj.jpa.entitysearch.RolesSearch;
import com.cyj.jpa.service.RolesService;
import com.cyj.jpa.util.IsEmptyUtils;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RolesRepository rolesRepository;

	@Override
	public List<Roles> getRolesList(List<Integer> ids) {
		return rolesRepository.getRolesList(ids);
	}

	@Override
	public List<Roles> getRolesLists() {
		return rolesRepository.findAll();
	}

	@Override
	public List<Roles> getUserRolesByUserId(Integer id) {
		return rolesRepository.getUserRolesByUserId(id);
	}

	@Override
	public boolean addUser(Roles r) {
		try {
			rolesRepository.save(r);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delRole(List<String> stuList) {
		try {
			rolesRepository.deleteBatch(stuList);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Page<Roles> sreachByRoles(RolesSearch rolesSearch, Pageable pageable) {
		return rolesRepository.findAll(this.getWhereClause(rolesSearch), pageable);
	}

	/**
	 * 查询条件动态组装 动态生成where语句 匿名内部类形式
	 * 
	 * @param us
	 * @return
	 */
	@SuppressWarnings({ "serial" })
	private Specification<Roles> getWhereClause(final RolesSearch rs) {
		return new Specification<Roles>() {

			@Override
			public Predicate toPredicate(Root<Roles> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();// 动态SQL表达式
				List<Expression<Boolean>> exList = predicate.getExpressions();// 动态SQL表达式集合

				if (!IsEmptyUtils.isEmpty(rs.getName())) {
					exList.add(cb.like(root.<String>get("name"), "%" + rs.getName() + "%"));
				}
				return predicate;
			}

		};
	}

	@Override
	public Roles getAllRoles(String name) {
		return rolesRepository.getAllRoles(name);
	}

	@Override
	public Roles getRolesById(Integer rid) {
		return rolesRepository.getOne(rid);
	}

	@Override
	public boolean deleteByRoleId(Integer stuList,Integer userId) {
		try {
			rolesRepository.deleteByRoleId(stuList,userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addByRole(Integer roleId, Integer userId) {
		return rolesRepository.addByRole(roleId, userId) > 0 ? true : false;
	}
	
	@Override
	public Integer setRoleModule(Integer roleId, Integer moduleIds) {
		return rolesRepository.setRoleModule(roleId, moduleIds);
	}

	@Override
	public boolean delRoleModule(Integer roleId) {
		try {
			rolesRepository.delRoleModule(roleId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

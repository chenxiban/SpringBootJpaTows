package com.cyj.jpa.service.impl;

import java.util.Date;
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

import com.cyj.jpa.dao.UserRepository;
import com.cyj.jpa.entity.User;
import com.cyj.jpa.entitysearch.UserSearch;
import com.cyj.jpa.service.UserService;
import com.cyj.jpa.util.IsEmptyUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean addUser(User u) {
		try {
			userRepository.save(u);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public User getAllUser(String name) {
		return userRepository.findsLoginName(name);
	}

	@Override
	public boolean delUser(List<String> stuList) {
		try {
			userRepository.deleteBatch(stuList);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 动态sql分页查询
	 */
	@Override
	public Page<User> sreachByUser(UserSearch userSearch, Pageable pageable) {
		return userRepository.findAll(this.getWhereClause(userSearch), pageable);
	}

	/**
	 * 查询条件动态组装 动态生成where语句 匿名内部类形式
	 * 
	 * @param us
	 * @return
	 */
	@SuppressWarnings({ "serial" })
	private Specification<User> getWhereClause(final UserSearch us) {
		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();// 动态SQL表达式
				List<Expression<Boolean>> exList = predicate.getExpressions();// 动态SQL表达式集合

				if (!IsEmptyUtils.isEmpty(us.getLoginName())) {
					exList.add(cb.like(root.<String>get("loginName"), "%" + us.getLoginName() + "%"));
				}
				if (!IsEmptyUtils.isEmpty(us.getIsLookout())) {
					exList.add(cb.like(root.<String>get("isLookout"), "%" + us.getIsLookout() + "%"));
				}
				if (!IsEmptyUtils.isEmpty(us.getBirthStart())) {
					exList.add(cb.greaterThanOrEqualTo(root.<Date>get("birthStart"), us.getBirthStart()));
				}
				if (!IsEmptyUtils.isEmpty(us.getBirthEnd())) {
					exList.add(cb.lessThanOrEqualTo(root.<Date>get("birthEnd"), us.getBirthEnd()));
				}
				if (!IsEmptyUtils.isEmpty(us.getEmail())) {
					exList.add(cb.like(root.<String>get("email"), "%" + us.getEmail() + "%"));
				}
				if (!IsEmptyUtils.isEmpty(us.getMtel())) {
					exList.add(cb.like(root.<String>get("mtel"), "%" + us.getMtel() + "%"));
				}
				return predicate;
			}

		};
	}

	@Override
	public List<Integer> getUserRole(Integer id) {
		return userRepository.getUserRole(id);
	}

	@Override
	public boolean loginUser(String passWords, String loginName) {
		return userRepository.loginUser(passWords, loginName) == null ? false : true;
	}

	@Override
	public boolean updUserByPsdWrongTime(String IsLookout,Date lastLoginTime, Integer psdWrongTime, Integer id) {
		return userRepository.updUserByPsdWrongTime(IsLookout,lastLoginTime, psdWrongTime, id) > 0 ? true : false;
	}

	@Override
	public List<String> queryPermissionValueByUserId(Integer id) {
		return userRepository.queryPermissionValueByUserId(id);
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.getOne(id);
	}

}

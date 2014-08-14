package com.cmct.portal.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.UserRoleDao;
import com.cmct.portal.po.UserRolePO;


@Service
public class UserRoleService {
	@Autowired
	private UserRoleDao userRoleDao;
	
	public void save(UserRolePO entity){
		userRoleDao.save(entity);
	}
	
	
	public List<UserRolePO> find(Integer id) {
		Map<String, Object> propertiesMap =new HashMap<String, Object>();
		propertiesMap.put("user.userid", id);
		return userRoleDao.find(propertiesMap);
	} 
	
	public List<UserRolePO> findUserAndRole(Integer userid,Integer roleid){
		Map<String, Object> propertiesMap =new HashMap<String, Object>();
		propertiesMap.put("user.userid", userid);
		propertiesMap.put("role.id", roleid);
		return userRoleDao.find(propertiesMap);
	}
	
	public void delete(List<UserRolePO> list){
		for(UserRolePO po:list){
			userRoleDao.delete(po);
		}
	}
	
	// 查询所有SN
	private static String SELECT_PERM_BY_USER = 
			"select b.permissionList " +
			"from T_USER_ROLE a " +
			"left outer join t_role_permission b on b.roleid = a.roleid " +
			"where a.userid = ?";
	// 查询所有SN
	private static String SELECT_URLS_BY_USER = 
			"select c.url " +
			"from T_USER_ROLE a " +
			"left outer join t_role_permission b on b.roleid = a.roleid " +
			"left outer join T_MODULE c on c.sn = b.permissionList " +
			"where a.userid = ?";
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 查询所有SN
	 * @param userId
	 * @return
	 */
	public Set<String> findPermbyUser(Integer userId){
		List<String> list = jdbcTemplate.query(SELECT_PERM_BY_USER, new Object[] {userId}, new PermRowMapper());
		Set<String> set = new HashSet<String>();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				set.add(list.get(i));
			}
		}
		return set;
	}
	/**
	 * 查询所有URL
	 * @param userId
	 * @return
	 */
	public Set<String> findUrlsByUser(Integer userId){
		List<String> list = jdbcTemplate.query(SELECT_URLS_BY_USER, new Object[] {userId}, new UrlsRowMapper());
		Set<String> set = new HashSet<String>();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(!StringUtils.isBlank(list.get(i))){
					String[] urls = list.get(i).split(";");
					for(int j= 0;j<urls.length;j++){
						if(!StringUtils.isBlank(urls[j])){
							set.add(urls[j]);
						}
					}
				}
			}
		}
		return set;
	}
	/**
	 *  其中一行数据
	 */
	private class PermRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String perm = rs.getString(1);
			return perm;
		}
	}
	/**
	 *  其中一行数据
	 */
	private class UrlsRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String urls = rs.getString(1);
			return urls;
		}
	}
	
}
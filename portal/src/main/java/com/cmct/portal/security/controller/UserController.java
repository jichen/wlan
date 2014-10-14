package com.cmct.portal.security.controller;


import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.UnknownAlgorithmException;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.springside.modules.utils.Encodes;

import com.cmct.common.Constants;
import com.cmct.common.annotation.Log;
import com.cmct.common.util.AbstractController;
import com.cmct.common.util.ui.Page;
import com.cmct.common.util.ui.PageFormModel;
import com.cmct.portal.po.RolePO;
import com.cmct.portal.po.RoleVo;
import com.cmct.portal.po.UserPO;
import com.cmct.portal.po.UserRolePO;
import com.cmct.portal.service.RoleService;
import com.cmct.portal.service.UserRoleService;
import com.cmct.portal.service.UserService;
/**
 * 
 * @title
 * @description
 * @usage
 * @copyright Copyright 2014 CMCT Corporation. All rights reserved.
 * @company 上海中移通信技术工程有限公司
 * @author 蒋志巍
 * @create 2014-3-7 上午15:55:45
 */


@Controller
@RequestMapping("security/user")
public class UserController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	private static String REL_ID = "userList";
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;

	
	
	
	@RequestMapping(value = "/preAdd")
	public ModelAndView preAdd() throws Exception{
		ModelAndView mav = new ModelAndView("pages/view/security/user/add");
		return mav;
	}
	
	/**
	 * 刷新页面
	 */
	@RequestMapping(value = "/refresh")
	@ResponseBody
	public String refresh() throws Exception {
		return this.ajaxDoneSuccess("",true,REL_ID);
	}
	
	
	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value = "/ajaxuserName")
	@ResponseBody
	public void ajaxuserName(String username, HttpServletRequest request,HttpServletResponse response) throws Exception{
		UserPO info=userService.onlyOne_Username(username);
		PrintWriter pw=null;
		pw=response.getWriter();
		String ajaxDate="0";
		if (info != null) {
			ajaxDate="1";
		}
		pw.write(ajaxDate);
		pw.close();
	}
	
	
	
	
	/**
	 * 添加 
	 */
	@Log(module = Constants.MODULE_SYS_USER, function = Constants.Funtion_Add)
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(UserPO bean, HttpServletRequest request) throws Exception{
		bean.setStatus("N");
		bean.setCreatetime(new Date());
		ByteSource salt=getSalt();
		byte[] hash=this.hash(bean.getPwd().getBytes(), salt.getBytes(), 1024);
		bean.setSalt(salt.toHex());
		bean.setPwdEncryption(Encodes.encodeHex(hash));
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setCreateusername(user.getUsername());
		
		//保存用户
		userService.saveUser(bean);
		return ajaxDoneSuccess("",true,REL_ID);
	}
	
	
	/**
	 * 预编辑
	 */
	@RequestMapping(value = "/preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Integer id) throws Exception {
		UserPO bean = userService.findOne(id);
		// 查找所有角色
		// List<Role> roleList = roleService.findAll();
		ModelAndView mav = new ModelAndView("pages/view/security/user/update");
		mav.addObject("bean", bean);
		// mav.addObject("roleList",roleList);
		return mav;
	}

	/**
	 * 编辑
	 */
	@Log(module = Constants.MODULE_SYS_USER, function = Constants.Funtion_Update)	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(UserPO bean, HttpServletRequest request)
			throws Exception {

		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());		
		ByteSource salt=getSalt();		
		byte[] hash=this.hash(bean.getPwd().getBytes(), salt.getBytes(), 1024);
		bean.setSalt(salt.toHex());
		bean.setPwdEncryption(Encodes.encodeHex(hash));		
		if(bean.getStatus()==null || bean.getStatus()==""){
			bean.setStatus("N");
		}
		userService.update(bean);
		return ajaxDoneSuccess(" ", true, REL_ID);
	}

	/**
	 * 删除
	 */
	@Log(module = Constants.MODULE_SYS_USER, function = Constants.Funtion_Delete)
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) throws Exception {
		userService.delete(id);
		return ajaxDoneSuccess(" ", false, REL_ID);
	}	
	
	
	
	/**
	 * 预分配权限
	 */
	@RequestMapping(value = "/preAssignRole/{id}")
	public ModelAndView preAssignRole(@PathVariable("id") Integer id) throws Exception {
		UserPO user = userService.findOne(id);
		List<UserRolePO> userRoles = userRoleService.find(id);
		List<RolePO> roles = roleService.findAll();
		if (roles != null) {
			for (int i = 0; i < roles.size(); i++) {
				boolean flag = false;
				for (UserRolePO tmp : userRoles) {
					if (tmp.getRole().getId().equals(roles.get(i).getId())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					roles.remove(roles.get(i));
					i--;
				}
			}
		}
		ModelAndView mav = new ModelAndView("pages/view/security/user/assignRole");
		mav.addObject("user", user);
		mav.addObject("userRoles", userRoles);
		mav.addObject("roles", roles);
		mav.addObject("user_id", id);

		return mav;
	}

	/**
	 * 授予权限
	 */
	@Log(module = Constants.MODULE_SYS_USER, function = Constants.Funtion_Assign_Roles)
	@ResponseBody
	@RequestMapping(value = "/granRole")
	public Map<String, Object> granRole(@RequestParam Integer userId, 
			@RequestParam Integer roleId , HttpServletRequest request) throws Exception {
		List<UserRolePO> list = userRoleService.findUserAndRole(userId, roleId);
		if (list == null || list.size() == 0) {
			UserRolePO userRole = new UserRolePO();
			userRole.setUser(userService.findOne(userId));
			userRole.setRole(roleService.findOne(roleId));
			userRoleService.save(userRole);
		}
		RolePO role = roleService.findOne(roleId);
		RoleVo vo = new RoleVo();
		vo.setId(role.getId());
		vo.setName(role.getName());
		vo.setDescription(role.getDescription());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("role_info", vo);
		return map;
	}

	/**
	 * 撤消权限
	 */
	@Log(module = Constants.MODULE_SYS_USER, function = Constants.Funtion_Undo_Role)
	@RequestMapping(value = "/revokeRole")
	@ResponseBody
	public Map<String, Object> revokeRole(@RequestParam Integer userId,
			@RequestParam Integer roleId , HttpServletRequest request) throws Exception {
		List<UserRolePO> list = userRoleService.findUserAndRole(userId, roleId);
		userRoleService.delete(list);
		RolePO role = roleService.findOne(roleId);
		RoleVo vo = new RoleVo();
		vo.setId(role.getId());
		vo.setName(role.getName());
		vo.setDescription(role.getDescription());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("role_info", vo);
		return map;
	}

	/**
	 * 撤消权限
	 */
	@Log(module = Constants.MODULE_SYS_USER, function = Constants.Funtion_Undo_Role)
	@RequestMapping(value = "/revokeAllRole/{id}")
	@ResponseBody
	public String revokeAllRole(@PathVariable Integer id) throws Exception {
		List<UserRolePO> list = userRoleService.find(id);
		userRoleService.delete(list);
		return ajaxDoneSuccess(" ", false, REL_ID);
	}

	
	
	
	/**
	 *输出列表 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {	
		List<UserPO> list=new ArrayList<UserPO>();
		Map<String,Object> propertiesMap=new HashMap<String,Object>();
		//设置显示数量
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		//查询语句
		String sqlCount="select count(*) from UserPO where 1=1 ";
		if(pageForm.getIsdelete()!=null){
			if(pageForm.getIsdelete().trim().length()>0){
				propertiesMap.put("status", pageForm.getIsdelete().trim());
				sqlCount=sqlCount+" and status= :status";
			}
		}
		if(pageForm.getName()!=null){
			if(pageForm.getName().trim().length()>0){
				propertiesMap.put("username", pageForm.getName().trim());
				sqlCount=sqlCount+" and username= :username";
			}
		}
		//获取列表
		list=userService.findPages(propertiesMap, start, limit);
		List<UserPO> userList=new ArrayList<UserPO>();
		for(int i=0;i<list.size();i++){
			UserPO po=list.get(i);
			if(null != userList && userList.size()>0){
				boolean flag=true;
				for(int j=0;j<userList.size();j++){
					UserPO po1=userList.get(j);
					if(po1.getUsername().equals(po.getUsername())){
						flag=false;
					}
				}
				if(flag){
					userList.add(po);
				}
			}else{
				userList.add(po);
			}
		}
		//计算符合条件的行数，分页需要
		page.setTotalCount(userService.getTotalCount_where(sqlCount, propertiesMap));
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("list",userList);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/security/user/list",mp);
		
	}
	
	
	
	/**
	 * 预修改密码页面
	 */
	@RequestMapping(value = "/prePassword")
	public String prePassword() {
		return "pages/view/security/user/password";
	}

	/**
	 * 预修改密码页面
	 */
	@RequestMapping(value = "/password")
	@ResponseBody
	public String passwrod(HttpSession session,@RequestParam String oldPassword, 
			@RequestParam String newPassword) throws Exception{
		String loginName = (String) session.getAttribute("_screenName");
		UserPO user = userService.findOne(loginName);
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)
				&& oldPassword.equals(user.getPwd())){
			user.setPwd(newPassword);
			ByteSource salt=getSalt();
			byte[] hash=this.hash(newPassword.getBytes(), salt.getBytes(), 1024);
			user.setSalt(salt.toHex());
			user.setPwdEncryption(Encodes.encodeHex(hash));
			userService.update(user);
			return ajaxDoneSuccess(" ");
		}
		return ajaxDoneSuccess(" ", false, StringUtils.EMPTY);
	}
	
	
	
	
	private ByteSource getSalt(){
		return new SecureRandomNumberGenerator().nextBytes();
	}
	
	public byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws UnknownAlgorithmException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }

}

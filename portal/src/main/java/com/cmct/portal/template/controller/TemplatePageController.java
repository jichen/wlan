package com.cmct.portal.template.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.cmct.common.Constants;
import com.cmct.common.annotation.Log;
import com.cmct.common.util.AbstractController;
import com.cmct.common.util.PropertyConfigureHandler;
import com.cmct.common.util.ui.Page;
import com.cmct.common.util.ui.PageFormModel;
import com.cmct.portal.po.TemplatePagePO;
import com.cmct.portal.po.UserPO;
import com.cmct.portal.service.TemplatePageService;


@Controller
@RequestMapping(value = "/templatepage/authpage")
public class TemplatePageController extends AbstractController {
	
	private static String REL_ID =  "templatepageList";
	private static String REL =  "templatepageList";
	@Autowired
	private TemplatePageService templatePageService;


	/**
	 *输出列表 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Collection<TemplatePagePO> list=null;
		List<TemplatePagePO> templatePages=new ArrayList<TemplatePagePO>();
		TemplatePagePO templatePage;
		Map<String,Object> propertiesMap =new HashMap<String,Object>();
		//设置显示数量
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		
		//查询语句
		String sqlCount="select count(*) from TemplatePagePO where 1=1 ";
		if(pageForm.getName()!=null){
			if(pageForm.getName().trim().length()>0){
				propertiesMap.put("templatename", "%"+pageForm.getName().trim()+"%");
				sqlCount=sqlCount+" and templatename like :templatename";
			}
		}
		if(pageForm.getIsdelete()!=null){
			if(pageForm.getIsdelete().trim().length()>0){
				propertiesMap.put("isdelete", pageForm.getIsdelete().trim());
				sqlCount=sqlCount+" and isdelete= :isdelete";
			}
		}
		
		//获取列表
		list=templatePageService.findPages(propertiesMap, start, limit);

		//计算符合条件的行数，分页需要
		page.setTotalCount(templatePageService.getTotalCount_where(sqlCount, propertiesMap));		

		for(TemplatePagePO templatePagePO:list){
			templatePage=new TemplatePagePO();
			templatePage.setId(templatePagePO.getId());
			templatePage.setTemplatename(templatePagePO.getTemplatename());
			templatePage.setCsscount(templatePagePO.getCsscount());
			templatePage.setImagecount(templatePagePO.getImagecount());
			templatePage.setTextcount(templatePagePO.getTextcount());
			templatePage.setIsdelete(templatePagePO.getIsdelete());
			templatePage.setIsmap(templatePagePO.getIsmap());
			templatePages.add(templatePage);
		}
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("list",templatePages);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/portalmanagement/authpage/list",mp);
	}
	
	/*
	 * 预加载
	 
	@RequestMapping(value = "/preadd/{id}")
	public ModelAndView preAdd(@PathVariable("id") Integer id) throws Exception {
		TemplatePagePO bean = templatePageService.findOne(id);
		ModelAndView mav = new ModelAndView("pages/view/portalmanagement/authpage/mapadd");
		mav.addObject("bean", bean);
		return mav;
	}
*/	
	
	
	/*
	 * 模板添加预加载
	 */	
	@RequestMapping(value = "/preadd")
	public ModelAndView preAdd() {
		ModelAndView mav = new ModelAndView("pages/view/portalmanagement/authpage/add");
		return mav;
	}
	
	@Log(module = Constants.MODULE_AUTH_PAGE, function = Constants.Funtion_Add)
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(TemplatePagePO bean,HttpServletRequest request) throws IOException {
		String originalFilename=bean.getTemplatelocal();
		String file_name=originalFilename.substring(originalFilename.lastIndexOf("\\"));
		file_name=file_name.substring(1);
		String path=uploadfile(originalFilename);
		UserPO user=(UserPO) WebUtils.getSessionAttribute(request,Constants.PORTAL_LOGIN_USER);
		bean.setPagename(file_name);
		bean.setTemplatelocal(path);
		bean.setCreateusername(user.getUsername());
		bean.setCreatetime(new Date());
		bean.setIsdelete("N");
		bean.setIsmap("N");
		templatePageService.save(bean);
		return ajaxDoneSuccess(" ",true, REL_ID);
	}
	
	
	/*
	 * 预加载
	 */	
	@RequestMapping(value = "/preupdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Integer id) throws Exception {
		TemplatePagePO bean = templatePageService.findOne(id);
		ModelAndView mav = new ModelAndView("pages/view/portalmanagement/authpage/update");
		mav.addObject("bean", bean);
		return mav;
	}
	
	/*
	 * 更新
	 */	
	@Log(module = Constants.MODULE_AUTH_PAGE, function = Constants.Funtion_Update)
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(TemplatePagePO bean,HttpServletRequest request) throws Exception {
		TemplatePagePO po = templatePageService.findOne(bean.getId());
		UserPO user=(UserPO) WebUtils.getSessionAttribute(request,Constants.PORTAL_LOGIN_USER);
		po.setUpdatetime(new Date());
		po.setUpdateusername(user.getUsername());
		po.setImagecount(bean.getImagecount());
		po.setTextcount(bean.getTextcount());
		po.setCsscount(bean.getCsscount());
		po.setTemplatename(bean.getTemplatename());
		if(bean.getIsdelete()==null || bean.getIsdelete()==""){
			po.setIsdelete("N");
		}else{
			po.setIsdelete("Y");
		}
		templatePageService.update(po);
		return ajaxDoneSuccess(" ",true, REL_ID);
	}	
	
	//文件处理
	//
	public String uploadfile(String originalFilename) throws IOException{
		File file =new File(originalFilename);
		InputStream in = null;
		FileOutputStream fos;
		// 本地路径
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileType=originalFilename.substring(originalFilename.lastIndexOf("."));
		String file_name=originalFilename.substring(originalFilename.lastIndexOf("\\"));
		file_name=file_name.substring(1, file_name.length());
		String path = (String)PropertyConfigureHandler.getCtxPropertiesMap().get("uploadfile_path")+sdf.format(new Date()) ;
		String localpath="";
		fileType=fileType.toLowerCase();
		if(fileType.equals(".css")){
			path = path +"/"+ Constants.FilePath_CSS;	
		}else if(fileType.equals(".jpg") || fileType.equals(".jpeg") || fileType.equals(".bmp")){
			path = path +"/"+ Constants.FilePath_IMAGE;
		}else{
			localpath=path = path +"/"+ Constants.FilePath_TEMPLATE_PAGE;
			path = path +"/"+ Constants.FilePath_TEMPLATE_PAGE +"/";
		}
		String filePath = path +  file_name;
		//创建文件夹
        File fileDir = new File(path);
        if (!fileDir.exists()) {
        	fileDir.mkdirs();
        }
		// 保存文件
		in = new FileInputStream(file);
		fos = new FileOutputStream(filePath);
		int length=0;
		byte[] buff=new byte[1024];
		while(-1!=(length=(in.read(buff,0,1024)))){
			fos.write(buff, 0, length);
		}
		in.close();
		fos.flush();
		fos.close();
		
		//返回的是模板保存的文件夹地址
		return localpath;
	}	
	
}

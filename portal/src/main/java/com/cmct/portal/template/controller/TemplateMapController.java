package com.cmct.portal.template.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


import com.cmct.common.Constants;
import com.cmct.common.annotation.Log;
import com.cmct.common.util.AbstractController;
import com.cmct.common.util.PropertyConfigureHandler;
import com.cmct.portal.po.TemplateMapPO;
import com.cmct.portal.po.TemplatePagePO;
import com.cmct.portal.service.TemplateMapService;
import com.cmct.portal.service.TemplatePageService;


@Controller
@RequestMapping(value = "/templatemap/authmap")
public class TemplateMapController extends AbstractController {
	
	private static String REL_ID =  "templatepageList";
	
	@Autowired
	private TemplatePageService templatePageService;

	@Autowired
	private TemplateMapService templateMapService;

	
	/*
	 * 预加载
	 */	
	@RequestMapping(value = "/preadd/{id}")
	public ModelAndView preAdd(@PathVariable("id") Integer id) throws Exception {
		TemplatePagePO bean = templatePageService.findOne(id);
		ModelAndView mav=null;
		//
		Map propertiesMap =new HashMap();
		propertiesMap.put("template_id",id);
		List<TemplateMapPO> list=templateMapService.findList(propertiesMap);
		if(list.size()>0){
			//已经添加过来，再次添加是更新
			mav = new ModelAndView("pages/view/portalmanagement/authpage/mapupdate");
			mav.addObject("list", list);
			mav.addObject("bean", bean);
		}else{
			//从未添加过来，是新增
			mav = new ModelAndView("pages/view/portalmanagement/authpage/mapadd");
			mav.addObject("bean", bean);
		}

		return mav;
	}


	/*
	 *添加操作
	 *
	 */
	@Log(module = Constants.MODULE_AUTH_PAGE, function = Constants.Funtion_Content_Add)
	@RequestMapping(value = "/tMadd")
	@ResponseBody
	public String add(HttpServletRequest request) throws IOException{
		List<TemplateMapPO> templateMaps=new ArrayList<TemplateMapPO>();
		TemplateMapPO templatemap=null;
		//获取基本信息
		String templatePageId=request.getParameter("id");
		Integer tid=Integer.valueOf(templatePageId);
		String mobile=request.getParameter("mobile");
		String pc=request.getParameter("pc");
		String csscount=request.getParameter("csscount");
		String imagecount=request.getParameter("imagecount");
		String textcount=request.getParameter("textcount");
		//判断text
		if(textcount!= null){
			Integer textnum=Integer.valueOf(textcount);
			for(Integer i=1;i<textnum+1;i++){
				//判断终端
				if(mobile!=null && mobile.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("mobile");
					templatemap.setData_type("text");
					templatemap.setValue_name("text_"+i);
					templatemap.setValue(request.getParameter("text_"+i));
					templatemap.setUrl("");
					templateMaps.add(templatemap);
				}
				if(pc!=null && pc.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("pc");
					templatemap.setData_type("text");
					templatemap.setValue_name("text_"+i);
					templatemap.setValue(request.getParameter("text_"+i));
					templatemap.setUrl("");
					templateMaps.add(templatemap);
				}
			}
		}
		//判断image
		String url="javascript:void(0)";
		if(imagecount!= null ){
			Integer imagenum=Integer.valueOf(imagecount);
			for(Integer i=1;i<imagenum+1;i++){
				//判断终端
				if(mobile!=null && mobile.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("mobile");
					templatemap.setData_type("image");
					templatemap.setValue_name("image_"+i+"_mobile");
					if(request.getParameter("image_"+i+"_mobile")!=null){
						templatemap.setValue(uploadfile(request.getParameter("image_"+i+"_mobile")));
					}
					if(request.getParameter("image_"+i+"_url_mobile")!=null){
						templatemap.setUrl(request.getParameter("image_"+i+"_url_mobile"));
					}else{
						templatemap.setUrl(url);
					}
					templateMaps.add(templatemap);
				}
				if(pc!=null && pc.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("pc");
					templatemap.setData_type("image");
					templatemap.setValue_name("image_"+i+"_pc");
					if(request.getParameter("image_"+i+"_pc")!=null){
						templatemap.setValue(uploadfile(request.getParameter("image_"+i+"_pc")));
					}
					if(request.getParameter("image_"+i+"_url_pc")!=null){
						templatemap.setUrl(request.getParameter("image_"+i+"_url_pc"));
					}else{
						templatemap.setUrl(url);
					}
					templateMaps.add(templatemap);
				}
			}
		}
		//判断css
		if(csscount!= null ){
			Integer cssnum=Integer.valueOf(csscount);
			for(Integer i=1;i<cssnum+1;i++){
				//判断终端
				if(mobile!=null && mobile.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("mobile");
					templatemap.setData_type("css");
					templatemap.setValue_name("css_"+i+"_mobile");	
					if(request.getParameter("css_"+i+"_mobile")!=null){
						templatemap.setValue(uploadfile(request.getParameter("css_"+i+"_mobile")));
					}
					templateMaps.add(templatemap);
				}
				if(pc!=null && pc.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("pc");
					templatemap.setData_type("css");
					templatemap.setValue_name("css_"+i+"_pc");
					if(request.getParameter("css_"+i+"_pc")!=null){
						templatemap.setValue(uploadfile(request.getParameter("css_"+i+"_pc")));
					}
					templateMaps.add(templatemap);
				}
			}
		}

		for(TemplateMapPO tm:templateMaps){
			System.out.println(tm.getValue());
			templateMapService.save(tm);
		}		
		//更新一下
		TemplatePagePO bean = templatePageService.findOne(tid);
		bean.setIsmap("Y");
		templatePageService.update(bean);
		
		
		return ajaxDoneSuccess(" " , true , REL_ID);
	}
	

	
	
	/*
	 * 预加载
	 */	
	@RequestMapping(value = "/preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Integer id) throws Exception {
		
		TemplatePagePO bean = templatePageService.findOne(id);	
		
		Map propertiesMap =new HashMap();
		propertiesMap.put("template_id",id);
		//根据id排序
		List<String[]> orderBies = new ArrayList<String[]>();
		String[] orderBy = new String[] { "id", "asc" };//根据id
		//String[] orderBy = new String[] { "id", "desc" };//根据id
		orderBies.add(orderBy);	
		
		//获取是否有pc
		propertiesMap.put("teminal_type","pc");
		List<TemplateMapPO> list_pc=templateMapService.findList(propertiesMap,orderBies);
		
		//获取是否有mobile
		propertiesMap.remove("teminal_type");
		propertiesMap.put("teminal_type","mobile");
		List<TemplateMapPO> list_mobile=templateMapService.findList(propertiesMap,orderBies);
		
		//返回值
		String pc="",mobile="";
		List<TemplateMapPO> list_css_pc=null;
		List<TemplateMapPO> list_css_mobile=null;
		List<TemplateMapPO> list_image_pc=null;
		List<TemplateMapPO> list_image_mobile=null;
		List<TemplateMapPO> list_text=null;
		ModelAndView mav = new ModelAndView("pages/view/portalmanagement/authpage/mapupdate");
		if(list_pc.size()>0) pc="Y";
		if(list_mobile.size()>0) mobile="Y";
		//获取不同终端的图片和css
		if("Y".equals(pc)){
			list_css_pc=returnList("css",list_pc);
			list_image_pc=returnList("image",list_pc);
			list_text=returnList("text",list_pc);
		}
		if("Y".equals(mobile)){
			list_css_mobile=returnList("css",list_mobile);
			list_image_mobile=returnList("image",list_mobile);
			//因为文字是通用的判断是否已经获取过
			if(!"Y".equals(pc)){
				list_text=returnList("text",list_mobile);
			}
		}
		
		
		mav.addObject("list_css_mobile", list_css_mobile);
		mav.addObject("list_image_mobile", list_image_mobile);
		mav.addObject("list_css_pc", list_css_pc);
		mav.addObject("list_image_pc", list_image_pc);
		mav.addObject("list_text", list_text);
		mav.addObject("pc", pc);
		mav.addObject("mobile", mobile);
		mav.addObject("bean", bean);
		return mav;
	}	
	
	




	/*
	 *修改操作
	 *
	 */
	@Log(module = Constants.MODULE_AUTH_PAGE, function = Constants.Funtion_Content_Update)
	@RequestMapping(value = "/tMupdate")
	@ResponseBody
	public String update(HttpServletRequest request) throws IOException{
		List<TemplateMapPO> templateMaps=new ArrayList<TemplateMapPO>();
		TemplateMapPO templatemap=null;
		String templatePageId=request.getParameter("id");
		Integer tid=Integer.valueOf(templatePageId);

		Map propertiesMap =new HashMap();
		propertiesMap.put("template_id",tid);
		List<TemplateMapPO> list=templateMapService.findList(propertiesMap);

		String mobile=request.getParameter("mobile");
		String pc=request.getParameter("pc");
		String csscount=request.getParameter("csscount");
		String imagecount=request.getParameter("imagecount");
		String textcount=request.getParameter("textcount");

		//判断text
		if(textcount!= null){
			Integer textnum=Integer.valueOf(textcount);
			for(Integer i=1;i<textnum+1;i++){
				//判断终端
				if(mobile!=null && mobile.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("mobile");
					templatemap.setData_type("text");
					templatemap.setValue_name("text_"+i);
					templatemap.setValue(request.getParameter("text_"+i));
					templatemap.setUrl("");
					templateMaps.add(templatemap);
				}
				if(pc!=null && pc.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("pc");
					templatemap.setData_type("text");
					templatemap.setValue_name("text_"+i);
					templatemap.setValue(request.getParameter("text_"+i));
					templatemap.setUrl("");
					templateMaps.add(templatemap);
				}
			}
		}
		//判断image

		String url="javascript:void(0)";
		if(imagecount!= null ){
			Integer imagenum=Integer.valueOf(imagecount);
			for(Integer i=1;i<imagenum+1;i++){
				//判断终端
				if(mobile!=null && mobile.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("mobile");
					templatemap.setData_type("image");
					templatemap.setValue_name("image_"+i+"_mobile");
					if(request.getParameter("image_"+i+"_mobile")!=null){
						if(request.getParameter("image_"+i+"_mobile").indexOf("\\")!=-1){
							templatemap.setValue(uploadfile(request.getParameter("image_"+i+"_mobile")));
						}else{
							templatemap.setValue(request.getParameter("image_"+i+"_mobile"));
						}
					}
					if(request.getParameter("image_"+i+"_url_mobile")!=null && request.getParameter("image_"+i+"_url_mobile").trim()!=""){
						templatemap.setUrl(request.getParameter("image_"+i+"_url_mobile"));
					}else{
						templatemap.setUrl(url);
					}
					templateMaps.add(templatemap);
				}
				if(pc!=null && pc.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("pc");
					templatemap.setData_type("image");
					templatemap.setValue_name("image_"+i+"_pc");
					if(request.getParameter("image_"+i+"_pc")!=null){
						if(request.getParameter("image_"+i+"_pc").indexOf("\\")!=-1){
							templatemap.setValue(uploadfile(request.getParameter("image_"+i+"_pc")));
						}else{
							templatemap.setValue(request.getParameter("image_"+i+"_pc"));
						}
					}
					if(request.getParameter("image_"+i+"_url_pc")!=null  && request.getParameter("image_"+i+"_url_pc").trim()!=""){
						templatemap.setUrl(request.getParameter("image_"+i+"_url_pc"));
					}else{
						templatemap.setUrl(url);
					}
					templateMaps.add(templatemap);
				}
			}
		}
		//判断css
		if(csscount!= null ){
			Integer cssnum=Integer.valueOf(csscount);
			for(Integer i=1;i<cssnum+1;i++){
				//判断终端
				if(mobile!=null && mobile.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("mobile");
					templatemap.setData_type("css");
					templatemap.setValue_name("css_"+i+"_mobile");	
					if(request.getParameter("css_"+i+"_mobile")!=null){
						if(request.getParameter("css_"+i+"_mobile").indexOf("\\")!=-1){
							templatemap.setValue(uploadfile(request.getParameter("css_"+i+"_mobile")));
						}else{
							templatemap.setValue(request.getParameter("css_"+i+"_mobile"));
						}
					}

					templateMaps.add(templatemap);
				}
				if(pc!=null && pc.equals("Y")){
					templatemap=new TemplateMapPO();
					templatemap.setTemplate_id(tid);
					templatemap.setTeminal_type("pc");
					templatemap.setData_type("css");
					templatemap.setValue_name("css_"+i+"_pc");
					if(request.getParameter("css_"+i+"_pc")!=null){
						if(request.getParameter("css_"+i+"_pc").indexOf("\\")!=-1){
							templatemap.setValue(uploadfile(request.getParameter("css_"+i+"_pc")));
						}else{
							templatemap.setValue(request.getParameter("css_"+i+"_pc"));
						}
					}
					templateMaps.add(templatemap);
				}
			}
		}

		//删除现有的数据重新插入
		for(TemplateMapPO tm:list){
			templateMapService.delete(tm);
		}
		for(TemplateMapPO tm:templateMaps){
			System.out.println(tm.getValue());
			templateMapService.save(tm);
		}		
		
		return ajaxDoneSuccess(" " , true , REL_ID);
	}
	
	
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
		
		fileType=fileType.toLowerCase();
		if(fileType.equals(".css")){
			path = path +"/"+ Constants.FilePath_CSS;	
		}else if(fileType.equals(".jpg") || fileType.equals(".jpeg") || fileType.equals(".bmp")){
			path = path +"/"+ Constants.FilePath_IMAGE;
		}else{
			path = path +"/"+ Constants.FilePath_TEMPLATE_PAGE;
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
		return filePath;
	}	

	
	//分类list
	private List<TemplateMapPO> returnList(String type,	List<TemplateMapPO> list) {
		List<TemplateMapPO> rL=new ArrayList<TemplateMapPO>();
		for(TemplateMapPO t:list){
			if(type.equals(t.getData_type())){			
				if("css".equals(t.getData_type())){
					String file_name=t.getValue().substring(t.getValue().lastIndexOf("/"));
					file_name=file_name.substring(1);
					t.setTeminal_type(file_name);
				}else if("image".equals(t.getData_type())){
					String file_name=t.getValue().substring(t.getValue().lastIndexOf("/"));
					file_name=file_name.substring(1);
					t.setTeminal_type(file_name);
				}
				rL.add(t);
			}
		}
		return rL;
	}
	private List<TemplateMapPO> returnList_order(String type, List<TemplateMapPO> list) {
		List<TemplateMapPO> rL=new ArrayList<TemplateMapPO>();
		for(TemplateMapPO t:list){
			if(type.equals(t.getData_type())){			
				System.out.println("value=="+t.getValue());
				rL.add(t);
			}
		}
		return rL;
	}
	
}

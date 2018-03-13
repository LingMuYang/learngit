package com.itstar.util;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.activation.MailcapCommandMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.FastHttpDateFormat;

import com.sun.org.apache.xerces.internal.impl.xs.identity.IdentityConstraint;










/**
 * 文件批量上传，大数据云盘上传工具类
 * @author Administrator
 *
 */
public class UploadsUtil {
	/**
	 * 批量文件上传
	 * @param request
	 * @param response
	 * @return
	 */
	public static HashMap<String, Object >uploadFiles(HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> map = new HashMap<String, Object>();
		//设置编码及
		try {
			String fileName;
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			//获取文件上传的目录
			String realPath = request.getRealPath("/");
			//定义上传服务器的目录
			String dirPath = realPath +"/upload";
			//判断服务器中是否存在upload文件夹
			File dirFile = new File(dirPath);
			if(!dirFile.exists()){
				dirFile.mkdirs();
			}
			//上传操作
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//获取文件上传列表
			 List items = upload.parseRequest(request);
			 if(null != items){
				 Iterator itr = items.iterator();
				 while(itr.hasNext()){
					 FileItem item = (FileItem) itr.next() ;
					 if(item.isFormField()){
						 continue;
					 }else{
						 //获取源文件的文件名
						 String name = item.getName();
						 //将上传到服务器中的文件进行重命名
						 int i = name.lastIndexOf(".");
						 //截取文件后缀名
						 String ext = name.substring(i,name.length());
						 //取一个新的文件名字
						 
						 fileName = new Date().getTime() + ext;
						 //将文件流转换成本地文件
						 File saveFile= new File(dirPath,fileName);
						 //写到服务器
						 item.write(saveFile);
						 //存储到map集合
						 map.put("name", item.getName());
						 map.put("newName", fileName);
						 map.put("size", item.getSize());
						 map.put("url", "upload/"+fileName);
					 }
				 }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
		
	}
	public static void main(String[]args){
		System.out.println("我爱你");
		
		for(int i = 0;i<20; i++){
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String newName = uuid+".jsp";
			System.out.println(newName);
		}
		//1.编写文件批量上传的方法
		
		//2.讲IO流转换服务器文件
		
		//3.把服务器文件存储到hadoop的HDFS文件系统中
		
		//4.对接前后台，返回到页面中进行展示
		
	}
}

package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//1.创建一个工厂
		FileItemFactory factory = new DiskFileItemFactory();
		//2.通过工厂生产一个文件上传的操作对象,后续的文件上传操作就靠它来执行
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设定编码格式
		upload.setHeaderEncoding("utf-8");
		//指定单个上传文件的最大尺寸,单位:字节，这里设为5Mb  
		upload.setSizeMax(5*1024*1024);
		// 指定一次上传多个文件的总尺寸,单位:字节，这里设为100Mb
		upload.setFileSizeMax(100*1024*1024);
		try {
			List<FileItem> list=upload.parseRequest(request);
			for(int i=0;i<list.size();i++) {
				FileItem fi=list.get(i);
				if(fi.isFormField()) {
					String fieldName=fi.getFieldName();
					if(fieldName.equals("username")) {
						System.out.println(fi.getString());
					}
				}else {
					//如果是上传文件，执行上传
					System.out.println("上传文件的名字："+fi.getName());
					String realPath=request.getServletContext().getRealPath("/upload");
					
					String fiName=UUID.randomUUID().toString();
					String oldEndName=fi.getName().substring(fi.getName().lastIndexOf("."));
					System.out.println(realPath+"\\"+fiName+oldEndName);
					
					File file=new File(realPath+"\\"+fiName+oldEndName);
					fi.write(file);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

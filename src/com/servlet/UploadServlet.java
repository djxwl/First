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
		
		//1.����һ������
		FileItemFactory factory = new DiskFileItemFactory();
		//2.ͨ����������һ���ļ��ϴ��Ĳ�������,�������ļ��ϴ������Ϳ�����ִ��
		ServletFileUpload upload = new ServletFileUpload(factory);
		//�趨�����ʽ
		upload.setHeaderEncoding("utf-8");
		//ָ�������ϴ��ļ������ߴ�,��λ:�ֽڣ�������Ϊ5Mb  
		upload.setSizeMax(5*1024*1024);
		// ָ��һ���ϴ�����ļ����ܳߴ�,��λ:�ֽڣ�������Ϊ100Mb
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
					//������ϴ��ļ���ִ���ϴ�
					System.out.println("�ϴ��ļ������֣�"+fi.getName());
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

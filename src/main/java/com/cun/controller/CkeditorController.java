package com.cun.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/edit")
public class CkeditorController {

	// 注意路径格式，一般为项目路径下的一个文件夹里边，项目发布到linux服务器上又得改了
	String imageFilePath = "C://LLLLLLLLLLLLLLLLLLL/20180209/TestCkeditor/src/main/webapp/static/myImage/";

	/**
	 * 进入编辑器页面
	 * @return
	 */
	@RequestMapping("/ckeditor")
	public String editor() {
		return "edit";
	}

	/**
	 * 编辑器图片上传实现
	 * @param file
	 * @param CKEditorFuncNum
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/ckeditorUpload")
	//名字upload是固定的，有兴趣，可以打开浏览器查看元素验证
	public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		//实际处理肯定是要加上一段唯一的字符串（如现在时间），这里简单加 cun
		String newFileName = "cun" + suffixName;
		//使用架包 common-io实现图片上传
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
		//实现图片回显，基本上是固定代码，只需改路劲即可
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">");
		sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/static/myImage/" + newFileName
				+ "','')");
		sb.append("</script>");

		return sb.toString();
	}

}

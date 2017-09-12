package cn.com.mumway.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.lang3.StringUtils;

public class FileTool {

	public static void main(String[] args) {
		String s = "D:\\datum\\data\\Test\\sharpenCode\\enty\\14360714981000202"
				+ "\\showme\\src\\com\\showme\\config\\sqlscript";
		s = "D:\\datum\\data\\Test\\sharpenCode\\enty\\14360714981000202"
				+ "\\showme\\src\\com\\showme\\config\\dbsql";
		String d = "D:\\datum\\data\\Test\\sharpenCode\\enty\\14360714981000202"
				+ "\\showme\\src\\com\\showme\\config";
		
		s = "D:\\datum\\develop\\java\\workspace\\scorerecord\\src\\com\\scorerecord\\config\\dbsql";
		d = "D:\\datum\\develop\\java\\workspace\\scorerecord\\src\\com\\scorerecord\\config";

		s = "D:\\datum\\develop\\java\\workspace\\rbs\\src\\com\\rbs\\config\\dbsql";
		d = "D:\\datum\\develop\\java\\workspace\\rbs\\src\\com\\rbs\\config";
		
		s="E:\\datum\\Code\\svn\\goodhabit\\code\\scorerecord\\src\\com\\scorerecord\\config\\dbsql";
		d="E:\\datum\\Code\\svn\\goodhabit\\code\\scorerecord\\src\\com\\scorerecord\\config";
		
		s="E:\\datum\\Code\\svn\\rbs\\code\\rbs\\src\\com\\rbs\\config\\dbsql";
		d="E:\\datum\\Code\\svn\\rbs\\code\\rbs\\src\\com\\rbs\\config";


		s="E:\\datum\\Code\\svn\\kxd\\showme\\code\\showme\\src\\com\\showme\\config\\dbsql";
		d="E:\\datum\\Code\\svn\\kxd\\showme\\code\\showme\\src\\com\\showme\\config";

		s="D:\\datum\\data\\code\\svn\\kxd\\showme\\code\\showme\\src\\com\\showme\\config\\dbsql";
		d="D:\\datum\\data\\code\\svn\\kxd\\showme\\code\\showme\\src\\com\\showme\\config";
		
		
		String r = merge(s,d,".sql");
		System.out.println(r);

	}
	
	/**
	 * 将多个文件合并成一个
	 * @param srcStr
	 * @param destStr
	 * @param specifySuffix
	 * @return
	 */
	public static String merge(String srcStr, String destStr, String specifySuffix){
		if(StringUtils.isBlank(srcStr) || StringUtils.isBlank(destStr)){
			return "源文件夹或者目标文件夹/文件夹为空";
		}
		File srcDir = new File(srcStr);
		File destFile = new File(destStr);
		return merge(srcDir, destFile, specifySuffix);
		
	}
	
	public static String merge(File srcDir, File destFile, final String specifySuffix){
		if(srcDir == null || !srcDir.exists() || !srcDir.isDirectory()){
			return "选择的源文件夹不存在或者无效";
		}
		if(destFile == null || !destFile.exists()){
			return "选择的目标文件或者文件夹不存在或者无效";
		}
		if(StringUtils.contains(destFile.getAbsolutePath(), srcDir.getAbsolutePath())){
			return "选择的目标文件夹不能存在于源文件夹中";
		}
		File destFileR = null;
		if(destFile.isDirectory()){
			destFileR = new File(destFile.getAbsolutePath() +File.separator+"merge.txt");
			try{
				destFileR.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
				return "创建文件错误："+destFileR.getAbsolutePath();
			}
		}
		
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(destFileR));
			mergeOnly(srcDir, bw, specifySuffix);
		}catch(Exception e){
			e.printStackTrace();
			return "写文件错误："+destFile.getAbsolutePath();
		}finally{
			try{
				if(bw != null){
					bw.flush();
					bw.close();
				}
			}catch(Exception e){
				return "关闭文件错误："+destFile.getAbsolutePath();
			}
		}
		
		return null;
	}

	public static String mergeOnly(File srcDir, BufferedWriter bw, final String specifySuffix){
		if(bw == null){
			return "输出流为空";
		}
		 
		File[] currFiles = null;
		if(StringUtils.isNotBlank(specifySuffix)){
			currFiles = srcDir.listFiles(new FileFilter(){
				public boolean accept(File pathname){
					try{
						return pathname.getAbsolutePath().endsWith(specifySuffix);
					}catch(Exception e){
						e.printStackTrace();
					}
					return false;
				}
			});
		}else{
			currFiles = srcDir.listFiles();
		}
		
		for(File f : currFiles){
			if(f.isFile()){// 合并到目标文件
				BufferedReader br = null;
				try{
					br = new BufferedReader(new FileReader(f));
					String t = null;
					while((t=br.readLine())!=null){
						bw.write("\n");
						bw.write(t);
					}
				}catch(Exception e){
					return "读取文件错误："+f.getAbsolutePath();
				}finally{
					try{
						if(br != null){
							br.close();
						}
					}catch(Exception e){
						return "关闭文件错误："+f.getAbsolutePath();
					}
				}
			}else{
				mergeOnly(f, bw, specifySuffix);
			}
		}
		
		return null;
		
	}
}

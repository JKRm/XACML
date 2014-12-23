package org.cas.iie.xp.util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/*
 * doc docx格式转换
 * @author Administrator
 */
public class DocConverter {
    private static final int environment=1;//环境1：windows 2:linux(涉及pdf2swf路径问题)
    private String fileString;
    private String outputPath="";//输入路径，如果不设置就输出在默认位置
    private String fileName;
    private File pdfFile;
    private File swfFile;
    private File docFile;
    private boolean mark;
    
    public void setMark(boolean mark) {
		this.mark = mark;
	}

	public boolean isMark() {
		return mark;
	}

	public DocConverter(String fileString)
    {
        ini(fileString);
    }
    
    /*
     * 重新设置 file
     * @param fileString
     */
    public void setFile(String fileString)
    {
        ini(fileString);
    }
    
    /*
     * 初始化
     * @param fileString
     */
    private void ini(String fileString)
    {
    	this.setMark(true);
        this.fileString=fileString;
        fileName=fileString.substring(0,fileString.lastIndexOf("."));
        docFile=new File(fileString);
        pdfFile=new File(fileName+".pdf");
        Date date = new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddhhmmss");
        String sd = sf.format(date);
        String swfLoc = System.getProperty("user.dir").replace("bin", "webapps")
		+ "\\localResource\\swfLocation\\";
        //String swfLoc = fileString.substring(0,fileString.lastIndexOf("\\")+1)+"\\"+"swfLocation\\";
        File tempFile = new File(swfLoc);
        if(!tempFile.exists()){
        	tempFile.mkdir();
        }
        swfFile=new File(swfLoc+sd+".swf");
    }
    
    /*
     * 转为PDF
     * @param file
     */
    private void doc2pdf() throws Exception
    {
    	
        if(docFile.exists())
        {
            if(!pdfFile.exists())
            {
                OpenOfficeConnection connection=new SocketOpenOfficeConnection(8100);
                try
                {
                    connection.connect();
                    DocumentConverter converter=new OpenOfficeDocumentConverter(connection);
                    converter.convert(docFile,pdfFile);
                    //close the connection
                    connection.disconnect();
                    System.out.println("****pdf转换成功，PDF输出："+pdfFile.getPath()+"****");
                }
                catch(java.net.ConnectException e)
                {
                    //ToDo Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("****swf转换异常，openoffice服务未启动！****");
                    throw e;
                }
                catch(com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e)
                {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，读取转换文件失败****");
                    throw e;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    throw e;
                }
            }
            else
            {
            	this.setMark(false);
                System.out.println("****已经转换为pdf，不需要再进行转化****");
            }
        }
        else
        {
            System.out.println("****swf转换器异常，需要转换的文档不存在，无法转换****");
        }
    }
    
    /*
     * 转换成swf
     */
    private void pdf2swf() throws Exception
    {
        Runtime r=Runtime.getRuntime();
        String swfName = null;
        String chome = System.getenv("CATALINA_HOME");
        if(!swfFile.exists())
        {
            if(pdfFile.exists())
            {
                {
                    try {
                        String command = System.getProperty("user.dir").replace("bin", "webapps")
        				+ "\\XACML_Project_Clients\\tools\\pdf2swf.exe" + " -o \""
        				+ swfFile.getPath() + "\" -s flashversion=9 \""
        				+ pdfFile.getPath() + "\"";
                        Process p=r.exec(command);
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.print(loadStream(p.getErrorStream()));
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.println("****swf转换成功，文件输出："+swfFile.getPath()+"****");
                        if(pdfFile.exists()&&this.isMark())
                        {
                            pdfFile.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
            else {
                System.out.println("****pdf不存在，无法转换****");
            }
        }
        else {
            System.out.println("****swf已存在不需要转换****");
        }
        
    }
    
    static String loadStream(InputStream in) throws IOException
    {
        int ptr=0;
        in=new BufferedInputStream(in);
        StringBuffer buffer=new StringBuffer();
        
        while((ptr=in.read())!=-1)
        {
            buffer.append((char)ptr);
        }
        return buffer.toString();
    }
    
    /*
     * 转换主方法
     */
    public boolean conver()
    {
        if(swfFile.exists())
        {
            System.out.println("****swf转换器开始工作，该文件已经转换为swf****");
            return true;
        }
        
        {
            System.out.println("****swf转换器开始工作，当前设置运行环境windows****");
        }
        
        try {
            doc2pdf();
            pdf2swf();
        } catch (Exception e) {
            // TODO: Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        
        if(swfFile.exists())
        {
            return true;
        }
        else {
            return false;
        }
    }
    
    /*
     * 返回文件路径
     * @param s
     */
    public String getswfPath()
    {
        if(swfFile.exists())
        {
            String tempString =swfFile.getPath();
            tempString=tempString.replaceAll("\\\\", "/");
            return tempString;
        }
        else{
            return "";
        }
    }
    
    /*
     * 设置输出路径
     */
    public void setOutputPath(String outputPath)
    {
        this.outputPath=outputPath;
        if(!outputPath.equals(""))
        {
            String realName=fileName.substring(fileName.lastIndexOf("/"),fileName.lastIndexOf("."));
            if(outputPath.charAt(outputPath.length())=='/')
            {
                swfFile=new File(outputPath+realName+".swf");
            }
            else
            {
                swfFile=new File(outputPath+realName+".swf");
            }
        }
    }
    
  public String getSwfName(){
	  
	  String swfName = swfFile.getName();
	  
	  return swfName;
  }
    
}
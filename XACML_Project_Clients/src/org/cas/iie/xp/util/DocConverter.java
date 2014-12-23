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
 * doc docx��ʽת��
 * @author Administrator
 */
public class DocConverter {
    private static final int environment=1;//����1��windows 2:linux(�漰pdf2swf·������)
    private String fileString;
    private String outputPath="";//����·������������þ������Ĭ��λ��
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
     * �������� file
     * @param fileString
     */
    public void setFile(String fileString)
    {
        ini(fileString);
    }
    
    /*
     * ��ʼ��
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
     * תΪPDF
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
                    System.out.println("****pdfת���ɹ���PDF�����"+pdfFile.getPath()+"****");
                }
                catch(java.net.ConnectException e)
                {
                    //ToDo Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("****swfת���쳣��openoffice����δ������****");
                    throw e;
                }
                catch(com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e)
                {
                    e.printStackTrace();
                    System.out.println("****swfת�����쳣����ȡת���ļ�ʧ��****");
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
                System.out.println("****�Ѿ�ת��Ϊpdf������Ҫ�ٽ���ת��****");
            }
        }
        else
        {
            System.out.println("****swfת�����쳣����Ҫת�����ĵ������ڣ��޷�ת��****");
        }
    }
    
    /*
     * ת����swf
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
                        System.err.println("****swfת���ɹ����ļ������"+swfFile.getPath()+"****");
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
                System.out.println("****pdf�����ڣ��޷�ת��****");
            }
        }
        else {
            System.out.println("****swf�Ѵ��ڲ���Ҫת��****");
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
     * ת��������
     */
    public boolean conver()
    {
        if(swfFile.exists())
        {
            System.out.println("****swfת������ʼ���������ļ��Ѿ�ת��Ϊswf****");
            return true;
        }
        
        {
            System.out.println("****swfת������ʼ��������ǰ�������л���windows****");
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
     * �����ļ�·��
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
     * �������·��
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
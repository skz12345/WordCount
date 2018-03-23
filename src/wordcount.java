package testpackge;
import java.io.*;
import java.util.*;

public class wordcount {
	 private String sFilename;    // 文件名
	 private String stopwords;//停词表文件
	 private String outputfile;//输出文件名
	 private String[] sParameter; // 参数数组  
	 private int iCharcount;      // 字符数
	 private int iWordcount;      // 单词数
	 private int iLinecount;      // 总行数
	 private int iNullLinecount;  // 空行数
	 private int iCodeLinecount;  // 代码行数
	 private int iNoteLinecount;  // 注释行数
	 private int openStopList=0;//启用停用词表
	 private String[] StopList;//停词表
	 String outContent="";//输出到结果文件的数据串
	 
	  wordcount(String[] sParameter, String sFilename,String stopwords,String outputfile)//构造函数
	 {
		 this.sParameter=sParameter;
		 this.sFilename=sFilename;
		 this.stopwords=stopwords;
		 this.outputfile=outputfile;

	 }
	
	 public void Operator()//参数判断函数，判断传入了那些执行功能相关的参数
	 {int flagOfS=0;//判断是否需要递归处理文件
	  int flag=2;//判断是否需要变更结果文件路径
		 for (String s :this.sParameter)
	     {
			 if(s.equals("-s"))
			 { flagOfS=1; 
			 
	        	  BianLi();
	        	
			 }
	     }
    if(flagOfS==0)
	     {
    	for (String s :this.sParameter)
    	{
    		if(s.equals("-e"))
  	          {
  	        	  openStopList=1;
  	        	  getStopList();
  	          }
    	}
    	
    	for (String s :this.sParameter)
	      {
	         //  基本功能
	    	 
	          if ((s.equals("-c") || s.equals("-w") || s.equals("-l")))
	         {
	        	  BaseCount();
	        	  
          
	         }
	          else if(s.equals("-a"))
	          {
	        	  MoreCount();
	        	  

	          }
	          
	          else if(s.equals("-e"))
	          {
	        	  ;
	          }
	          else if(s.equals("-o"))
	          {
	        	  if(outputfile.length()<=1)
	        		   System.out.println("error ");
	        	  else
	        		  flag=1;
	          }
	         else
	         {
	             System.out.println("参数 "+s+"不存在");
	             break;
	         }
	     }
    	 Display();
	     outputTxt(flag);
	     
	 }
	 }  
	 
	 
	// 基本功能：字符数 单词数 行数
	 private void BaseCount()
	 {
		 
	     try
	     {
	    	 BufferedReader st1 = new BufferedReader(new FileReader(this.sFilename));
	         int nChar;
	         int charcount = 0;
	         int wordcount = 0;
	         int stopWordCount=0;
	         int linecount = 0;
	         
	        
	         while ((nChar = st1.read()) != -1)
	         {
	             charcount++;     // 统计字符数

	             
	             if (nChar == '\n')
	             {
	                 linecount++; // 统计行数
	             }
	         }
	         
	         
	           st1.close();
	         
	        	 BufferedReader st2 = new BufferedReader(new FileReader(this.sFilename));//统计单词数
	    		 String read1 = null;
	    		 int lengthofSplit=0;
	    		   while (( read1 =st2.readLine()) != null)
	    		   {
	    			  read1=read1.trim();//去除空格
	    		     String[] arrSplit =read1.split(" |,");//以空格或者逗号分隔
	    		     lengthofSplit=arrSplit.length;
	    		     for(String s:arrSplit)
	    		     {
	    		    	 if(s.length()==0)
	    		    		 lengthofSplit--;//去除空格
	    		     }
	    		     wordcount+=lengthofSplit;
	    		     if(openStopList==1)
	    		     {
	    		      for(String s:arrSplit)
	    		      {
	    		    	 for(String stop:StopList)
	    		    	 if(s.equals(stop))
	    		    		 stopWordCount++;
	    		    		 
	    		      }
	    		     }
	    		     
	    		   }
	    		   st2.close();
	         
	         
	         iCharcount = charcount;
	         iWordcount = wordcount -stopWordCount;
	         iLinecount = linecount + 1;
	         
	     }
	     catch (IOException ex)
	     {
	    	 ex.printStackTrace();
	         return;
	     }
	 }
	 

	
	 private void Display()//输出结果数据串的设置
	 {int flagOfc=0;
	  int flagOfw=0;
	  int flagOfl=0;
	  int flagOfa=0;
	     for (String s :sParameter)
	     {

	         if(s.equals("-c"))
       	  {
	        	 flagOfc=1;

       	  }
       	  if(s.equals("-w"))
       	  {
       		flagOfw=1;
       		  
       	  }
       	  if(s.equals("-l"))
       	  {flagOfl=1;
       		
       	  }
       	if(s.equals("-a"))
        {flagOfa=1;
       		
        }
       	
	         
	     }
	     if(flagOfc==1)
	     {
	    	 outContent = outContent + this.sFilename + ",字符数：" +iCharcount + "\r\n";
	     }
	     if(flagOfw==1)
	     {
	    	 outContent = outContent + this.sFilename + ",单词数：" +iWordcount + "\r\n";
	     }
	     if(flagOfl==1)
	     {
	    	 outContent = outContent + this.sFilename + ",行数：" +iLinecount + "\r\n";
	     }
	     if(flagOfa==1)
	     {
	    	 outContent = outContent + this.sFilename + ",代码行/空行/注释行:" +iCodeLinecount+"/"+iNullLinecount+"/"+iNoteLinecount + "\r\n";
	     }
	     
	     
	 }
	 
	 
	// 扩展功能：空行数 代码行数 注释行数
	 private void MoreCount()
	 {int flag=0;
	 int flagOfword=0;
	 int countOfword=0;
	 int numberOfMLines=0;
	
	     try
	     {
	        
	    	 BufferedReader br = new BufferedReader(new FileReader(this.sFilename));
	         String line;
	         int nulllinecount = 0;
	         int codelinecount = 0;
	         int notelinecount = 0;
	         while ((line = br.readLine()) != null)
	         {
	
	        	  
	             if (line == " " || line.trim().length() <= 1)//空行
	             {
	                 nulllinecount++;
	                 flag=1;
	            }

	             else{ //判断//类型的注释行
	            	 String [] s=new String[10000];
	          for (int x = 0; x < line.length(); x++) {
	           
	            s[x]=String.valueOf(line.charAt(x));
	        	 int temp=(x+1)<(line.length()-1)? x+1:(line.length()-1);
	        	 if((x<line.length()-1&&s[x].equals("/")&&line.charAt(temp)=='/'))
	        	  {
	        		
	            for(int i=0;i<x;i++)
	            	{if(line.charAt(i)!=' ')
	            	  {  if(line.charAt(i)=='\t')
	            		  continue;
	            		countOfword++;
	            	  }
	            	}
	            if(countOfword>1)
	            {
	            	flagOfword=1;
	             
	            }
	            else
	            {
	            	flag=1;
	            	notelinecount++;
	            }
	            
	            //System.out.println("wordcount"+countOfword);
	            
  
	              }
	        	  	               
	       	                    
	                   }
	            	 if(flag==0)
	 	            {
	 	                 codelinecount++;
	 	            }
	             }
	          
	            flag=0;
	            flagOfword=0;
	            countOfword=0;
	         }
	         br.close();
	         
	         
	          flagOfword=0;
	    	  countOfword=0;
	    	  numberOfMLines=0;
	         BufferedReader brm = new BufferedReader(new FileReader(this.sFilename));
	         String linem;
	         int flagOfM=0;//标记是否有/*开始了
	         int countss=0;//记录每行字符数以便判断是否是注释行
	         int counts=0;//记录每行字符数以便判断是否是注释行
	        while ((linem = brm.readLine()) != null)//判断/**/类型的注释行
	         {
	        	 
	        	 String [] sss=new String[10000];
	        	 if(flagOfM==1)
	        		numberOfMLines++;
	        	 
		          for (int x = 0; x < linem.length(); x++)
		          {
		           
		            sss[x]=String.valueOf(linem.charAt(x));
		        	 int temp=(x+1)<(linem.length()-1)? x+1:(linem.length()-1);
		        if(flagOfM==0)
		        { countss=0;//
		        	 if(x<linem.length()-1&&sss[x].equals("/")&&linem.charAt(temp)=='*')
		        	  {
		        		 for(int i=0;i<x;i++)
			            	{if(linem.charAt(i)!=' ')
			            	  {  if(linem.charAt(i)=='\t')
			            		  continue;
			            	  countss++;
        	 
			            	  }
			            	}
			            if(countss>1)
			            {
			            	flagOfM=0;

			            }
			            else
			            {
	
			            	flagOfM=1;
			            	
			            }
			            continue;
		        	  }
		        	 
	
		        	
		        }
		         if(flagOfM==1)
		        {counts=0;
		        	if(x<linem.length()-1&&sss[x].equals("*")&&linem.charAt(temp)=='/')
		        	{
		        		for(int i=temp+1;i<linem.length()-1;i++)
		        			{if(linem.charAt(i)!=' ')
			            	  {  if(linem.charAt(i)=='\t')
			            		  continue;
			            		counts++;
			            	  }
		        			}
		        		if(counts<=1)
		        		{
		        			numberOfMLines++;
			            	flagOfM=0;
			            	
		        		}
		        		else
		        		{
		        			flagOfM=0;
		        		}
		        		
		        	}
		        	
		       	  
		        	
		        }
		        
		      }
	        	 
	         }
	         brm.close();
	        // System.out.println("  numberOfMLines "+ numberOfMLines);//多行注释的行数
	         iNullLinecount = nulllinecount;
	         iCodeLinecount = codelinecount-numberOfMLines;
	         iNoteLinecount = notelinecount+numberOfMLines;
	         
	     }
	     catch (IOException ex)
	     {
	    	 ex.printStackTrace();
	         return;
	     }
	 }
	 
	
	 
	 private void getStopList()//获取停用词数组
	 {   int temp=0;
	 
		 try{
		 BufferedReader st = new BufferedReader(new FileReader(this.stopwords));
		 String read = null;
		   while (( read = st.readLine()) != null)
		   {
		     String[] arrStopSplit =read.split(" ");
		     temp+=arrStopSplit.length;
		    
		   }
		   StopList=new String[temp];
		   
		   temp=0;
		  st.close();
		   
		  BufferedReader st3 = new BufferedReader(new FileReader(stopwords));
			 String reads = null;
			   while (( reads = st3.readLine()) != null)
			   {
			     String[] arrStopSplit =reads.split(" ");//以空格分割
			    for(int i=0;i<arrStopSplit.length;i++)
			    {
			    	StopList[temp+i]=arrStopSplit[i];
			    }
			    temp+=arrStopSplit.length;//
			    
			   }
			   st3.close();
			   
			 /* for(String s:StopList)
			  {
				  System.out.println(s);
			  }*/
		 }
		 catch (IOException ex)
		    {
		    	ex.printStackTrace();
		        return;
		    }
		 
		 
	 }
	 
	 
	 private void outputTxt(int flag)//设置输出文件信息
	 {
		 if(flag==1)
			{
				try{
					File outfile = new File(this.outputfile);
					outfile.createNewFile(); // 创建新文件	
					FileWriter fw = new FileWriter(outfile);  
		        	BufferedWriter outStream = new BufferedWriter(fw);   
		        	outStream.write(outContent);
		        	outStream.flush(); 
		        	outStream.close(); 
					}
					catch(Exception e)
					{
						 e.printStackTrace();
					}
			}
			else if(flag==2)
			{
				try{
					String outpathname = "result.txt";
					File outfile = new File(outpathname);
		        	FileWriter fw = new FileWriter(outfile);  
		        	BufferedWriter outStream = new BufferedWriter(fw);   
		        	outStream.write(outContent);
		        	outStream.flush();  
		        	outStream.close(); 
					}
					catch(Exception e)
					{
						 e.printStackTrace();
					}
			}
	 }
	 
	 private void BianLi()//-s功能
	 {String type="";
		 List<String> paths = new ArrayList<String>();
		 List<String> finalFiles=new ArrayList<String>();//为获取符合条件的文件创造的队列
		 if(sFilename.charAt(0)=='*')//非全路径情况
		 { type=sFilename.substring(1);
	        paths = getAllFile(new File("./"),paths,type,finalFiles);
	       
		 }
		 else//全路径情况
		 {
			 int flag = -1;//用以分离路径和*.c
				for(int i=0;i<sFilename.length()-1;i++)
				{
					if(sFilename.charAt(i)=='*')
						if(sFilename.charAt(i+1)=='.')
							flag = i+1;
					 
				}

				type=sFilename.substring(flag);
				paths = getAllFile(new File(sFilename.substring(0,flag-1)),paths,type,finalFiles);
				
		 }
			 
	      /*  for(String path:paths){//
	            System.out.println(path);  
	        }*/
	        int flag=2;
	        for(String path:paths)//对于符合条件的文件循环操作
	        {
	        	this.sFilename=path;
	        	for (String s :this.sParameter)
	        	{
	        		if(s.equals("-e"))
		  	          {
		  	        	  openStopList=1;
		  	        	  getStopList();
		  	          }
	        	}
	        	
	        	for (String s :this.sParameter)
	  	      {
	  	         
	  	    	 if(s.equals("-s"))
	  	    		 continue;
	  	    	if ((s.equals("-c") || s.equals("-w") || s.equals("-l")))
		         {
		        	  BaseCount();
		        	 

		         }
	  	          else if(s.equals("-a"))
	  	          {
	  	        	  MoreCount();
	   
	  	          }
	  	          
	  	          else if(s.equals("-e"))
	  	          {
	  	        	  ;
	  	          }
	  	          else if(s.equals("-o"))
	  	          {   
	  	        	  if(outputfile.length()<1)
	  	        		   ;
	  	        	  else
	  	        		  flag=1;
	  	          }
	  	         else
	  	         {
	  	             System.out.println("参数 "+s+"不存在");
	  	             break;
	  	         }
	  	     }
	        	Display();
	        	outputTxt(flag);
	        	flag=2;
	        	
	        }
		 
	 }
	 
	 private static List<String> getAllFile(File filePath,List<String> filePaths,String type,List<String> finalFiles){
	        File[] files = filePath.listFiles();//递归获取文件夹以及子目录下的所有符合条件的文件
	        
	        if(files == null){
	            return filePaths;    
	        }    
	        for(File f:files){
	            if(f.isDirectory()){
	                filePaths.add(f.getPath());
	                getAllFile(f,filePaths,type,finalFiles);
	            }else{
	            	if(f.getName().endsWith(type))
	                {filePaths.add(f.getPath());
	            	finalFiles.add(f.getPath());}
	            }    
	        }
	       
	        
	        /*for(String path:finalFiles){
	            System.out.println(path);  
	        }*/
	        
	        return finalFiles;
	    }
	 
	 
	 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  
			  int countOfFunction=0;
		      int countOfFile=0;

		   for(String s:args)
		   {
			   if(s.length()==2)
				   countOfFunction++;//统计功能参数个数
		   }
		   
		   String[] functions=new String[countOfFunction];//功能参数数组
		   String sFilename="";//输入文件
		   String stoplistFile="";//停用词表文件
		   String outputFile="";//输出文件
		  // String[] files=new String[countOfFile];
		   int x=0,y=0,temp,flagoOfFile=0;
		  
				   for (String s:args)
				     {
				         
				         if( s.length()==2)
				         { functions[x]=s;
				              x++;
				         
				         
				        	 temp=(y+1)<args.length?(y+1):args.length;
				        	
				        	 if(s.equals("-o"))
				        	 {
				        		 outputFile=args[temp];
				        		
				        	 }
				        	 else if(s.equals("-e"))
				        	 {
				        		 stoplistFile=args[temp];
				        		 
				        	 }
				         }
				        	 else
				        	 {
				        		 if(flagoOfFile==0)
				        		 {
				        		 sFilename=s;
				        		 flagoOfFile=1;
				        		 }
				        	 }
				        	 
				        		 
				         
				        	y++; 
					  // System.out.println(s);
				     }
				  /* for(String s:functions)
				   {
					   System.out.println(s);
					   
				   }*/
				  /* System.out.println("sFilename "+sFilename);
				   System.out.println("stoplistFile "+stoplistFile);
				   System.out.println("outputFile "+outputFile);*/
	  
			       wordcount test=new wordcount(functions,sFilename,stoplistFile,outputFile);      
			       test.Operator(); 
			       
		  
		  
		 
	}


	

}

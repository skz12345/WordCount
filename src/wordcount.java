package testpackge;
import java.io.*;
import java.util.*;

public class wordcount {
	 private String sFilename;    // �ļ���
	 private String stopwords;//ͣ�ʱ��ļ�
	 private String outputfile;//����ļ���
	 private String[] sParameter; // ��������  
	 private int iCharcount;      // �ַ���
	 private int iWordcount;      // ������
	 private int iLinecount;      // ������
	 private int iNullLinecount;  // ������
	 private int iCodeLinecount;  // ��������
	 private int iNoteLinecount;  // ע������
	 private int openStopList=0;//����ͣ�ôʱ�
	 private String[] StopList;//ͣ�ʱ�
	 String outContent="";//���������ļ������ݴ�
	 
	  wordcount(String[] sParameter, String sFilename,String stopwords,String outputfile)//���캯��
	 {
		 this.sParameter=sParameter;
		 this.sFilename=sFilename;
		 this.stopwords=stopwords;
		 this.outputfile=outputfile;

	 }
	
	 public void Operator()//�����жϺ������жϴ�������Щִ�й�����صĲ���
	 {int flagOfS=0;//�ж��Ƿ���Ҫ�ݹ鴦���ļ�
	  int flag=2;//�ж��Ƿ���Ҫ�������ļ�·��
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
	         //  ��������
	    	 
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
	             System.out.println("���� "+s+"������");
	             break;
	         }
	     }
    	 Display();
	     outputTxt(flag);
	     
	 }
	 }  
	 
	 
	// �������ܣ��ַ��� ������ ����
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
	             charcount++;     // ͳ���ַ���

	             
	             if (nChar == '\n')
	             {
	                 linecount++; // ͳ������
	             }
	         }
	         
	         
	           st1.close();
	         
	        	 BufferedReader st2 = new BufferedReader(new FileReader(this.sFilename));//ͳ�Ƶ�����
	    		 String read1 = null;
	    		 int lengthofSplit=0;
	    		   while (( read1 =st2.readLine()) != null)
	    		   {
	    			  read1=read1.trim();//ȥ���ո�
	    		     String[] arrSplit =read1.split(" |,");//�Կո���߶��ŷָ�
	    		     lengthofSplit=arrSplit.length;
	    		     for(String s:arrSplit)
	    		     {
	    		    	 if(s.length()==0)
	    		    		 lengthofSplit--;//ȥ���ո�
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
	 

	
	 private void Display()//���������ݴ�������
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
	    	 outContent = outContent + this.sFilename + ",�ַ�����" +iCharcount + "\r\n";
	     }
	     if(flagOfw==1)
	     {
	    	 outContent = outContent + this.sFilename + ",��������" +iWordcount + "\r\n";
	     }
	     if(flagOfl==1)
	     {
	    	 outContent = outContent + this.sFilename + ",������" +iLinecount + "\r\n";
	     }
	     if(flagOfa==1)
	     {
	    	 outContent = outContent + this.sFilename + ",������/����/ע����:" +iCodeLinecount+"/"+iNullLinecount+"/"+iNoteLinecount + "\r\n";
	     }
	     
	     
	 }
	 
	 
	// ��չ���ܣ������� �������� ע������
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
	
	        	  
	             if (line == " " || line.trim().length() <= 1)//����
	             {
	                 nulllinecount++;
	                 flag=1;
	            }

	             else{ //�ж�//���͵�ע����
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
	         int flagOfM=0;//����Ƿ���/*��ʼ��
	         int countss=0;//��¼ÿ���ַ����Ա��ж��Ƿ���ע����
	         int counts=0;//��¼ÿ���ַ����Ա��ж��Ƿ���ע����
	        while ((linem = brm.readLine()) != null)//�ж�/**/���͵�ע����
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
	        // System.out.println("  numberOfMLines "+ numberOfMLines);//����ע�͵�����
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
	 
	
	 
	 private void getStopList()//��ȡͣ�ô�����
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
			     String[] arrStopSplit =reads.split(" ");//�Կո�ָ�
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
	 
	 
	 private void outputTxt(int flag)//��������ļ���Ϣ
	 {
		 if(flag==1)
			{
				try{
					File outfile = new File(this.outputfile);
					outfile.createNewFile(); // �������ļ�	
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
	 
	 private void BianLi()//-s����
	 {String type="";
		 List<String> paths = new ArrayList<String>();
		 List<String> finalFiles=new ArrayList<String>();//Ϊ��ȡ�����������ļ�����Ķ���
		 if(sFilename.charAt(0)=='*')//��ȫ·�����
		 { type=sFilename.substring(1);
	        paths = getAllFile(new File("./"),paths,type,finalFiles);
	       
		 }
		 else//ȫ·�����
		 {
			 int flag = -1;//���Է���·����*.c
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
	        for(String path:paths)//���ڷ����������ļ�ѭ������
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
	  	             System.out.println("���� "+s+"������");
	  	             break;
	  	         }
	  	     }
	        	Display();
	        	outputTxt(flag);
	        	flag=2;
	        	
	        }
		 
	 }
	 
	 private static List<String> getAllFile(File filePath,List<String> filePaths,String type,List<String> finalFiles){
	        File[] files = filePath.listFiles();//�ݹ��ȡ�ļ����Լ���Ŀ¼�µ����з����������ļ�
	        
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
				   countOfFunction++;//ͳ�ƹ��ܲ�������
		   }
		   
		   String[] functions=new String[countOfFunction];//���ܲ�������
		   String sFilename="";//�����ļ�
		   String stoplistFile="";//ͣ�ôʱ��ļ�
		   String outputFile="";//����ļ�
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

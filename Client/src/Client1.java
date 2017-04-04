/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DJSCE.Student
 */
//165.63
//150.44
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.io.*;
public class Client1 implements Runnable{
 
    static int itemsetNumber=0;
    static int batch;
    static String transaFile;
    static String outputFile;
    static Vector<String> candidates=new Vector<String>(); //the current candidates
    static String configFile="config.txt"; //configuration file
    // String transaFile="transa.txt"; //transaction file
    //String outputFile="output.txt";//output file
    static int numItems; //number of items per transaction
    static int numTransactions; //number of transactions
    static int numTrans;
    static String isv=" ";   
    static double minSup; //minimum support for a frequent itemset
    static String oneVal[]; //array of value per column that will be treated as a '1'
    static String itemSep = " "; //the separator value for items in the database
 static double exe;
    Client1(int b, String trname, int transno, int items, double sc) {
        transaFile=trname;
        //System.out.println("Transaction file"+transaFile);
 numTransactions=transno;
 try{
     updown c=(updown)Naming.lookup("rmi://192.168.34.37//updownService");
 String na="transaction no1"+b+".txt";
 
 String g=transno+"";
 
   FileWriter fw= new FileWriter(na, true);
            BufferedWriter  file_out = new BufferedWriter(fw);
            System.out.println("transno"+transno);
            file_out.write(g);
           
          String  s=g;
            byte[] buff=s.getBytes();
            c.upload(na,buff);
             file_out.close();
 }
 catch(Exception e)
 {
         System.out.println("catch 1");
 }       
            
 numItems=items;
 minSup=sc;
  //System.out.println("min supp"+sc);
 batch=b; //To change body of generated methods, choose Tools | Templates.

 main();
    }
    Client1()
    {
    }
    
   
   public static void main(){
       oneVal = new String[numItems];
           itemsetNumber=0;
            for(int i=0; i<oneVal.length; i++)
                oneVal[i]="1";
       System.setProperty("java.rmi.server.hostname","192.168.34.37");
     /*  if (batch!=1){
       try{
                FileInputStream file_in = new FileInputStream(transaFile);
            BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
            StringBuffer sb=new StringBuffer();
            String line = data_in.readLine();
            
            while (line != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = data_in.readLine();
                
            }
            
            String s=sb.toString();   
            file_in.close();
            data_in.close();
        FileWriter fw= new FileWriter("trans1.txt", true);
	BufferedWriter  file_out = new BufferedWriter(fw);
                        file_out.write(s);
                        //file_out.newLine();
	file_out.close();
    }
       catch(Exception e){}
       }
       */
       Date d; //date object for timing purposes
       long start, end; //start and end time
       String line1="";
   
       //getConfig();
       numTrans=numTransactions;
   
       d = new Date();
       start = d.getTime();
        do
        {	try{
        		PrintWriter pw=new PrintWriter(outputFile);
			pw.flush();
            PrintWriter p=new PrintWriter("FreqSets1.txt");
			p.flush();
            }catch(Exception e){
}
            //increase the itemset that is being looked at
            itemsetNumber++;
            
            outputFile="output1"+batch+""+itemsetNumber+".txt";
            //generate the candidates
            generateCandidates(itemsetNumber);

            //determine and display frequent itemsets
            calculateFrequentItemsets(itemsetNumber);
            if(candidates.size()!=0)
            {
                System.out.println("Frequent " + itemsetNumber + "-itemsets");
                System.out.println(candidates);
            }
            
 try{
            String fname=outputFile;
           // System.out.println("OUT name "+outputFile);
            updown c=(updown)Naming.lookup("rmi://192.168.34.37//updownService");
            
            FileInputStream file_in = new FileInputStream(fname);
            BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
            StringBuffer sb=new StringBuffer();
            String line = data_in.readLine();
            
            while (line != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = data_in.readLine();
                
            }
            
            String s=sb.toString();
            System.out.println(s);
            byte[] buff=s.getBytes();
            c.upload(fname,buff);
	//System.out.println("uploaded");
        }
        catch(Exception e)
        {
        }
   //System.out.println("Candidate size"+candidates.size());
if (candidates.size()>0){ 
     String line="";
    int size;
	//System.out.println("hello "+i+"bye "+o+" a"+a);
	try
	{
			updown c=(updown)Naming.lookup("rmi://192.168.34.37:1099//updownService");
	do
	{
           

		//System.out.println("Downloading file from Client "+(a+1));
                String s=batch+"Batch"+itemsetNumber+"Freq.txt";
			File fi=new File(s);
			size=(int)fi.length();
                        //String freq="FreqSets1"+ct+".txt";
			FileOutputStream fout1=new FileOutputStream("FreqSets1.txt");                        
            	
			byte b1[]=c.download(s);
			String s1 = Arrays.toString(b1);
                        String s2 = new String(b1);
                        size=s2.length();
			
			fout1.write(b1,0,size);
			Client1 t1= new Client1();
			Thread t=new Thread(t1);
			t.start();
			t.sleep(15000);
			FileInputStream file_in = new FileInputStream("FreqSets1.txt");
                        BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
                        
                        data_in = new BufferedReader(new InputStreamReader(file_in));
                        line = data_in.readLine();
			
	

}while(line==null);
}
catch(Exception e){System.out.println("catch here"+e);}
   
	try{
        		PrintWriter pw=new PrintWriter(outputFile);                        
			pw.flush();
                        numTrans=transactionReduction("FreqSets1.txt");
            }catch(Exception e){}
        //if there are <=1 frequent items, then its the end. This prevents reading through the database again. When there is only one frequent itemset.
}       
        }while(candidates.size()>0);
        
 try{     
            FileWriter fw= new FileWriter(outputFile, true);
            BufferedWriter  file_out = new BufferedWriter(fw);
            file_out.write("No more files generated");
            file_out.newLine();
            file_out.close();
            String fname=outputFile;
         //System.out.println("OUT name "+outputFile);
            updown c=(updown)Naming.lookup("rmi://192.168.34.37//updownService");
            
            FileInputStream file_in = new FileInputStream(fname);
            BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
            StringBuffer sb=new StringBuffer();
            String line = data_in.readLine();
            
            while (line != null) {
                

                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = data_in.readLine();
                
            }
            
            String s=sb.toString();
            System.out.println(s);
            byte[] buff=s.getBytes();
            c.upload(fname,buff);
    }
    catch(Exception e){}
     
   String line="";
    int size;
	//System.out.println("hello "+i+"bye "+o+" a"+a);
	try
	{
			updown c=(updown)Naming.lookup("rmi://192.168.34.37:1099//updownService");
	do
	{
           

		//System.out.println("Downloading file from Client "+(a+1));
                String s="association rules"+batch+".txt";
			File fi=new File(s);
			size=(int)fi.length();
                        //String freq="FreqSets1"+ct+".txt";
			FileOutputStream fout1=new FileOutputStream(s);
                       
            	
			byte b1[]=c.download(s);
			String s1 = Arrays.toString(b1);
                        String s2 = new String(b1);
                        size=s2.length();
			
			fout1.write(b1,0,size);
			Client1 t1= new Client1();
			Thread t=new Thread(t1);
			t.start();
			t.sleep(15000);
			FileInputStream file_in = new FileInputStream(s);
                          BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
                        //line=line.replaceAll(line, "");
                        //System.out.println("after replaceall "+line);
            			 data_in = new BufferedReader(new InputStreamReader(file_in));
             			line = data_in.readLine();
			
	

}while(line==null);
}
catch(Exception e){System.out.println("catch here"+e);}
        MainClass m=new MainClass();
            m.main();
   
    /*try{
    	Thread t=new Thread();
			t.start();
			t.sleep(10000);
    	updown c=(updown)Naming.lookup("rmi://192.168.34.37//updownService");
			File fi=new File("association rules.txt");
			int size=(int)fi.length();
			FileOutputStream finalout1=new FileOutputStream("association rules 1.txt");
			byte b1[]=c.download("association rules.txt");
			finalout1.write(b1,0,size);
		 d = new Date();
    end = d.getTime();
    
    //display the execution time
    System.out.println("Execution time is: "+((double)(end-start)/1000) + " seconds.");
    }
    catch(Exception e)
    {
    }*/
        
      d = new Date();
    end = d.getTime();
    
    //display the execution time
    System.out.println("Execution time is: "+((double)(end-start)/1000) + " seconds."); 
     exe=(double)(end-start)/1000;
}
   
    private static void getConfig()
    {
        FileWriter fw;
        BufferedWriter file_out;
   
        
        try
        {
            FileInputStream file_in = new FileInputStream(configFile);
            BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
            //number of items
            numItems=Integer.valueOf(data_in.readLine()).intValue();
            
            //number of transactions
            numTransactions=Integer.valueOf(data_in.readLine()).intValue();
            
            //minsup
            minSup=(Double.valueOf(data_in.readLine()).doubleValue());
          
            
            //output config info to the user
            System.out.print("\nInput configuration: "+numItems+" items, "+numTransactions+" transactions, ");
            System.out.println("minsup = "+minSup+"%");
            minSup/=100.0;
            
            oneVal = new String[numItems];
          
            for(int i=0; i<oneVal.length; i++)
                oneVal[i]="1";
          
        }
        //if there is an error, print the message
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
        
        private static void generateCandidates(int n)
        {
        	try{
        	
        	
            Vector<String> tempCandidates = new Vector<String>(); //temporary candidate string vector
            String str1, str2; //strings that will be used for comparisons
            StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared
            
            //if its the first set, candidates are just the numbers
            if(n==1)
            {
                for(int i=1; i<=numItems; i++)
                {
                    tempCandidates.add(Integer.toString(i));
                }
            }
            else if(n==2) //second itemset is just all combinations of itemset 1
            {
                //add each itemset from the previous frequent itemsets together
                for(int i=0; i<candidates.size(); i++)
                {
                    st1 = new StringTokenizer(candidates.get(i));
                    str1 = st1.nextToken();
                    
                    for(int j=i+1; j<candidates.size(); j++)
                    {
                        st2 = new StringTokenizer(candidates.elementAt(j));
                        str2 = st2.nextToken();
                        tempCandidates.add(str1 + isv + str2);
                      //  System.out.println(str1 + isv + str2);
                        
                    }
                }
            }
            else
            {
                //for each itemset
                for(int i=0; i<candidates.size(); i++)
                {
                    //compare to the next itemset
                    for(int j=i+1; j<candidates.size(); j++)
                    {
                        //create the strigns
                        str1 = new String();
                        str2 = new String();
                        //create the tokenizers
                        st1 = new StringTokenizer(candidates.get(i));
                        st2 = new StringTokenizer(candidates.get(j));
                        
                        //make a string of the first n-2 tokens of the strings
                        for(int s=0; s<n-2; s++)
                        {
                            str1 = str1 + isv + st1.nextToken();
                            str2 = str2 + isv + st2.nextToken();
                            //System.out.println("str1:"+str1+"   str2:"+str2);
                        }
                        
                        //if they have the same n-2 tokens, add them together
                        if(str2.compareToIgnoreCase(str1)==0)
                            tempCandidates.add((str1 + isv + st1.nextToken() + isv + st2.nextToken()).trim());
                    }
                }
            }
            //clear the old candidates
            candidates.clear();
            //set the new ones
            candidates = new Vector<String>(tempCandidates);
            //        System.out.println(candidates);
            tempCandidates.clear();
        }catch(Exception e){}
        
        }
        /************************************************************************
         * Method Name  : calculateFrequentItemsets
         * Purpose      : Determine which candidates are frequent in the n-th itemsets
         *              : from all possible candidates
         * Parameters   : n - iteger representing the current itemsets being evaluated
         * Return       : None
         *************************************************************************/
        private static void calculateFrequentItemsets(int n)
        {
            
            
            //System.out.println("In calfreq");
            itemSep=" ";
        	try{
        		
            Vector<String> frequentCandidates = new Vector<String>(); //the frequent candidates for the current itemset
            FileInputStream file_in; //file input stream
            BufferedReader data_in; //data input stream
            FileWriter fw;
            BufferedWriter file_out;
            
            StringTokenizer st, stFile; //tokenizer for candidate and transaction
            boolean match; //whether the transaction has all the items in an itemset
            boolean trans[] = new boolean[numItems]; //array to hold a transaction so that can be checked
            int count[] = new int[candidates.size()]; //the number of successful matches
            
            try
            {
                //output file
                fw= new FileWriter(outputFile, true);
                //System.out.println("OUT name "+outputFile);
                file_out = new BufferedWriter(fw);
                //load the transaction file
                file_in = new FileInputStream(transaFile);
                data_in = new BufferedReader(new InputStreamReader(file_in));
                //String lines="";
                //for each transaction
                for(int i=0; i<numTrans; i++)
                //while( (lines = data_in.readLine())!= null )
                {
                    //System.out.println("Got here " + i + " times"); //useful to debug files that you are unsure of the number of line
                    stFile = new StringTokenizer(data_in.readLine(), itemSep); //read a line from the file to the tokenizer
                    //put the contents of that line into the transaction array
                    for(int j=0; j<numItems; j++)
                    {
                        trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
                       // System.out.println("In trans for"+trans[j]);
                    }
                   // System.out.println("trans no"+numTransactions);
                    //check each candidate
                    for(int c=0; c<candidates.size(); c++)
                    {
                        
                        match = false; //reset match to false
                        //tokenize the candidate so that we know what items need to be present for a match
                        st = new StringTokenizer(candidates.get(c));
                                             //  	System.out.println("candidates:"+candidates+ " "+candidates.get(c));
                        //check each item in the itemset to see if it is present in the transaction
                        while(st.hasMoreTokens())
                        {
                            match = (trans[Integer.valueOf(st.nextToken())-1]);
                            if(!match) //if it is not present in the transaction stop checking
                                break;
                        }
                        if(match) //if at this point it is a match, increase the count
                            count[c]++;
                                 //   System.out.println(count[c]);
                    }
                    
                }
                for(int i=0; i<candidates.size(); i++)
                {
                    //  System.out.println("Candidate: " + candidates.get(c) + " with count: " + count + " % is: " + (count/(double)numItems));
                    //if the count% is larger than the minSup%, add to the candidate to the frequent candidates
                    
                    double xyz=(count[i]/(double)numTransactions);
                    //                    System.out.println("Count"+i+" "+count[i]+"\nxyz="+xyz+"\n");
                    if(xyz>=minSup)
                    {
                        frequentCandidates.add(candidates.get(i));
                        //put the frequent itemset into the output file
                       // System.out.println("Inside file write"+candidates.get(i) + " " + count[i]/(double)numTransactions);
                        file_out.write(candidates.get(i) + " " + count[i]/(double)numTransactions);
                        file_out.newLine();
                        
                    }
                }
                file_out.write("");
                file_out.close();
                file_in.close();
            data_in.close();
            }
            
            //if error at all in this process, catch it and print the error messate
            catch(IOException e)
            {
                System.out.println(e);
            }
            //clear old candidates
            candidates.clear();
            //new candidates are the old frequent candidates
            candidates = new Vector<String>(frequentCandidates);
            frequentCandidates.clear();
        }catch(Exception e){}
        }
        
      public void run(){  
//System.out.println("thread is running...");  
}    
        
 private static int transactionReduction (String s) throws IOException
{
   // System.out.println("In trans red");
    /*FileWriter fw= new FileWriter("trans1.txt", true);
                        BufferedWriter  file_out = new BufferedWriter(fw);
                        file_out.write("Hi");
                        file_out.close();
    */                    
int counter=0;
int exists[] = new int[numItems]; // array with number of columns INITIALIZE ALL VALUES TO FALSE
for(int j=0; j<numItems; j++){
    exists[j]=0;
}
FileInputStream file_in = new FileInputStream(s);
BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
String line = "";
while( (line = data_in.readLine())!= null ){
   // System.out.println("Freq Sets"+ line);
                String [] tokens = line.split(" ");
                int last=tokens.length;
                for (int k=0;k<last-2;k++)  	
                {
                            int a=Integer.parseInt(tokens[k])-1;
                            exists[a]=1;
                }
    }  
//System.out.println("exists file ");
for (int k=0;k<numItems;k++)  	
                {
                           
                            //System.out.print(exists[k]);
                } 
File inputFile = new File(transaFile);
File tempFile = new File("1Client"+itemsetNumber+"temp.txt");
PrintWriter pw=new PrintWriter(tempFile);
			pw.flush();
                        pw.close();
FileInputStream file_in1 = new FileInputStream(inputFile);
BufferedReader data_in1 = new BufferedReader(new InputStreamReader(file_in1));
String line1 = "";
while( (line1 = data_in1.readLine())!= null ){
    
                String [] tokens = line1.split(" ");
                int flag=0;
                int last=tokens.length;
                //System.out.println("No of trans cols"+last);
                for (int k=0;k<last;k++)  	
                {
                        if (exists[k]==1 && tokens[k].equals("1")){
                            
                            flag=1;
                            //System.out.println("Loop broken");
                            break;
                            
                            }
                }
                if (flag==1){
                    //System.out.println("Transaction"+line1);
                FileWriter fw= new FileWriter(tempFile, true);
                        BufferedWriter  file_out = new BufferedWriter(fw);
                        file_out.write(line1);
                        file_out.newLine();
                        file_out.close();
                        counter++;
                        //System.out.println("counter"+counter);
                }
    }  
data_in1.close();
file_in1.close();
//boolean success=inputFile.delete();
if (inputFile.delete()){
boolean success=tempFile.renameTo(inputFile);
System.out.println("Success"+success);
}
return counter;
}      
}

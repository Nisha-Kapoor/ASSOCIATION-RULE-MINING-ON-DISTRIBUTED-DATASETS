import java.rmi.*;  
import java.rmi.server.*; 
import java.util.*;
import java.io.*;
/*public class globalRemote extends UnicastRemoteObject implements global{  
globalRemote()throws RemoteException{  
	super();  
}  
	public void add(int iterationNo) throws java.rmi.RemoteException
    {	
        	Client3 ap = new Client3();
        	ap.Pass(iterationNo);
        	ap.Process();
    }      
}  
*/
class Client3 implements Runnable{
static	int iterationNo=0;
static int client;
static int exists[], a[];
static int tsum=0;
static int batch;
static int previous;
static double count, avg, minSup,minConf;
static String item="", outputFile, out;
static int counter, fileGen, fileCount;
static double exe;
/* public void Pass(int b){
int a=b;
outputFile=a+"FrequentItems.txt";
} */

public static void main (int cl,int b,double ms,double mc) throws IOException {  
client=cl;
 minSup=ms;
 batch=b;
 minConf=mc;
 exists=new int[client];
 a =new int[client];
 iterationNo=0;
 previous=batch-1;
 int client_no=1;


fileCount=0;
tsum=0;
            

                     
                   

	
	try
	{
			updown c=(updown)Naming.lookup("rmi://192.168.34.37:1099//updownService");

       
         for(int i=1;i<=client;i++) // loop for saving the number of transactions
         {
              String s2=""  ;      
               
             do{
                
		   String h="transaction no"+i+""+batch+".txt";
			File fi=new File(h);
			int size=(int)fi.length();
                       
			byte b1[]=c.download(h);
			
                        s2 = new String(b1);				
						
          
             }while(s2==null);
                a[i-1]=Integer.parseInt(s2);
               System.out.println(a[i-1]);
                tsum+=a[i-1];

         } 
         System.out.println("tsum "+tsum);
         int v=0;
          String o= "TransactionNumber.txt";
	File file1 = new File(o);
        if (batch>1){ 
        
             BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));    
               
            	
             			String line = br1.readLine();	
                                v=Integer.parseInt(line);
                                  System.out.println("old line"+v);
                                v+=tsum;
						
        }
        else if (batch==1)
            v=tsum;
                
                        FileWriter fw= new FileWriter(o, true);
                        PrintWriter pws= new PrintWriter(o);
                        pws.flush();
	BufferedWriter  file_out = new BufferedWriter(fw);
          
                        file_out.write(v+"");
                        file_out.newLine();
	file_out.close();
         //client_no++;
                        
} 
catch(Exception e){System.out.println("catch here"+e);}	


        
        
while (true){
  for (int i=0; i<client; i++){
      	if (exists[i]!=2)
exists[i]=0;
}
iterationNo++;
fileGen=0;
//System.out.println("iterationNo"+iterationNo);
//System.out.println("batch" +batch);
outputFile=batch+"Batch"+iterationNo+"Freq.txt";
//System.out.println("iterationNo batch");
try{
   // System.out.println("try");
    PrintWriter pw=new PrintWriter(outputFile);
	pw.flush();
System.out.println("flush");
}
	catch(Exception e){}
//System.out.println("OFile="+outputFile);

    

try{
for (int i=0; i<client; i++){
	out="out"+(i+1)+""+(batch-1)+".txt";
       // System.out.println( "out "+ out);
    
	PrintWriter pw=new PrintWriter(out);
	pw.flush();
}	
}catch(Exception e){System.out.println(e);
}
//counter=0;
//System.out.println(iterationNo);
for (int i=0; i<client; i++){
	String s=Integer.toString(iterationNo);
String output="output"+(i+1)+""+(batch)+""+s+".txt";
//System.out.println("output"+output);
out="out"+(i+1)+""+batch+".txt";
//System.out.println("batch no "+batch+"calling getoutput"+" Output"+output+"   out"+out+"   i"+i);
getOutput(output, out,i);
}
for (int i=0; i<client; i++)
//System.out.println(i+" "+exists[i]);
counter=0;
for (int i=0; i<client; i++){
if (exists[i]!=2){
	counter=1;
	break;
}
}
//System.out.println("Counter "+counter);
if (counter==0) break;
try{
String arr[]= new String[100];
int n=0;
for (int i=0; i<client; i++)
{
     if (exists[i]==1){
out="out"+(i+1)+""+batch+".txt";
FileInputStream file_in = new FileInputStream(out);
BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
String line = "";
while( (line = data_in.readLine())!= null ){
	item="";
    	String [] tokens = line.split(" ");
      	int last=tokens.length;
   	for (int k=0;k<last-1;k++)  		
   	item += tokens[k]+" ";	
	if (Arrays.asList(arr).contains(item));
        else
        {
            arr[n]=item;
            n++;
        }
}
}
}
//for(int m=0; m<arr.length; m++) 
 //   System.out.println(arr[m]);
 for(int k=0; k<arr.length; k++){
     count=0.0;
    for (int j=0; j<client; j++)     
    {
	//System.out.print(j+"In j for loop");
    	if (exists[j]==1){
            String a="out"+(j+1)+""+batch+".txt";
	count+=(match(a, arr[k],j,batch));
        System.out.println("COUNT after match: "+count);
	}
    }
   

    avg=count/(double)tsum; 
    //System.out.println("count="+count+"  tsum"+tsum+"   div"+avg);
     int trans1=0;
    if (batch!=1){
        
    File file = new File("TransactionNumber.txt");
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

String line = br.readLine();

        trans1 = Integer.parseInt(line);

System.out.println("trans1"+trans1);
        double numer, denom;
        String s=previous+"Batch"+iterationNo+"Freq.txt";
        //System.out.println("s"+s+"and arr[k]"+arr[k]);
        double no=matchNew(s, arr[k]);
       System.out.println("no "+no+"  count  "+count);
        numer=count+no;
        
        denom=trans1;
   // oldAvg=no*previous;
    //System.out.println("oldAvg"+oldAvg);
    avg=(double)numer/denom;
    System.out.println("2  numer="+numer+"  denom"+denom+"   div"+avg);
    //System.out.println("Sum"+avg);
    //avg/=(double)batch;
    //System.out.println("New avg"+avg);
    }
  	if(avg>=minSup)
                    {
	fileGen=1;
	FileWriter fw1= new FileWriter(outputFile, true);
	BufferedWriter  file_out1 = new BufferedWriter(fw1);
	System.out.println("item and avg"+item+avg);
                        file_out1.write(arr[k] + " " + avg);
                        file_out1.newLine();
	file_out1.close();
                    }

}	



}

catch(Exception e){System.out.println(e);}
//System.out.println("fileGen"+fileGen);
if (fileGen==1){
	//System.out.println("inside generate");
    fileCount=fileCount+1;
  //  System.out.println("fileCount1"+fileCount);
}
else if (fileGen==0){
    try{
		//System.out.println("inside no more files generated");

        FileWriter fw2= new FileWriter(outputFile, true);
	BufferedWriter  file_out2 = new BufferedWriter(fw2);
                        file_out2.write("No more files generated");
                        file_out2.newLine();
	file_out2.close();
    }
    catch(Exception e){}
}

        try{
            updown c=(updown)Naming.lookup("rmi://192.168.34.37:1099//updownService");
            
            FileInputStream file_in = new FileInputStream(outputFile);
            BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
            String s="";
            StringBuffer sb=new StringBuffer();
            String line = data_in.readLine();
           // System.out.println("uploading");
            while (line != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = data_in.readLine();
            }
            
            s=sb.toString();
            byte[] buff=s.getBytes();
           // System.out.println(s);
            c.upload(outputFile,buff);
            file_in.close();
            data_in.close();
        }
        catch(Exception e)
        {System.out.println(e);
        } 
        try{
    Client3 t1= new Client3();
			Thread t=new Thread(t1);
			t.start();
			//System.out.println("Before sleep");
			t.sleep(5000);
			//System.out.println("After sleep");
    }
    catch(Exception e){System.out.println(e);}
 
try{
for (int i=0; i<client; i++){
    
	out="out"+(i+1)+""+batch+".txt";
	//System.out.println("out"+out);
	PrintWriter pw=new PrintWriter(out);
	pw.flush();
	  //System.out.println("flushing");
}	
}catch(Exception e){System.out.println(e);
}
}
 //    System.out.println("file count"+fileCount);
associationRules(fileCount);

}
public static void getOutput(String i, String o, int a){
	//System.out.println(exists[a]!=2);
	if (exists[a]!=2){
    String line="";
//	System.out.println("hello "+i+"bye "+o+" a"+a);
	
    
    int size;
	System.out.println("Downloading file from Client "+(a+1));
	
	line="";
	
	try
	{
			updown c=(updown)Naming.lookup("rmi://192.168.34.37:1099//updownService");
	do
	{
            

		
			File fi=new File(i);
			size=(int)fi.length();
                        //String freq="FreqSets1"+ct+".txt";
			FileOutputStream fout1=new FileOutputStream(o);                                    	
			byte b1[]=c.download(i);
			String s1 = Arrays.toString(b1);
                        String s2 = new String(b1);
                        size=s2.length();
			//System.out.println("s1 "+s1+"s2 "+s2);
                       //System.out.println("hello "+i+"bye "+o+" a"+a);
			//System.out.println("size "+size);
                        //if(size>5000){
                          //  break;
                        //}
			fout1.write(b1,0,size);
			Client3 t1= new Client3();
			Thread t=new Thread(t1);
			t.start();
			t.sleep(1000);
			FileInputStream file_in = new FileInputStream(o);
                          BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
                        //line=line.replaceAll(line, "");
                        //System.out.println("after replaceall "+line);
            			 data_in = new BufferedReader(new InputStreamReader(file_in));
             			line = data_in.readLine();	
						
						
}while(line==null);
if (exists[a]==2)
	return;
else{
if (line.equals("No more files generated")){
//System.out.println("no more");

			exists[a]=2;
            }
			
		else {
			//System.out.println("counter loop");
				exists[a]=1;
                               // counter=1;
}}
}catch(Exception e){System.out.println("catch here"+e);}	
}
}
public static double match(String out, String item, int client, int batch) throws IOException{
int flag=0;
int trans=0;
File file = new File(out);
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
String line = "";
while( (line = br.readLine())!= null && flag!=1){
    String var_1="";
    	String [] tokens = line.split(" ");
    	int last=tokens.length;
    for (int i=0;i<(last-1);i++)      	
   			var_1 += tokens[i]+" ";
 
if (var_1.equals(item)){
count = Double.parseDouble(tokens[last-1]);
flag=1;
break;
		}
	}


trans = a[client];


if (flag==0)
    count=0.0;
br.close();
System.out.println("count="+count+"  trans"+trans+"   multi"+(count*trans));
return count*trans;

}
public static double matchNew(String out, String item) throws IOException{
  double count1=0.0;
int flag=0;
int trans=0;
File file = new File(out);
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
String line = "";
while( (line = br.readLine())!= null && flag!=1){
    String var_1="";
    	String [] tokens = line.split(" ");
    	int last=tokens.length;
    for (int i=0;i<(last-2);i++)      	
   			var_1 += tokens[i]+" ";
 
if (var_1.equals(item)){
count1 = Double.parseDouble(tokens[last-1]);
flag=1;
break;
		}

	}
File file1 = new File("TransactionNumber.txt");
BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
//String name=batch+" Batch:";
String line1 = "";
line1 = br1.readLine(); 
//if (line1.equals(name)){
   
trans = Integer.parseInt(line1);
trans-=tsum;




if (flag==0)
    count1=0.0;
br.close();
System.out.println("2   count="+count+"  trans"+trans+"   multi"+(count*trans));

return count1*trans;

}
   public void run(){  
//System.out.println("thread is running...");  
}    
   
   public static void associationRules(int n){
	   //System.out.println("Batch number "+n);
        String s=batch+"Batch"+n+"Freq.txt";
       // System.out.println("Name in AR "+s);
        AprioriCalculation ap = new AprioriCalculation();		
        ap.aprioriProcess(s, batch,minConf,exe);
   }
}
class AprioriCalculation
{
    int batch;
    Vector<String> candidates=new Vector<String>(); //the current candidates
    String configFile="config.txt"; //configuration file
    String transaFile="transa.txt"; //transaction file
    String outputFile;//output file
    int numItems; //number of items per transaction
    int numTransactions; //number of transactions
    double minSup; //minimum support for a frequent itemset
    String oneVal[]; //array of value per column that will be treated as a '1'
    String itemSep = " "; //the separator value for items in the database
	static int y=0,gg=0;
	 //static int counter[]=new int[100];
	 //int cc=0;
	 	int arr[]=new int[100];
	 static int counter=0;
	String line[]=new String[15];
	static double th=0;
      Vector<String> association=new Vector<String>();
     int ta=0;
   	int k=0,u=-1;
        static double exe;
    public void aprioriProcess(String name, int b,double ms,double ex)
    {	int q=0;
    y=0;
    gg=0;
    counter=0;
    ta=0;
   	 k=0;
         u=-1;
        batch=b;
        th=ms;
        exe=ex;
        outputFile="association rules"+batch+".txt";
      //  System.out.println("th "+th+"batch "+batch);
        
    	 try
			{
			String call=name;
	//		System.out.println("started");
	//		  System.out.println(call);
			FileInputStream file_in = new FileInputStream(call);
			
            BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
            
            while( (line[y] = data_in.readLine())!= null )
            {
            
            y++;
            }
           // System.out.println(y);
           file_in.close();
           data_in.close();
			}
			catch(Exception e)
			{
				 System.out.println(e);
			}
    	while(q<y){
    	
        Date d; //date object for timing purposes
        long start, end; //start and end time
        int itemsetNumber=0; //the current itemset being looked at
        //get config
        //getConfig();
		
        System.out.println("Association rules:\n");

        //start timer
        d = new Date();
        start = d.getTime();

        //while not complete
        do
        {
            //increase the itemset that is being looked at
            itemsetNumber++;

            //generate the candidates
            generateCandidates(itemsetNumber,q);
           // System.out.println("counter"+counter);
          /*  if(candidates.size()!=0)
            {
                String ww=line[q];
                                     //System.out.println("string"+ww);
                                     int u=ww.split(" ").length;
                                     int rr=u+1;
                                     String p=ww.substring(0,rr);
                                    // System.out.println("try"+u+" length"+p);
                                     if(!p.equals(candidates))
                                    // System.out.println("Frequent " + itemsetNumber + "-itemsets");
               System.out.println(candidates);
            }*/
          //  determine and display frequent itemsets
            calculateFrequentItemsets(itemsetNumber,q);
            
        //if there are <=1 frequent items, then its the end. This prevents reading through the database again. When there is only one frequent itemset.
        }while(candidates.size()>1);
            MainClass m=new MainClass();
            m.main();
        //end timer
        d = new Date();
        end = d.getTime();

        //display the execution time
        System.out.println("Execution time is: "+((double)(end-start)/1000) + " seconds.");
         exe=(double)(end-start)/1000;
        q++;
    }
    
    }    
    private void generateCandidates(int n,int q)
    {
        Vector<String> tempCandidates = new Vector<String>(); //temporary candidate string vector
        String str1, str2; //strings that will be used for comparisons
        	System.out.println("started candidates generation");
        StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared
       
        if(n==1)
        {
            
                
			
					
						//System.out.println(line[q]);
                                    String	tokens[] = line[q].split(" ");
                                    
    				// tokens = line.split(" ");
					int p=line[q].split(" ").length;
					//System.out.println(p);
                                        
					p=p-2;
						
					for(int i=0;i<p;i++)
					{
						tempCandidates.add(tokens[i]);
					
						//	System.out.println(tempCandidates.get(i));
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
                    tempCandidates.add(str1 + " " + str2);
                 
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
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                       
                    }

                    //if they have the same n-2 tokens, add them together
                    if(str2.compareToIgnoreCase(str1)==0)
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                }
            }
        }
        
        //clear the old candidates
        candidates.clear();
        //set the new ones
        candidates = new Vector<String>(tempCandidates);
        tempCandidates.clear();
        /*for(int i=0;i<candidates.size();i++)
        	//System.out.println(candidates.get(i));
        	*/
    }
    private void calculateFrequentItemsets(int n,int y)
    {
        int check=0;
         Vector<String> frequentCandidates = new Vector<String>(); //the frequent candidates for the current itemset
        //FileInputStream file_in=new FileInputStream(); //file input stream
        //BufferedReader data_in=new BufferedReader(); //data input stream
        //FileWriter fw=new FileWriter();
        //BufferedWriter file_out=new BufferedWriter();
		int s=1;
          
	//System.out.println("started calculate");
        //counter=0;
       
        StringTokenizer st, stFile; //tokenizer for candidate and transaction
        boolean match; //whether the transaction has all the items in an itemset
        boolean trans[] = new boolean[numItems]; //array to hold a transaction so that can be checked
        int count[] = new int[candidates.size()]; //the number of successful matches
	
    
        int z=0,q=0,b=0;
       try{double ans=0;
       	 BufferedWriter out = new BufferedWriter (new FileWriter((outputFile)));
       			 //fw= new FileWriter(outputFile, true);
                //file_out = new BufferedWriter(fw);
                
       			line[y]=line[y].trim();
       			// System.out.println("value of b "+candidates.size());
       			for(b=0;b<candidates.size();b++)
       			{
       				System.out.println("inside for "+b);
       				
       				z=0;
       				String name;
       				int h=candidates.get(b).split(" ").length;
       				if(h==1)
       				name=batch+"Batch"+h+"Freq.txt";
       				else
       				 name=batch+"Batch"+h+"Freq.txt";
              //  System.out.println(name);
                FileInputStream file_in = new FileInputStream(name);
                BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
                String combi[]=new String[15];
                	 file_in = new FileInputStream(name);
                data_in = new BufferedReader(new InputStreamReader(file_in));
       			
       				//System.out.println("candidates"+candidates.get(b));
       				 while( (combi[z] = data_in.readLine())!= null && (!combi[z].equals("No more files generated")))
       				 {
                                     String ww=combi[z];
                                //     System.out.println("string"+ww);
                                     int u=ww.split(" ").length;
                                     int rr=u+1;
                                     String p=ww.substring(0,rr);
                                    // System.out.println("try"+u+" length"+p);
                                     if(p.equals(candidates.get(b)))
                                     {
                                     gg=1;
                                     break;
                                     }
       				// System.out.println("hello"+candidates.get(b)+"bye"+combi[z]);
       				 //	combi[z]=combi[z].trim();
       				// System.out.println("combi"+combi[z]);
       				 	//System.out.println(combi[z].length());
       				 	String tokens[]=combi[z].split(" ");
       				 	int tn=combi[z].split(" ").length;
       				 	String values[]=new String[tn-1];
       				 	
       				 		
       				 	
       				 //	System.out.println("len"+tn);
       				 /*	for(int i=0;i<tn;i++)
       				 		Systestem.out.printlnm.out.println("tokens"+tokens[i]+"i"+i);
       				 */
       				 	int ln=line[y].split(" ").length;
       				 	String lns[]=line[y].split(" ");
       				 	
       				 	if(tn==3)
       				 	{
       				 		//System.out.println("candidates"+candidates.get(b));
       				 		if(candidates.get(b).equals(tokens[0]))
       				 		{
       				 			 ans=(double)(Double.parseDouble(lns[ln-1]))/(Double.parseDouble(tokens[tn-1]));
       				 				//System.out.println("answer"+candidates.get(b));
       				 		System.out.println("association rules "+candidates.get(b)+" "+ans);
       				 								if(ans>th)
       				 	{
       				 		String wq=" ",fii=" ";
       				 		for(int i=0;i<ln-1;i++)
       				 		{	if(lns[i].equals(" "))
       				 				break;
       				 			if(!(lns[i].equals(tokens[0]))){
       				 			
       				 				wq=wq+lns[i]+fii;
                            
       				 			}}
       				 		System.out.println("association rules "+candidates.get(b)+" ---->"+wq+" with support count "+ans);
                                               // counter[cc]=cc;
                                                //cc++;
                                                counter++;
       				 		String tp=candidates.get(b)+" -> "+wq+" "+ans;
       				 		association.add(tp);
       				 		System.out.println("appending");
       				 		
        								  
       				 	}
       				 									break;
       				 			
       				 		}
       				 	}
       				 	
       				 	else
       				 	{
       				 		
       				 		String w[]=candidates.get(b).split(" ");
       				 		int w1=candidates.get(b).split(" ").length;
       				 		int l=1;
       				 		//System.out.println("tn"+tn);
       				 		for(int i=0;i<tn-2;i++)
       				 		{	//System.out.println("tokens"+tokens[i]);
       				 			tokens[i]=tokens[i].trim();
       				 			//System.out.println("w"+w[i]);
       				 			w[i]=w[i].trim();
       				 			if(!tokens[i].equals(w[i]))
       				 			{	//System.out.println("inise");
       				 				l=0;
       				 			//	break;
       				 			}
       				 		}
       				 		if(l==1)
       				 		{
       				 			 ans=(double)(Double.parseDouble(lns[ln-1]))/(Double.parseDouble(tokens[tn-1]));
       				 			 	if(ans>th)
       				 	{
       				 			String wq=" ",fi=" ";
       				 			int count1=0;
       				 	/*	for(int i=0;i<w1;i++)
       				 		{	//tokens[i]=tokens[i].trim();
       				 			//w[i]=w[i].trim();
       				 			System.out.println(w[i]);
       				 			if(lns[i].equals(" "))
       				 				break;
       				 			if((lns[i].equals(w[i]))){
       				 			
       				 				wq=wq+lns[i];
                            		count1++;
       				 			}}
       				 		*/int u1=0;
       				 			for(int i=0;i<ln-1;i++)
       				 			{	u1=0;
       				 				if(!(lns[i].equals(" ")))
       				 				for(int j=0;j<w1;j++)	
       				 					if((lns[i].equals(w[j])))
       				 						u1=1;
       				 				if(u1==0)
       				 						fi=fi+lns[i]+wq;
       				 			}
       				 		System.out.println("association rules "+candidates.get(b)+" ---->"+fi+" with support count "+ans);
       				 		String tp=candidates.get(b)+" -> "+fi+" "+ans;
       				 		//counter[cc]=cc;
       				 		//cc++;
       				 		counter++;
                                                association.add(tp);
       				 		
       				 	}
       				 				
                                                                
       				 				
       				 				
       				 		}
       				 	}
       				 	
      
                    
       				 	z++;
       				 
         									
       				 	
       				 	
       				 }
       			
       				 //if(counter)
        			//System.out.println("counter"+counter);
                              file_in.close();
            data_in.close();
           // fw.close();
            //file_out.close();              
                                 
       			}
       		
       			int yp=0;
       			for(int i=0;i<1;i++)
       			{	
       				if(u>0)
       				{
       					
       					 yp=arr[u];
       				}
       			arr[k]=counter;
       			k++;
       			u++;
       		//	System.out.println("k"+k+"u"+u);
       				
       			if(u>0 && gg==0)
       			{
       				//System.out.println("ak"+arr[u-1]+"u"+arr[u]);
       			int oo=arr[u]-arr[u-1];
        		System.out.println("Total no of association rules "+oo);
       			}
        		else if(gg==0)
        			System.out.println("Total no of association rules"+(arr[u]));	
       			}
       			 StringBuffer sb=new StringBuffer();
       			for(int i=0;i<association.size();i++)
       			{
       			
       			//out.newLine();
       			out.append(association.get(i));
       			out.newLine();
       			 sb.append(association.get(i));
                sb.append(System.getProperty("line.separator"));
                
       			
       			
       			}
       			String ss=sb.toString();
       			out.close();
       			 updown c=(updown)Naming.lookup("rmi://192.168.34.37:1099//updownService");
             byte[] buff=ss.getBytes();
            c.upload(outputFile,buff);
             
       }
       catch(Exception e)
       {
           System.out.println(e);
       }


                
        
    }
}
						
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author NIDHI
 */
public class CreateGraph extends JFrame {
    
    String [][] array=new String [20][20];
    
   
    public CreateGraph(String at, String ct,int i)
    {
        if (i!=0)
            generate(i);
        else
            generateMain();
        
        PieDataset data =createDataset(i);
        JFreeChart chart=createChart(data,ct);
        ChartPanel cp=new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(500,300));
        setContentPane(cp);
        
    }
    void generate(int item)
    {
        System.out.println("generate");
        String line[]=new String[100];
        int i=-1;
        
        try {
            int batch=1;
            //FileInputStream file_in = new FileInputStream("association rules.txt");
            java.io.File file=new java.io.File("association rules"+batch+".txt");
            batch++;
           Scanner sc=new Scanner(file);
                do{
                   // System.out.println("do");
                    i++;
                    line[i]= sc.nextLine();
                   // System.out.println("line read "+line[i]);
                    
                }while(sc.hasNext());
        }catch(Exception e){System.out.println("Exception"+ e);}
                        

        
        //for(int k=1;k<=5;k++)
        { 
            int u=0;                
        	int y=0;
        	while(line[u]!=null)
    		{
    			String arr[]=line[u].split(" ");
    			for(int j=0;j<i ;j++)
        		{
        	
    			
                    if(!arr[j].equals("->"))
                    {
                        if(item==Integer.parseInt(arr[j]))
                        {
                            
                           array[item][y]=line[u];
                           System.out.println("storing "+array[item][y]);
                           y++;
                           
                        }                        
                    }
                    else
                    {
                    	j=i;
                        break;                   
                    }
    			}
    			u++;       
        	}
        }
        
        

        /*for(int y=1;y<6;y++)
        {
        
        	for(int x=0;x<array[y].length;x++)
        	{
        		double count=0;
        		System.out.println("array y="+y+"x="+x+" "+array[y][x]);
        		if(array[y][x]!=null)
        		{
        			String[]  sp=array[y][x].split(" ");
        			int last=sp.length;
        			count=Double.parseDouble(sp[last-1]);
        			//result.setValue(array[y][x]+"",count);
        			//System.out.println("Result:"+result.toString());
        		}
        	}
        }*/
        
    }
    void generateMain()
    {
        System.out.println("generate");
        String line[]=new String[100];
        int i=-1;
        
        try {
            int batch=1;
            //FileInputStream file_in = new FileInputStream("association rules.txt");
            java.io.File file=new java.io.File("association rules"+batch+".txt");
            batch++;
           Scanner sc=new Scanner(file);
                do{
                   // System.out.println("do");
                    i++;
                    line[i]= sc.nextLine();
                   // System.out.println("line read "+line[i]);
                    
                }while(sc.hasNext());
        }catch(Exception e){System.out.println("Exception"+ e);}
                        

        
        for(int k=1;k<=9;k++)
        { 
            int u=0;                
        	int y=0;
        	while(line[u]!=null)
    		{
    			String arr[]=line[u].split(" ");
    			for(int j=0;j<i ;j++)
        		{
        	
    			
                    if(!arr[j].equals("->"))
                    {
                        if(k==Integer.parseInt(arr[j]))
                        {
                            
                           array[k][y]=line[u];
                           System.out.println("storing "+array[k][y]);
                           y++;                           
                        }                        
                    }
                    else
                    {
                    	j=i;
                        break;                   
                    }
    			}
    			u++;       
        	}
        }
        
    }
    private PieDataset createDataset(int item)
    {
        DefaultPieDataset result=new DefaultPieDataset();
        if(item!=0)            
        {
            int flag=0;
            for(int x=0;x<array[item].length;x++)
            { 
                double count=0;
                if(array[item][x]!=null)
                {
                    flag=1;
                    String[]  sp=array[item][x].split(" ");
                    int last=sp.length;
                    count=Double.parseDouble(sp[last-1]);        			
                    String key=array[item][x];
                    result.setValue(key, count);
                }
            }
            if(flag==0)
                result.setValue("no assocition rules",100);
                    
        }
        else
        {
            for(int y=1;y<10;y++){
                double count=0;
                int flag=0;
                for(int x=0;x<array[y].length;x++)
                { 
                    
                    if(array[y][x]!=null)
                    {
                        flag=1;
                        String[]  sp=array[y][x].split(" ");
                        int last=sp.length;
                        count+=Double.parseDouble(sp[last-1]);        			                        
                    }                    
                }                
                if(flag==0)
                {
                    String key="No Rules "+y;
                    result.setValue(key,0);
                }
                else
                {
                        String key="Rules "+y;
                        result.setValue(key, count);
                }
                    

                }
            
            }
            
 
        
                
                
                {
                        
                }
        return result;
    }  
    
    private JFreeChart createChart(PieDataset ds, String title)
    {
        JFreeChart chart=ChartFactory.createPieChart3D(title, ds, true, true, false); //legend tools locale
        PiePlot3D plot=(PiePlot3D) chart.getPlot();
        plot.setStartAngle(90);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(1.0f);
        return chart;        
    }
    
}

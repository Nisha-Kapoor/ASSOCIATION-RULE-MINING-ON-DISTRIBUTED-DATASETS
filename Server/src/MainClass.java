/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JFrame;

/**
 *
 * @author NIDHI
 */
public class MainClass {
    public static void main() {
    
        for(int i=1;i<6;i++)
        {
            String heading="Association Rules "+i;
            CreateGraph cc=new CreateGraph("Pie Chart Test",heading,i);
            cc.pack();
            cc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cc.setVisible(true);
        }
            CreateGraph cc=new CreateGraph("Pie Chart Test","Association Rules",0);
            cc.pack();
            cc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cc.setVisible(true);
        
    }
    
    
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package addingunits;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import pssrextractor.SSr;

/**
 *
 * @author Alejandro
 */
public class AddingUnits {
    
    public static void addUnits(String pathway) throws Exception{
        
        File f=new File(pathway);
        
        if(f.getPath().endsWith(".mfaa") && f.length()>0){
            
            ArrayList<String> lecture=new ArrayList<>();
            ArrayList<String> result=new ArrayList<>();
            
            try {
                
                FileReader fr = new FileReader(f.getPath());
                try (BufferedReader enter = new BufferedReader(fr)) {
                    
                    String s;
                    
                    while((s=enter.readLine())!=null)
                        lecture.add(s);
                }
            }
            catch(java.io.FileNotFoundException fnfex) {
                System.out.println("File not found: " + fnfex);
            }
            catch(java.io.IOException ioex) {}
            
            SSr query=null;
            String temp=null;
            
            for (int i = 0, j=1; i < lecture.size(); i+=2,j+=2) {
                
                query=new SSr(lecture.get(i).substring(1),"");
                result.add(lecture.get(i));
                
                if(lecture.get(j).length()<60){
                    
                    temp=query.getF5().toUpperCase()+query.getPattern();
                    
                    do{
                        
                        temp+=query.getPattern();
                        
                    }while(temp.length()<40);
                    
                     temp+=query.getF3().toUpperCase();
                    
//                    System.out.println(temp.length());
                     result.add(temp);
                }
                else
                    result.add(lecture.get(j));
            }
            
            File ftemp= new File(pathway.replace(pathway.substring(pathway.lastIndexOf("\\")+1),"added.mfaa"));
            
            if(ftemp.exists()) ftemp.delete();
            
            try (FileWriter fw = new FileWriter(ftemp); BufferedWriter bw = new BufferedWriter(fw); PrintWriter exit = new PrintWriter(bw)) {
                
                for(String s: result)
                    exit.println(s);
            }
            catch(java.io.IOException ioex) { }
            
//            for (int i = 1; i < lecture.size(); i+=2) {
//
//                System.out.println(result.get(i).length());
//            }
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
//        addUnits("C:\\Users\\Alejandro\\Desktop\\all.gbk\\genome_assemblies_Acinetobacter_pittii\\genome_assemblies_Acinetobacter_pittii_all.mfaa");

         for(String path: args){
             
             File F=new File(path);
             File[] fs;
    
             if(F.isDirectory())
                 fs= F.listFiles();
             else
                 fs=new File[]{F};
    
             for(File f:fs){
        
             if(f.isDirectory() /*&& f.length()>0*/)
                 main(new String[]{f.getPath()});
             else if(f.getPath().endsWith(".mfaa") && f.length()>0)
                 addUnits(f.getPath());
            }
        }
    }
}


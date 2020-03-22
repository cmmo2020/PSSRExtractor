/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pssrextractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *A class that write objects that a have toString method in a file. 
 * 
 * @author Alejandro
 */
public class Writer {
    
    private String Filter;
    private ArrayList<?> Items;
    private String Dirname;
    private String Filepath;
//    private int Count;
    
    public Writer() {
        this.Filter = null;
        this.Items = null;
        this.Dirname = null;
        this.Filepath = null;
//        this.Count=1;
    }
    
    /**
     *Write the items of the ArrayList while have a toString method.
     * 
     * @param filter Represent the meaning of the information write in the file.
     * 
     * @param items The objects to write.
     * 
     * @param dirname The directory to write the file.
     * 
     * @param filepath The file to write.
     */
    public void write(String filter, ArrayList<?> items, String dirname, String filepath){
        
        this.Filter = filter;
        this.Items = items;
        this.Dirname = dirname;
        this.Filepath = filepath;
//        this.Count=1;
        
        File dir=new File(this.Dirname);
        
        if(!dir.exists())
            dir.mkdir();
        
        if(dir.exists())
        {
            try {
                File [] files=dir.listFiles();
                for (File f: files) {
                    if(f.getPath().equals(this.Filepath))
                    {
                        f.delete();
//                        this.Filepath+= this.Count;
//                        Count++;
//                        break;
                    }
                }
                try (FileWriter fw = new FileWriter(this.Filepath); BufferedWriter bw = new BufferedWriter(fw); PrintWriter exit = new PrintWriter(bw)) {
                    
                    
                    exit.println(this.Filter);
                    
                    for(Object o : Items)
                        exit.println(o.toString());
                    
                }
            }
            catch(java.io.IOException ioex) { }
        }
    }
    
    /**
     *Gets the filter write in the file.
     * 
     * @return A string that represent the filter.
     */
    public String getFilter() {
        return Filter;
    }
    
    /**
     *Gets the items write in the file.
     * 
     * @return A ArrayList that contains the Items to write.
     */
    public ArrayList<?> getItems() {
        return Items;
    }
    
    /**
     *Gets the name of the directory that contain the file wrote.
     * 
     * @return A string that represent the name of the directory.
     */
    public String getDirname() {
        return Dirname;
    }
    
    /**
     *Gets the direction of the file that contain the items wrote.
     * 
     * @return A string that represent the path of the file.
     */
    public String getFilepath() {
        return Filepath;
    }
    
    
}

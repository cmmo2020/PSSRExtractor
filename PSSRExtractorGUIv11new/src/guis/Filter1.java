/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package guis;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author carlos
 */
public class Filter1 extends FileFilter{
    
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            String extension = s.substring(i+1).toLowerCase();
            return "fa".equals(extension) || 
                    "fasta".equals(extension) ||
                    "gbk".equals(extension) ||
                    "gbf".equals(extension);
        }
        return false;
    }
    
    @Override
    public String getDescription() {
        
        return "Sequence files (.fa .fasta .gbk .gbf)";
    }
    
    
    
}

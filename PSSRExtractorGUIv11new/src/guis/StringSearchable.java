/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package guis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the Searchable interface that searches a List of String objects.
 * This implementation searches only the beginning of the words, and is not be optimized
 * for very large Lists.
 * @author G. Cope
 *
 */
public class StringSearchable implements Searchable<String,String>{
    private final List<String> terms = new ArrayList<>();
  
    /**
     * Constructs a new object based upon the parameter terms.
     * @param terms The inventory of terms to search.
     */
    public StringSearchable(List<String> terms){
        this.terms.addAll(terms);
        
    }     
    
    @Override
    public Collection<String> search(String value) {
        List<String> founds = new ArrayList<>();
        String value_UF = value.substring(0, 1).toUpperCase() + value.substring(1);
        String value_UA = value.toUpperCase();
        for ( String s : terms ){
            if ( s.indexOf(value) == 0 || s.indexOf(value_UF) == 0 || s.indexOf(value_UA) == 0)
                founds.add(s);
        }
        return founds;
    }
    
}
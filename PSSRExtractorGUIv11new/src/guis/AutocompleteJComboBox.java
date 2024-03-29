/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guis;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * JComboBox with an autocomplete drop down menu. This class is hard-coded for String objects, but can be
 * altered into a generic form to allow for any searchable item.
 * @author G. Cope
 */
public class AutocompleteJComboBox extends JComboBox{ 
    static final long serialVersionUID = 4321421L; 
    private final Searchable<String,String> searchable; 
    /**
     * Constructs a new object based upon the parameter searchable
     * @param s
     */
    public AutocompleteJComboBox(Searchable<String,String> s){ 
        super();
        this.searchable = s;
        setEditable(true);
        Component c = getEditor().getEditorComponent();
        if ( c instanceof JTextComponent ){
            final JTextComponent tc = (JTextComponent)c;
            tc.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                }
                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    update();
                }
                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    update();
                }
                public void update(){
                    SwingUtilities.invokeLater(() -> {
                        if (!"".equals(tc.getText())) {
                            List<String> founds = new ArrayList<>(searchable.search(tc.getText()));
                            Set<String> foundSet = new HashSet<>();
                            for (String s1 : founds) {
                                foundSet.add(s1.toLowerCase());
                            }
                            Collections.sort(founds);//sort alphabetically
                            setEditable(false);
                            removeAllItems();
                            //if founds contains the search text, then only add once.
                            if ( !foundSet.contains( tc.getText().toLowerCase()) ){
                                addItem( tc.getText() );
                            }
                            for (String s2 : founds) {
                                addItem(s2);
                            }
                            setEditable(true);
                            setPopupVisible(true);
                        }
                        requestFocus();//m
                    });
                   
                }
            });
            //When the text component changes, focus is gained
            //and the menu disappears. To account for this, whenever the focus
            //is gained by the JTextComponent and it has searchable values, we show the popup.
            tc.addFocusListener(new FocusListener(){
                @Override
                public void focusGained(FocusEvent arg0) {
                    if ( tc.getText().length() > 0 ){
                        setPopupVisible(true);
                    }
                }
                @Override
                public void focusLost(FocusEvent arg0) {
                    
                }
            });      
        }else{
            throw new IllegalStateException("Editing component is not a JTextComponent!");
        }
    }
}
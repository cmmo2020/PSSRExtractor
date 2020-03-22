/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pssrextractor;

import java.util.Objects;

/**
 * A object that have useful general information about all the results with the same query acc ver.
 *
 * @author Alejandro
 */
public class Generic_Result {
    
    private final String Query_acc_ver;
    private final String Access_number;
    private final String Pattern;
    private final int RUS;
    private final Integer Number_of_Runits;
    private final Double Inaccuracy;
    private final Double Entropy_5;
    private final Double Entropy_3;
    private final int Min_quantity;
    private final int Max_quantity;
    private final int Range;
    private final double Alelic_frequency;
    private final int Alelics_Number;
    private final double PIC;
    private final String Exceptions;
    
    
    /**
     * @param Query_acc_ver The access version of the input sequence (or other type of search term) to
     *        which all of the entries in a database are to be compared.
     * @param Access_number The query access number in the data base.
     * @param Pattern The repeat unit of the query.
     * @param RUS The quantity of nitrogenated bases that have the repeat unit.
     * @param Number_of_Runits The quantity of repeat unit that have the query between two flanks. 
     * @param Inaccuracy The percent of inaccuracy of the subject respect the query.
     * @param Entropy_5 Conformational entropy of the 5' flank.
     * @param Entropy_3 Conformational entropy of the 3' flank.
     * @param Min_quantity The quantity of repeat unit in the subject of minimum distance.
     * @param Max_quantity The quantity of repeat unit in the subject of maximum distance.
     * @param Range The difference between the max and the minimum number of nitrogenated bases of a query acc ver.
     * @param Alelic_frequency This variable also known like "polymorphism cup(Pj)" represent the frequency of the allele.
     * A gene is defined polymorphic when:
     * <ul>
     * <li>Pj<=0.95 or Pj<=0.99.
     * </ul>
     * <p>
     * @param Alelics_Number The number of variant in one sample.
     * @param PIC The probability that, in a unique locus, each alelle par, chosen by azar of the population, are different.
     * @param Exceptions All the exceptions that may have the subjects of the 
     * same query that falsify the calculus:  
     * <ul>
     * <li>D: degenerate: It means that the subject sequence have flanks with
     * a percent of identity and a percent of cover less than the establishes.
     * <li>NF: not fount: It means that the query sequence don't 
     * appear in the NCBI data base.
     * <li>U: unpair: It means that the subject sequence is only formed by one
     * flank because don't exist a HtBLASTParser_Unit that represent other flank
     * with the same subject access ver, a percent of identity and a percent of 
     * cover greater or equals than the arguments establishes.
     * <li>O: outlier: It means that the number of repeats units not guarantee 
     * that the subject sequence be a microsatellite.
     * </ul>
     * <p>
     * This exceptions are may be mixed for eg:
     * 
     * <li>If the subjects with the same query have exceptions then this ones 
     * will be accumulates like "D O UD".
     * </ul>
     * <p>
     * The exceptions in a generic result only are reports when all the subjects
     * of the same query have exceptions.  
     */
    public Generic_Result(String Query_acc_ver, String Access_number, String Pattern, int RUS, Integer Number_of_Runits, Double Inaccuracy, Double Entropy_5, Double Entropy_3, int Min_quantity, int Max_quantity, int Range, double Alelic_frequency, int Alelics_Number, double PIC, String Exceptions) {
        this.Query_acc_ver = Query_acc_ver;
        this.Access_number = Access_number;
        this.Pattern = Pattern;
        this.RUS = RUS;
        this.Number_of_Runits = Number_of_Runits;
        this.Inaccuracy = Inaccuracy;
        this.Entropy_5 = Entropy_5;
        this.Entropy_3 = Entropy_3;
        this.Min_quantity = Min_quantity;
        this.Max_quantity = Max_quantity;
        this.Range = Range;
        this.Alelic_frequency = Alelic_frequency;
        this.Alelics_Number = Alelics_Number;
        this.PIC = PIC;
        this.Exceptions = Exceptions;
    }
    
    public String getQuery_acc_ver() {
        return Query_acc_ver;
    }
    
    public String getAccess_number() {
        return Access_number;
    }
    
    public String getPattern() {
        return Pattern;
    }
    
    public int getRUS() {
        return RUS;
    }
    
    public Integer getNumber_of_Runits() {
        return Number_of_Runits;
    }
    
    public Double getInaccuracy() {
        return Inaccuracy;
    }
    
    public Double getEntropy_5() {
        return Entropy_5;
    }
    
    public Double getEntropy_3() {
        return Entropy_3;
    }
       
    public int getMin_quantity() {
        return Min_quantity;
    }
    
    public int getMax_quantity() {
        return Max_quantity;
    }
    
    /**
     *Gets the range of this generic result.
     *
     * @return A integer that represent the range.
     */
    public int getRange() {
        return Range;
    }
    
    /**
     *Gets the polymorphism of this generic result.
     *
     * @return A double that represent the polymorphism.
     */
    public double getAlelic_Frequency() {
        return Alelic_frequency;
    }
    
    /**
     *Gets the alelic variant abundance of this generic result.
     *
     * @return A double that represent the alelic variant abundance.
     */
    public int getAlelics_Number() {
        return Alelics_Number;
    }
    
    /**
     *Gets the locus heterocigocity of this generic result.
     *
     * @return A double that represent the locus heterocigocity.
     */
    public double getPIC() {
        return PIC;
    }
    
    public String getExceptions() {
        return Exceptions;
    }
    
    @Override
    public boolean equals(Object o){
        
        if(o.getClass()==Generic_Result.class)
        {
            Generic_Result g=(Generic_Result)o;
            
            return  Query_acc_ver.equals(g.Query_acc_ver) &&
                    this.Access_number.equals(g.Access_number) &&
                    this.Pattern.equals(g.Pattern) &&
                    this.RUS==g.RUS &&
                    this.Number_of_Runits.equals(g.Number_of_Runits) &&
                    this.Inaccuracy.equals(g.Inaccuracy) &&
                    this.Entropy_5.equals(g.Entropy_5) &&
                    this.Entropy_3.equals(g.Entropy_3) &&
                    this.Range==g.Range &&
                    this.Alelic_frequency==g.Alelic_frequency &&
                    this.Alelics_Number==g.Alelics_Number &&
                    this.PIC==g.PIC;
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.Query_acc_ver);
        hash = 11 * hash + Objects.hashCode(this.Access_number);
        hash = 11 * hash + Objects.hashCode(this.Pattern);
        hash = 11 * hash + this.RUS;
        hash = 11 * hash + Objects.hashCode(this.Number_of_Runits);
        hash = 11 * hash + Objects.hashCode(this.Inaccuracy);
        hash = 11 * hash + Objects.hashCode(this.Entropy_5);
        hash = 11 * hash + Objects.hashCode(this.Entropy_3);
        hash = 11 * hash + this.Range;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.Alelic_frequency) ^ (Double.doubleToLongBits(this.Alelic_frequency) >>> 32));
        hash = 11 * hash + this.Alelics_Number;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.PIC) ^ (Double.doubleToLongBits(this.PIC) >>> 32));
        return hash;
    }
    
    @Override
    public String toString(){
        
        return Query_acc_ver+"\t"+
                Access_number+"\t"+
                Pattern+"\t"+
                RUS+"\t"+
                Number_of_Runits+"\t"+
                Inaccuracy+"\t"+
                Entropy_5+"\t"+
                Entropy_3+"\t"+
                Min_quantity+"\t"+
                Max_quantity+"\t"+
                Range+"\t"+
                Alelic_frequency+"\t"+
                Alelics_Number+"\t"+
                PIC+"\t"+
                Exceptions;
    }
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pssrextractor;

import java.util.ArrayList;
import java.util.List;

/**
 *Evaluate the alelic frequency of each query in a hit table.
 *
 * @author Alejandro
 */
public abstract class Polymorphism {
    
    private static double maximum(List<Double> numbers){
        
        double max_value=numbers.get(0);
        
        for(int i=1;i<numbers.size();i++){
            
            if(numbers.get(i)>max_value)
                max_value=numbers.get(i);
        }
        
        return max_value;
    }
    
    private static double minimum(List<Double> numbers){
        
        double min_value=numbers.get(0);
        
        for(int i=1;i<numbers.size();i++){
            
            if(numbers.get(i)<min_value)
                min_value=numbers.get(i);
        }
        
        return min_value;
    }
    
    private static ArrayList<List<HtBLASTParser_Unit>> sublist(ArrayList<HtBLASTParser_Unit> a){
        
        ArrayList<List<HtBLASTParser_Unit>> sublists=new ArrayList<>();
        String query=a.get(0).getQuery_acc_ver();
        int start=0;
        
        if(a.size()==1)
        {
            sublists.add(a);
            return sublists;
        }
        
        for (int i = 1; i < a.size(); i++) {
            
            if(!query.equals(a.get(i).getQuery_acc_ver()) && i!=a.size()-1)
            {
                query=a.get(i).getQuery_acc_ver();
                
                sublists.add(a.subList(start, i));
                
                start=i;
            }
            
            if(i==a.size()-1)
            {
                if(a.get(i).getQuery_acc_ver().equals(query))
                {
                    sublists.add(a.subList(start, a.size()));
                }
                else
                {
                    sublists.add(a.subList(i-1, i));
                    sublists.add(a.subList(i, i+1));
                }
            }
        }
        
        return sublists;
    }
    
    private static ArrayList<List<Specific_Result>> sublistSresults(ArrayList<Specific_Result> a){
        
        ArrayList<List<Specific_Result>> sublists=new ArrayList<>();
        String query=a.get(0).getQuery_acc_ver();
        int start=0;
        
        if(a.size()==1)
        {
            sublists.add(a);
            return sublists;
        }
        
        for (int i = 1; i < a.size(); i++) {
            
            if(!query.equals(a.get(i).getQuery_acc_ver()) && i!=a.size()-1)
            {
                query=a.get(i).getQuery_acc_ver();
                
                sublists.add(a.subList(start, i));
                
                start=i;
            }
            
            if(i==a.size()-1)
            {
                if(a.get(i).getQuery_acc_ver().equals(query))
                {
                    sublists.add(a.subList(start, a.size()));
                }
                else
                {
                    sublists.add(a.subList(i-1, i));
                    sublists.add(a.subList(i, i+1));
                }
            }
        }
        
        return sublists;
    }
    
    private static double alelicFrequency(double distance_id, ArrayList<Double> distances){
        
        double count=0;
        
        for (Double distance : distances) {
            
            if (distance == distance_id) {
                count++;
            }
        }
        
        return count/distances.size();
    }
    
    private static int alelicsNumber(ArrayList<Double> distances){
        
        ArrayList<Double> av=new ArrayList<>();
        
        for (Double d: distances) {
            
            if(!av.contains(d))
                av.add(d);
        }
        
        return av.size();
    }
    
    private static double PIC(ArrayList<Double> distances){
        
        double plus=0;
        ArrayList<Double> distances_selected=new ArrayList<>();
        
        for (Double d: distances) {
            
            if(!distances_selected.contains(d))
                distances_selected.add(d);
        }
        
        for (Double d: distances_selected) {
            
            plus+=Math.pow(alelicFrequency(d,distances),2);
        }
        
        return 1-plus;
    }
    
    private static String outliers(int r_unit, int d){
        
        int o=0;
        
        if(r_unit==1)
            o=157;
        else if(r_unit==2)
            o=364;
        else if(r_unit==3)
            o=109;
        else if(r_unit==4)
            o=45;
        else if(r_unit==5)
            o=150;
        else if(r_unit==6)
            o=193;
        
        if(d>=o*5)
            return "*****";
        else if(d>=o*4)
            return "****";
        else if(d>=o*3)
            return "***";
        else if(d>=o*2)
            return "**";
        else if(d>o)
            return "*";
        else
            return " ";
    }
    
    private static SSr getSSr(String query_acc_ver){
        
        SSr r = new SSr();
        
        r.setAccess(query_acc_ver.substring(query_acc_ver.indexOf('>')+1, query_acc_ver.indexOf('|')));
        r.setPattern(query_acc_ver.substring(query_acc_ver.indexOf('|')+1, query_acc_ver.indexOf('[')));
        r.setLocation(query_acc_ver.substring(query_acc_ver.indexOf('[')+1, query_acc_ver.indexOf(']')));
        r.setCopies(query_acc_ver.substring(query_acc_ver.indexOf("c:")+2, query_acc_ver.indexOf("|s:")));
        r.setScore(query_acc_ver.substring(query_acc_ver.indexOf("s:")+2, query_acc_ver.indexOf("|m:")));
        r.setMatch(query_acc_ver.substring(query_acc_ver.indexOf("m:")+2, query_acc_ver.indexOf("|mm")));
        r.setMismatch(query_acc_ver.substring(query_acc_ver.indexOf("mm:")+3, query_acc_ver.indexOf("|i:")));
        r.setIndel(query_acc_ver.substring(query_acc_ver.indexOf("i:")+2, query_acc_ver.indexOf("|ina:")));
        r.setInacc(query_acc_ver.substring(query_acc_ver.indexOf("ina:")+4, query_acc_ver.indexOf("|5e")));
        r.setEnt5(query_acc_ver.substring(query_acc_ver.indexOf("5e:")+3, query_acc_ver.indexOf("|3e:")));
        r.setEnt3(query_acc_ver.substring(query_acc_ver.indexOf("3e:")+3));
        
        return r;
    }
    
    private static String toString(List<String> l){
        
        String r=null;
        
        for (int i = 0; i < l.size(); i++) {
            
            if(i==0)
                r=l.get(i)+" ";
            else if(i==l.size()-1)
                r+=l.get(i);
            else
                r+=l.get(i)+" ";
        }
        
        return r;
    }
    
    /**
     * Calculate the quantity of nitrogenated bases and the quantity of repeat 
     * units that are between two HtBLASTParser_Units that have equals 
     * subject_acc_ver, a percent of identity and a percent of cover greater or 
     * equals than the arguments establishes. 
     * 
     * @param a
     *        A ArrayList with the queries to evalue.
     *
     * @param percent_of_identity
     *        The percents of the extent
     *        to which two (nucleotide or amino acid) sequence have the same residues
     *        at the same positions in an alignment.
     *
     * @param percent_of_cover
     *        The percent in that the subject sequence are similar at the query.
     *
     * @return A ArrayList of Specific_Result.
     * @throws java.lang.Exception If percent of identity or percent_of_cover are < 90.
     */
    public static ArrayList<Specific_Result> evaluate(ArrayList<HtBLASTParser_Unit> a, float percent_of_identity, float percent_of_cover) throws Exception{
        
        if(percent_of_identity<90 || percent_of_cover<90)
            throw new Exception("The percent_of_identity and the percent_of_cover must be >=90");
        
        ArrayList<List<HtBLASTParser_Unit>> sublists=sublist(a);
        ArrayList <Specific_Result> result=new ArrayList<>();
        ArrayList <Specific_Result> temp=new ArrayList<>();
        ArrayList <HtBLASTParser_Unit> left=new ArrayList<>();
        ArrayList <HtBLASTParser_Unit> right=new ArrayList<>();
        
        for (List<HtBLASTParser_Unit> l: sublists) {
            
            for (int i=0;i < l.size(); i++) {
                
                String e=" ";
                
                if(l.get(i).getSubject_acc_ver() == null)
                {
                    e="NF";
                    result.add(new Specific_Result(l.get(i).getQuery_acc_ver(),null,null,null,null,null,' '," ",e));
                }
                
                else if (l.get(i).getAlignment_length() > 40) {
                    
                    String o=outliers(l.get(i).getQuery_acc_ver().substring(l.get(i).getQuery_acc_ver().indexOf("|")+1, 
                                    l.get(i).getQuery_acc_ver().indexOf("[")).length(),Math.round((l.get(i).getAlignment_length() - 40) / (float) l.get(i).getQuery_acc_ver().substring(l.get(i).getQuery_acc_ver().indexOf("|") + 1, 
                                    l.get(i).getQuery_acc_ver().indexOf("[")).length()));
                    
                    if(l.get(i).getPercent_of_identity()<percent_of_identity)
                        e="D";
                    
                    if(!o.equals(" "))
                    {
                        if(e.equals(" "))
                            e="O";
                        else
                            e+="O";
                    }
                    
                    if (l.get(i).getS_start() < l.get(i).getS_end()) {
                        result.add(new Specific_Result(l.get(i).getQuery_acc_ver(),
                                l.get(i).getSubject_acc_ver(), l.get(i).getS_start(),
                                l.get(i).getS_end(), l.get(i).getAlignment_length() - 40,
                                Math.round((l.get(i).getAlignment_length() - 40) / (float) l.get(i).getQuery_acc_ver().substring(l.get(i).getQuery_acc_ver().indexOf("|") + 1,
                                l.get(i).getQuery_acc_ver().indexOf("[")).length()), '+', o, e));
                    }
                    else if (l.get(i).getS_start() > l.get(i).getS_end()) {
                        result.add(new Specific_Result(l.get(i).getQuery_acc_ver(),
                                l.get(i).getSubject_acc_ver(), l.get(i).getS_start(),
                                l.get(i).getS_end(), l.get(i).getAlignment_length() - 40,
                                Math.round((l.get(i).getAlignment_length() - 40) / (float) l.get(i).getQuery_acc_ver().substring(l.get(i).getQuery_acc_ver().indexOf("|") + 1,
                                l.get(i).getQuery_acc_ver().indexOf("[")).length()), '-', o, e));
                    }
                }
                else
                {
                    
                    if(l.get(i).getQ_end()<=20)
                        left.add(l.get(i));
                    
                    else
                        right.add(l.get(i));
                    
                    for (int j = l.indexOf(l.get(i))+1; j < l.size(); j++) {
                        
                        if(l.get(i).getSubject_acc_ver().equals(l.get(j).getSubject_acc_ver()) && l.get(j).getQ_end()<=20 )
                            left.add(l.get(j));
                        else if(l.get(i).getSubject_acc_ver().equals(l.get(j).getSubject_acc_ver()) && l.get(j).getQ_start()>=20)
                            right.add(l.get(j));
                        else
                        {
                            i=j-1;
                            break;
                        }
                    }
                    
                    if(left.isEmpty() || right.isEmpty()){
                        
                        e="U";
                        
                        if(l.get(i).getPercent_of_identity()<percent_of_identity || l.get(i).getAlignment_length()*100/20<percent_of_cover)
                            e+="D";
                        
                        if(l.get(i).getS_start()<l.get(i).getS_end())
                            result.add(new Specific_Result(l.get(i).getQuery_acc_ver(),
                                    l.get(i).getSubject_acc_ver(),l.get(i).getS_start(),
                                    l.get(i).getS_end(), null, null,'+', " ", e));
                        else
                            result.add(new Specific_Result(l.get(i).getQuery_acc_ver(),
                                    l.get(i).getSubject_acc_ver(),l.get(i).getS_start(),
                                    l.get(i).getS_end(), null, null,'-', " ", e));
                        
                        left.clear();
                        right.clear();
                    }
                    else{
                        
                        for (HtBLASTParser_Unit u: left) {
                            
                            for (HtBLASTParser_Unit u1: right) {
                                
                                e=" ";
                                
                                String o=outliers(l.get(i).getQuery_acc_ver().substring(l.get(i).getQuery_acc_ver().indexOf("|")+1,
                                                l.get(i).getQuery_acc_ver().indexOf("[")).length(),
                                                Math.round((float)(Math.abs(u1.getS_start()-u.getS_end())-1)/(float)u.getQuery_acc_ver().substring(u.getQuery_acc_ver().indexOf("|")+1,
                                                u.getQuery_acc_ver().indexOf("[")).length()));
                                
                                if(u.getPercent_of_identity() < percent_of_identity || u.getAlignment_length()*100/20 < percent_of_cover || u1.getPercent_of_identity() < percent_of_identity || u1.getAlignment_length()*100/20 < percent_of_cover)
                                    e="D";
                                
                                if(!o.equals(" "))
                                {
                                    if(e.equals(" "))
                                        e="O";
                                    else
                                        e+="O";
                                }
                                
                                if(u.getS_start()<u.getS_end() && u1.getS_start()<u1.getS_end() && u1.getS_start()>u.getS_end())
                                    temp.add(new Specific_Result(u.getQuery_acc_ver(),
                                            u.getSubject_acc_ver(),u.getS_start(),
                                            u1.getS_end(),
                                            Math.abs(u1.getS_start()-u.getS_end())-1,
                                            Math.round((float)(Math.abs(u1.getS_start()-u.getS_end())-1)/(float)u.getQuery_acc_ver().substring(u.getQuery_acc_ver().indexOf("|")+1,
                                            u.getQuery_acc_ver().indexOf("[")).length()),'+', o, e));
                                else if(u.getS_start()<u.getS_end() && u1.getS_start()<u1.getS_end() && u.getS_start()>u1.getS_end())
                                    temp.add(new Specific_Result(u.getQuery_acc_ver(),
                                            u.getSubject_acc_ver(),u1.getS_start(),
                                            u.getS_end(),Math.abs(u.getS_start()-u1.getS_end())-1,
                                            Math.round((float)(Math.abs(u.getS_start()-u1.getS_end())-1)/(float)u.getQuery_acc_ver().substring(u.getQuery_acc_ver().indexOf("|")+1,
                                            u.getQuery_acc_ver().indexOf("[")).length()),'+', o, e));
                                else if(u.getS_start()>u.getS_end() && u1.getS_start()>u1.getS_end() && u1.getS_start()<u.getS_end())
                                    temp.add(new Specific_Result(u.getQuery_acc_ver(),
                                            u.getSubject_acc_ver(),u.getS_start(),u1.getS_end(),
                                            Math.abs(u.getS_end()-u1.getS_start())-1,Math.round((float)(Math.abs(u.getS_end()-u1.getS_start())-1)/(float)u.getQuery_acc_ver().substring(u.getQuery_acc_ver().indexOf("|")+1,
                                            u.getQuery_acc_ver().indexOf("[")).length()),'-', o, e));
                            }
                        }
                        
                        if(!temp.isEmpty()){
                            List<Double> q=new ArrayList<>();
                            
                            for (Specific_Result er: temp) {
                                
                                q.add((double)er.getNumber_of_Runits());
                            }
                            
                            double d=minimum(q);
                            
                            for(Specific_Result er: temp)
                                if(er.getNumber_of_Runits()==d)
                                    result.add(er);
                        }
                        
                        temp.clear();
                        left.clear();
                        right.clear();
                    }
                }
            }
        }
        return result;
    }
    /**
     * Gets the query access version, the pattern (repeat unit), the pattern 
     * length and the number of copies of the repeat unit, the inaccuracy, the 
     * entropy of the 5' flank and the 3' flank of the query, calculate the 
     * minimum and the max quantity of repeat units, the range (max variation 
     * between the minimum and the max quantity of repeat units), alelic 
     * frequency, alelics number and polymorphism information content (PIC) 
     * and report the exceptions between queries with the same query access 
     * version.
     *
     * @param values A ArrayList with the Specific Results to evalue.
     *
     * @return A ArrayList of Generic Results.
     */
    public static ArrayList<Generic_Result> evaluate(ArrayList<Specific_Result> values){
        
        ArrayList<List<Specific_Result>> sublists=sublistSresults(values);
        ArrayList <Generic_Result>result=new ArrayList <>();
        ArrayList<Double> distances_ur=new ArrayList<>();
        List<String> allexceptions=new ArrayList<>();
        List<String> exceptions=new ArrayList<>();
        SSr query=null;
        String e= " ";
        boolean q_add=false;
        
        for(List<Specific_Result> l:sublists) {
            
            for (Specific_Result er:l) {
                
                if(q_add==false)
                {
                    query=getSSr(er.getQuery_acc_ver());
                    distances_ur.add(Double.parseDouble(query.getCopies()));
                    q_add=true;
                }
                
                if(er.getExceptions().equals(" "))
                    distances_ur.add((double)er.getNumber_of_Runits());
                else
                    allexceptions.add(er.getExceptions());
            }
            
            if(allexceptions.size()==l.size()){
                for (String s: allexceptions) {
                    if(!exceptions.contains(s))
                        exceptions.add(s);
                }
                
                exceptions.sort(null);
                
                e=toString(exceptions);
            }
            
            result.add(new Generic_Result(l.get(0).getQuery_acc_ver(),
                    query.getAccess(),
                    query.getPattern(),
                    query.getPattern().length(),
                    Integer.parseInt(query.getCopies()),
                    Double.parseDouble(query.getInacc()),
                    Double.parseDouble(query.getEnt5()),
                    Double.parseDouble(query.getEnt3()),
                    (int)minimum(distances_ur),
                    (int)maximum(distances_ur),
                    (int)Math.round(maximum(distances_ur)-minimum(distances_ur)),
                    Double.parseDouble(String.format("%1.3f", alelicFrequency(Double.parseDouble(query.getCopies()),distances_ur)).replace(',', '.')),
                    alelicsNumber(distances_ur),
                    Double.parseDouble(String.format("%1.3f", PIC(distances_ur)) .replace(',', '.')),e));
            
            distances_ur.clear();
            allexceptions.clear();
            exceptions.clear();
            q_add=false;
            e= " ";
        }
        
        return result;
    }
}


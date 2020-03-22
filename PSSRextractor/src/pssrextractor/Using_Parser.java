/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pssrextractor;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class Using_Parser {
    
    static void print(ArrayList<String> a)
    {
        for(String s:a)
            System.out.println(s);
    }
    
    static void print_int(ArrayList<Integer> a)
    {
        for(Integer i:a)
            System.out.println(i);
    }
    
    static void printUnits(ArrayList<HtBLASTParser_Unit> a)
    {
        for(HtBLASTParser_Unit u: a)
            System.out.println(u.toString());
    }
    
    static void printUnits(List<HtBLASTParser_Unit> a)
    {
        for(HtBLASTParser_Unit u: a)
            System.out.println(u.toString());
    }
    
    static void printEspecific_Results(ArrayList<Specific_Result> a)
    {
        for(Specific_Result er: a)
            System.out.println(er.toString());
    }
    
    static void printGeneric_Results(ArrayList<Generic_Result> a)
    {
        for(Generic_Result gr: a)
            System.out.println(gr.toString());
    }
    
    static void printTuples(ArrayList<Tuple> a)
    {
        for(Tuple t: a)
            System.out.println(t.toString());
    }
    
    static void printArray(ArrayList<Object[]> a)
    {
        for(Object[] b:a)
        {
            for(Object o: b)
                System.out.print(o.toString()+", ");
            
            System.out.println();
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        
        String[] pathways=new String[args.length-2];
        
        for (int i = 0, j = 0; i < pathways.length; i++, j++) {
            pathways[i]=args[j];
        }
        
        HtBLASTParser bp=new HtBLASTParser(pathways);
        
        Writer w=new Writer();
        
        int index=0;
        
        for (ArrayList<HtBLASTParser_Unit> units: bp.getBox_of_Units()) {
            for (int i = index; i < bp.getPathways().size(); i++) {
                
                if(bp.getPathways().get(i).endsWith(".htt"))
                {
//                    System.out.println(bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt")));
//                    System.out.println(bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"\\"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"_specific_result_"+ args[args.length-2]+"_"+args[args.length-1]+".xls");
//                    System.out.println(bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"\\"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"_generic_result_"+args[args.length-2]+"_"+args[args.length-1]+".xls");
//                    
                    w.write("# query_acc.ver"+"\t"+"subject_acc.ver"+"\t"+"star.p"+"\t"+"end.p"+"\t"+"quantity_of_n.bases"+"\t"+"quantity_of_r.units"+"\t"+"direction"+"\t"+"outliers"+"\t"+"exceptions", Polymorphism.evaluate( units, Float.parseFloat(args[args.length-2]), Float.parseFloat(args[args.length-1])), bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt")), bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"\\"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"_specific_result_"+ args[args.length-2]+"_"+args[args.length-1]+".xls");
                    w.write("# query_acc.ver"+"\t"+"access_number"+"\t"+"pattern"+"\t"+"RUS"+"\t"+"RN"+"\t"+"inaccuracy"+"\t"+"entropy_5"+"\t"+"entropy_3"+"\t"+"min_RN"+"\t"+"max_RN"+"\t"+"range"+"\t"+"frequency"+"\t"+"alleles"+"\t"+"PIC"+"\t"+"exceptions", Polymorphism.evaluate(Polymorphism.evaluate(units, Float.parseFloat(args[args.length-2]), Float.parseFloat(args[args.length-1]))), bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt")), bp.getPathways().get(i).substring(0, bp.getPathways().get(i).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"\\"+bp.getPathways().get(i).substring(bp.getPathways().get(i).lastIndexOf("\\")+1,bp.getPathways().get(i).indexOf(".htt"))+"_generic_result_"+args[args.length-2]+"_"+args[args.length-1]+".xls");
                    index=i+1;
                    break;
                }
            }
        }

//System.out.println(bp.getPathways().get(1).substring(0, bp.getPathways().get(1).lastIndexOf("\\"))+"\\result_"+bp.getPathways().get(1).substring(bp.getPathways().get(1).lastIndexOf("\\")+1,bp.getPathways().get(1).indexOf(".htt")));

//String fpath_p =bp.getPathways().get(2).substring(0, bp.getPathways().get(2).lastIndexOf("\\")) + "\\results_" + bp.getPathways().get(2).substring(bp.getPathways().get(2).lastIndexOf("\\")+1, bp.getPathways().get(2).indexOf(".htt"));
//                        String fpath_e ="\\"+bp.getPathways().get(2).substring(bp.getPathways().get(2).lastIndexOf("\\")+1, bp.getPathways().get(2).indexOf(".htt")) + "_generic_result.xls";
//                        String fpath = fpath_p + fpath_e;
//                        
//System.out.println(fpath_p);
//System.out.println(fpath_e);
//System.out.println(fpath);


    }
}



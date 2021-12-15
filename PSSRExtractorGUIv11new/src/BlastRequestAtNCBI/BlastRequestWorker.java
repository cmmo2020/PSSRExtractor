/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package BlastRequestAtNCBI;

import guis.ProxyAuthenticator;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;
import pssrextractor.HtBLASTParser_Unit;
import pssrextractor.Polymorphism;
import pssrextractor.SSr;
import pssrextractor.Writer;

/**
 *
 * @author carlos
 */
public class BlastRequestWorker extends SwingWorker< String, String >{
    private final TextArea logsTextArea;
    private final JButton executeButton;
    private final JButton cancelButton;
    private final String organism;
    List<String> seq_array;
    private ArrayList<HtBLASTParser_Unit> HitTable;
    private final int jS2, jS3;
    private final float jS4, jS5;
    private final String jT5;
    public static boolean return_code = true;
    private boolean stoped = false; // cancel log
    
    public BlastRequestWorker( TextArea logs, JButton execute, JButton cancel,
            String organ, int vjS2, int vjS3, float vjS4, float vjS5, String vjT5)
    {
        logsTextArea = logs;
        executeButton = execute;
        cancelButton = cancel;
        organism = organ;
        jS2 = vjS2;
        jS3 = vjS3;
        jS4 = vjS4;
        jS5 = vjS5;
        jT5 = vjT5;
        seq_array = new ArrayList<>();
        String seq="";
        try {
            FileInputStream fi=new FileInputStream(vjT5);
            Scanner s = new Scanner(fi);
            int count = 1;
            seq = s.next() + '\n';
            while (s.hasNext()){
                count++;
                seq += s.next() + '\n';
                if (count == 200) {seq_array.add(seq); seq = ""; count=0;}
            }
            if (!"".equals(seq)) seq_array.add(seq);
            
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }
    }
    
    @Override
    protected String doInBackground()  {
        executeButton.setEnabled(false);
        cancelButton.setEnabled(true);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        logsTextArea.append("Start Time: " + hour + "h:" + minutes + "m:" + seconds + "s:" + '\n');
        String fpath_p =jT5.substring(0, jT5.lastIndexOf(File.separator)) + File.separator +"results_" + jT5.substring(jT5.lastIndexOf(File.separator)+1, jT5.indexOf(".mfaa"));
        String fpath_e =File.separator+jT5.substring(jT5.lastIndexOf(File.separator)+1, jT5.indexOf(".mfaa")) + "_generic_result_"+ jS2 +"_" + jS3 + "_" +  jS4+"_"+jS5+".xls";
        String fpath_d =File.separator+jT5.substring(jT5.lastIndexOf(File.separator)+1, jT5.indexOf(".mfaa")) + "_specific_result_"+ jS2 +"_" + jS3 + "_" +  jS4+"_"+jS5+".xls";
        String fpath = fpath_p + fpath_e;
        String fpath1 = fpath_p + fpath_d;
        File ftest = new File(fpath);
        File ftest1 = new File(fpath1);
        if (ftest.exists()) ftest.delete();
        if (ftest1.exists()) ftest1.delete();
        int count = 0;
        logsTextArea.append("Total Subqueries to Process = " + seq_array.size() + '\n' );
        boolean head=true;
        for (String seq : seq_array) {
            try {
                count++;
                if (count > 1) head =false;
                logsTextArea.append("Subquery No." + count + '\n');
                String req = "";
                while ("".equals(req = get_RID_RTOE(seq))) {
                    sleep(3500);
                    if (stoped) {logsTextArea.append("RID Request Interrupted..." + '\n');return "Exit Process";}
                }
                while ((HitTable = getHitTable(req)) == null) {
                    
                    if(logsTextArea.getText().contains("Error"))
                        return null;

                    sleep(3500);
                    if (stoped) {logsTextArea.append("Hit Table Request Interrupted..." + '\n');return "Exit Process";}
                }
                
                Writer w=new Writer();
                w.write(head, "# Q_acc.ver"+"\t"+
                        "S_acc.ver"+"\t"+
                        "S_start"+"\t"+
                        "S_end"+"\t"+
                        "bp"+"\t"+
                        "R.units"+"\t"+
                        "Strand"+"\t"+
                        "Outliers"+"\t"+
                        "Exceptions",
                        Polymorphism.evaluate(HitTable, jS4, jS5),
                        fpath_p,
                        fpath1);
                w.write(head, "#SSR_id"+"\t"+
                        "Q_Acc.ver"+"\t"+
                        "Q_Start"+"\t"+
                        "Q_End"+"\t"+
                        "Pattern"+"\t"+
                        "P_Length"+"\t"+
                        "RN"+"\t"+
                        "Inaccuracy(%)"+"\t"+
                        "L_Flank"+"\t"+
                        "Entropy_LF"+"\t"+
                        "R_Flank"+"\t"+
                        "Entropy_RF"+"\t"+
                        "min_RN"+"\t"+
                        "max_RN"+"\t"+
                        "Range"+"\t"+
                        "Frequency"+"\t"+
                        "Alleles"+"\t"+
                        "PIC"+"\t"+
                        "Exceptions",
                        Polymorphism.evaluate(Polymorphism.evaluate(HitTable, jS4, jS5)),
                        fpath_p,
                        fpath );
                logsTextArea.append("Subquery Process DONE!" + '\n');
                sleep(3500);
            } catch (Exception ex) {
                logsTextArea.append(ex.getMessage());
            }
            
        }
        logsTextArea.append("--------------------------------------------" + '\n');
        logsTextArea.append("Total Subqueries processed = " + count + '\n');
        logsTextArea.append("Look for result at: " + fpath_p + '\n');
        
        return "";
    }
    public String get_RID_RTOE(String seq) throws Exception{
        String url2 = "";
        boolean rid = false;
        String data = constructRequest(seq,"\""+organism+"\"[organism]", Integer.toString(jS2), Integer.toString(jS3));
        try{
            // Send data
            URL url = new URL("https://blast.ncbi.nlm.nih.gov/Blast.cgi");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            BufferedReader rd;
            String urlResults;
            String eta;
            
            try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
                wr.write(data);
                wr.flush();
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                urlResults = url + "?RID=";
                String line;
                eta = "10";
                while ((line = rd.readLine()) != null){
                    int index;
                    if ((index = line.indexOf("RID =")) > -1){
                        line = line.substring(index + 5).trim();
                        urlResults = urlResults.concat(line);
                        rid = true;
                        if (line.equals("")){
                            sleep(100);
                            logsTextArea.append(urlResults + "...Blast search error" + '\n');
                            System.err.println(urlResults + "Blast search error");
                            
                        }
                    }
                    else if ((index = line.indexOf("RTOE =")) > -1){
                        eta = line.substring(index + 6).trim();
                        System.err.println(line);
                        logsTextArea.append(line + "sec." + " Wait..." +'\n');
                        sleep(100);
                    }
                    if (stoped) {return "";}
                    sleep(100);
                }
            }
            rd.close();
            int waitTime = Integer.parseInt(eta);
            Thread.sleep(waitTime * 1000);
            if (rid){
                url2 = urlResults + "&RESULTS_FILE=on&OLD_BLAST=false" + "&FORMAT_TYPE=Text&FORMAT_OBJECT=Alignment&ALIGNMENT_VIEW=Tabular&CMD=Get";
                System.err.println(url2);
            }
            else url2="";
        }
        catch (HeadlessException | IOException | InterruptedException | NumberFormatException e){
            logsTextArea.append( e.getMessage() + '\n');
            logsTextArea.append("Problem getting RID code! Retrying..." + '\n');
        }
        return url2;
    }
    
    public ArrayList<HtBLASTParser_Unit> getHitTable(String url) throws MalformedURLException, IOException {
        ArrayList<HtBLASTParser_Unit> units=new ArrayList<>();
        ArrayList<String> lecture=new ArrayList<>();
        List<String> check=new ArrayList<>();
        List<String> qav=new ArrayList<>();
        List<String> stream =new ArrayList<>();
        List<String> headers=new ArrayList<>();
        boolean hit_rend= false;
        boolean rend_excp= false;
        String html_chk="";
        boolean html_lg=true;
        if ( stoped ) // si se canceló un cálculo
            return null;
        try {
            logsTextArea.append("Start BLAST job!..." +'\n');
            sleep(100);
            while(html_lg){
                URL url2 = new URL(url);
                URLConnection conn = url2.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoOutput(true);
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if (stoped) return null;
                html_chk = rd.readLine();
                if (!html_chk.contains("# blastn")){ // antes !html_chk.contains("# blastn")
                    html_lg=true;
                    sleep(3500);
                }
                else  html_lg = false;
            }
            logsTextArea.append("Starting Hit Table Rendering Process..." + '\n');
            sleep(3500);
            try {
                FileUtils.copyURLToFile(new URL(url), new File("hit_table.htt"), 30000, 30000);
            }catch(IOException  e){
                logsTextArea.append("Problem in retrieving Hit-Table! Retrying" +'\n');
                logsTextArea.append(e.getMessage() + '\n');
                rend_excp = true;
            }
            
            
        }catch(IOException | InterruptedException e){
            logsTextArea.append(e.getMessage() + '\n');
            logsTextArea.append("Problem in BLAST job! Retrying..." +'\n');
            rend_excp = true;
        }
         
        if (rend_excp) {units = null; return units;}
        try (BufferedReader fi = new BufferedReader (new FileReader("hit_table.htt"))) {
            String s;
            
            while((s=fi.readLine()) != null){
                stream.add(s);
            }
        }
        hit_rend = true;
        if (hit_rend){
            
            String checkHitTable = stream.toString();
            
            if(!checkHitTable.contains("subject acc.ver") ||
                    !checkHitTable.contains("% identity") ||
                    !checkHitTable.contains("q. start") ||
                    !checkHitTable.contains("q. end") ||
                    !checkHitTable.contains("s. start") ||
                    !checkHitTable.contains("s. end") ||
                    !checkHitTable.contains("evalue")){
                logsTextArea.append("Error: "+checkHitTable.substring(1,checkHitTable.indexOf('<'))+'\n');
                return null;
            }
           
            for(String s1:stream){
                if(s1.contains("#"))
                {
                    if(s1.startsWith("# Query:") && !check.contains(s1))
                        check.add(s1);
                    if( s1.equals("# blastn") && headers.size()==5)
                    {
                        lecture.add(headers.get(2));
                        headers.clear();
                        headers.add(s1);
                    }
                    else
                        headers.add(s1);
                }
                else if(!s1.equals("") && !s1.contains("#"))
                {
                    lecture.add(s1);
                    headers.clear();
                }
            }
            for(String value:lecture)
            {
                if(value.startsWith("# Query:"))
                    units.add(new HtBLASTParser_Unit(value.substring(9),null,null,null,null,null,null,null,null,null,null,null));
                else{
                    
                    String[] lectemp=value.split("\t");
                    
                    units.add(new HtBLASTParser_Unit(lectemp[0],
                            lectemp[1],
                            Float.parseFloat(lectemp[2]),
                            Integer.parseInt(lectemp[3]),
                            Integer.parseInt(lectemp[4]),
                            Integer.parseInt(lectemp[5]),
                            Integer.parseInt(lectemp[6]),
                            Integer.parseInt(lectemp[7]),
                            Integer.parseInt(lectemp[8]),
                            Integer.parseInt(lectemp[9]),
                            Float.parseFloat(lectemp[10]),
                            Float.parseFloat(lectemp[11])));
                }
            }
            
            for (HtBLASTParser_Unit u: units)
            {
                if(!qav.contains(u.getQuery_acc_ver()))
                    qav.add(u.getQuery_acc_ver());
            }
            
            if(qav.size()<check.size())
                units.add(new HtBLASTParser_Unit(check.get(check.size()-1).substring(9),null,null,null,null,null,null,null,null,null,null,null));
            
        }
        else units = null;
        
        return units;
    }
    
    
    @Override
    protected void process( List< String > valsPublicados )
    {
        for ( int i = 0; i < valsPublicados.size(); i++ )
            logsTextArea.append( valsPublicados.get( i ) + "\n" );
    }
    
    @Override
    protected void done()
    {
        executeButton.setEnabled( true ); // habilita el botón Obtener primos
        cancelButton.setEnabled( false ); // deshabilita el botón Cancelar
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        logsTextArea.append("End Time: " + hour + "h:" + minutes + "m:" + seconds + "s:" + '\n');
        try{
            String res = get();
        } catch (InterruptedException ex) {
            logsTextArea.append("End Time: " + hour + "h:" + minutes + "m:" + seconds + "s:" + '\n');
        } catch (ExecutionException ex) {
            Logger.getLogger(BlastRequestWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopCalculus()
    {
        stoped = true;
    }
    public static void setProxy(String userName, char[] password){
        
        if(!userName.isEmpty() || password!=null)
             Authenticator.setDefault(new ProxyAuthenticator(userName, password));
     
        System.setProperty("java.net.useSystemProxies", "true");
    }
    private static String addData(String data, String key, String value)throws UnsupportedEncodingException  {
        
        data += "&" +URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
        return data;
    }
    
    private static String addUnits(String data){
        
        String[] lecture=data.split("\n");
        String result="";
        SSr query=null;
        String temp=null;
        
        for (int i = 0, j=1; i < lecture.length; i+=2,j+=2) {
            
            query=new SSr(lecture[i].substring(1),"");
            result+=lecture[i]+"\n";
            
            if(lecture[j].length()<60){
                
                temp=query.getF5().toUpperCase()+query.getPattern();
                
                do{
                    
                    temp+=query.getPattern();
                    
                }while(temp.length()<40);
                
                temp+=query.getF3().toUpperCase();
                
                result+=temp+"\n";
            }
            else
                result+=lecture[j]+"\n";
        }
        
        return result;
    }
    
    public static String constructRequest( String residues, String e_query, String hitListSize, String expect) throws UnsupportedEncodingException, Exception{
        
        String request = URLEncoder.encode("CMD", "UTF-8") + "=" + URLEncoder.encode("Put", "UTF-8");
        request = addData(request, "QUERY_BELIEVE_DEFLINE", "false");
        request = addData(request, "QUERY", addUnits(residues));
        request = addData(request, "DATABASE", "nr");
        request = addData(request, "ENTREZ_QUERY", e_query);
        request = addData(request, "LCASE_MASK", "true");
        //Number of hits to keep 500
        request = addData(request, "HITLIST_SIZE", hitListSize);
        // Filter: String[]{"None", "Low Complexity", "Human Repeats", "Masked" }
        request = addData(request, "FILTER", "F");
        //Expect value 10.0
        if(Float.parseFloat(expect)<30)
            throw new Exception("expect must be >= 30");
        else
            request = addData(request, "EXPECT", expect);
        //HTML, XML, Text, Tabulear, etc.
        request = addData(request, "FORMAT_TYPE", "Tabular");
        request = addData(request, "PROGRAM", "blastn");
        request = addData(request, "CLIENT", "web");
        request = addData(request, "BLAST_PROGRAM", "blastn");
        return request;
    }
}

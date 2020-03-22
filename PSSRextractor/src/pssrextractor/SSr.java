/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pssrextractor;

/**
 *
 * @author carlos1
 */
public class SSr {
    private String access;
    private String pattern;
    private String location;
    private String copies;
    private String score;
    private String match;
    private String mismatch;
    private String indel;
    private String inacc;
    private String ent5;
    private String ent3;
    private String ssr;
    

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSsr() {
        return ssr;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getMismatch() {
        return mismatch;
    }

    public void setMismatch(String mismatch) {
        this.mismatch = mismatch;
    }

    public String getIndel() {
        return indel;
    }

    public void setIndel(String indel) {
        this.indel = indel;
    }

    public String getInacc() {
        return inacc;
    }

    public void setInacc(String inacc) {
        this.inacc = inacc;
    }

    public String getEnt5() {
        return ent5;
    }

    public void setEnt5(String ent5) {
        this.ent5 = ent5;
    }

    public String getEnt3() {
        return ent3;
    }

    public void setEnt3(String ent3) {
        this.ent3 = ent3;
    }
    

    public void setSsr(String ssr) {
        this.ssr = ssr;
    }
    
    
}

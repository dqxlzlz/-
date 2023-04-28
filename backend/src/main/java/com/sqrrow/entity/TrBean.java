package org.csu.metrics.domain;

public class TrBean {
    String loc;
    String cp;
    String cc;

    public TrBean(String loc, String cp, String cc) {
        this.loc = loc;
        this.cp = cp;
        this.cc = cc;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}

package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;


public class Packages {
    private List<Package> pack;

    public List<Package> getPack() {
        return pack;
    }
    @XmlElement(name = "package")
    public void setPack(List<Package> pack) {
        this.pack = pack;
    }
}

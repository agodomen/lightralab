package edu.muc.platform.plugin.tools;

import org.apache.log4j.Logger;

import edu.muc.model.BaseModel;
import edu.muc.platform.plugin.DictKeys;


public class Syslog extends BaseModel<Syslog> {

    private static final long serialVersionUID = 2051998642258015518L;

    private static Logger log = Logger.getLogger(Syslog.class);

    public static final Syslog dao = new Syslog();

}

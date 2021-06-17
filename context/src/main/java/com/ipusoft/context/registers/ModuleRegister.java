package com.ipusoft.context.registers;

/**
 * author : GWFan
 * time   : 5/18/21 10:15 AM
 * desc   : module注册中心
 */

public interface ModuleRegister {
    /**
     * base_datastore
     */
    String DATA_STORE_LIBRARY = "com.ipusoft.mmkv.datastore.DataStoreApp";
    /**
     * database
     */
    String DATA_BASE = "com.ipusoft.database.DataBaseApplication";

    String SIM_MODULE = "com.ipusoft.sim.SimModuleApp";

    String SIP_MODULE = "com.ipusoft.siplibrary.SipModuleApp";

    String PHONE_MODULE = "com.ipusoft.phone.PhoneApp";

    String[] modules = {
            DATA_STORE_LIBRARY,
            DATA_BASE,
            SIM_MODULE,
            SIP_MODULE,
            PHONE_MODULE
    };
}

package com.ipusoft.context;

/**
 * author : GWFan
 * time   : 5/18/21 10:15 AM
 * desc   :
 */

public interface ModuleRegister {
    /**
     * base_datastore
     */
    String DATA_STORE_LIBRARY = "com.ipusoft.datastore.DataStoreApp";
    /**
     * base_network
     */
    String BASE_NETWORK = "com.ipusoft.network.RetrofitManager";
    /**
     * database
     */
    String DATA_BASE = "com.ipusoft.database.DataBaseApplication";

    String SIM_MODULE = "com.ipusoft.sim.SimModuleApp";

    String SIP_MODULE = "com.ipusoft.siplibrary.SipModuleApp";

    String[] modules = {
            DATA_STORE_LIBRARY,
            BASE_NETWORK,
            DATA_BASE,
            SIM_MODULE,
            SIP_MODULE
    };
}

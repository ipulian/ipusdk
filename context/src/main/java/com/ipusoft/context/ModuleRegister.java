package com.ipusoft.context;

/**
 * author : GWFan
 * time   : 5/18/21 10:15 AM
 * desc   :
 */

public interface ModuleRegister {
    /**
     * open_service_x
     */
    String X_LIBRARY = "com.ipusoft.xlibrary.XModuleApp";

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

    String[] modules = {
            X_LIBRARY,
            DATA_STORE_LIBRARY,
            BASE_NETWORK,
            DATA_BASE
    };
}

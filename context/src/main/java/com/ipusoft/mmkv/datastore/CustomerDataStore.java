package com.ipusoft.mmkv.datastore;

import com.ipusoft.context.bean.CustomerConfig;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.mmkv.constant.StorageConstant;

/**
 * author : GWFan
 * time   : 5/16/21 10:11 AM
 * desc   :
 */

public class CustomerDataStore {
    /**
     * @param customerConfig 客户配置
     */
    public static void setCustomerConfig(CustomerConfig customerConfig) {
        if (customerConfig != null) {
            AccountMMKV.set(StorageConstant.CUSTOMER_CONFIG, GsonUtils.toJson(customerConfig));
        }
    }

    public static CustomerConfig getCustomerConfig() {
        String json = AccountMMKV.getString(StorageConstant.CUSTOMER_CONFIG);
        CustomerConfig config = null;
        if (StringUtils.isNotEmpty(json)) {
            try {
                config = GsonUtils.fromJson(json, CustomerConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return config;
    }
}

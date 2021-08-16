package com.ipusoft.mmkv.datastore;

import com.ipusoft.context.bean.CustomerConfig;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.mmkv.constant.StorageConstant;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.ReflectUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : GWFan
 * time   : 8/10/21 8:55 AM
 * desc   :
 */

public class CustomerConfigRepo {
    private static final String TAG = "CustomerConfigRepo";

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

    /**
     * @return 用于筛选的自定义字段
     */
    public static List<CustomerConfig.ConfigItem> getUseInSearchField(CustomerConfig config) {
        List<CustomerConfig.ConfigItem> list = new ArrayList<>();
        if (config != null) {
            List<CustomerConfig.ConfigItem> customerSelectField = config.getCustomerSelectField();
            if (ArrayUtils.isNotEmpty(customerSelectField)) {
                for (CustomerConfig.ConfigItem item : customerSelectField) {
                    if (StringUtils.equals("1", item.getUseInSearch())) {
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }

    /**
     * @return 阶段列表
     */
    public static List<String> getStageList() {
        return getStageList(CustomerDataStore.getCustomerConfig());
    }

    public static List<String> getStageList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> stage = customerConfig.getStage();
            if (stage != null) {
                for (CustomerConfig.ConfigItem item : stage) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * @return 联系 列表
     */
    public static List<String> getConnectList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> connect = customerConfig.getConnect();
            if (connect != null) {
                for (CustomerConfig.ConfigItem item : connect) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    public static List<String> getConnectList() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> connect = customerConfig.getConnect();
            if (connect != null) {
                for (CustomerConfig.ConfigItem item : connect) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    public static String getConnectCodeByName(CustomerConfig customerConfig, String name) {
        String code = "";
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> connect = customerConfig.getConnect();
            if (connect != null) {
                for (CustomerConfig.ConfigItem item : connect) {
                    if (StringUtils.equals(item.getItemName(), name)) {
                        code = item.getItemCode();
                    }
                }
            }
        }
        return code;
    }

    /**
     * @return 分类列表
     */
    public static List<String> getSortList() {
        return getSortList(CustomerDataStore.getCustomerConfig());
    }

    public static List<String> getSortList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> sort = customerConfig.getSort();
            if (sort != null) {
                for (CustomerConfig.ConfigItem item : sort) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * @return 标签列表
     */
    public static List<String> getLabelList() {
        return getLabelList(CustomerDataStore.getCustomerConfig());
    }

    public static List<String> getLabelList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> label = customerConfig.getLabel();
            if (label != null) {
                for (CustomerConfig.ConfigItem item : label) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * @return 来源列表
     */
    public static List<String> getSourceList() {
        return getSourceList(CustomerDataStore.getCustomerConfig());
    }

    public static List<String> getSourceList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> source = customerConfig.getSource();
            if (source != null) {
                for (CustomerConfig.ConfigItem item : source) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * @return 客户池列表
     */
    public static List<String> getCustomerPoolList() {
        return getCustomerPoolList(CustomerDataStore.getCustomerConfig());
    }

    public static List<String> getCustomerPoolList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> pools = customerConfig.getCustomerPool();
            if (pools != null) {
                for (CustomerConfig.ConfigItem item : pools) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * @return 线索池列表
     */

    public static List<String> getCluePoolList() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        return getCluePoolList(customerConfig);
    }

    public static List<String> getCluePoolList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> cluePool = customerConfig.getCluePool();
            if (cluePool != null) {
                for (CustomerConfig.ConfigItem item : cluePool) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * 标签
     *
     * @return
     */
    public static List<String> getTagList() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> tag = customerConfig.getTag();
            if (ArrayUtils.isNotEmpty(tag)) {
                for (CustomerConfig.ConfigItem item : tag) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    /**
     * 等级
     *
     * @return
     */
    public static List<String> getLevelList() {
        return getLevelList(CustomerDataStore.getCustomerConfig());
    }

    public static List<String> getLevelList(CustomerConfig customerConfig) {
        List<String> list = new ArrayList<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> level = customerConfig.getLevel();
            if (ArrayUtils.isNotEmpty(level)) {
                for (CustomerConfig.ConfigItem item : level) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

    public static Map<String, String> getIndustryMap() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        Map<String, String> map = new HashMap<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> industry = customerConfig.getIndustry();
            if (industry != null && industry.size() > 0) {
                for (CustomerConfig.ConfigItem item : industry) {
                    map.put(item.getItemCode(), item.getItemName());
                }
            }
        }
        return map;
    }

    /**
     * 查询industryId
     *
     * @param industry
     * @return
     */
    public static String getIndustryId(String industry) {
        Map<String, String> industryMap = getIndustryMap();
        String id = "";
        if (StringUtils.isEmpty(industry)) {
            return id;
        }
        for (Map.Entry<String, String> entry : industryMap.entrySet()) {
            if (industry.equals(entry.getValue())) {
                id = entry.getKey();
                return id;
            }
        }
        return id;
    }

    public static String getIndustryById(String itemCode) {
        String industry = "";
        if (StringUtils.isEmpty(itemCode)) {
            return industry;
        }
        Map<String, String> industryMap = getIndustryMap();
        for (Map.Entry<String, String> entry : industryMap.entrySet()) {
            if (StringUtils.equals(itemCode, entry.getKey())) {
                industry = entry.getValue();
                return industry;
            }
        }
        return industry;
    }

    public static Map<String, String> getSexMap() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        Map<String, String> map = new HashMap<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> sex = customerConfig.getSex();
            if (ArrayUtils.isNotEmpty(sex)) {
                for (CustomerConfig.ConfigItem item : sex) {
                    map.put(item.getItemCode(), item.getItemName());
                }
            }
        }
        return map;
    }


    public static Map<String, String> getReverseSexMap() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        Map<String, String> map = new HashMap<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> sex = customerConfig.getSex();
            if (ArrayUtils.isNotEmpty(sex)) {
                for (CustomerConfig.ConfigItem item : sex) {
                    map.put(item.getItemName(), item.getItemCode());
                }
            }
        }
        return map;
    }

    public static Map<String, String> getPoolMap() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        Map<String, String> map = new HashMap<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> customerPool = customerConfig.getCustomerPool();
            if (customerPool != null && customerPool.size() > 0) {
                for (CustomerConfig.ConfigItem item : customerPool) {
                    map.put(item.getItemCode(), item.getItemName());
                }
            }
        }
        return map;
    }

    public static String getPoolId(String poolName) {
        Map<String, String> poolMap = getPoolMap();
        String id = "";
        if (StringUtils.isEmpty(poolName)) {
            return id;
        }
        for (Map.Entry<String, String> entry : poolMap.entrySet()) {
            if (poolName.equals(entry.getValue())) {
                id = entry.getKey();
                return id;
            }
        }
        return id;
    }

    public static Map<String, String> getCluePoolMap() {
        CustomerConfig customerConfig = CustomerDataStore.getCustomerConfig();
        Map<String, String> map = new HashMap<>();
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> cluePool = customerConfig.getCluePool();
            if (cluePool != null && cluePool.size() > 0) {
                for (CustomerConfig.ConfigItem item : cluePool) {
                    map.put(item.getItemCode(), item.getItemName());
                }
            }
        }
        return map;
    }


    public static String getCluePoolId(String cluePoolName) {
        Map<String, String> poolMap = getCluePoolMap();
        String id = "";
        if (StringUtils.isEmpty(cluePoolName)) {
            return id;
        }
        for (Map.Entry<String, String> entry : poolMap.entrySet()) {
            if (cluePoolName.equals(entry.getValue())) {
                id = entry.getKey();
                return id;
            }
        }
        return id;
    }

    /**
     * 根据字段名称查询字段值List
     *
     * @param fieldCode
     * @return
     */
    public static List<CustomerConfig.ConfigItem> getFieldListByCode(CustomerConfig customerConfig, String fieldCode) {
        List<CustomerConfig.ConfigItem> list = new ArrayList<>();
        if (StringUtils.isEmpty(fieldCode)) {
            return list;
        }
        if (customerConfig != null) {
            list = ReflectUtils.getValueByFieldName(fieldCode, customerConfig);
        }
        return list;
    }

    public static CustomerConfig.ConfigItem getCusFieldByCode(CustomerConfig customerConfig, String fieldCode) {
        CustomerConfig.ConfigItem configItem = null;
        if (StringUtils.isEmpty(fieldCode)) {
            return null;
        }
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> customerSelectField = customerConfig.getCustomerSelectField();
            for (CustomerConfig.ConfigItem item : customerSelectField) {
                if (StringUtils.equals(fieldCode, item.getItemCode())) {
                    configItem = item;
                    break;
                }
            }
        }
        return configItem;
    }

    /**
     * 根据字段名称查询字段值List
     *
     * @param fieldCode
     * @return
     */
    public static List<String> getValByFieldCode(CustomerConfig customerConfig, String fieldCode) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(fieldCode)) {
            return list;
        }
        if (customerConfig != null) {
            List<CustomerConfig.ConfigItem> fieldListByName = ReflectUtils.getValueByFieldName(fieldCode, customerConfig);
            if (fieldListByName != null) {
                for (CustomerConfig.ConfigItem item : fieldListByName) {
                    list.add(item.getItemName());
                }
            }
        }
        return list;
    }

}

package com.ai.cloud.skywalking.analysis.categorize2chain;

import com.ai.cloud.skywalking.analysis.categorize2chain.model.ChainInfo;
import com.ai.cloud.skywalking.analysis.categorize2chain.model.ChainNode;
import com.ai.cloud.skywalking.analysis.config.Config;
import com.ai.cloud.skywalking.analysis.config.HBaseTableMetaData;
import com.google.gson.Gson;

import org.apache.hadoop.hbase.client.Put;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChainDetail {
    private boolean isNormal = true;
    private String chainToken;
    private Map<String, ChainNode> chainNodeMap = new HashMap<String, ChainNode>();
    private String userId;

    public ChainDetail(ChainInfo chainInfo, boolean isNormal) {
        chainToken = chainInfo.getCID();
        for (ChainNode chainNode : chainInfo.getNodes()) {
            chainNodeMap.put(chainNode.getTraceLevelId(), chainNode);
        }
        userId = chainInfo.getUserId();

        this.isNormal = isNormal;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void save(Put put) throws SQLException {
        for (Map.Entry<String, ChainNode> entry : chainNodeMap.entrySet()){
            put.addColumn(HBaseTableMetaData.TABLE_CHAIN_DETAIL.COLUMN_FAMILY_NAME.getBytes(),entry.getKey().getBytes(),
                    entry.getValue().toString().getBytes());
        }
        if (isNormal) {
            DBCallChainInfoDao.saveChainDetail(this);
        }
    }

    public Collection<ChainNode> getChainNodes() {
        return chainNodeMap.values();
    }

    public String getUserId() {
        return userId;
    }

    public String getChainToken() {
        return chainToken;
    }
}

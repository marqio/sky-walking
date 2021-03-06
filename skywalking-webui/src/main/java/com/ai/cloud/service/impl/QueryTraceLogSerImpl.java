/**
 * 
 */
package com.ai.cloud.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.cloud.dao.inter.IBuriedPointSDAO;
import com.ai.cloud.service.inter.IQueryTraceLogSer;
import com.ai.cloud.util.Constants;
import com.ai.cloud.vo.mvo.TraceLogEntry;

/**
 * 
 * @author tz
 * @date 2015年11月18日 下午5:57:11
 * @version V0.1
 */
@Service
public class QueryTraceLogSerImpl implements IQueryTraceLogSer {

	@Autowired
	IBuriedPointSDAO buriedPointSDAO;

	@Override
	public Map<String, TraceLogEntry> queryLogByTraceId(String traceId) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return buriedPointSDAO.queryLogByTraceId(Constants.TABLE_NAME_CHAIN, traceId);
	}

}

package com.boyu.erp.platform.gateway.client.dao;

import java.util.List;
import java.util.Map;


public interface ServiceDao {
	public List<Map> listByClientId(Long clientId);
	public Map getClient(String clientId);
}

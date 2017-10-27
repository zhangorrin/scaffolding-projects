package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.OauthClientDetailsRepository;
import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
import com.orrin.sca.common.service.uaa.domain.OauthClientDetailsEntity;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.service.SysResourcesService;
import com.orrin.sca.component.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysResourcesService")
public class SysResourcesServiceImpl implements SysResourcesService {
	@Autowired
	private SysResourcesRepository sysResourcesRepository;

	@Autowired
	private OauthClientDetailsRepository oauthClientDetailsRepository;

	@Override
	public Page<SysResourcesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysResourcesRepository.findAll(pageable);
	}

	@Override
	public List<SysResourcesEntity> findAllMenuSysResources() {
		return sysResourcesRepository.findByResourceTypeAndEnableOrderByPriorityAscClientIdAsc("MENU", true);
	}

	@Override
	public List<MenuModel> wrapMenu(List<SysResourcesEntity> sysResourcesEntityList) {
		List<MenuModel> menus = new ArrayList<>();
		List<OauthClientDetailsEntity> clientList = oauthClientDetailsRepository.findAll();
		Map<String,List<SysResourcesEntity>> decomposeResourceOnClientIdMap = new HashMap<>();
		if(clientList != null && clientList.size() > 0) {

			for (SysResourcesEntity sre : sysResourcesEntityList) {
				if (decomposeResourceOnClientIdMap.containsKey(sre.getClientId())) {
					decomposeResourceOnClientIdMap.get(sre.getClientId()).add(sre);
				} else {
					List<SysResourcesEntity> a = new ArrayList<SysResourcesEntity>();
					a.add(sre);
					decomposeResourceOnClientIdMap.put(sre.getClientId(), a);
				}
			}

			for (OauthClientDetailsEntity ocde : clientList) {
				MenuModel menuModel = new MenuModel();
				menuModel.setResourceId(ocde.getClientId());
				menuModel.setResourceName(ocde.getClientName());
				menuModel.setTitle(ocde.getClientName());
				menuModel.setChildren(this.wrapMenu(ocde.getClientId(), decomposeResourceOnClientIdMap.get(ocde.getClientId())));
				menus.add(menuModel);
			}
		}

		return menus;
	}

	protected List<MenuModel> wrapMenu(String clientId, List<SysResourcesEntity> sysResourcesEntityList) {
		List<MenuModel> menus = new ArrayList<>();
		if(sysResourcesEntityList != null){
			for(SysResourcesEntity sre : sysResourcesEntityList) {
				if(StringUtils.hasText(sre.getFatherResourceId()) && sre.getFatherResourceId().equals(clientId)){
					MenuModel menuModel = MenuModel.fromSysResources(sre);
					menuModel.setChildren(this.findMenuChildren(sre.getResourceId(),sysResourcesEntityList));
					menus.add(menuModel);
				}
			}
		}

		return menus;
	}


	protected List<MenuModel> findMenuChildren(String resourceId,List<SysResourcesEntity> sysResourcesEntityList) {
		List<MenuModel> menus = new ArrayList<>();
		for(SysResourcesEntity sre : sysResourcesEntityList) {
			if(resourceId.equals(sre.getFatherResourceId())){
				MenuModel menuModel = MenuModel.fromSysResources(sre);
				menuModel.setChildren(this.findMenuChildren(sre.getResourceId(),sysResourcesEntityList));
				menus.add(menuModel);
			}
		}

		return menus;
	}

	/*@Override
	public Page<SysResources> findCriteria(Integer page, Integer size, SysResources sysResources) {
		return null;
	}*/
}

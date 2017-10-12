package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
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
import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysResourcesService")
public class SysResourcesServiceImpl implements SysResourcesService {
	@Autowired
	private SysResourcesRepository sysResourcesRepository;

	@Override
	public Page<SysResourcesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysResourcesRepository.findAll(pageable);
	}

	@Override
	public List<SysResourcesEntity> findAllMenuSysResources() {
		return sysResourcesRepository.findByResourceTypeAndEnableOrderByPriorityAsc("MENU", true);
	}

	@Override
	public List<MenuModel> wrapMenu(List<SysResourcesEntity> sysResourcesEntityList) {
		List<MenuModel> menus = new ArrayList<>();
		for(SysResourcesEntity sre : sysResourcesEntityList) {
			if(!StringUtils.hasText(sre.getFatherResourceId()) || sre.getFatherResourceId().equals("0")){
				MenuModel menuModel = MenuModel.fromSysResources(sre);
				menuModel.setChildren(this.findMenuChildren(sre.getResourceId(),sysResourcesEntityList));
				menus.add(menuModel);
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

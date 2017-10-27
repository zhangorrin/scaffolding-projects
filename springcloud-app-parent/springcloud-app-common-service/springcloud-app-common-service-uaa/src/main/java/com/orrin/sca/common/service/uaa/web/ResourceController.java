package com.orrin.sca.common.service.uaa.web;

import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
import com.orrin.sca.common.service.uaa.domain.SysResources;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.service.feignclient.SysResourceServiceApi;
import com.orrin.sca.common.service.uaa.service.SysResourcesService;
import com.orrin.sca.common.service.uaa.web.vo.ResourceMoreInfo;
import com.orrin.sca.component.menu.MenuModel;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.component.utils.string.LocalStringUtils;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/resource")
public class ResourceController implements SysResourceServiceApi {

    @Autowired
    private SysResourcesRepository sysResourcesRepository;

    @Autowired
    private SysResourcesService sysResourcesService;

    @Override
    @RequestMapping(path = "/addprivilege", method = RequestMethod.POST)
    public ResponseResult<SysResources> insertPrivilege(@RequestBody ResourcePrivilegeEntity resourcePrivilegeEntity) {
        ResponseResult<SysResources> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        int count = sysResourcesRepository.countByGlobalUniqueId(resourcePrivilegeEntity.getGlobalUniqueId());

        if(count > 0){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("globalUniqueId repeate ! : " + "resourcePrivilege.resourceGlobalUniqueId()");
            return responseResult;
        }

        SysResourcesEntity sysResourcesEntity = new SysResourcesEntity();
        sysResourcesEntity.setGlobalUniqueId(resourcePrivilegeEntity.getGlobalUniqueId());
        sysResourcesEntity.setResourceDesc(resourcePrivilegeEntity.getResourceDesc());
        sysResourcesEntity.setResourcePath(resourcePrivilegeEntity.getResourcePath());
        sysResourcesEntity.setResourceName(resourcePrivilegeEntity.getResourceName());
        sysResourcesEntity.setEnable(true);


        sysResourcesEntity = sysResourcesRepository.saveAndFlush(sysResourcesEntity);

        responseResult.setData(sysResourcesEntity);

        return responseResult;
    }

    @Override
    @RequestMapping(path = "/authResources", method = RequestMethod.GET)
    public ArrayList<HashMap<String,String>> findAuthResources() {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        List<Object[]> result  = sysResourcesRepository.findAuthResources();
        Iterator<Object[]> it = result.iterator();

        while(it.hasNext()){
            Object[] o = it.next();
            HashMap<String,String> map = new HashMap<String,String>();
            String resourcePaths = (String)o[0];
            if(resourcePaths.contains(",")){
                String resourcePath[] = resourcePaths.split(",");
                for(String rp : resourcePath) {
                    map.put("resourcePath", rp);
                    map.put("authorityMark", (String)o[1]);
                    map.put("globalUniqueId", (String)o[3]);
                    map.put("requestMethod", (String)o[4]);
                    list.add(map);
                }
            }else {
                map.put("resourcePath", resourcePaths);
                map.put("authorityMark", (String)o[1]);
                map.put("globalUniqueId", (String)o[3]);
                map.put("requestMethod", (String)o[4]);
                list.add(map);
            }

        }
        return list;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseResult<SysResources> insert(@RequestBody SysResourcesEntity resourcesEntity) {
        ResponseResult<SysResources> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        int count = sysResourcesRepository.countByGlobalUniqueId(resourcesEntity.getGlobalUniqueId());

        if(!StringUtils.hasText(resourcesEntity.getResourceId())){
            resourcesEntity.setResourceId(LocalStringUtils.uuidLowerCase());
        }else {
            count--;
        }

        if(count > 0){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("globalUniqueId repeate ! : " + "resourcePrivilege.resourceGlobalUniqueId()");
            return responseResult;
        }

        resourcesEntity = sysResourcesRepository.saveAndFlush(resourcesEntity);

        responseResult.setData(resourcesEntity);

        return responseResult;
    }


    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public ResponseResult<Page<SysResourcesEntity>> list() {
        ResponseResult<Page<SysResourcesEntity>> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        Pageable pageable = new PageRequest(0, 100000, Sort.Direction.ASC, "priority");
        Page<SysResourcesEntity> resourcesPage = sysResourcesRepository.findAll(pageable);
        responseResult.setData(resourcesPage);

        return responseResult;
    }

    @RequestMapping(path = "/{resourceId}", method = RequestMethod.DELETE)
    public ResponseResult<Void> delete(@PathVariable("resourceId") String resourceId) {
        ResponseResult<Void> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        SysResourcesEntity sysResourcesEntityQuery = new  SysResourcesEntity();
        sysResourcesEntityQuery.setFatherResourceId(resourceId);
        sysResourcesEntityQuery.setEnable(null);
        sysResourcesEntityQuery.setIssys(null);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("fatherResourceId", match -> match.ignoreCase(false));
        Example<SysResourcesEntity> entityExample = Example.of(sysResourcesEntityQuery, exampleMatcher);
        long deleteCheck = sysResourcesRepository.count(entityExample);

        if(deleteCheck > 0) {
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg(" this resource has children !");
            return responseResult;
        }

        sysResourcesRepository.delete(resourceId);

        return responseResult;
    }

    @RequestMapping(path = "/menu", method = RequestMethod.GET)
    public ResponseResult<List<MenuModel>> menu() {
        ResponseResult<List<MenuModel>> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        List<SysResourcesEntity> resourcesEntityList = sysResourcesService.findAllMenuSysResources();
        List<MenuModel> menuModels = sysResourcesService.wrapMenu(resourcesEntityList);
        responseResult.setData(menuModels);

        return responseResult;
    }

    @RequestMapping(path = "/moreinfo/{resourceId}", method = RequestMethod.GET)
    public ResponseResult<ResourceMoreInfo> moreinfo(@PathVariable("resourceId") String resourceId) {
        ResponseResult<ResourceMoreInfo> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        SysResourcesEntity mainResource = sysResourcesRepository.findOne(resourceId);
        ResourceMoreInfo resourceMoreInfo = new ResourceMoreInfo();
        resourceMoreInfo.setMainResource(mainResource);

        SysResourcesEntity sysResourcesEntityQuery = new  SysResourcesEntity();
        sysResourcesEntityQuery.setResourceType("URL");
        sysResourcesEntityQuery.setFatherResourceId(resourceId);
        sysResourcesEntityQuery.setEnable(null);
        sysResourcesEntityQuery.setIssys(null);


        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("resourceType", match -> match.ignoreCase(false))
                .withMatcher("fatherResourceId", match -> match.ignoreCase(false))
                ;
        Example<SysResourcesEntity> entityExample = Example.of(sysResourcesEntityQuery, exampleMatcher);
        List<SysResourcesEntity> contentResources = sysResourcesRepository.findAll(entityExample);
        resourceMoreInfo.setContentResources(contentResources);

        responseResult.setData(resourceMoreInfo);

        return responseResult;
    }
}

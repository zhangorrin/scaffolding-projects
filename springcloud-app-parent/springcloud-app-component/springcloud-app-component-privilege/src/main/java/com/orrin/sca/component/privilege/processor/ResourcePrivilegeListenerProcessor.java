package com.orrin.sca.component.privilege.processor;

import com.orrin.sca.common.service.uaa.service.SysResourceServiceApi;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilege;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.component.utils.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.*;

public class ResourcePrivilegeListenerProcessor {
    private Map<String, Object> beans;

    @Autowired
    private SysResourceServiceApi sysResourceServiceApi;

    public Map<String, Object> getBeans() {
        return beans;
    }

    public ResourcePrivilegeListenerProcessor() {
        beans = new HashMap<>();
        beans.putAll(SpringUtil.getBeansWithAnnotation(RestController.class));
        beans.putAll(SpringUtil.getBeansWithAnnotation(Controller.class));
    }

    public List<ResourcePrivilegeEntity> getResourcePrivilegeByannotation(){
        List<ResourcePrivilegeEntity> list = new ArrayList<>();
        List<Method> methodList = new ArrayList<>();

        for(Object bean:beans.values()){
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
            methodList.addAll(Arrays.asList(methods));
        }

        for(Method method : methodList) {
            ResourcePrivilege resourcePrivilege = AnnotationUtils.getAnnotation(method, ResourcePrivilege.class);
            RequestMapping requestMapping = AnnotationUtils.getAnnotation(method, RequestMapping.class);

            if(resourcePrivilege != null){
                ResourcePrivilegeEntity resourcePrivilegeEntity = new ResourcePrivilegeEntity();
                resourcePrivilegeEntity.setGlobalUniqueId(resourcePrivilege.resourceGlobalUniqueId());
                resourcePrivilegeEntity.setResourceName(resourcePrivilege.resourceName());
                resourcePrivilegeEntity.setResourceDesc(resourcePrivilege.resourceDesc());

                if(requestMapping != null) {
                    String paths[] = (requestMapping.path()!=null)?requestMapping.path():requestMapping.value();
                    resourcePrivilegeEntity.setResourcePath(StringUtils.arrayToDelimitedString(paths, ","));
                }

                list.add(resourcePrivilegeEntity);
            }

        }

        return list;
    }

    @Override
    public String toString() {
        for(Object bean:beans.values()){
            System.err.println(bean==null?"null":bean.getClass().getName());
        }
        System.err.println("=====ContextRefreshedEvent=====");

        return "ResourcePrivilegeListenerProcessor{" +
                "beans=" + beans +
                '}';
    }
}

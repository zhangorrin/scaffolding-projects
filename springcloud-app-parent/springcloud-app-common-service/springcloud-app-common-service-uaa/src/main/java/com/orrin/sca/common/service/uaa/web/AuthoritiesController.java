package com.orrin.sca.common.service.uaa.web;

import com.orrin.sca.common.service.uaa.dao.SysAuthoritiesRepository;
import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesEntity;
import com.orrin.sca.common.service.uaa.service.SysAuthoritiesResourcesService;
import com.orrin.sca.common.service.uaa.web.vo.AuthoritiesAndResources;
import com.orrin.sca.common.service.uaa.web.vo.AuthoritiesRequestParams;
import com.orrin.sca.component.jpa.dao.Range;
import com.orrin.sca.component.utils.string.LocalStringUtils;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/authority")
public class AuthoritiesController {

    @Autowired
    private SysAuthoritiesRepository sysAuthoritiesRepository;

    @Autowired
    private SysAuthoritiesResourcesService sysAuthoritiesResourcesService;

    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public ResponseResult<Page<SysAuthoritiesEntity>> list(@RequestBody AuthoritiesRequestParams authoritiesRequestParams) {
        ResponseResult<Page<SysAuthoritiesEntity>> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");


        SysAuthoritiesEntity queryEntity = new  SysAuthoritiesEntity();
        queryEntity.setAuthorityMark(authoritiesRequestParams.getAuthorityMark());


        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("authorityMark", match -> match.contains());


        if(StringUtils.hasText(authoritiesRequestParams.getMessage())){
            queryEntity.setMessage(authoritiesRequestParams.getMessage());
            exampleMatcher.withMatcher("message", match -> match.contains());
        }

        if(StringUtils.hasText(authoritiesRequestParams.getAuthorityName())){
            queryEntity.setAuthorityName(authoritiesRequestParams.getAuthorityName());
            exampleMatcher.withMatcher("authorityName", match -> match.contains());
        }

        Example<SysAuthoritiesEntity> entityExample = Example.of(queryEntity, exampleMatcher);

        com.orrin.sca.component.jpa.dao.Range<SysAuthoritiesEntity> rangeCreatedDate = new com.orrin.sca.component.jpa.dao.Range<>("createdDate", authoritiesRequestParams.getCreatedDateStart(),authoritiesRequestParams.getCreatedDateEnd());

        List<Range<SysAuthoritiesEntity>> rangeList = new ArrayList<>();
        rangeList.add(rangeCreatedDate);


        Pageable pageable = new PageRequest(authoritiesRequestParams.getQueryPage(), authoritiesRequestParams.getSize(), Sort.Direction.ASC, "authorityId");
        Page<SysAuthoritiesEntity> resourcesPage = sysAuthoritiesRepository.queryByExampleWithRange(entityExample, rangeList, pageable);
        responseResult.setData(resourcesPage);

        return responseResult;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseResult<SysAuthoritiesEntity> insertOrEdit(@RequestBody SysAuthoritiesEntity sysAuthoritiesEntity) {
        ResponseResult<SysAuthoritiesEntity> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        if(!StringUtils.hasText(sysAuthoritiesEntity.getAuthorityId())) {
            sysAuthoritiesEntity.setAuthorityId(LocalStringUtils.uuidLowerCase());
        }

        SysAuthoritiesEntity saeCheck = sysAuthoritiesRepository.findByAuthorityMark(sysAuthoritiesEntity.getAuthorityMark());

        if(saeCheck != null && !saeCheck.getAuthorityId().equals(sysAuthoritiesEntity.getAuthorityId())){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("authorityMark repeat !");
            return responseResult;
        }

        sysAuthoritiesEntity = sysAuthoritiesRepository.saveAndFlush(sysAuthoritiesEntity);
        responseResult.setData(sysAuthoritiesEntity);

        return responseResult;
    }

    @RequestMapping(path = "/{authorityId}", method = RequestMethod.DELETE)
    public ResponseResult<Void> delete(@PathVariable("authorityId") String authorityId) {
        ResponseResult<Void> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        sysAuthoritiesResourcesService.deleteByAuthorityId(authorityId);

        return responseResult;
    }

    @RequestMapping(path = "/{authorityId}", method = RequestMethod.GET)
    public ResponseResult<SysAuthoritiesEntity> getSysAuthoritiesEntity(@PathVariable("authorityId") String authorityId) {
        ResponseResult<SysAuthoritiesEntity> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        SysAuthoritiesEntity sysAuthoritiesEntity = sysAuthoritiesRepository.findOne(authorityId);

        if(sysAuthoritiesEntity == null){
            sysAuthoritiesEntity = sysAuthoritiesRepository.findByAuthorityMark(authorityId);
        }

        if(sysAuthoritiesEntity == null){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("could not find authority entity by authorityId or authorityMark");
            return responseResult;
        }

        responseResult.setData(sysAuthoritiesEntity);

        return responseResult;
    }

    @RequestMapping(path = "/andresource/{authorityId}", method = RequestMethod.GET)
    public ResponseResult<AuthoritiesAndResources> getSysAuthoritiesAndResources(@PathVariable("authorityId") String authorityId, HttpServletRequest request) {

        String resourceName = request.getParameter("resourceName");
        String page = request.getParameter("page");
        String size = request.getParameter("size");

        if(!StringUtils.hasText(resourceName)){
            resourceName = null;
        }

        if(!StringUtils.hasText(page)){
            page = "0";
        }

        if(!StringUtils.hasText(size)){
            size = "10";
        }

        ResponseResult<AuthoritiesAndResources> responseResult = sysAuthoritiesResourcesService.findAuthoritiesAndResources(authorityId,resourceName, Integer.parseInt(page), Integer.parseInt(size));
        return responseResult;
    }
}

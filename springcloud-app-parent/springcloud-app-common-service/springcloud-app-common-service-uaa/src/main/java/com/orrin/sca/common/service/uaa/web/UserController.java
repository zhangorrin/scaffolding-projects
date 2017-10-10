package com.orrin.sca.common.service.uaa.web;

import com.orrin.sca.common.service.uaa.domain.SysUsersEntity;
import com.orrin.sca.common.service.uaa.service.SysUsersService;
import com.orrin.sca.component.jpa.dao.Range;
import com.orrin.sca.component.utils.json.annotation.JSON;
import com.orrin.sca.component.utils.security.SecurityUtils;
import com.orrin.sca.component.utils.string.LocalStringUtils;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SysUsersService sysUsersService;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @JSON(type = SysUsersEntity.class, filter = "password")
    public ResponseResult<Page> index(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size, HttpServletRequest request){
        ResponseResult<Page> responseResult = new ResponseResult<Page>();
        responseResult.setResponseCode("00000");

        if(size == null || size == 0){
            size = Integer.valueOf(10);
        }

        if(page == null) {
            page = Integer.valueOf(0);
        }

        String username = request.getParameter("userName");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String createdDateStart = request.getParameter("createdDateStart");
        String createdDateEnd = request.getParameter("createdDateEnd");

        Page<SysUsersEntity> sysUsersEntities = null;

        if(StringUtils.hasText(username) || StringUtils.hasText(name) || StringUtils.hasText(mobile) || StringUtils.hasText(createdDateStart)|| StringUtils.hasText(createdDateEnd)){
            SysUsersEntity sysUsersEntity = new SysUsersEntity(false);
            sysUsersEntity.setUsername(username);
            sysUsersEntity.setName(name);
            sysUsersEntity.setMobile(mobile);

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("username", match -> match.contains())
                    .withMatcher("name", new ExampleMatcher.GenericPropertyMatcher().contains())
                    .withMatcher("mobile", match -> match.contains());

            Comparable<SysUsersEntity> ccreatedDateEnd = null;

            if(StringUtils.hasText(createdDateEnd)){
                ccreatedDateEnd = ( s11)  -> s11.getCreatedDate().compareTo(DateTime.parse(createdDateEnd));
            }

            Comparable<SysUsersEntity> ccreatedDateStart = null;
            if(StringUtils.hasText(createdDateStart)){
                ccreatedDateStart = ( s11)  -> s11.getCreatedDate().compareTo(DateTime.parse(createdDateStart));
            }

            Example<SysUsersEntity> entityExample = Example.of(sysUsersEntity, exampleMatcher);

            Range<SysUsersEntity> rangeCreatedDate = new Range<SysUsersEntity>("createdDate", ccreatedDateStart,ccreatedDateEnd);

            List<Range<SysUsersEntity>> rangeList = new ArrayList<>();
            rangeList.add(rangeCreatedDate);

            Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "userId");
            sysUsersEntities = sysUsersService.queryByExampleWithRange(entityExample, rangeList, pageable);
        }else {
            sysUsersEntities = sysUsersService.findSysUsersNoCriteria(page,size);
        }

        responseResult.setData(sysUsersEntities);

        return responseResult;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @JSON(type = SysUsersEntity.class, filter = "password")
    public ResponseResult<SysUsersEntity> addOrEdit(@RequestBody SysUsersEntity sysUsersEntity, HttpServletRequest request){
        ResponseResult<SysUsersEntity> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");

        if(!StringUtils.hasText(sysUsersEntity.getUserId())){
            sysUsersEntity.setUserId(LocalStringUtils.uuidLowerCase());
            sysUsersEntity.setCreatedBy(SecurityUtils.getCurrentUserUsername());
        }

        sysUsersEntity.setLastModifiedBy(SecurityUtils.getCurrentUserUsername());


        sysUsersEntity = sysUsersService.saveAndFlush(sysUsersEntity);

        responseResult.setData(sysUsersEntity);

        return responseResult;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @JSON(type = SysUsersEntity.class, filter = "password")
    public ResponseResult<SysUsersEntity> getByUserId(@PathVariable("userId") String userId, HttpServletRequest request){
        ResponseResult<SysUsersEntity> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");

        SysUsersEntity sysUsersEntitie = sysUsersService.findByUserId(userId);
        responseResult.setData(sysUsersEntitie);

        return responseResult;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @JSON(type = SysUsersEntity.class, filter = "password")
    public ResponseResult deleteByUserId(@PathVariable("userId") String userId, HttpServletRequest request){
        ResponseResult<SysUsersEntity> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");

        sysUsersService.deleteByUserId(userId);

        return responseResult;
    }
}

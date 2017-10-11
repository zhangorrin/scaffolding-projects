package com.orrin.sca.common.service.uaa.web;

import com.orrin.sca.common.service.uaa.domain.SysUsersEntity;
import com.orrin.sca.common.service.uaa.service.SysUsersService;
import com.orrin.sca.common.service.uaa.web.vo.UserRequestParams;
import com.orrin.sca.common.service.uaa.web.vo.UserUpdatePwdRequestParams;
import com.orrin.sca.component.jpa.dao.Range;
import com.orrin.sca.component.utils.json.annotation.JSON;
import com.orrin.sca.component.utils.security.SecurityUtils;
import com.orrin.sca.component.utils.string.LocalStringUtils;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(path = "/list", method = RequestMethod.POST)
    @JSON(type = SysUsersEntity.class, filter = "password")
    public ResponseResult<Page> index(@RequestBody UserRequestParams userRequestParams, HttpServletRequest request){
        ResponseResult<Page> responseResult = new ResponseResult<Page>();
        responseResult.setResponseCode("00000");

        Page<SysUsersEntity> sysUsersEntities = null;

        if(StringUtils.hasText(userRequestParams.getUsername()) || StringUtils.hasText(userRequestParams.getName()) || StringUtils.hasText(userRequestParams.getMobile()) || userRequestParams.getCreatedDateStart() != null|| userRequestParams.getCreatedDateEnd()!=null){
            SysUsersEntity sysUsersEntity = new SysUsersEntity(false);
            sysUsersEntity.setUsername(userRequestParams.getUsername());
            sysUsersEntity.setName(userRequestParams.getName());
            sysUsersEntity.setMobile(userRequestParams.getMobile());

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("username", match -> match.contains())
                    .withMatcher("name", new ExampleMatcher.GenericPropertyMatcher().contains())
                    .withMatcher("mobile", match -> match.contains());

            Example<SysUsersEntity> entityExample = Example.of(sysUsersEntity, exampleMatcher);

            Range<SysUsersEntity> rangeCreatedDate = new Range<>("createdDate", userRequestParams.getCreatedDateStart(),userRequestParams.getCreatedDateEnd());

            List<Range<SysUsersEntity>> rangeList = new ArrayList<>();
            rangeList.add(rangeCreatedDate);

            Pageable pageable = new PageRequest(userRequestParams.getPage(), userRequestParams.getSize(), Sort.Direction.ASC, "userId");
            sysUsersEntities = sysUsersService.queryByExampleWithRange(entityExample, rangeList, pageable);
        }else {
            sysUsersEntities = sysUsersService.findSysUsersNoCriteria(userRequestParams.getPage(), userRequestParams.getSize());
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
        sysUsersEntity.setPassword(null);
        sysUsersEntity.setLastModifiedBy(SecurityUtils.getCurrentUserUsername());

        SysUsersEntity checkUsername = sysUsersService.findByUsername(sysUsersEntity.getUsername());
        if(checkUsername!=null && !checkUsername.getUserId().equals(sysUsersEntity.getUserId())){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("username repeat! ");
            return responseResult;
        }

        sysUsersEntity = sysUsersService.saveAndFlush(sysUsersEntity);

        responseResult.setData(sysUsersEntity);

        return responseResult;
    }

    @RequestMapping(path = "/updatepwd", method = RequestMethod.POST)
    @JSON(type = SysUsersEntity.class, filter = "password")
    public ResponseResult<SysUsersEntity> updatePwd(@RequestBody UserUpdatePwdRequestParams userUpdatePwdRequestParams, HttpServletRequest request){
        ResponseResult<SysUsersEntity> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");

        SysUsersEntity checkUser = sysUsersService.findByUserId(userUpdatePwdRequestParams.getUserId());
        if(checkUser == null){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("user not exists! ");
            return responseResult;
        }

        if(!userUpdatePwdRequestParams.getPasswd().equals(userUpdatePwdRequestParams.getPasswdCheck())) {
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("the tow new passwords are different! ");
            return responseResult;
        }

        if(!StringUtils.hasText(userUpdatePwdRequestParams.getPasswd())) {
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("the new password is empty! ");
            return responseResult;
        }

        checkUser.setPassword(userUpdatePwdRequestParams.getPasswd());
        checkUser = sysUsersService.saveAndFlush(checkUser);

        responseResult.setData(checkUser);

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

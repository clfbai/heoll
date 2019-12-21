package com.boyu.erp.platform.usercenter.aspect;

import com.boyu.erp.platform.usercenter.components.OperationAuthority;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @program: boyu-platform_text
 * @description: 操作权限检查切面
 * @author: clf
 * @create: 2019-07-03 11:38
 */
@Slf4j
@Aspect
@Component
public class OperationAuthorityAspect {

    /**
     * 操作权限
     * 操作权限Id 拼接规则 表名+模块名(控制层名称或api、service名称)+操作权限后缀
     * 例如  product_ProductController_OPERATION_CRUD(后缀字段ParameterString.OPERATION_AUTHORITY)
     */
    @Autowired
    private TableService tableSerivce;

    @Autowired
    private SysPrivilegeSerivce privilegeSerivce;

    @Pointcut("@within(com.boyu.erp.platform.usercenter.components.OperationAuthority) || @annotation(com.boyu.erp.platform.usercenter.components.OperationAuthority)")
    private void tableFieldFiltration() {
    }

    @Around("tableFieldFiltration()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("操作权限切面: ");
        Method m = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        //用户权限集合
        OperationAuthority authority = m.getAnnotation(OperationAuthority.class);
        if (!m.isAnnotationPresent(OperationAuthority.class) || StringUtils.isBlank(authority.name())) {
            //没有添加注解就放行或者注解为空放行
            return pjp.proceed();
        }
        int a = 0;
        //操作权限后缀字段 ParameterString.OPERATION_AUTHORITY
        SysPrivilege privilege = privilegeSerivce.OperationAuthorityPriv(authority.name() + ParameterString.OPERATION_AUTHORITY);
        //权限表没有对应的操作权限添加操作权限
        if (privilege == null) {
            TableFile tableFile = new TableFile();
            tableFile.setDatabaseName(CommonFainl.DATABEASNAME);
            //第一个字段必须是表名
            tableFile.setTableName(authority.name().split("\\|")[0]);
            SysPrivilege pr = new SysPrivilege();
            pr.setPrivId(authority.name() + ParameterString.OPERATION_AUTHORITY);
            //权限类型
            pr.setPrivType(CommonFainl.PRIV_SCOPE_STS);
            //权限范围
            pr.setPrivScp(CommonFainl.PRIV_TYPE_BT);
            //如果要区分增删除改操作则在拼接注释后缀加对应的字段默认不区分
            pr.setDescription(tableSerivce.findtableName(tableFile).getTableChineseName() + descpe(authority.name()));
            privilegeSerivce.addOperationAuthority(pr);
            privilege = privilegeSerivce.OperationAuthorityPriv(authority.name() + ParameterString.OPERATION_AUTHORITY);
            log.info("添加的权限: " + privilegeSerivce.OperationAuthorityPriv(authority.name() + ParameterString.OPERATION_AUTHORITY));
        }
        for (SysPrivilege p : user.getPrivilegeSet()) {
            if (privilege.equals(p.getPrivId())) {
                a = 1;
            }
        }

        if (a > 0) {
            return pjp.proceed();
        } else {
            throw new ServiceException(ResultCode.PRIV_ARGUMENT_ERROR, "您尚未获得权限Id :" + authority.name() + ParameterString.OPERATION_AUTHORITY);
        }
    }


    public String descpe(String name) {
        if (name.indexOf(CommonFainl.ADD) > 0) {
            return CommonFainl.OADescriptionADD;
        }
        if (name.indexOf(CommonFainl.UPDATE) > 0) {
            return CommonFainl.OADescriptionUPDATE;
        }
        if (name.indexOf(CommonFainl.DELETE) > 0) {
            return CommonFainl.OADescriptionDELETE;
        }
        return CommonFainl.OADescription;
    }

}

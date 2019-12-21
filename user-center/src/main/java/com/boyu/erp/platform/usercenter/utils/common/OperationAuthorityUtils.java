package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysUnitPrivService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.system.UserScopeServer;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform_text
 * @description: 操作权限工具类
 * @author: clf
 * @create: 2019-07-03 15:10
 */
@Slf4j
@Component
public class OperationAuthorityUtils {

    /**
     * 操作权限
     * 操作权限Id 拼接规则 表名+模块名(控制层名称或api、service名称)+操作权限后缀
     * 例如  product_ProductController_OPERATION_CRUD(后缀字段ParameterString.OPERATION_AUTHORITY)
     */
    @Autowired
    private TableService tableSerivce;
    @Autowired
    private SysPrivilegeSerivce privilegeSerivce;
    @Autowired
    private UserScopeServer userScopeServer;
    @Autowired
    private SysUnitService unitService;

    @Autowired
    private SysUnitPrivService unitPrivService;

    /**
     * 功能描述: 不区分CRUD
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 15:26
     */
    public String isAuthority(String tableName, String modelName) {
        //操作权限后缀字段 ParameterString.OPERATION_AUTHORITY
        String str = tableName + modelName + ParameterString.OPERATION_AUTHORITY;
        //权限表没有对应的操作权限添加操作权限
        instrPrivId(str, tableName);
        return str;
    }

    /**
     * 功能描述: 区分CRUD
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 15:26
     */
    public String isAuthority(String tableName, String modelName, String type) {
        //操作权限后缀字段 ParameterString.OPERATION_AUTHORITY
        String str = "";
        if (CommonFainl.ADD.equalsIgnoreCase(type)) {
            str = tableName + modelName + CommonFainl.ADD;
        }
        if (CommonFainl.UPDATE.equalsIgnoreCase(type)) {
            str = tableName + modelName + CommonFainl.UPDATE;
        }
        if (CommonFainl.SELECT.equalsIgnoreCase(type)) {
            str = tableName + modelName + CommonFainl.SELECT;
        }
        if (CommonFainl.DELETE.equalsIgnoreCase(type)) {
            str = tableName + modelName + CommonFainl.DELETE;
        }
        instrPrivId(str, tableName);
        return str;
    }

    /**
     * 功能描述:  判断权限表有无权限Id 没有添加
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 15:34
     */
    public void instrPrivId(String str, String tableName) {
        SysPrivilege privilege = privilegeSerivce.OperationAuthorityPriv(str);
        if (privilege == null) {
            TableFile tableFile = new TableFile();
            tableFile.setDatabaseName(CommonFainl.DATABEASNAME);
            //第一个字段必须是表名
            tableFile.setTableName(tableName.split("\\|")[0]);
            SysPrivilege pr = new SysPrivilege();
            //如果要区分增删除改操作调用此函数的重载 添加type 字段
            pr.setPrivId(str);
            //权限类型
            pr.setPrivType(CommonFainl.PRIV_SCOPE_STS);
            //权限范围
            pr.setPrivScp(CommonFainl.PRIV_TYPE_BT);
            pr.setDescription(tableSerivce.findtableName(tableFile).getTableChineseName() + descpe(str));
            privilegeSerivce.addOperationAuthority(pr);
            log.info("添加的权限: " + privilegeSerivce.OperationAuthorityPriv(str));
        }
    }


    /**
     * 判断用户在登录组织有无对应的权限
     */
    public boolean isNotAuthority(SysUser user, String str) {
        int a = 0;
        for (SysPrivilege p : user.getPrivilegeSet()) {
            if (p.getPrivId().equalsIgnoreCase(str)) {
                a = 1;
            }
        }
        return a > 0;
    }
    /**
     *
     * 功能描述: 根据表名和模块名自动生成权限描述
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 11:18
     */
    public boolean isEmptAuthority(String tableName, String modelName, SysUser user) {

        return isNotAuthority(user, isAuthority(tableName, modelName));
    }

    /**
     * 功能描述: 根据表名，操作类名生成操作权限
     * 同时判断用户有无权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/24 9:37
     */
    public Map<String, Object> isPriv(String table, String modelName, SysUser user) {
        Map<String, Object> map = new HashMap<>();
        if (!(this.isEmptAuthority(table, modelName, user))) {
            map.put("bo", false);
            map.put("privId", "尚未获得操作权限:" + this.isAuthority(table, modelName));
        } else {
            map.put("bo", true);
        }
        return map;
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


    /**
     * 功能描述: 生成并检查用户在对应组织有无指定权限
     *
     * @param modelName (权限Id)
     * @param table     (表名 用来添加权限描述)
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 15:18
     */
    public PrivIdExamine isUnitPriv(String table, String modelName, String type, Long unitId, SysUser user) {
        String privId = isAuthority(table, modelName, type);
        return this.isNotAuthority(unitId, user, privId);
    }


    /**
     * 功能描述: 生成并检查用户在对应组织有无指定权限
     *
     * @param privId   (权限Id)
     * @param unitId   (组织Id)
     * @param privName (权限描述)
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 15:18
     */
    public PrivIdExamine isUnitPriv(String privId, String privName, Long unitId, SysUser user) {
        if(privilegeSerivce.OperationAuthorityPriv(privId)==null){
            SysPrivilege pr = new SysPrivilege();
            //如果要区分增删除改操作调用此函数的重载 添加type 字段
            pr.setPrivId(privId);
            //权限类型
            pr.setPrivType(CommonFainl.PRIV_SCOPE_STS);
            //权限范围
            pr.setPrivScp(CommonFainl.PRIV_TYPE_BT);
            pr.setDescription(privName);
            privilegeSerivce.addOperationAuthority(pr);
        }
        return this.isNotAuthority(unitId, user, privId);
    }


    /**
     * 判断用户在对应组织有无对应的权限
     */
    public PrivIdExamine isNotAuthority(Long unitId, SysUser user, String privId) {
        SysUnit unit = unitService.selectByPrimaryKey(unitId);
        //用户权限
        List<SysPrivilege> list = user.getPrivilegeSet();
        //选中组织权限
        List<SysPrivilege> unitlist = unitPrivService.getUnitPriv(unitId);
        if (CollectionUtils.isEmpty(unitlist)) {
            return new PrivIdExamine(privId, false, "组织:" + unit.getUnitName() + " 尚未拥有任何权限");
        } else {
            boolean b = false;
            m:
            for (SysPrivilege s : unitlist) {
                if (s.getPrivId().equalsIgnoreCase(privId)) {
                    b = true;
                    log.info("判断权限Id:"+s.getPrivId());
                    break m;
                }
            }
            if (!b) {
                return new PrivIdExamine(privId, false, "组织:" + unit.getUnitName() + "尚未拥有: '" + privId + "' 权限");
            }
        }
        if (unitId.equals(user.getOwnerId())) {
            //查询当前登录组织权限
            if (CollectionUtils.isNotEmpty(list)) {
                List<SysPrivilege> getList = list.parallelStream().filter(s -> s.getPrivId().equals(privId)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(getList)) {
                    return new PrivIdExamine(privId, true, "");
                }
                return new PrivIdExamine(privId, false, "你在组织:" + unit.getUnitName() + "尚未获得该权限:" + privId);
            }
        }
        //查询用户在其他组织权限
        UnitDomainVo vo = new UnitDomainVo();
        vo.setUnitId(unitId);
        String type = "";
        Set<SysPrivilege> set = userScopeServer.getUnitScopePriv(vo, user, type);
        if (CollectionUtils.isNotEmpty(set)) {
            if (CollectionUtils.isNotEmpty(set.parallelStream().filter(s -> s.getPrivId().equals(privId)).collect(Collectors.toList()))) {
                return new PrivIdExamine(privId, true, "");
            }
        }
        return new PrivIdExamine(privId, false, "你在组织:" + unit.getUnitName() + "尚未获得: '" + privId + "' 权限");
    }


    /**
     * 判断权限是否存在，不存在添加
     */
    public void isPrivId(String privId, String desc) {
        if (privilegeSerivce.OperationAuthorityPriv(privId) == null) {
            SysPrivilege privilege = new SysPrivilege();
            privilege.setPrivId(privId);
            privilege.setDescription(desc);
            privilege.setPrivType("SYSTEM");
            privilege.setPrivScp("OL");
            privilegeSerivce.addOperationAuthority(privilege);
        }
    }

    public boolean isUserPrivId(SysUser user, String privId, String desc) {
        isPrivId(privId, desc);
        if (CollectionUtils.isNotEmpty(user.getPrivilegeSet())) {
            for (SysPrivilege p : user.getPrivilegeSet()) {
                if (p.getPrivId().equalsIgnoreCase(privId))
                    return true;
            }
        }
        return false;
    }

    public boolean isUserPrivId(SysUser user, String privId) {
        if (CollectionUtils.isNotEmpty(user.getPrivilegeSet())) {
            for (SysPrivilege p : user.getPrivilegeSet()) {
                if (p.getPrivId().equalsIgnoreCase(privId))
                    return true;
            }
        }
        return false;
    }

}

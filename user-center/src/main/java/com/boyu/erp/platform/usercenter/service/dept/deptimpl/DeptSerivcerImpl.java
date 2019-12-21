package com.boyu.erp.platform.usercenter.service.dept.deptimpl;

import com.boyu.erp.platform.usercenter.entity.dept.Department;
import com.boyu.erp.platform.usercenter.entity.dept.DeptAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.dept.DepartmentMapper;
import com.boyu.erp.platform.usercenter.mapper.dept.DeptAttrDefMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.service.dept.DeptSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitOwnerSerivcer;
import com.boyu.erp.platform.usercenter.utils.dept.DeptTreeUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.dept.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 类描述:
 *
 * @Description: 部门服务
 * @auther: CLF
 * @date: 2019/9/2 11:45
 */
@Slf4j
@Service
@Transactional
public class DeptSerivcerImpl implements DeptSerivcer {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DeptTreeUtils deptTreeUtils;
    @Autowired
    private DeptAttrDefMapper deptAttrDefMapper;
    @Autowired
    private SysUnitOwnerSerivcer unitOwnerSerivcer;
    @Autowired
    private SysUnitMapper unitMapper;


    @Override
    public List<DepartmentVo> selectDept(DepartmentVo department, SysUser user) {
        List<DepartmentVo> list = departmentMapper.getDepts(department);
        // 封装下级的返回值
        //deptTreeUtils.deptTree(listgoods, department.getOwnerId());
        return list;
    }

    /**
     * 功能描述:
     *
     * @param: 查询单个门店信息
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 11:43
     */
    @Override
    public Department getDepartment(Department dept) {

        return departmentMapper.selectByPrimaryKey(dept.getDeptId());
    }

    /**
     * 功能描述: 增加部门信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 11:21
     */
    @Override
    public int addDept(DepartmentVo department, SysUser user) throws ServiceException {
        Department dept = new Department();
        BeanUtils.copyProperties(department, dept);
        dept.setOprId(user.getUserId());
        dept.setUpdTime(new Date());
        dept.setDeptStatus(CommonFainl.USER_STATUS);
        department.setOprId(user.getUserId());
        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setUnitId(department.getDeptId());
        unitOwner.setOwnerId(department.getOwnerId());
        unitOwner.setDeleted(CommonFainl.FALSE);
        unitOwner.setUnitNum(department.getDeptNum());
        SysUnit unit = new SysUnit();
        BeanUtils.copyProperties(department, unit);
        unit.setUpdTime(new Date());
        unit.setOprId(user.getUserId());
        if (dept.getDeptId() != null) {
            departmentMapper.updateByDept(dept);
            unit.setUnitId(department.getDeptId());
            unitMapper.updateByPrimaryKeySelective(unit);
            updatetUnitOwner(unitOwner);
        } else {
            String unitHierarchy = unitMapper.selectByPrimaryKey(department.getOwnerId()).getUnitHierarchy();
            if (StringUtils.isBlank(unitHierarchy)) {
                throw new ServiceException("403", "部门属主ID为空");
            }
            //新增组织为部门时组织层级 为当前代码
            unit.setUnitHierarchy(unitHierarchy + "|" + department.getUnitCode());
            unit.setCtrlUnitId(department.getOwnerId());
            unitMapper.insertSelective(unit);
            dept.setDeptId(unit.getUnitId());
            departmentMapper.insertDept(dept);
            unitOwner.setUnitId(unit.getUnitId());
            updatetUnitOwner(unitOwner);
        }
        return 1;
    }

    /**
     * 功能描述:
     *
     * @param: 修改部门
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 15:18
     */
    @Override
    public int updateDept(DepartmentVo department, SysUser user) {
        SysUnit unit = new SysUnit();
        BeanUtils.copyProperties(department, unit);
        Department dept = new Department();
        BeanUtils.copyProperties(department, dept);
        unit.setUnitHierarchy(null);
        if (department.getSupDeptId() == 0L) {
            department.setShared(null);
        }
        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setOwnerId(department.getOwnerId());
        unitOwner.setUnitId(department.getDeptId());
        unitOwner.setUnitNum(department.getDeptNum());
        unitOwnerSerivcer.updateSysUnitOwner(unitOwner);
        int a = departmentMapper.updateByDept(dept);
        a += unitMapper.updateByPrimaryKeySelective(unit);
        return a;
    }

    /**
     * 功能描述: 删除部门信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 15:29
     */
    @Override
    public int deleteDept(Department department, SysUser user) {
        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setUnitId(department.getDeptId());
        unitOwner.setOwnerId(department.getOwnerId());
        unitOwnerSerivcer.deleteUnitOwner(unitOwner);
        if (0L == department.getSupDeptId()) {
            department.setSupDeptId(null);
        }
        department.setDeptStatus(CommonFainl.FILAN_STATUS);
        return departmentMapper.updateByDept(department);
    }

    @Override
    public List<DeptAttrDef> selectDeptAttrDef() {
        return deptAttrDefMapper.selectList();
    }

    @Override
    public int addDeptAttrDef(DeptAttrDef mode) {
        return deptAttrDefMapper.insertSelective(mode);
    }

    @Override
    public int updateDeptAttrDef(DeptAttrDef mode) {
        return deptAttrDefMapper.updateByPrimaryKeySelective(mode);
    }

    @Override
    public int deleteDeptAttrDef(DeptAttrDef mode) {
        return deptAttrDefMapper.deleteByPrimaryKey(mode.getAttrType());
    }

    private void updatetUnitOwner(SysUnitOwner unitOwner) {
        if (unitOwnerSerivcer.getUnitOwner(unitOwner) != null) {
            unitOwnerSerivcer.updateSysUnitOwner(unitOwner);
        } else {
            unitOwnerSerivcer.addSysUnitOwner(unitOwner);
        }
    }

}

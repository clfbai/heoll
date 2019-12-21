package com.boyu.erp.platform.usercenter.utils.dept;

import com.boyu.erp.platform.usercenter.vo.dept.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DeptTreeUtils {
    /**
     * 功能描述:
     *
     * @param: ownerId (部门属主Id)
     * @return:
     * @auther: CLF
     * @date: 2019/9/2 11:53
     */
    public List<DepartmentVo> deptTree(List<DepartmentVo> list, Long OwnerId) {
        List<DepartmentVo> setList = new ArrayList<>();
        for (DepartmentVo vo : list) {
            if (vo.getDeptLvl() == 1L) {
                setList.add(vo);
            }
        }
        if (CollectionUtils.isEmpty(setList)) {
            return setList;
        }
        for (DepartmentVo vo : setList) {
           //vo.setTreeDepts(setTree(vo, listgoods));
        }
        return setList;
    }

    private List<DepartmentVo> setTree(DepartmentVo dept, List<DepartmentVo> list) {
        List<DepartmentVo> chinkList = new ArrayList<>();
        for (DepartmentVo vo : list) {
            if (vo.getSupDeptId() != null && vo.getSupDeptId().equals(dept.getDeptId())) {
                chinkList.add(vo);
              //dept.setLast(false);
            }
        }
        if (CollectionUtils.isEmpty(chinkList)) {
           //dept.setLast(true);
            return new ArrayList<>();
        }
        for (DepartmentVo vo : chinkList) {
            setTree(vo, list);
        }
        return chinkList;
    }
}

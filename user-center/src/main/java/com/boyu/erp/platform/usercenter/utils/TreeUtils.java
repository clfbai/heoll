package com.boyu.erp.platform.usercenter.utils;

import com.boyu.erp.platform.usercenter.entity.system.SysMenuDtl;
import com.boyu.erp.platform.usercenter.vo.system.TreeModel;
import com.boyu.erp.platform.usercenter.vo.system.TreeVo;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeUtils {

    /**
     * 注:根目录为的 tParnNodeId为0
     */
    public static List<TreeVo> getTree(List<TreeVo> menuDtls) {
        List<TreeVo> list = new ArrayList<>();
        for (TreeVo m : menuDtls) {
            if (m.getParnNodeId() == 0) {
                //获取根节点
                list.add(m);
            }
        }
        for (TreeVo entity : list) {
            /**
             *步骤1.  递归给根节点 TreeVo集合赋值
             * */
            entity.setSetTreeVo(getChildNodes(entity.getNodeId(), menuDtls));
        }
        return list;
    }

    /**
     * nodeId 上级节点的 Id
     * menuDtls 用户拥有的目录集合
     */
    public static List<TreeVo> getChildNodes(Long nodeId, List<TreeVo> menuDtls) {
        //上级节点 TreeVo 集合
        List<TreeVo> childList = new ArrayList<>();
        for (TreeVo typeEntity : menuDtls) {
            //判断是否有上级节点 且 上级节点不为根节点（根节点录Id为0）
            if (typeEntity.getParnNodeId() > 0) {
                if (nodeId.equals(typeEntity.getParnNodeId())) {
                    //添加  上级节点 TreeVo 集合
                    childList.add(typeEntity);
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (TreeVo entity : childList) {
            /**
             *重复步骤1.
             * */
            entity.setSetTreeVo(getChildNodes(entity.getNodeId(), menuDtls));
        }
        return childList;
    }


    /**
     * 查询指定节点下所有目录及节点
     */
    public static List<SysMenuDtl> getByTreeNodeId(TreeModel menuDtl, List<SysMenuDtl> menuTree) {
        List<SysMenuDtl> list = new ArrayList<>();
        List<SysMenuDtl> returnList = new ArrayList<>();
        for (SysMenuDtl m : menuTree) {
            if (menuDtl.getNodeId().equals(m.getParnNodeId())) {
                list.add(m);
            }
        }

        if (list.size() == 0) {
            return null;
        }

        for (SysMenuDtl m : list) {
            setSysMenu(m.getNodeId(), menuTree, returnList);
        }
        list.addAll(returnList);
        return list;
    }

    private static Object setSysMenu(Long nodeId, List<SysMenuDtl> menuTree, List<SysMenuDtl> returnList) {
        List<SysMenuDtl> text = new ArrayList<>();
        for (SysMenuDtl m : menuTree) {
            if (nodeId .equals(m.getParnNodeId())) {
                returnList.add(m);
                text.add(m);
            }
        }
        if (text.size() == 0) {
            return null;
        }
        for (SysMenuDtl m : text) {
            setSysMenu(m.getNodeId(), menuTree, returnList);
        }
        return null;
    }


}

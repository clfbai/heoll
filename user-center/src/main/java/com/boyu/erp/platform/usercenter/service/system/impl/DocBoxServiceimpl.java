package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.basic.DocBox;
import com.boyu.erp.platform.usercenter.mapper.basic.DocBoxMapper;
import com.boyu.erp.platform.usercenter.service.system.DocBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname DocBoxServiceimpl
 * @Description TODO
 * @Date 2019/9/16 15:26
 * @Created by wz
 * 单据实体箱
 */
@Service
@Transactional
public class DocBoxServiceimpl implements DocBoxService {

    @Autowired
    private DocBoxMapper docBoxMapper;

    /**
     * 重启单据和实体单关联
     * @param box
     * @return
     */
    @Override
    public int reattach(DocBox box) {
        List<DocBox> boxList = docBoxMapper.selectByList(box);
        if(boxList!=null && boxList.size()>0){
            for (DocBox docBox:boxList) {
                docBox.setClosed("F");
                if(!docBox.getWarehId().equals(0)){
                    //添加装箱未决库存   预留

                }
            }
        }
        return 0;
    }
}

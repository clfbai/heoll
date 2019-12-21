package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.brand.BgDtl;
import com.boyu.erp.platform.usercenter.mapper.brand.BgDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.BgMapper;
import com.boyu.erp.platform.usercenter.model.BgDtlModel;
import com.boyu.erp.platform.usercenter.service.system.BgDtlService;
import com.boyu.erp.platform.usercenter.vo.BgVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Classname BgDtlServiceImpl
 * @Description TODO
 * @Date 2019/5/7 11:24
 * @Created by js
 */
@Service
@Transactional
public class BgDtlServiceImpl implements BgDtlService {

    @Autowired
    private BgDtlMapper bgDtlMapper;

    @Autowired
    private BgMapper bgMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BgDtl> selectAll(BgDtl dtl) {
        return bgDtlMapper.selectAll(dtl);
    }

    @Override
    public int insert(BgDtl record) {
        return bgDtlMapper.insert(record);
    }

    /**
     * 添加
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(BgDtl record) {
        if (!record.getList().isEmpty()) {
            record.getList().stream().forEach(s -> addLmabda(record.getBgId(), s));
            return record.getList().size();
        }
        return bgDtlMapper.insertSelective(record);
    }

    public void addLmabda(String b, BgDtl b2) {
        b2.setBgId(b);
        bgDtlMapper.insertSelective(b2);
    }

    /**
     * 删除
     *
     * @param dtl
     * @return
     */
    @Override
    public int deleteBgDtl(BgDtl dtl) {
        return bgDtlMapper.deleteBgDtl(dtl);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @Override
    public int updateBgDtl(BgVo vo) {
        int count = 0;
        vo.setUpdTime(new Date());
        //需要添加分组
        if (StringUtils.isNotBlank(vo.getBgId())) {
            Bg b = new Bg();
            b.setBgId(vo.getBgId());
            List<BgVo> bgVos = bgMapper.selectAll(b);
            if (!CollectionUtils.isNotEmpty(bgVos)) {
                BeanUtils.copyProperties(vo, b);
                bgMapper.insert(b);
            }
        }
        //品牌明细
        for (BgDtl dtl : vo.getAddBgDtl()) {
            dtl.setBgId(vo.getBgId());
            bgDtlMapper.insert(dtl);
            count++;
        }
        for (BgDtl dtl : vo.getDeleteBgDtl()) {
            dtl.setBgId(vo.getBgId());
            bgDtlMapper.deleteBgDtl(dtl);
            count++;
        }

        return count;
    }

    /**
     * 修改品牌分组(bg_dtl)表
     *
     * @param model
     * @return
     */
    @Override
    public int updateBgAndBgDtl(BgDtlModel model) {
        return bgDtlMapper.updateBgDtl(model);
    }


}

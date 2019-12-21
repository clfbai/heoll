package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaDrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaRtrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaTrService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.BaseScopeUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.ChaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.OptionByPsaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PsaServiceimpl
 * @Description TODO
 * @Date 2019/6/21 10:23
 * @Created wz
 * 采购协议
 */
@Service
@Transactional
public class PsaServiceimpl implements PsaService {

    private final String tableName = "PSA";
    @Autowired
    private PsaMapper psaMapper;
    @Autowired
    private ChaMapper chaMapper;
    @Autowired
    private PsaDrMapper psaDrMapper;
    @Autowired
    private PsaRtrMapper psaRtrMapper;
    @Autowired
    private PsaTrMapper psaTrMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PsaDrService psaDrService;
    @Autowired
    private PsaRtrService psaRtrService;
    @Autowired
    private PsaTrService psaTrService;


    /**
     * 采购协议查询
     * @param psa
     * @param page
     * @param size
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<PsaVo> selectVdeAll(PsaVo psa, Integer page, Integer size, SysUser user) throws ServiceException{

        List<PsaVo> psaList = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page,size);
            psaList = psaMapper.selectALLByVde(psa);
        }else{
            PageHelper.startPage(page,size);
            psaList = psaMapper.selectUserListByVde(psa);
        }

        PageInfo<PsaVo> pageInfo=new PageInfo<PsaVo>(psaList);
        return pageInfo;
    }

    /**
     * 分页查询销售协议
     * @param psa
     * @param page
     * @param size
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<PsaVo> selectVdrAll(PsaVo psa, Integer page, Integer size, SysUser user) throws ServiceException {
        List<PsaVo> psaList = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page,size);
            psaList = psaMapper.selectALLByVdr(psa);
        }else{
            PageHelper.startPage(page,size);
            psaList = psaMapper.selectUserListByVdr(psa);
        }

        PageInfo<PsaVo> pageInfo=new PageInfo<PsaVo>(psaList);
        return pageInfo;
    }

    /**
     * 添加采购协议
     * @param psaVo
     * @return
     * @throws ServiceException
     */
    @Override
    public int insertPsa(PsaVo psaVo,SysUser user) throws ServiceException{
        //将当前操作员放入对象中
        psaVo.setOprId(user.getPrsnl().getPrsnlId() + "");
        List<Psa> psa = psaMapper.selectByPrimaryKey(new PsaVo(psaVo.getVenderId(),psaVo.getVendeeId(),psaVo.getPsaCtlr()));

        if(CollectionUtils.isNotEmpty(psa))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"协议已存在！");
        }

        SysParameter par = parameterMapper.findById("PSA_SEPARATED_ALLOWED");

        if(par.getParmVal().equals("F")){
            //判断参数去判断该供应商是否已签订协议
            psa = psaMapper.selectByDifference(new PsaVo(psaVo.getVenderId(),psaVo.getVendeeId(),psaVo.getPsaCtlr()));

            if(CollectionUtils.isNotEmpty(psa))
            {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"供应商与采购商之间，不允许存在不同协议控制方的购销协议！");
            }
        }

        //创建渠道协议
        if(psaVo.getChaVo()!=null){
            chaMapper.insertByPsa(psaVo);
        }

        if(psaVo.getTrList()!=null && psaVo.getTrList().size()>0){
            psaTrService.insertByPsa(psaVo);
        }
        if(psaVo.getDrList()!=null && psaVo.getDrList().size()>0){
            psaDrService.insertByPsa(psaVo);
        }
        if(psaVo.getRtrList()!=null && psaVo.getRtrList().size()>0){
            psaRtrService.insertByPsa(psaVo);
        }

        //判断

        return psaMapper.insertPsa(psaVo);
    }

    /**
     * 修改采购协议
     * @param psaVo
     * @return
     * @throws ServiceException
     */
    @Override
    public int updatePsa(PsaVo psaVo, SysUser user) throws ServiceException
    {
        psaVo.setOprId(user.getPrsnl().getPrsnlId() + "");
        ChaVo cha = psaVo.getChaVo();
        if(cha!=null){
            if(cha.getAuthMode()!=null||cha.getManMode()!=null||cha.getAuthArea()!=null
                    ||cha.getLicFee()!=null||cha.getRtPrlRefId()!=null||cha.getStlDlyDays()!=null){
                chaMapper.updateByPsa(psaVo);
            }
        }
        return psaMapper.updatePsa(psaVo);
    }

    /**
     * 删除采购协议
     * @param psaVo
     * @return
     * @throws ServiceException
     */
    @Override
    public int deletePsa(PsaVo psaVo) throws ServiceException
    {
        chaMapper.deleteByPsa(psaVo);
        psaDrMapper.deleteByPsa(psaVo);
        psaRtrMapper.deleteByPsa(psaVo);
        psaTrMapper.deleteByPsa(psaVo);

        return psaMapper.deletePsa(psaVo);
    }

    /**
     * 采购中查询是否存在协议
     * @param vo
     * @return
     */
    @Override
    public OptionByPsaVo selectByPsaByVde(OptionByPsaVo vo) {
        return  psaMapper.selectByPsaByVde(vo);
    }

    /**
     * 销售中查询是否存在协议
     * @param vo
     * @return
     */
    @Override
    public OptionByPsaVo selectByPsaByVdr(OptionByPsaVo vo) {
        return  psaMapper.selectByPsaByVdr(vo);
    }

    /**
     * 判断
     * @param venderId
     * @param vendeeId
     * @param user
     * @return
     */
    @Override
    public int judge(String venderId,String vendeeId, SysUser user) {
        if(StringUtils.isNotEmpty(venderId)){
            List<Psa> psa = psaMapper.selectByPrimaryKey(new PsaVo(venderId,user.getDomain().getUnitId()+"","E"));
            if(psa!=null && psa.size()>0){
                //参照者不为空
                if(StringUtils.isNotEmpty(psa.get(0).getPuPrlRefId().toString())){
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "该供应商有价格参照者，定价无效！");
                }
            }else{
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "该供应商的协议控制方不为采购商，无权定价！");
            }
        }else{
            List<Psa> psa = psaMapper.selectByPrimaryKey(new PsaVo(user.getDomain().getUnitId()+"",vendeeId,"R"));
            if(psa!=null && psa.size()>0){
                //参照者不为空
                if(StringUtils.isNotEmpty(psa.get(0).getSlPrlRefId().toString())){
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "该采购商有价格参照者，定价无效！");
                }
            }else{
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "该采购商的协议控制方不为供应商，无权定价！");
            }
        }

        return 1;
    }
}

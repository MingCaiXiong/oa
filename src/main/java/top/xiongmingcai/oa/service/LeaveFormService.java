package top.xiongmingcai.oa.service;

import top.xiongmingcai.oa.dao.AdmLeaveFormDao;
import top.xiongmingcai.oa.entity.AdmLeaveForm;
import top.xiongmingcai.oa.utils.MyBatisUtils;

import java.util.List;

public class LeaveFormService implements AdmLeaveFormService{
    @Override
    public AdmLeaveForm queryById(Long formId) {
        return null;
    }

    @Override
    public List<AdmLeaveForm> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public AdmLeaveForm insert(AdmLeaveForm admLeaveForm) {

        return null;
    }

    @Override
    public AdmLeaveForm update(AdmLeaveForm admLeaveForm) {
        return null;
    }

    @Override
    public boolean deleteById(Long formId) {
        return false;
    }
}

package com.device.manage.services;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.DepartModel;
import com.device.manage.repository.DepartRepository;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 */
@Service
public class DepartService {
    @Autowired
    private DepartModel departModel;
    @Autowired
    private DepartRepository departRepository;

    /**
     * 增加部门
     *
     * @param departModel
     * @throws SelfExcUtils
     */
    public void addDepart(DepartModel departModel) throws SelfExcUtils {
        checkRepeat(departModel.getDepart());
        departRepository.save(departModel);
    }

    /**
     * 避免重复添加部门
     *
     * @param depart
     * @throws SelfExcUtils
     */
    private void checkRepeat(String depart) throws SelfExcUtils {
        DepartModel departModel = departRepository.findByDepart(depart).orElse(null);
        if (departModel != null) {
            throw new SelfExcUtils(400, "部门已存在，无需重复添加");
        }
    }

    /**
     * 获得分页数据
     *
     * @param pageable
     * @return
     */
    public Page<DepartModel> findAll(Pageable pageable) {
        return departRepository.findAll(pageable);
    }

    /**
     * 根据部门的名称查找部门
     *
     * @param query
     * @return
     */
    public List<DepartModel> search(String query) {
        return departRepository.search(query);
    }

    /**
     * 删除指定id的部门
     *
     * @param id
     * @return
     */
    public ResponseAspect delete(Integer id) {
        departModel = departRepository.findById(id).orElse(null);
        if (departModel == null) {
            return ResponseUtils.error(400, "部门不存在，无法进行删除操作");
        } else {
            departRepository.deleteById(id);
            return ResponseUtils.success("删除成功", null);
        }
    }

    /**
     * 更新指定的部门名称
     * @param model
     * @return
     */
    public ResponseAspect update(DepartModel model) {
        departModel = departRepository.findById(model.getId()).orElse(null);
        if (departModel == null) {
            return ResponseUtils.error(400, "部门不存在，无法进行更新操作");
        } else {
            departRepository.save(model);
            return ResponseUtils.success("修改成功", null);
        }
    }

    /**
     * 设置数据的组装格式
     *
     * @param departs
     * @return
     */
    public Map<Integer, Object> formateData(List departs) {
        Map<Integer, Object> results = new HashMap<>();
        DepartModel model;
        int index = 0;
        for (Object depart : departs) {
            Map<String, Object> result = new HashMap<>();
            model = (DepartModel) depart;
            result.put("id", model.getId());
            result.put("depart", model.getDepart());
            results.put(index, result);
            index++;
        }
        return results;
    }
}

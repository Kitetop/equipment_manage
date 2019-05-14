package com.device.manage.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/05/13
 */
final public class PageUtils {

    private Integer page;
    private Integer limit;
    private Boolean sort = false;

    /**
     * 默认的构造函数
     */
    public PageUtils() {
    }

    /**
     * 设置默认的页数、页面条数以及是否进行分页
     *
     * @param page
     * @param limit
     */
    public PageUtils(Integer page, Integer limit, Boolean sort) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
    }

    /**
     * 重载构造函数，不进行排序
     *
     * @param page
     * @param limit
     */
    public PageUtils(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    /**
     * 验证传递过来的数据是否合理
     * @param page
     * @param limit
     */
    public void checkData(String page, String limit) {
        try {
            Integer p = Integer.parseInt(page);
            Integer l = Integer.parseInt(limit);
            if (p < 1 || l < 1) {
                throw new SelfExcUtils(400, "非法的页码、条数限制");
            } else {
                this.page = p;
                this.limit = l;
            }
        } catch (NumberFormatException e) {
            throw new SelfExcUtils(400, e.getMessage());
        }
    }

    /**
     * 获得分页的根据
     *
     * @param sort
     * @return
     */
    public Pageable getPageable(Sort sort) {
        Pageable pageable = new PageRequest(this.page - 1, this.limit, sort);
        return pageable;
    }

    /**
     * 重载函数，获得不排序的分页设置
     * @return
     */
    public Pageable getPageable() {
        Pageable pageable = new PageRequest(this.page - 1, this.limit);
        return pageable;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getSort() {
        return sort;
    }

    public void setSort(Boolean sort) {
        this.sort = sort;
    }
}

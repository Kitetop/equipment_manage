package com.device.manage.utils;

import org.springframework.data.domain.Pageable;

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
    public PageUtils() {}
    /**
     * 设置默认的页数、页面条数以及是否进行分页
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
     * @return
     */
    public Boolean validata(Object page, Object limit) {
        if((page instanceof Integer) && (limit instanceof Integer)) {
            if((Integer)page < 1 || (Integer)limit < 1) {
                return false;
            } else {
                this.page = (Integer) page;
                this.limit = (Integer) limit;
                return true;
            }
        } else {
            return false;
        }
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

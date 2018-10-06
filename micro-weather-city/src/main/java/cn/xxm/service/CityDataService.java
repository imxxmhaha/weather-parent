package cn.xxm.service;

import cn.xxm.vo.City;

import java.util.List;

/**
 *
 * @author xxm
 * @create 2018-09-01 13:37
 */
public interface CityDataService {

    /**
     * 获取City 列表
     * @return
     * @throws Exception
     */
    List<City> listCity() throws Exception;
}

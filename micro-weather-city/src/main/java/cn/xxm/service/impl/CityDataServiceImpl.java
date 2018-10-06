package cn.xxm.service.impl;

import cn.xxm.service.CityDataService;
import cn.xxm.utils.XmlBuilder;
import cn.xxm.vo.City;
import cn.xxm.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 13:39
 */
@Service
public class CityDataServiceImpl implements CityDataService {

    /**
     * 获取City 列表
     * @return
     * @throws Exception
     */
    @Override
    public List<City> listCity() throws Exception {
        // 读取xml文件
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(),"UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = br.readLine()) !=null){
            buffer.append(line);
        }

        // XML转为Java对象
        CityList cityList = (CityList) XmlBuilder.xmlStrToObject(CityList.class,buffer.toString());

        return cityList.getCityList();
    }
}

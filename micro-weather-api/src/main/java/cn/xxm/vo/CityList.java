package cn.xxm.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 13:21
 */
@XmlRootElement(name="c")
@XmlAccessorType(XmlAccessType.FIELD)
public class CityList  {


    @XmlElement(name = "d")
    private List<City> cityList;

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}

package cn.xxm.vo;

import com.sun.java.swing.plaf.windows.WindowsTableHeaderUI;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 16:46
 */
@Data
public class WeatherReportVo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String title ;
    private String cityName;
    private List<City> cityList;
    private Weather weather;

    public WeatherReportVo(String title, String cityName, List<City> cityList, Weather weather) {
        this.title = title;
        this.cityName = cityName;
        this.cityList = cityList;
        this.weather = weather;
    }

}

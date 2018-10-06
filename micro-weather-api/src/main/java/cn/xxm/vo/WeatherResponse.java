package cn.xxm.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-08-30 21:17
 */
@Data
public class WeatherResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String time;  //2018-09-23 15:14:44
    private CityInfo cityInfo;
    private String date;  //20180923
    private String  message; //Success !
    private Integer  status; //200
    private Weather data;
}

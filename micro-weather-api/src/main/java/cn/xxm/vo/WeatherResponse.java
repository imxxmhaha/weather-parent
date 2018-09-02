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

    private String date;
    private String  message;
    private Integer  status;
    private String city;
    private Integer count;
    private Weather data;
}

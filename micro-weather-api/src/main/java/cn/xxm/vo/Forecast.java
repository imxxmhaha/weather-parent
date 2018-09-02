package cn.xxm.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 未来天气
 *
 * @author xxm
 * @create 2018-08-30 21:11
 */
@Data
public class Forecast implements Serializable {
    private static final long serialVersionUID = 1L;


    private String date;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private String aqi;
    private String fx;
    private String fl;
    private String type;
    private String notice;


}

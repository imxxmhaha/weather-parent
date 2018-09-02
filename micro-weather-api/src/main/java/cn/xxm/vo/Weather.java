package cn.xxm.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 天气信息
 *
 * @author xxm
 * @create 2018-08-30 21:08
 */
@Data
public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;

    private String shidu;
    private Double pm25;
    private Double pm10;
    private String ganmao;
    private String quality;
    private String  wendu;
    private Yesterday yesterday;
    private List<Forecast> forecast;


}

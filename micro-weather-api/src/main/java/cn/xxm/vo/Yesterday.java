package cn.xxm.vo;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-08-30 21:13
 */
@Data
public class Yesterday implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;
    private String sunrise;
    private String sunset;
    private String aqi;
    private String notice;
    private String high;
    private String fx;
    private String low;
    private String fl;
    private String type;
}

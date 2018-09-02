package cn.xxm.constant;

/**
 * @author xxm
 * @create 2018-09-01 12:55
 */
public class WeatherConstant {
    public static final String QUERY_CITY_LIST_URL = "https://www.sojson.com/open/api/weather/json.shtml";
    public static final String QUERY_WEATHER_URI="https://www.sojson.com/open/api/weather/json.shtml?";   //天气前缀

    public static final String CITYLIST_BASIC_URL = "http://127.0.0.1:7003/getCityList";   //weather-city的获取城市服务接口的url
    public static final String WEATHER_BASIC_URL = "http://127.0.0.1:7001/getWeather/";    //weather-basic的根据天气名称获取天气详情的接口地址前缀
}

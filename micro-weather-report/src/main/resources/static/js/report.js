/**
 * report 页面下拉框时间
 * author:xxm
 */
$(function(){
    $("#selectCityId").change(function(){
        var cityName = $("#selectCityId").val();
        // alert(cityName);
        var url = '/report/getWeather/'+cityName;
        window.location.href = url;
    })
});

/**
 * report 页面下拉框时间
 * author:xxm
 */
$(function(){
    $("#selectCityId").change(function(){
        var cityId = $("#selectCityId").val();
        // alert(cityId);
        var url = '/report/getWeather/'+cityId;
        window.location.href = url;
    })
});

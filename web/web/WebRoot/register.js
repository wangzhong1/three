function fillProvince() {
    $.ajax({
        type: "post",
        url: "queryProvinceCity.do",
        data: {},
        dataType: "json",
        success: function (response) {
            var provinceElement = document.getElementById("province");
            //清除select的所有option
            provinceElement.options.length = 0;
            //增加一个选项
            provinceElement.add(new Option("请选择省份", ""));
            //循环增加其他所有选项
            for (index = 0; index < response.length; index++) {
                provinceElement.add(new Option(response[index].provinceName, response[index].provinceCode))
            }
        }
    })
}

$(document).ready(function () {
    fillProvince(); //调用函数，填充省份下拉框

    $("#province").change(function (e) {
        $("#city").empty();
        $("#city").append($("<option>").val("").text("请选择城市"));
        if ($(this).val() == "") {
            $("#provinceError").css("color", "#c00202");
            $("#provinceError").text("必须选择省份!");
            return;
        }
        province_correct = true;
        $("#provinceError").text("");
        var provinceCode = $("#province").val();
        $ajax({
            type: "post",
            url: "queryProvinceCity.do",
            data: { provinceCode: provinceCode },
            dataType: "json",
            success: function (response) {
                for (index = 0; index < response.length; index++) {
                    var option = $("<option>").val(response[index].cityCode).text(response[index].cityName);
                    $(#city).append(option);
                }
            }
        })
    })
})
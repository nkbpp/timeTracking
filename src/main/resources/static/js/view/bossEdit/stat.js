$(document).ready(function () {

    $("#statusDateS").val(formatDate(new Date()))
    $("#statusDatePo").val(formatDate(new Date()))
    $('.datepicker').datepicker({
        format: 'dd.mm.yyyy',
        language: "ru"
    });

    let itbody = $("body");
    itbody.on('click', 'a', function () {
        if ($(this).attr('id') === "statusBtn") { //Кнопка найти
            ajaxBossEditStat()
        }
    });

});

function getBossEditStatJson() {
    let object = {};
    object['dateS'] = $("#statusDateS").val()
    object['datePo'] = $("#statusDatePo").val()
    return JSON.stringify(object);
}

function ajaxBossEditStat() {
    let stat = $("#statusBtn").attr('name');
    let login = $("#employee").val();
    $.ajax({
        url: "/boss/edit/stat/" + stat + "/" + login,
        data: getBossEditStatJson(),
        type: 'post',
        contentType: "application/json",
        success: function (response) {
            console.log("Успешно")
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractIt tbody').html("");
        }
    });
}
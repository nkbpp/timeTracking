$(document).ready(function () {

    $("#dateS").val(formatDate(new Date()))
    $("#datePo").val(formatDate(new Date()))
    $('.datepicker').datepicker({
        format: 'dd.mm.yyyy',
        language: "ru"
    });
    ajaxBossStat()

    let itbody = $("body");
    itbody.on('click', 'a', function () {
        if ($(this).attr('id') === "statBossFind") { //Кнопка найти
            ajaxBossStat()
        }

        if ($(this).attr('id') === "downloadBtn") { //Кнопка Скачать
            $.ajax({
                url: "/boss/stat/getNameFile",
                data: getBossStatJson(),
                type: 'post',
                contentType: "application/json",
                success: function (data, status, xhr) {
                    let link = document.createElement('a');
                    link.href = data;
                    link.click();
                }
            });
        }

    });

});

function getBossStatJson() {
    let object = {};
    object['dateS'] = $("#dateS").val()
    object['datePo'] = $("#datePo").val()
    return JSON.stringify(object);
}

function ajaxBossStat() {

    let headHtml = "<tr>" +
        "<th>Сотрудник</th>";
    let D = new Date($("#dateS").val().split('.')[2], $("#dateS").val().split('.')[1], $("#dateS").val().split('.')[0])
    let Till = new Date($("#datePo").val().split('.')[2], $("#datePo").val().split('.')[1], $("#datePo").val().split('.')[0])
    let result = []
    while (D.getTime() <= Till.getTime()) {
        result.push('' + padTo2Digits(D.getDate()) + '.' + padTo2Digits(D.getMonth()) + '.' + D.getFullYear());
        headHtml += "<th>" + (padTo2Digits(D.getDate()) + '.' + padTo2Digits(D.getMonth()) + '.' + D.getFullYear()) + "</th>";
        D.setDate(D.getDate() + 1);
    }
    headHtml += "</tr>"
    $("#tableBossStat thead").html(headHtml);
    getSpinnerTable("tableBossStat")

    $.ajax({
        url: "/boss/stat",
        data: getBossStatJson(),
        type: 'post',
        contentType: "application/json",
        success: function (response) {
            //console.log(response)

            let tbody = ""
            new Map(Object.entries(response)).forEach((value, key, map) => {
                tbody += "<tr><td>" + key + "<!--<a href='#'>&#10000;</a>--></td>";
                result.forEach(
                    (item) => {
                        let f = value.find(x => x.currentDate == item)
                        if (f === undefined) {
                            tbody += "<th class='table-secondary'></th>"

                        } else {
                            if (f.businessTrip == true) {
                                tbody += "<th class='table-info'>Командировка</th>"
                            } else if (f.sickLeave == true) {
                                tbody += "<th class='table-info'>Больничный</th>"
                            } else if (f.vacation == true) {
                                tbody += "<th class='table-info'>Отпуск</th>"
                            } else {
                                let time = "";
                                if (f.beginningOfWork == null) {
                                    time += "<div class='row m-0'><div class='col-12  bg-danger'>&nbsp;</div></div>"
                                } else {
                                    time += ("<div class='row m-0'><div class='col-12  bg-success'>" + f.beginningOfWork + "</div></div>")
                                }
                                if (f.endOfWork == null) {
                                    time += "<div class='row m-0'><div class='col-12  bg-danger'>&nbsp;</div></div>"
                                } else {
                                    time += ("<div class='row m-0'><div class='col-12  bg-success'>" + f.endOfWork + "</div></div>")
                                }
                                tbody += ("<th class='p-0 m-0'>" + time + "</th>")
                            }
                        }

                    }
                );
                tbody += "<tr>"
            });

            $("#tableBossStat tbody").html(tbody);
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractIt tbody').html("");
        }
    });
}
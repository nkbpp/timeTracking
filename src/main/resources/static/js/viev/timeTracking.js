$(document).ready(function () {

    //инициализация
    $("#businessTripDateS").val(formatDate(new Date()))
    $("#businessTripDatePo").val(formatDate(new Date()))
    $("#vacationDateS").val(formatDate(new Date()))
    $("#vacationDatePo").val(formatDate(new Date()))
    $("#sickLeaveDateS").val(formatDate(new Date()))
    $("#sickLeaveDatePo").val(formatDate(new Date()))
    $('.datepicker').datepicker({
        format: 'dd.mm.yyyy',
        language: "ru"
    });
    $.ajax({
        url: '/timeTracker/get',
        method: 'post',
        success: function (data) {
            $("#formTimeControl").attr("name", data.id);

            if (data.vacation == true) {
                $("#vacation").prop("checked", true);
                containerStatusInvisible()
                $(".vacation").removeClass("d-none");
            } else if (data.sickLeave == true) {
                $("#sickLeave").prop("checked", true);
                containerStatusInvisible()
                $(".sickLeave").removeClass("d-none");
            } else if (data.businessTrip == true) {
                $("#businessTrip").prop("checked", true);
                containerStatusInvisible()
                $(".businessTrip").removeClass("d-none");
            } else {
                if (data.beginningOfWork != null) {
                    clearInterval(morningInterval);
                    $("#morning").prop("disabled", true);
                    $("label[for=morning]").removeClass("btn-secondary").removeClass("btn-warning").removeClass("btn-danger").addClass("btn-success");
                    $("label[for=morning]").text(data.beginningOfWork);
                }
                if (data.endOfWork != null) {
                    clearInterval(eveningInterval);
                    $("#evening").prop("disabled", true);
                    $("label[for=evening]").removeClass("btn-secondary").removeClass("btn-warning").removeClass("btn-danger").addClass("btn-success");
                    $("label[for=evening]").text(data.endOfWork);
                }
                markerVisible()
            }
        },
        error: function (response) {
            initialToats("Ошибка!!!", response.responseText, "err").show();
        }
    });


    //статусы
    $("body").on('change', '[name=statusRadio]', function () {
        let stat = $(this).attr("id");

        containerStatusInvisible()

        let uuid = $("#formTimeControl").attr("name");

        if (stat == "atwork") {
            markerVisible()
            $.ajax({
                url: '/timeTracker/status/' + stat + '/' + uuid,
                method: 'put',
                success: function (data) {
                    initialToats("Статус изменен успешно", data, "success").show();
                },
                error: function (response) {
                    initialToats("Ошибка!!!", response.responseText, "err").show();
                }
            });
        } else if (stat == "vacation") {
            $(".vacation").removeClass("d-none");
        } else if (stat == "sickLeave") {
            $(".sickLeave").removeClass("d-none");
        } else if (stat == "businessTrip") {
            $(".businessTrip").removeClass("d-none");
        }

    });

    //утренняя отметка
    $("body").on('change', '#morning', function () {
        let uuid = $("#formTimeControl").attr("name");
        $.ajax({
            url: '/timeTracker/timesOfDay/morning/' + uuid,
            method: 'put',
            success: function (data) {
                initialToats("Статус изменен успешно", data, "success").show();
            },
            error: function (response) {
                initialToats("Ошибка!!!", response.responseText, "err").show();
            }
        });
    });

    //вечерняя отметка
    $("body").on('change', '#evening', function () {
        let uuid = $("#formTimeControl").attr("name");
        $.ajax({
            url: '/timeTracker/timesOfDay/evening/' + uuid,
            method: 'put',
            success: function (data) {
                initialToats("Статус изменен успешно", data, "success").show();
            },
            error: function (response) {
                initialToats("Ошибка!!!", response.responseText, "err").show();
            }
        });


    });

    let dateStart = new Date();
    dateStart.setHours(TIMEPARAM.mornBegin.hour);
    dateStart.setMinutes(TIMEPARAM.mornBegin.minute);
    let dateEnd = new Date();
    dateEnd.setHours(TIMEPARAM.mornEnd.hour);
    dateEnd.setMinutes(TIMEPARAM.mornEnd.minute);
    let morningInterval = setInterval(interval, 1000, "morning",
        dateStart,
        dateEnd
    );

    $("body").on('change', '#morning', function () {
        clearInterval(morningInterval);
        $("#morning").prop("disabled", true);
        $("label[for=morning]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-success");
    });

    dateStart = new Date();
    dateStart.setHours(TIMEPARAM.evnBegin.hour);
    dateStart.setMinutes(TIMEPARAM.evnBegin.minute);
    dateEnd = new Date();
    dateEnd.setHours(TIMEPARAM.evnEnd.hour);
    dateEnd.setMinutes(TIMEPARAM.evnEnd.minute);
    let eveningInterval = setInterval(interval, 1000, "evening",
        dateStart,
        dateEnd
    );

    $("body").on('change', '#evening', function () {
        clearInterval(eveningInterval);
        $("#evening").prop("disabled", true);
        $("label[for=evening]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-success");
    });

    function interval(id, dateStart, dateEnd) {
        let cDate = new Date();
        if ($(id).prop('checked')) {
            clearInterval((id == "morning") ? morningInterval : eveningInterval);
            $("label[for=" + id + "]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-success");
            return;
        } else if ((cDate >= dateStart && cDate <= dateEnd)) {
            $("#morning").removeProp("disabled");
            $("label[for=" + id + "]").removeClass("btn-secondary").addClass("btn-warning");
            let d = new Date(dateEnd - cDate);

            $("label[for=" + id + "]").html(
                padTo2Digits(dateEnd.getHours() - cDate.getHours() - 1) + ":" +
                padTo2Digits(d.getMinutes()) + ":" +
                padTo2Digits(d.getSeconds())
            );
            return;
        } else if (cDate < dateStart) {
            $("#" + id).prop("disabled", true);
            $("label[for=" + id + "]").html("Будет доступна с " + padTo2Digits(dateStart.getHours()) + ":" + padTo2Digits(dateStart.getMinutes()) + ":00");
            return;
        } else {
            $("#" + id).prop("disabled", true);
            $("label[for=" + id + "]").html("00:00:00");
            $("label[for=" + id + "]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-danger");
            clearInterval((id == "morning") ? morningInterval : eveningInterval);
            return;
        }
        return;
    }

    $("body").on('click', 'a', function () {

        if ($(this).attr('id') === "businessTripBtn" ||
            $(this).attr('id') === "vacationBtn" ||
            $(this).attr('id') === "sickLeaveBtn") {

            let stat = ($(this).attr("id")).replace("Btn","");

            let object = {};
            object['dateS'] = $("#" + stat + "DateS").val()
            object['datePo'] = $("#" + stat + "DatePo").val()
            let json = JSON.stringify(object);

            let uuid = $("#formTimeControl").attr("name");

            console.log(stat)
            console.log(uuid)
            $.ajax({
                url: "/timeTracker/status/period/" + stat + '/' + uuid,
                data: json,
                type: 'post',
                contentType: "application/json",
                success: function (data) {
                    initialToats("Статус изменен успешно", data, "success").show();
                },
                error: function (response) {
                    initialToats("Ошибка!!!", response.responseText, "err").show();
                }
            });
        }


    });

});

function markerVisible() {
    $(".marker").removeClass("d-none");
}

function containerStatusInvisible() {
    $("#containerStatus").children().addClass("d-none");
}

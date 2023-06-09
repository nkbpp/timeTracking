$(document).ready(function () {

    //инициализация
    $.ajax({
        url: '/timeTracker/get',
        method: 'post',
        success: function (data) {
            $("#formTimeControl").attr("name", data.id);
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

            if (data.vacation == true) {
                $("#vacation").prop("checked", true);
                markerInvisible()
            } else if (data.sickLeave == true) {
                $("#sickLeave").prop("checked", true);
                markerInvisible()
            } else if (data.businessTrip == true) {
                $("#businessTrip").prop("checked", true);
                markerInvisible()
            }

            console.log(data);
        },
        error: function (response) {
            initialToats("Ошибка!!!", response.responseText, "err").show();
        }
    });

    //статусы
    $("body").on('change', '[name=statusRadio]', function () {
        let stat = $(this).attr("id");

        if (stat == "atwork") {
            markerVisible()
        } else {
            markerInvisible()
        }
            let uuid = $("#formTimeControl").attr("name");
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

    let morningInterval = setInterval(interval, 1000, "morning", 8);

    $("body").on('change', '#morning', function () {
        clearInterval(morningInterval);
        $("#morning").prop("disabled", true);
        $("label[for=morning]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-success");
    });

    let eveningInterval = setInterval(interval, 1000, "evening", 16);

    $("body").on('change', '#evening', function () {
        clearInterval(eveningInterval);
        $("#evening").prop("disabled", true);
        $("label[for=evening]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-success");
    });


    function interval(id, checkTime) {
        let hours = new Date().getHours();
        let minutes = 59 - new Date().getMinutes();
        let seconds = 59 - new Date().getSeconds();
        if ($(id).prop('checked')) {
            clearInterval((id == "morning") ? morningInterval : eveningInterval);
            $("label[for=" + id + "]").removeClass("btn-secondary").removeClass("btn-warning").addClass("btn-success");
            return;
        } else if (hours == checkTime) {
            $("#morning").removeProp("disabled");
            $("label[for=" + id + "]").removeClass("btn-secondary").addClass("btn-warning");
            $("label[for=" + id + "]").html(
                ("00") + ":" + ((minutes < 10 ? "0" : "") + minutes) + ":" + ((seconds < 10 ? "0" : "") + seconds));
            return;
        } else if (hours < checkTime) {
            $("#" + id).prop("disabled", true);
            $("label[for=" + id + "]").html("Будет доступна с " + ((checkTime < 10) ? "0" : "") + checkTime + ":00");
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


});

function markerVisible() {
    $("#marker").removeClass("invisible").addClass("visible");
}

function markerInvisible() {
    $("#marker").removeClass("visible").addClass("invisible");
}
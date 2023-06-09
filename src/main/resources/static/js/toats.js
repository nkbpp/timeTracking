let options = {
    animation : true,
    autohide : false,
    delay : 2000,
};

function initialToats(header, message, type) {
    let i = 0;
    let tcs = $(".toast-container .show");
    let tc = $(".toast-container");
    if(tcs.last().attr('id') !== undefined) {
        i = +(tcs.last().attr('id').slice(-1))+1;
    } else {
        tc.html(""); //очистить контейнер
    }
    tc.html(tc.html() + getToats(i))

    //console.log("i = ",i);

    let myToast = $("#myToast"+i);

    const date = new Date();
    $('#myToastTime'+i).text(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
    $('#myToastHeaderMes'+i).text(header);
    $('#myToastMessage'+i).text(message);

    switch (type) {
        case "err":
            myToast.addClass("bg-danger");
            $('#myToastHeader'+i).addClass("bg-danger");
            break;
        case "success":
            myToast.addClass("bg-success");
            $('#myToastHeader'+i).addClass("bg-success");
            break;
    }

    if(message == "Махинация предотвращена!"){
        $('#myToastMessage'+i).html(
            '<img src="images/ahahah.gif" alt="' + message + '">');
    }

    let toast = new bootstrap.Toast(myToast, options);

    //myToastHide();
    myToastHide("#myToast"+i);

    return toast;
}

function myToastHide(myToast){
    setTimeout( function () {
        $(myToast).removeClass("show");
    }, 7000);
}

/*    function myToastHide(){
        setTimeout( function () {
            $(".toast-container .show").first().removeClass("show");
        }, 7000);
    }*/

function getToats(id) {
    return "<div id='myToast" + id + "' class='toast hide' role='alert' aria-live='assertive' aria-atomic='true' style='--bs-bg-opacity: .2;'>" +
        "<div class='toast-header bg-gradient text-white' id='myToastHeader" + id + "'>" +
        "<strong class='me-auto' id='myToastHeaderMes" + id + "' style='--bs-bg-opacity: .3;'></strong>" +
        "<small id='myToastTime" + id + "'></small>" +
        "<button type='button' class='btn-close' data-bs-dismiss='toast' aria-label='Закрыть'></button>" +
        "</div> " +
        "<div class='toast-body' id='myToastMessage" + id + "' >" +
        "</div>" +
        "</div>";
}
function getSpinner() {
    return "<div class='d-flex justify-content-center m-2'>" +
        "<div class='spinner-border' role='status'>" +
        "<span class='visually-hidden'>Загрузка...</span>" +
        "</div>" +
        "</div>";
}

function getSpinnerTable(idTable) { // передать id таблицу
    let ths = "";
    for (let i = 0; i < $('#' + idTable + '').tableCellCount() - 1; i++) {
        ths += "<th></th>"
    }
    $('#' + idTable + ' tbody').html('<tr><th>' + getSpinner() + '</th>' + ths + '</tr>');//спинер
}

function getSpinnerButton() {
    return '<span class="spinner-border spinner-border-sm mx-2" role="status" aria-hidden="true"></span>';
}

$.fn.tableCellCount = function () {
    return $(this).find('tr:first td, tr:first th').get().reduce(function (a, b) {
        return a + (b.colSpan ? parseInt(b.colSpan) : 1);
    }, 0)
}
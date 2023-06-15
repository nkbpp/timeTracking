function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(date) {
    return [
        padTo2Digits(date.getDate()),
        padTo2Digits(date.getMonth() + 1),
        date.getFullYear(),
    ].join('.');
}

function replaceNull(val) {
    return val === null ? "" : val;
}

function replaceNullDecimal(val) {
    return val === null ? 0 : +val;
}

function logObj(obj) { //для вывода обьектов в лог
    let str = "";
    for (k in obj) {
        str += k + ": " + obj[k] + "\r\n";
    }
    console.log(str);
}
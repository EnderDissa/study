window.onload = function (){
    clocksss();
    setInterval("clocksss()", 11000);
}
function clocksss(){
    let date = new Date();
    let hour = date.getHours();
    let min = date.getMinutes();
    let sec = date.getSeconds();
    if(hour < 10) hour = "0" + hour;
    if(min < 10) min = "0" + min;
    if(sec < 10) sec = "0" + sec;
    document.getElementById("h").innerHTML = hour;
    document.getElementById("m").innerHTML = min;
    document.getElementById("s").innerHTML = sec;

}
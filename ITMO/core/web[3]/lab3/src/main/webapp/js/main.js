
var components = {
    x: 0,
    y: null,
    r: 1,
};
drawPoint(components.x,components.y,components.r)

function rSelector(r){
    components.r=r
    drawFig(r)
    console.log("r="+r)
    console.log(components)
}
function ySelector(y){
    components.y=y.replace(",",".");
    drawPoint(components.x,components.y,components.r)
    console.log(components)
}
function xSelector(x){
    components.x=x.replace(",",".");
    drawPoint(components.x,components.y,components.r)
    console.log(components)
}
function drawAllDots(){
    $("#table tr").each(function (){

        //let rowLength = table.rows.length;
        let row = $(this);
        let x = parseFloat(row.find("td:eq(1)").text());
        let y = parseFloat(row.find("td:eq(2)").text());
        let r = parseFloat(row.find("td:eq(3)").text());
        let result = (row.find("td:eq(4)").text() === "Точка попала");
        console.log("x: " + x + " y: " + y +" r: " + r +  " res: " + result);
        drawPointRes(x, y, result);
    })
}
$(document).ready(drawAllDots())
// class CanvasDrawer{
//
//     canvas;
//     ctx;
//     lastR;
//     constructor(canvas) {
//         this.canvas = canvas;
//         this.ctx = canvas.getContext("2d");
//         this.ctx.fillStyle = "cornflowerblue";
//         this.drawAxes();
//     }
//
//     // обрабатывает клик по графику
//     clickDot(event){
//         let loc = this.windowToCanvas(canvas, event.clientX, event.clientY);
//         if(this.lastR == null){
//             alert('выберите радиус сначала');
//         }
//
//         // получаем значения x и y
//         let x = this.xFromCanvas(loc.x);
//         let y = this.yFromCanvas(loc.y);
//         //console.log("x: " + x + " y: " + y);
//
//         // сохраняем значение радиуса в сессии
//         sessionStorage.setItem("lastR", this.lastR);
//
//         //вызываем ремут комманд, передаем данные для точки
//         addDotFromCanvas(
//             [
//                 {name: "x", value: x.toString()},
//                 {name: "y", value: y.toString()},
//                 {name: "r", value: this.lastR.toString()}
//             ]
//         )
//         checkUpdate(); //загружаем первую страницу пагинации
//         updateButtons(); //обновляем кнопки пагинации
//
//         //this.drawArea(this.lastR);
//         updateCan();  //обновляем рисунок
//
//
//     }
//
//     drawDot(x, y, r, result){
//         //  console.log("r=" + r + " lastR=" + this.lastR);
//         x = this.xToCanvas(x);
//         y = this.yToCanvas(y);
//         //console.log("x=" + x + " y=" + y);
//         if(r == this.lastR){
//             if(result) {this.ctx.fillStyle = "green";}
//             else {this.ctx.fillStyle = "red";}
//             this.ctx.fillRect(x, y, 4, 4);
//             this.ctx.fillStyle = "cornflowerblue";
//         }
//     }
//
//
//     //рисует все точки из таблицы на график
//     drawAllDots(){
//         $("#table tr").each(function (){
//             //let rowLength = table.rows.length;
//             let row = $(this);
//             let x = parseFloat(row.find("td:eq(1)").text());
//             let y = parseFloat(row.find("td:eq(2)").text());
//             let r = parseFloat(row.find("td:eq(3)").text());
//             let result = (row.find("td:eq(4)").text() === "Точка попала");
//
//             // console.log("x: " + x + " y: " + y +" r: " + r +  " res: " + result);
//             canvasDrawer.drawDot(x, y, r, result);
//
//         })
//     }
//
//
//     //перерисовывает график - рисует область, оси и все точки
//     drawArea(r){
//         this.lastR = r;
//         //alert(r);
//         sessionStorage.setItem("lastR", this.lastR);
//         this.ctx.clearRect(0,0, 500, 500);
//         r = this.rToCanvas(r);
//         // пругл
//         this.ctx.beginPath();
//         this.ctx.moveTo(250, 250);
//         this.ctx.lineTo(250 - r, 250);
//         this.ctx.lineTo(250 - r,  250 - (r/2) );
//         this.ctx.lineTo(250, 250 - (r/2));
//         this.ctx.lineTo(250, 250);
//         this.ctx.fill();
//         // треугл
//
//         this.ctx.beginPath();
//         this.ctx.moveTo(250, 250);
//         this.ctx.lineTo(250, 250 - r);
//         this.ctx.lineTo(250 + (r/2), 250);
//         this.ctx.lineTo(250, 250);
//         this.ctx.fill();
//         // круг
//         this.ctx.beginPath();
//         this.ctx.arc(250, 250, r, Math.PI/2, Math.PI);
//         this.ctx.lineTo(250, 250);
//         this.ctx.lineTo(250, 250+r);
//         this.ctx.fill();
//
//         this.drawAxes();
//         this.drawAllDots();
//     }
//
//     //рисует оси
//     drawAxes(){
//         this.ctx.beginPath();
//         this.ctx.moveTo(0, 250);
//         this.ctx.lineTo(500, 250);
//         this.ctx.moveTo(250, 0);
//         this.ctx.lineTo(250, 500)
//         this.ctx.stroke();
//     }
//
//     //переводят координаты туда и обратно
//     xToCanvas(x){
//         return (x * 50) + 250;
//     }
//     yToCanvas(y){
//         return 250 - (y * 50);
//     }
//     rToCanvas(r){
//         return (r/5) * 250;
//     }
//     xFromCanvas(x){
//         return (x - 250)/50;
//     }
//     yFromCanvas(y){
//         return (250 - y)/50;
//     }
//     windowToCanvas(canvas, x, y){
//         let bbox = canvas.getBoundingClientRect();
//         return {x: x -bbox.left * (canvas.width / bbox.width),
//             y: y - bbox.top * (canvas.height / bbox.height)
//         };
//     }
// }
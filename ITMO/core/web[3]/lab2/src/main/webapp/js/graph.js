let graph_click_enabled = false;

const elt = document.getElementById('calculator');
elt.addEventListener('click',handleGraphClick);
const calculator = Desmos.GraphingCalculator(elt, {
    keypad: false,
    expressions: false,
    settingsMenu: false,
    invertedColors: false,
    xAxisLabel: 'x',
    yAxisLabel: 'y',
    xAxisStep: 1,
    yAxisStep: 1,
    xAxisArrowMode: Desmos.AxisArrowModes.POSITIVE,
    yAxisArrowMode: Desmos.AxisArrowModes.POSITIVE
});

calculator.setMathBounds({
    left: -4,
    right: 4,
    bottom: -4,
    top: 4
});

let newDefaultState = calculator.getState();
calculator.setDefaultState(newDefaultState);

function drawPointXY(x, y) {
    calculator.setExpression({
        id: x + '' + y,
        latex: '(' + x + ', ' + y + ')',
        color: Desmos.Colors.RED
    });
}
function drawPoint(x, y, r) {
    if (!(x==null || y==null)){
    calculator.setExpression({
        id: 'xy',
        latex: '(' + x + ', ' + y + ')',
        color: Desmos.Colors.YELLOW
    });}
    if (r!=null) {
        drawFig(r);
    }
}


function drawFig(R){
    calculator.setExpression({ id: 'triangle', latex: `\\polygon((${-R/2}, 0), (0, 0), (0, ${R}))`, color: Desmos.Colors.ORANGE,fillOpacity: 0.25});
    calculator.setExpression({ id: 'rectangle', latex: `\\polygon((0, 0), (${R}, 0), (${R}, ${-R}), (0, ${-R}))`, color: Desmos.Colors.ORANGE,fillOpacity: 0.25});
    calculator.setExpression({id: 'circle', latex: `r<=${R} \\{\\pi\\le\\theta\\le\\frac{3*\\pi}{2}\\}`, color: Desmos.Colors.ORANGE,});
}

function drawPointRes(x, y,result) {calculator.setExpression({
    id: x + '' + y,
    latex: '(' + x + ', ' + y + ')',
    color: result ? Desmos.Colors.GREEN : Desmos.Colors.RED
});
}

function clearGraph(){
    calculator.setBlank({})
}
// const graphCanvas = document.getElementById("graph_canvas");
// const canvasRect = graphCanvas.getBoundingClientRect();
// const ctx = graphCanvas.getContext('2d');
//
// const one = 30;
// const width = graphCanvas.width;
// const height = graphCanvas.height;
//
// function redrawGraph() {
//     ctx.beginPath();
//     ctx.clearRect(0, 0, graphCanvas.width, graphCanvas.height);
//
//     //рисуем основные элементы графика
//     drawFunction(choosen.r);
//     drawAxis();
//
//     //рисуем точки из истории с этим радиусом
//     drawPreviousPoints();
//
//     //рисуем все точки выбранные на панели
//     for(let _x in choosen.x) {
//         if(choosen.y != null){
//             drawPoint(choosen.x[_x], choosen.y, 0, 0, 0);
//         }
//     }
//
// }
//
// function drawAxis(){
//     ctx.strokeStyle = 'rgb(80,80,80)';
//     ctx.fillStyle = 'rgb(80,80,80)';
//
//     // Вертикали
//     ctx.moveTo(0, height / 2);
//     ctx.lineTo(width, height / 2);
//     ctx.moveTo(width / 2, 0);
//     ctx.lineTo(width / 2, height);
//     ctx.stroke();
//     ctx.closePath();
//
//     ctx.strokeStyle = 'rgb(0, 0, 0)';
//     ctx.beginPath();
//     let i = 0;
//     let lSize = 6;
//     while (i * one <= width / 2) {
//         ctx.moveTo(width / 2 + i * one, height / 2 + lSize / 2);
//         ctx.lineTo(width / 2 + i * one, height / 2 - lSize / 2);
//         ctx.moveTo(width / 2 + -i * one, height / 2 + lSize / 2);
//         ctx.lineTo(width / 2 + -i * one, height / 2 - lSize / 2);
//         i++;
//     }
//     i = 0;
//     while (i * one <= height / 2) {
//         ctx.moveTo(width / 2 - lSize / 2, height / 2 + i * one);
//         ctx.lineTo(width / 2 + lSize / 2, height / 2 + i * one);
//         ctx.moveTo(width / 2 - lSize / 2, height / 2 + -i * one);
//         ctx.lineTo(width / 2 + lSize / 2, height / 2 + -i * one);
//         i++;
//     }
//
//     ctx.stroke();
//     ctx.closePath();
// }
//
// function drawFunction(r){
//     if (r !== null) {
//         ctx.strokeStyle = 'rgb(233,197,255)';
//         ctx.fillStyle = 'rgb(233,197,255)';
//         ctx.beginPath();
//         let r = choosen.r * one;
//         // Прямоугольник
//         ctx.fillRect(width / 2, height / 2, r, -1*r/2);
//
//         // Треугольник
//         ctx.moveTo(width / 2, height / 2);
//         ctx.lineTo(width / 2, height / 2 - r / 2);
//         ctx.lineTo(width / 2 - r, height / 2);
//
//         // Круг
//         ctx.moveTo(width / 2, height / 2);
//         ctx.arc(width / 2, height / 2, r, -3 * Math.PI / 2, -1 * Math.PI, false);
//
//         ctx.fill();
//         ctx.closePath();
//     }
// }
//
// function drawPoint(x, y, R, r, g, b){
//     x *= one * choosen.r/R;
//     y *= one * choosen.r/R;
//     ctx.beginPath();
//     ctx.fillStyle = "rgb(" + r + "," + g + "," + b + ")";
//     ctx.arc((width / 2) + x, (height / 2) - y, 2, 0, 2 * Math.PI);
//     ctx.fill();
//     ctx.closePath();
// }
//
// function drawPreviousPoints(){
//     pointsContainer.forEach(point =>{
//             if(point[3] === true){
//                 drawPoint(point[0], point[1], point[2], 0, 153, 0);
//             }
//             else{
//                 drawPoint(point[0], point[1], point[2], 153, 0, 0);
//             }
//
//         }
//     );
// }
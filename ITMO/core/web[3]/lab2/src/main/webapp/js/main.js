
var components = {
    x: document.querySelectorAll('button[class="x"]'),
    y: document.getElementById("y-value"),
    r: document.getElementById("r-value"),
    submit: document.getElementById("submit-button")
};
var choosen = {
    x: null,
    y: null,
    r: null
};
updateSubmitLock();
// массив попавших точек для каждого радиуса
var pointsContainer = [];
var resultsTable = document.getElementById("results-content");


$(document).ready(function () {
    console.log("таблица восстановлена из сессии")

    getTableBySession();
    initialize_table(pointsContainer);
    drawPoint(choosen.r);
});

components.x.forEach(element => element.addEventListener("click", setOnClick));
function setOnClick(){
    choosen.x=this.value
    console.log("selected X:",choosen.x)
    updateSubmitLock();
    drawPoint(choosen.x,choosen.y, choosen.r);

    //style
    components.x.forEach(function (element){
        element.classList.remove('selected-btn');
    })
    this.classList.add('selected-btn');
    // this.style.boxShadow = "0 0 40px 5px #f41c52";
    // this.style.transform = "scale(1.05)";
}

components.y.onchange = function checkEnteredY(){
    const yMin = -5.0;
    const yMax = 3.0;
    let y = components.y.value;
    let parsedY;
    choosen.y = null;

    if(isNaN(y.trim()) || !y.match('[\-\+]?([0-5]?.[0-9]*)')){
        components.y.setCustomValidity('пожалуйста, введите корректное значение Y.');
        components.y.reportValidity();
        components.y.classList.remove('correct-inp');
        updateSubmitLock();
        return;
    }
    parsedY = parseFloat(y);
    if (isNaN(parsedY) || yMin > parsedY || parsedY > yMax) {
        components.y.setCustomValidity('значение Y должно быть в пределах от -5 до 3 включительно.');
        components.y.reportValidity();
        components.y.classList.remove('correct-inp');
        updateSubmitLock();
        return;
    }
    choosen.y = parsedY;
    console.log("typed Y:", choosen.y)
    components.y.classList.add('correct-inp');
    updateSubmitLock();
    drawPoint(choosen.x,choosen.y, choosen.r);
    //style
    makeStyled(components.y)
}

components.r.onchange = function checkEnteredR(){
    const rMin = 2.0;
    const rMax = 5.0;
    let r = components.r.value;
    let parsedR;
    choosen.r = null;

    if(isNaN(r.trim()) || !r.match('[\-\+]?([0-5]?.[0-9]*)')){
        components.r.setCustomValidity('пожалуйста, введите корректное значение R.');
        components.r.reportValidity();
        components.r.classList.remove('correct-inp');
        updateSubmitLock();
        return;
    }
    parsedR = parseFloat(r);
    if (isNaN(parsedR) || rMin > parsedR || parsedR > rMax) {
        components.r.setCustomValidity('значение R должно быть в пределах от 2 до 5 включительно.');
        components.r.reportValidity();
        components.r.classList.remove('correct-inp');
        updateSubmitLock();
        return;
    }
    choosen.r=parsedR
    console.log("typed R:", choosen.r)
    components.r.classList.add('correct-inp');
    updateSubmitLock();
    drawPoint(choosen.x,choosen.y, choosen.r);

    //style
    makeStyled(components.r)
}

$('#submit-button').click(async function () {
    let [x, y, r] = validateAndParse(choosen.x, choosen.y, choosen.r)
    let result = validate_values(x, y, r);
    let hit = false
    if (result) {
        await sendForm(x, y, r)
    }
});

$('#clear-button').click(async function (event) {
    console.log("таблица будет очищена!");
    clean_table();
    clearGraph();
    location.reload ()
    // getTableBySession();
});



function handleGraphClick(event){
    if (choosen.r==null||choosen.r===""){
        alert("введите R!")
        return;
    }

    let radius=choosen.r
    console.log(radius)
    const rect=elt.getBoundingClientRect();
    let x=event.clientX-rect.left
    let y=event.clientY-rect.top
    const mathCoordinates = calculator.pixelsToMath({x: x, y: y});
    console.log('выбор точки...');
    console.log(mathCoordinates);
    x=mathCoordinates.x
    y=mathCoordinates.y

    let result = validate_values(x,y, radius);
    if (result) {
        sendForm(x, y, radius)
    }
}
// document.addEventListener('click', (ev)=>this.handleClick(ev));
// function handleClick(event) {
//     let x = event.clientX;
//     let y = event.clientY;
//     let one = 30;
//
//     if (x > canvasRect.left && x < canvasRect.right &&
//         y < canvasRect.bottom && y > canvasRect.top) {
//
//         //пересчитываем значения в локальные координаты
//         x = (x - canvasRect.left - (canvasRect.width/2))/one;
//         y = ((canvasRect.height/2) - y + canvasRect.top)/one;
//
//         //console.log("click point = ", x, y);
//         // if(choosen.r !== null){
//         //     sendForm(x.toFixed(3).toString(), y.toFixed(3).toString(), choosen.r);
//         // }
//
//     }
// }

function updateSubmitLock(){
    components.submit.disabled = choosen.x == null || choosen.y == null || choosen.r == null;
    if (!components.submit.disabled){
        components.submit.classList.add('toggled_s-btn');
    }
    else{
        components.submit.classList.remove('toggled_s-btn');
    }
}

function makeStyled(element){
    //style
    // element.style.boxShadow = "0 0 40px 5px #f41c52";
    // element.style.transform = "scale(1.05)";
}
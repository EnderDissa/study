let xValid = false, yValid = false, rValid = false;
var radioValidValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
var buttonValidValues = [1, 1.5, 2, 2.5, 3];
async function viewTable(){
    fetch(
        'script.php',
        {
            headers: {'Content-Type':"application/json"},
            method: 'POST',
            body: JSON.stringify()
        }
    ).then((res) => {
        return res.json()})
        .then((data) => {
            console.log(data);
            let finalData ="";
            data.forEach((itemData) => {
                var html_data= "<tr><td>"+itemData["x"]+"</td>"+
                    "<td>"+itemData["y"]+"</td>"+
                    "<td>"+itemData["r"]+"</td>"+
                    "<td>"+itemData["time"]+"</td>"+
                    "<td>"+itemData["date"]+"</td>"+
                    "<td>"+itemData["result"]+"</td>"+
                    "</tr>";
                finalData+=html_data;

            });
            document.getElementById("table2").innerHTML = finalData;
        }).catch(err => pass);}
const button1 = document.getElementById("submit_button").onclick = sendReq;
const button2 = document.getElementById("remove_button").onclick = sessClear;

function validateSelection(value, validValues) {
    return validValues.includes(value);
}
let selectedXBtn;
const errorMessageBox = document.getElementById('error-message');
document.addEventListener("DOMContentLoaded",function(){
    const xBtns=document.querySelectorAll('.form__r-btn');
    console.log(xBtns)
    xBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const selectedValue = parseFloat(btn.value);
            xBtns.forEach(otherBtn => {
                console.log("X="+btn.value)
                otherBtn.classList.remove('selected-btn');
            });
            if (selectedValue !== selectedXBtn) {
                if (validateSelection(selectedValue, buttonValidValues)) {
                    btn.classList.add('selected-btn');
                    selectedXBtn = selectedValue;
                    xValid = true;
                    errorMessageBox.textContent = '';
                } else {
                    selectedXBtn = undefined;
                    xValid = false;
                    errorMessageBox.textContent = 'Укажите значение Х.';
                }
            } else {
                btn.classList.remove('selected-btn');
                selectedXBtn = undefined;
                xValid = false;
                errorMessageBox.textContent = 'Укажите значение Х.';
            }
            // redrawGraph(selectedRBtn ? selectedRBtn : "R");
            xValid=true
            toggleSubmitBtn();
        });
    });
})
let selectedYBtn
const yInput = document.querySelector('input[name="y"]');
yInput.addEventListener('input', () => {
    yValid = false;
    console.log(yInput.value)
    if (yInput.value.length > 17) {
        yInput.value = yInput.value.slice(0, 17);
    }

    const regex = /^-*[0-9.,]+$/;
    if (!regex.test(yInput.value)) {
        yInput.setCustomValidity('некорректный ввод!');
        yInput.reportValidity();
        toggleSubmitBtn();
        return;
    }

    const yValue = parseFloat(yInput.value.trim().replace(',', '.'));
    if (isNaN(yValue)) {
        yInput.setCustomValidity('некорректный ввод!');
    } else if (yValue < -3 || yValue > 5) {
        yInput.setCustomValidity('значение должно быть в интервале (-3...5)');
    } else {
        yValid = true;
        selectedYBtn=yValue
        yInput.setCustomValidity('');
    }
    yInput.reportValidity();
    toggleSubmitBtn();
});




let selectedRBtn
document.addEventListener("change",function() {
    const rInput = document.getElementsByName('r');
    //console.log(rInput[0].checked)
    for (var i = 0, iLen = rInput.length; i < iLen; i++) {
        if (rInput[i].checked==true){
            console.log(rInput[i].value)
            rValid=true
            selectedRBtn=parseFloat(rInput[i].value.trim().replace(',', '.'))
        }
    }
    toggleSubmitBtn();
});
// rInput[0].onClick =function() {
//     console.log("123")
//     var r = document.getElementsByName("r");
//     var rchecked = false;
//     for ( var i = 0; i < r.length; i++) {
//         if(r[i].checked) {
//             rchecked = true;
//             break;
//         }
//     }
//     console.log("r went")
//     console.log(rchecked)
//     toggleSubmitBtn();
// }




const submitBtn = document.querySelector('.form__big-btn[type="submit"]');
function toggleSubmitBtn() {
    submitBtn.disabled = !(xValid && yValid && rValid)
    if(xValid && yValid && rValid){
        console.log(selectedXBtn,selectedYBtn,selectedRBtn)
    } 
}
// function formatParams(params) {
//     return "?" + Object
//         .keys(params)
//         .map(function (key) {
//             return key + "=" + encodeURIComponent(params[key])
//         })
//         .join("&")
// }
async function sendReq(){
    console.log("req send")
    let elemntX = selectedXBtn;
    let elemntY = selectedYBtn;
    let elemntR = selectedRBtn;
    const headers ={
        'Content-Type':"application/json; charset=UTF-8"
    }
    if(xValid&&yValid&&rValid){
        var response = await fetch('add.php', {
            headers: headers,
            method: 'POST',
            body: JSON.stringify({
                x: elemntX,
                y: elemntY,
                r: elemntR,
            })
        });
        const data= await response.text().then(function (serverAnswer) {
            document.getElementById("table2").innerHTML = serverAnswer;

        })
        viewTable();
    }
    else
        alert("Введены некорректные данные! Пожалуйста, повторите попытку");
}



viewTable();
async function sessClear(){
    fetch(
        'clear.php', {
            method: 'POST'
        }
    )
    location.reload();
}
// document.addEventListener("DOMContentLoaded", function () {
//     const xBtns = document.getElementsByName('x');
//
//     rBtns.forEach(btn => {
//         btn.addEventListener('click', () => {
//             const selectedValue = parseFloat(btn.value);
//             rBtns.forEach(otherBtn => {
//                 otherBtn.classList.remove('selected-btn');
//             });
//             if (selectedValue !== selectedRBtn) {
//                 if (validateSelection(selectedValue, buttonValidValues)) {
//                     btn.classList.add('selected-btn');
//                     selectedRBtn = selectedValue;
//                     rValid = true;
//                     errorMessageBox.textContent = '';
//                 } else {
//                     selectedRBtn = undefined;
//                     rValid = false;
//                     errorMessageBox.textContent = 'некорректный ввод!';
//                 }
//             } else {
//                 btn.classList.remove('selected-btn');
//                 selectedRBtn = undefined;
//                 rValid = false;
//                 errorMessageBox.textContent = 'некорректный ввод!';
//             }
//             redrawGraph(selectedRBtn ? selectedRBtn : "R");
//             toggleSubmitBtn();
//         });
//     });
// });
// const xSelect = document.getElementsByName('x');
// print(xSelect)
// xSelect.forEach(btn => {
//     btn.addEventListener('click', () => {
//         const selectedValue = parseFloat(btn.value);
//         xSelect.forEach(otherBtn => {
//             otherBtn.classList.remove('selected-btn');
//         });
// xSelect.addEventListener('change', () => {
//     const selectedXSelect = parseFloat(xSelect.value.replace(',', '.'));
//     if (validateSelection(selectedXSelect, radioValidValues)) {
//         xValid = true;
//         xSelect.setCustomValidity('');
//     } else {
//         xValid = false;
//         xSelect.setCustomValidity('некорректный ввод!');
//     }
//     xSelect.reportValidity();
//     toggleSubmitBtn();
// });
//     });});
// const yInput = document.querySelector('input[name="y"]');
// yInput.addEventListener('input', () => {
//     yValid = false;
//     if (yInput.value.length > 17) {
//         yInput.value = yInput.value.slice(0, 17);
//     }
//
//     const regex = /^[0-9.,]+$/;
//     if (!regex.test(yInput.value)) {
//         yInput.setCustomValidity('некорректный ввод!');
//         yInput.reportValidity();
//         toggleSubmitBtn();
//         return;
//     }
//
//     const yValue = parseFloat(yInput.value.trim().replace(',', '.'));
//     if (isNaN(yValue)) {
//         yInput.setCustomValidity('некорректный ввод!');
//     } else if (yValue < -3 || yValue > 5) {
//         yInput.setCustomValidity('значение должно быть в интервале (-3...5)');
//     } else {
//         yValid = true;
//         yInput.setCustomValidity('');
//     }
//     yInput.reportValidity();
//     toggleSubmitBtn();
// });
//
// let selectedRBtn;
// const errorMessageBox = document.getElementById('error-message');
// document.addEventListener("DOMContentLoaded", function () {
//     const rBtns = document.querySelectorAll('.form__r-btn');
//
//     rBtns.forEach(btn => {
//         btn.addEventListener('click', () => {
//             const selectedValue = parseFloat(btn.value);
//             rBtns.forEach(otherBtn => {
//                 otherBtn.classList.remove('selected-btn');
//             });
//             if (selectedValue !== selectedRBtn) {
//                 if (validateSelection(selectedValue, buttonValidValues)) {
//                     btn.classList.add('selected-btn');
//                     selectedRBtn = selectedValue;
//                     rValid = true;
//                     errorMessageBox.textContent = '';
//                 } else {
//                     selectedRBtn = undefined;
//                     rValid = false;
//                     errorMessageBox.textContent = 'некорректный ввод!';
//                 }
//             } else {
//                 btn.classList.remove('selected-btn');
//                 selectedRBtn = undefined;
//                 rValid = false;
//                 errorMessageBox.textContent = 'некорректный ввод!';
//             }
//             redrawGraph(selectedRBtn ? selectedRBtn : "R");
//             toggleSubmitBtn();
//         });
//     });
// });
//
//
//
//
// const submitBtn = document.querySelector('.form__big-btn[type="submit"]');
// function toggleSubmitBtn() {
//     submitBtn.disabled = !(xValid && yValid && rValid)
// }
//
//
// const button1 = document.getElementById("submit_button").onclick = sendReq;
// const button2 = document.getElementById("remove_button").onclick = sessClear;
// let elemntX, elemntY, elemntsR;
//
// async function viewTable(){
//     fetch(
//         'script.php',
//         {
//             headers: {'Content-Type':"application/json"},
//             method: 'POST',
//             body: JSON.stringify()
//         }
//     ).then((res) => {
//         return res.json()})
//         .then((data) => {
//             console.log(data);
//             let finalData ="";
//             data.forEach((itemData) => {
//                 var html_data= "<tr><td>"+itemData["x"]+"</td>"+
//                     "<td>"+itemData["y"]+"</td>"+
//                     "<td>"+itemData["r"]+"</td>"+
//                     "<td>"+itemData["time"]+"</td>"+
//                     "<td>"+itemData["date"]+"</td>"+
//                     "<td>"+itemData["result"]+"</td>"+
//                     "</tr>";
//                 finalData+=html_data;
//
//             });
//             document.getElementById("table").innerHTML = finalData;
//         }).catch(err => alert("Ошибка HTTP " + err.status + ". Повторите попытку позже." + err));}
//
//
// viewTable();
//
// async function sessClear(){
//     fetch(
//         'clear.php', {
//             method: 'POST'
//         }
//     )
//     location.reload();
// }
//
// async function sendReq(){
//     elemntX = document.getElementById("x").value;
//     elemntY = document.getElementById("Y").value;
//     elemntR = document.getElementsByClass("form__r-btn");
//     // var elemntR = 0;
//     // for (var i=0, length = elemntsR.length; i < length; i++){
//     //     if(elemntsR[i].checked)
//     //         elemntR = elemntsR[i].value;
//     // }
//     const headers ={
//         'Content-Type':"application/json; charset=UTF-8"
//     }
//     if(checkX(elemntX)&&checkY(elemntY)&&checkR(elemntR)){
//         var response = await fetch('add.php', {
//             headers: headers,
//             method: 'POST',
//             body: JSON.stringify({
//                 x: elemntX,
//                 y: elemntY,
//                 r: elemntR,
//             })
//         });
//
//         const data= await response.text().then(function (serverAnswer) {
//             document.getElementById("table").innerHTML = serverAnswer;
//
//         }).catch(err => alert("Ошибка HTTP " + err.status + ". Повторите попытку позже." + err));
//
//         viewTable();
//     }
//     else
//         alert("Введены некорректные данные! Пожалуйста, повторите попытку");
// }
// function checkX(data){
//     if(!isNaN(data)){
//         return true;
//     }
//     else{
//         return false;}
//
// }
// function checkY(data){
//     elemntY = document.getElementById("Y").value;
//     let string_y = elemntY;
//     print(elemntY)
//
//     // if(!isNaN(data)&&data>-5&&data<5){
//
//     if(string_y.match(/((-[0-4])|([0-4]))\.([0-9]{50,})/)){
//         return false;
//     }else
//         return true;
//     // }
//     // else{
//     //  return false;}
// }
// function checkR(data){
//     if(data!== 0){
//         return true;}
//     else{
//         return false;}
// }
//
//
//
//

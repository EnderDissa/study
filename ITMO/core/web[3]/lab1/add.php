<?php
require __DIR__ . "/coordinatesValidator.php";
require __DIR__."/areaCheck.php";
@session_start();
if ($_SERVER["REQUEST_METHOD"] !== "POST") {
    http_response_code(405);
    return;
}
$x=0;
$y=0;
$r=0;

header("application/json; charset=UTF-8");

$foo = json_decode(file_get_contents("php://input"),true);

if(!isset($_POST['x'])){
    $x=$foo['x'];

    if(!isset($_POST['x'])){
        $y = $foo['y'];

        if(!isset($_POST['r'])){
            $r =$foo['r'];
// $validator = new CoordinatesValidator($x, $y, $r);
// if ($validator->checkData()) {
            $time = microtime(true) - $_SERVER["REQUEST_TIME_FLOAT"];

            date_default_timezone_set('Europe/Moscow');

            $date = date('Y/m/d h:i:s:a', time());

            $coordStatus = areaCheck::check($x, $y, $r);
            $result = $coordStatus?"Попала":"Не попала";
            $data = ["x"=>$x, "y"=>$y, "r"=>$r, "result"=>$result, "date"=>$date, "time"=>$time];

            array_push($_SESSION["results"], $data);
// }
// else{
//     http_response_code(422);
//     return;
// }
        }}}
?>
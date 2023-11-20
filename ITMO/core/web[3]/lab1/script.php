<?php
@session_start();

if(session_status()==0){
    http_response_code(404);
}

if(!isset($_SESSION["results"])){
    $_SESSION["results"] = array();
}
if ($_SERVER["REQUEST_METHOD"] !== "POST") {
    http_response_code(405);
    return;
}
//  echo "[\n";
//echo "[\n";
// foreach ( as $tableRow) {
//     $encoded_data = $tableRow);
echo json_encode(array_reverse($_SESSION["results"]));
//    if(sizeof($_SESSION["results"])>1&&next($_SESSION["results"])!=null)
//echo ",";

//   file_put_contents('data.json', $encoded_data);
//         echo "{ \"x\": ".$tableRow["x"].",\n";
//         echo " \"y\": ".$tableRow["y"].",\n";
//         echo " \"r\": ".$tableRow["r"].",\n";
//         echo " \"result\": \"".$tableRow["result"]."\",\n";
//         echo " \"date\": \"".$tableRow["date"]."\",\n";
//         // if(sizeof($_SESSION["results"])>1)
//         // echo " \"time\": ".$tableRow["time"]."},\n";

// if(next($_SESSION["results"])==null){
//     echo " \"time\": ".$tableRow["time"]."}\n";
//     echo "]";
// }
// else{
//     echo " \"time\": ".$tableRow["time"]."},\n";
//  }

// echo "]";

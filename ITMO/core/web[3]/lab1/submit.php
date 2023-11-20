<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Denis Berman</title>
    <link rel="icon" href="pics/pic1.png">
    <link rel="stylesheet" href="style.css">
</head>


<body>
<div class="page">
    <header class="header">
        <div class="container">
            <div class="header_title">
                |Берман Денис Константинович|P3233|
            </div>
        </div>
    </header>

    <main class="main">
        <div class="container">
            <h1 class="main_title">WEB №1, вариант 1413</h1>
            <div class="main_row">
                <div class="main_left">
                    <img src="pics/RES.png">
                    <!--                <canvas id="graph"></canvas>-->
                </div>
                <div class="main_right">
                    <form id="form"  method="post">
                        <label class="form_label">введите X:</label>
                        <div class="form_row">
                            <button class="form__r-btn" type="button" value=1>1</button>
                            <button class="form__r-btn" type="button" value=1.5>1.5</button>
                            <button class="form__r-btn" type="button" value=2>2</button>
                            <button class="form__r-btn" type="button" value=2.5>2.5</button>
                            <button class="form__r-btn" type="button" value=3>3</button>
                        </div>
                        <label for="Y"class="form__label">введите Y:</label>
                        <div class="form_row">
                            <input id="Y"required class="form__y-input" name="y" placeholder="Введите Y от -3 до 5" step="any" type="text" value="">
                        </div>
                        <label class="form__label"id="r">введите R</label>
                        <div class="form__row">
                            <input type="radio" id="x1" name="r"value=1>
                            <label for="x1">1</label>
                            <input type="radio" id="x2" name="r"value=1.5>
                            <label for="x2">1.5</label>
                            <input type="radio" id="x3" name="r"value=2>
                            <label for="x3">2</label>
                            <input type="radio" id="x4" name="r"value=2.5>
                            <label for="x4">2.5</label>
                            <input type="radio" id="x5" name="r"value=3>
                            <label for="x5">3</label>
                        </div>

                        <p id="error-message"></p>
                        <div class="form_row">
                            <button class="form__big-btn" disabled type="submit" id="submit_button">Submit</button>
                            <button class="form__big-btn" type="reset" id="clear_button">Clear</button>
                        </div>
                    </form>
                </div>
                <div class="main_table-block">
                    <table class="main_table" id="table">
                        <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Time</th>
                            <th>Work</th>
                            <th>Result</th>
                        </tr>
                        </thead>

                        <tbody class="table" id="table">
                        <?php
                        if ($_SERVER["REQUEST_METHOD"] == "POST") {
                            $startTime = microtime(true);

                            // Retrieve form data
                            $X = $_POST["Xvalue"];
                            $Y = $_POST["Yvalue"];
                            $R = $_POST["Rvalue"];

                            $valid_r=array(1,1.5,2,2.5,3);
                            $valid_x=array(-3,-2,-1,0,1,2,3,4,5);

                            $is_valid = true;

                            if ((trim($R) == "") || (!is_numeric($R)) || (!in_array((float)$R, $valid_r))) {
                                echo "<h2> Not valid R </h2>";

                                $is_valid = false;
                            }

                            if ((trim($X) == "") || (!is_numeric($X)) || (!in_array((float)$X, $valid_x))) {
                                echo "<h2> Not valid X</h2>";

                                $is_valid = false;
                            }

                            if ((trim($Y) == "") || (!is_numeric($Y)) || ($Y < -3)||($Y > 5)) {
                                echo "<h2>Not valid Y</h2>";

                                $is_valid = false;
                            }
                            $rfile = file_get_contents("results.txt", filesize("results.txt"));

                            // Display the submitted data

                            echo $rfile;

                            $wfile = @fopen("results.txt", "a") or die("Unable to append file!");

                            if ($is_valid){
                                $res="idk";

                                if ($X>0 && $Y>0){
                                    $res="miss";

                                }

                                if ($X>=0 && $Y<=0){

                                    if($X<=$R && $Y<=($R/2)){
                                        $res ="hit";
                                    } else {
                                        $res = "miss";
                                    }
                                }
                                if ($X<=0 && $Y <=0){
                                    if($X**2+$Y**2<=$R**2){
                                        $res="hit";
                                    } else {
                                        $res="miss";
                                    }
                                }
                                if ($X<=0 && $Y>=0){// 2y+x<= r
                                    if (2*$Y+$X<=$R){
                                        $res="hit";
                                    }
                                    $res="miss";
                                }

                                $script_time_end = microtime(true);
                                $script_time_res = $script_time_end - $startTime;

                                date_default_timezone_set('Europe/Moscow');
                                $date = date('m/d/Y h:i:s a', time());
                                $new_str = "<tr><td>".$X."</td><td>".$Y."</td><td>".$R."</td><td>".$res."</td><td>".$date."</td><td>".$script_time_res."</td></tr>";
                                echo $new_str;
                                fwrite($wfile, $new_str);
                            }

                            fclose($wfile);
                        }
                        if ($_SERVER["REQUEST_METHOD"]=="GET") {
                            $rfile = file_get_contents("results.txt", filesize("results.txt"));

                            // Display the submitted data

                            echo $rfile;
                        }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
</div>
<footer class="footer">
    <div class="container">
        <div class="footer_text">
            <a class="footer__title" href="https://se.ifmo.ru/courses/web">
                2023 / HERMO
            </a>
        </div>
    </div>
</footer>
<!--        <div class="container">-->
<!--            <div class="box-1"><img src="/pic1.png" alt="123"></div>-->
<!--            <div class="box-2">B</div>-->
<!--            <div class="box-3">C</div>-->
<!--            <div class="box-4">D</div>-->
<!--        </div>-->
</div>
<script src="script.js"></script>
</body>
</html>
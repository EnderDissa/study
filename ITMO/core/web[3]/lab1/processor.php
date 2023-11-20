<!DOCTYPE html>
<html lang="ru">
<body>
<?php

echo "My first PHP script!";
echo "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve form data
    $x = $_POST["Xvalue"];
    $y = $_POST["Yvalue"];
    $r = $_POST["Rvalue"];

    // Display the submitted data
    echo "X: " . $x . "<br>";
    echo "Y: " . $y . "<br>";
    echo "R: " . $r . "<br>";
}

?>

</body>
</html>

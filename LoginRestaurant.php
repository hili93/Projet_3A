<?php
    $con = mysqli_connect("mysql6.000webhost.com", "a4706684_hili", "123hili@", "a4706684_p3Adb");
    
    $email = $_POST["email"];
    $password = $_POST["password"];

    
    $statement = mysqli_prepare($con, "SELECT * FROM Restaurants WHERE email = ? AND password = ? ");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $email, $password, $city);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["name"] = $name;
        $response["email"] = $email;
        $response["password"] = $password;
        $response["city"] = $city;
    }
    
    echo json_encode($response);
?>
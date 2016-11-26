<?php
    $con = mysqli_connect();
    
    $name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];
	$city = $_POST["city"];
	
    $statement = mysqli_prepare($con, "INSERT INTO Restaurants (name, email, password, city) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement,"ssss",$name, $email, $password, $city);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>
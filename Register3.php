<?php
    $con = mysqli_connect();
    
    $name = $_POST["name"];
    $familyName = $_POST["familyName"];
    $email = $_POST["email"];
    $password = $_POST["password"];
	$city = $_POST["city"];
	$restaurant = $_POST["restaurant"];
	$client = $_POST["client"];
	
    $statement = mysqli_prepare($con, "INSERT INTO UsersTable (name, familyName, email, password, city, restaurant, client) VALUES (?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement,"sssssss",$name, $familyName, $email, $password, $city, $restaurant, $client);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>
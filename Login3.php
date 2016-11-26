<?php
    $con = mysqli_connect();
    
    $email = $_POST["email"];
    $password = $_POST["password"];
	$restaurant = $_POST["restaurant"];
	$client = $_POST["client"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM UsersTable WHERE email = ? AND password = ? AND restaurant = ? AND client = ?");
    mysqli_stmt_bind_param($statement, "ssss", $email, $password, $restaurant, $client);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $familyName, $email, $password, $city, $restaurant, $client);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["name"] = $name;
		$response["familyName"] = $familyName;
        $response["email"] = $email;
        $response["password"] = $password;
        $response["city"] = $city;
		$response["restaurant"] = $restaurant;
		$response["client"] = $client;
    }
    
    echo json_encode($response);
?>
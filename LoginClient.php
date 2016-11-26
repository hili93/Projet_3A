<?php
    $con = mysqli_connect();
    
    $email = $_POST["email"];
    $password = $_POST["password"];

    
    $statement = mysqli_prepare($con, "SELECT * FROM Clients WHERE email = ? AND password = ? ");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $familyName, $email, $password, $city);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["name"] = $name;
		$response["familyName"] = $familyName;
        $response["email"] = $email;
        $response["password"] = $password;
        $response["city"] = $city;
    }
    
    echo json_encode($response);
?>
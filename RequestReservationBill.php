<?php
    $con = mysqli_connect();

    $reservationID = $_POST["reservationID"];


    $statement = mysqli_prepare($con, "SELECT Price FROM reservations WHERE reservation_id = ?");
    mysqli_stmt_bind_param($statement, "s", $reservationID);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $Price); 
	
 while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
		$response["Price"] = $Price;
    }
    
    echo json_encode($response);
?>
<?php
    $con = mysqli_connect();

    $resto = $_POST["Token"];


	$st2 = mysqli_query($con, "SELECT restaurant_id FROM Restaurants WHERE email = '$resto'");
    $res2 = mysqli_fetch_array($st2);
	$restaurantID = $res2["restaurant_id"];
	
	echo "RESTO ID = ";
	echo $restaurantID;
	
    $statement = mysqli_query($con, "SELECT client_id FROM reservations WHERE resto_id = ?");

	$response = array();

	
	while($row = mysqli_fetch_assoc($statement)){
		$response[]=$row;
	}		  
    
    echo json_encode($response);
?>
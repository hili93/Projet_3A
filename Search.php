<?php
$con = mysqli_connect("mysql6.000webhost.com", "a4706684_hili", "123hili@", "a4706684_p3Adb");
    
	$city = $_POST["city"];
   
	$statement = mysqli_query($con, "SELECT name FROM Restaurants WHERE city = ? ");
  
    
    $response = array();

	
	while($row = mysqli_fetch_assoc($statement)){
		$response[]=$row;
	}		
    
    
    echo json_encode($response);
?>
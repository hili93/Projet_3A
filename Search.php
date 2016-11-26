<?php
$con = mysqli_connect();
    
	$city = $_POST["city"];
   
	$statement = mysqli_query($con, "SELECT name FROM Restaurants WHERE city = ? ");
  
    
    $response = array();

	
	while($row = mysqli_fetch_assoc($statement)){
		$response[]=$row;
	}		
    
    
    echo json_encode($response);
?>
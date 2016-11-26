<?php
    $con = mysqli_connect();

    $EmailClient = $_POST["EmailClient"];


    $st = mysqli_query($con, "SELECT client_id FROM Clients WHERE email = '$EmailClient'");
    $res = mysqli_fetch_array($st);
    $clientID = $res["client_id"];
	
	
    $statement = mysqli_query($con, "SELECT reservation_id, restoName,Payed FROM reservations WHERE client_id = '$clientID'");

	$response = array();

	
	while($row = mysqli_fetch_assoc($statement)){
		$response[]=$row;
	}		  
    

    echo json_encode($response);
?>
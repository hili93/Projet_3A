<?php
   function send_notification ($tokens, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $tokens,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key = AIzaSyBpDXTzXlgK9s3nPQ9b-w1PMKz95-_zmuY ',
			'Content-Type: application/json'
			);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}
	






    $con = mysqli_connect();
 
	$resto = $_POST["resto"];
    $client = $_POST["client"];

	$st1 = mysqli_query($con, "SELECT client_id FROM Clients WHERE name = '$client'");
    $res1 = mysqli_fetch_array($st1);
	$clientID = $res1["client_id"];
	

	
	$st2 = mysqli_query($con, "SELECT restaurant_id FROM Restaurants WHERE name = '$resto'");
    $res2 = mysqli_fetch_array($st2);
	$restaurantID = $res2["restaurant_id"];
	

    $statement = mysqli_prepare($con, "INSERT INTO reservations (resto_id, client_id) VALUES (?,?)");

    mysqli_stmt_bind_param($statement,"ii",$restaurantID,$clientID);
    mysqli_stmt_execute($statement);
    
    $response = array();
    
    $response["success"] = true; 
    echo json_encode($response);
	
/////////////////////////////////////	
	$con = mysqli_connect() or die("Error connecting");

        //$restaurant_name=$_POST["resto"]


        $st1 = mysqli_query($con, "SELECT restaurant_id FROM Restaurants WHERE name = '$resto'");
        $res1 = mysqli_fetch_array($st1);
	$clientID = $res1["restaurant_id"];
	
        $sql = " Select Token From app_users WHERE client_id = '$clientID'";

	$result = mysqli_query($con,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["Token"];
		}
	}

	mysqli_close($con);

	$message = array("message" => "Click to see your reservations !");
	$message_status = send_notification($tokens, $message);
	//echo $message_status;
?>
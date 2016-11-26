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
	



	$con = mysqli_connect("mysql6.000webhost.com", "a4706684_hili", "123hili@", "a4706684_p3Adb") or die("Error connecting");

        $clientID = $_POST["clientID"];
		$price = $_POST["total"];
		
        $sql = " Select Token From app_users WHERE client_id = '$clientID'";

	$result = mysqli_query($con,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["Token"];
		}
	}

	mysqli_close($con);

	$message = array("message" => "Click to see your bill !");
	$message_status = send_notification($tokens, $message);
	//echo $message_status;
    	

    $statement = mysqli_prepare($con, "UPDATE reservations SET(Price = ?) WHERE client_id = ?");

    mysqli_stmt_bind_param($statement,"i",$price,$clientID);
    mysqli_stmt_execute($statement);
    
    $response = array();
    
    $response["success"] = true; 
    echo json_encode($response);
?>
<?php 

	if (isset($_POST["Token"])) {
		
		   $_uv_Token=$_POST["Token"];

		   $conn = mysqli_connect("mysql6.000webhost.com", "a4706684_hili", "123hili@", "a4706684_p3Adb") or die("Error connecting");

		   $q="INSERT INTO app_users (Token) VALUES ( '$_uv_Token') "
              ." ON DUPLICATE KEY UPDATE Token = '$_uv_Token';";
              
      mysqli_query($conn,$q) or die(mysqli_error($conn));

      mysqli_close($conn);

	}


 ?>
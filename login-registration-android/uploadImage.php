<?php
$con = mysqli_connect("localhost", "root","","login_regiser");
 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 $DefaultId = 0;

 $ImageData = $_POST['image_data'];

 $ImageName = $_POST['image_tag'];

 $ImagePath = "upload/$ImageName.jpg";

 $ServerURL = "yourPath/$ImagePath";

 $InsertSQL = "INSERT INTO images_try (u_path,u_name) values('$ServerURL','$ImageName')";

 if(mysqli_query($conn, $InsertSQL)){

 file_put_contents($ImagePath,base64_decode($ImageData));

 echo "Your Image Has Been Uploaded.";
 }

 mysqli_close($con);
 }else{
 echo "Please Try Again";
 }

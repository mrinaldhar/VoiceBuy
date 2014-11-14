<?php
$link="localhost";
$username="u462282488_vbapp";
$dbname="u462282488_sign";
$passwd="rohith2219";
$fname=$_POST['Firstname'];
$lname=$_POST['Lastname'];
$gen=$_POST['Gender'];
$mob=$_POST['Mobileno'];
$email=$_POST['Email'];
$passm=$_POST['Password'];
	$conn=mysqli_connect($link,$username,$passwd,$dbname);
	if(!$conn)
	{
		die("Connection failed: " . mysqli_connect_error());
	}

	else
	{
	//	echo "Connected successfully</br>";
                //echo $fname.' '.$lname.' '.$gen.' '.$mob.' '.$email.' '.$passm;
		$query="INSERT INTO  Sign_in(Firstname,Lastname,Gender,Mobileno,Email,Password) VALUES('{$fname}','{$lname}','{$gen}','{$mob}','{$email}','${passm}')";
		$result=mysqli_query($conn,$query);
		if($result)
		{
	//		echo "Inserted successfully</br>";
		}
		else
		{
	//		echo 'Error:'.mysqli_error($conn);
		}
	}
	mysqli_close($conn);
?>	
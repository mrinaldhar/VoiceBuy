<?php
$link="localhost";
$username="u462282488_vbapp";
$dbname="u462282488_sign";
$passwd="rohith2219";
$email=$_POST['username'];
	$conn=mysqli_connect($link,$username,$passwd,$dbname);
	if(!$conn)
	{
		die("Connection failed: " . mysqli_connect_error());
	}

	else
	{
               // echo "Connected Successfully"."</br>"."$email"."</br>";
		$query="SELECT * FROM Sign_in WHERE Email='$email'";
                if($query)
                {    
		      $result=mysqli_query($conn,$query);
                      if($result)
                       {$out=mysqli_fetch_assoc($result);
                                 
                        if($out){
                         //  echo "output:  ". $out['Password']."</br>";
		           print json_encode($out);
                       }
                       else{
                               print "{'Email':'','Password':''}";
                           }
                       }
                     
                  
                
                 }
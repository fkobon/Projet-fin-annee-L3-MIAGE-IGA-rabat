<?php

 // PHP variable to store the host address
 $db_host  = "YourDatabaseHost";
 // PHP variable to store the username
 $db_uid  = "userid";
 // PHP variable to store the password
 $db_pass = "password";
 // PHP variable to store the Database name  
 $db_name  = "databaseName"; 
        // PHP variable to store the result of the PHP function 'mysql_connect()' which establishes the PHP & MySQL connection  
 //$db_con = mysql_connect($db_host,$db_uid,$db_pass) or die('could not connect');
 $db_con = mysqli_connect("localhost", "root", "","android") or die(mysql_error());
 //mysql_select_db($db_name);
 $sql = "SELECT * FROM people WHERE  birthyear > '". $_POST["birthyear"]."'";
 $result = mysqli_query($db_con,$sql);

while ($row = mysqli_fetch_array($result)) 
  $output[]=$row;
 print(json_encode($output));
 mysql_close();   
?>


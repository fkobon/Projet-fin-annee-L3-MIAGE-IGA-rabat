<?php
 
/*
 * Following code will list all the data in the db
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/connexion.php';
 
// connecting to db
$db = new CONNEXION_DB();
 $login = $_POST['IDENT_ETUD'];
 //$passwd = $_POST['MOTPASS_ETUD'];
// get all infromation from etudiants table
//$result = mysqli_query($db->connection(),"SELECT nom,username,groupe FROM etudiants where nom='$valeur_nom' AND username='$valeur_username'") or die(mysql_error());
$result = mysqli_query($db->connection(),"SELECT NOM_PROF,DATE_ABS FROM absences a  join professeurs p on a.CIN_PROF = p.CIN_PROF
										join abs_concern ac on a.NUM_ABS = ac.NUM_ABS where ac.NUM_GR = 
										(SELECT NUM_GR FROM etudiant where IDENT_ETUD='$login')") or die(mysql_error());
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    $response["valeurs"] = array();
	
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $ligne = array();
        $ligne["NOM_PROF"] = $row["NOM_PROF"];
		$ligne["DATE_ABS"] = $row["DATE_ABS"];
        // push single row into final response array
        array_push($response["valeurs"], $ligne);
    }
    // success
    $response["success"] = 1;
	
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No data found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>

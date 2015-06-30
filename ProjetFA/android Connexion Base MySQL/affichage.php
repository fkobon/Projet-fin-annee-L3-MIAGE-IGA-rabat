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
 $passwd = $_POST['MOTPASS_ETUD'];
// get all infromation from etudiants table
//$result = mysqli_query($db->connection(),"SELECT nom,username,groupe FROM etudiants where nom='$valeur_nom' AND username='$valeur_username'") or die(mysql_error());
$result = mysqli_query($db->connection(),"SELECT IDENT_ETUD,MOTPASS_ETUD,ID_FILI,NOM_ETUD,PRENOM_ETUD FROM etudiant e join groupes g on e.NUM_GR = g.NUM_GR WHERE IDENT_ETUD='$login' AND MOTPASS_ETUD='$passwd'") or die(mysql_error());
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    $response["valeurs"] = array();
	
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $ligne = array();
        $ligne["IDENT_ETUD"] = $row["IDENT_ETUD"];
        $ligne["MOTPASS_ETUD"] = $row["MOTPASS_ETUD"];
		$ligne["ID_FILI"] = $row["ID_FILI"];
		$ligne["NOM_ETUD"] = $row["NOM_ETUD"];
		$ligne["PRENOM_ETUD"] = $row["PRENOM_ETUD"];
 
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

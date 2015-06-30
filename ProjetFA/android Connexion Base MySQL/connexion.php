 <?php

class CONNEXION_DB {

	
  function __construct() { 
        $this->connection(); // connexion à la base
    }

  function __destruct() {
        $this->fermer(); // fermer la connexion
   }

 function connection() {
        // connexion à la base , ici : "zied" = mon mot de passe
        $connexion = mysqli_connect("localhost", "root", "","iganews") or die(mysql_error());

         // selection de la base

        //$db = mysqli_select_db("android") or die(mysql_error()) or die(mysql_error());

        return $connexion;

    }

  function fermer() {
       mysqli_close($this->connection()); //Fermer la connexion
    }
}

?>
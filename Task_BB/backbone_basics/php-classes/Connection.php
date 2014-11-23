<?php
/* Connection Class */
class Connection {
	
	private $link;

	function __construct($character_set="") {
		$this->link = @mysql_connect("localhost", "root", "");
		mysql_select_db("backbone_basics", $this->link);
		mysql_query("SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'", $this->link);
		return $this->link;
	}

	function Disconnect() {
		mysql_close($this->link);
	}
}
?>
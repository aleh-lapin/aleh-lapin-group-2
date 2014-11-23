<?php
/* BookmarkList Class */
class JSON
{
	private $input;

	function __construct( $input ) {
		$this->input = $input;
	}

	public function toModel() {
		$result = $this->input;

		$line = mysql_fetch_assoc( $result );
		$JSON = json_encode(  array_map('stripslashes', $line) );
		return $JSON;
	}

	public function toCollection() {
		$result = $this->input;

		$JSON = "[";
		
		$num_rows = mysql_num_rows($result);
		for ($i=0; $i<$num_rows; $i++) {
			$line = mysql_fetch_assoc($result);
			$JSON .= json_encode(  array_map("stripslashes", $line) ) . ",";
		}
		if ($i>0) {
			$JSON = substr($JSON, 0, -1) . "]"; 
		}
		else {
			$JSON .= "]";
		}
		
		return $JSON;
	}
}
?>
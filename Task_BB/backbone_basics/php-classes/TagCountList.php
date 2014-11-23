<?php
/* TagCountList Class */
class TagCountList
{

	private $page;
	private $countPerPage;

	function __construct() {
		$this->page = 1;
		$this->countPerPage = 100;
	}

	public function setPage( $page ) {
		$this->page = $page;
	}
	public function setCountPerPage( $countPerPage ) {
		$this->countPerPage = $countPerPage;
	}
	
	private function get() {
	  $query = "select tag, count(tag) as count from tag group by tag order by tag ";
			
		if ($this->page != 0) {
			$query .= "limit ". ($this->page-1) * $this->countPerPage .", ". $this->countPerPage;
		}
		
		return mysql_query($query);
	}

	public function toJSON() {
		return (new JSON($this->get()))->toCollection();
	}

	
}
?>
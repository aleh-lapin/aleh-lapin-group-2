<?php
/* TagList Class */
class TagList
{

	private $page;
	private $countPerPage;
	private $id_bookmark;

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
	public function setIdBookmark( $id_bookmark ) {
		$this->id_bookmark = $id_bookmark;
	}

	private function get() {
	  $query = "select  id_bookmark, tag from tag where id_bookmark = ".$this->id_bookmark." order by tag ";
			
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
<?php
/* BookmarkList Class */
class BookmarkList
{

	private $page;
	private $countPerPage;
	private $tag;

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

	public function setTag( $tag ) {
		$this->tag = $tag;
	}

	private function get() {
	  if ($this->tag == "") {
	  	$query = "select  id, url, title from bookmark order by title ";
	  }
	  else {
	  	$query = "select  id, url, title from bookmark, tag where bookmark.id = tag.id_bookmark and tag.tag = '".$this->tag."' order by title ";
	  }
			
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
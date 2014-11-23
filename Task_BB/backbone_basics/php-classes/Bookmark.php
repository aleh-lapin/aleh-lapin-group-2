<?php
/* Bookmark Class */
class Bookmark
{
		private $id;
		private $url;
		private $title;
		private $tags;

		function __construct() {
				
		}

	public function set_id( $id ) {
			$this->id = $id;
	}

	public function set( $obj ) {
			if (!empty($obj->id)) {
				$this->id = $obj->id;
			}
			$this->url = addslashes($obj->url);
			$this->title = addslashes($obj->title);
			// convert to array
			$this->tags = explode(",", addslashes($obj->tags));
	}

	private function get() {
		$query = "select id,
						 url,
						 title
					from bookmark
					where id = $this->id";
		return mysql_query($query);
	}

	public function toJSON() {
		return (new JSON($this->get()))->toModel();
	}

	// если задан id -> update, othewise -> insert
	public function save() {
		
		if (empty($this->id) or ($this->id == 0)) {
			$query = 'insert into bookmark(url, title)
					values("'.$this->url.'", "'.$this->title.'")';
			mysql_query($query);
			$this->id = mysql_insert_id();

			// save tags
			for($i=0; $i<count($this->tags); $i++) {
				$query = "insert into tag(id_bookmark, tag)  values($this->id, '".$this->tags[$i]."')";
				mysql_query($query);
			}

		}
		else {
			$query = 'update bookmark set
						url = "'.$this->url.'",
						title = "'.$this->title.'"
					where id = '.$this->id;
			mysql_query($query);

			// del tags
			$query = "delete from tag where id_bookmark = ". $this->id;
			mysql_query($query);

			// insert tags
			for($i=0; $i<count($this->tags); $i++) {
				$query = "insert into tag(id_bookmark, tag)  values($this->id, '".$this->tags[$i]."')";
				mysql_query($query);
			}
		}
	}

	public function delete() {
		$query = "delete from tag where id_bookmark =" . $this->id;
		mysql_query($query);

		$query = "delete from bookmark where id = " . $this->id;
		mysql_query($query);
	}
}
?>
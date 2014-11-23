$(function(){

	Backbone.emulateHTTP = true;
	Backbone.emulateJSON = true;

	var app = {};
	app.models = {};
	app.models.BookmarkModel = Backbone.Model.extend({
	
		defaults: {
			id : '',
			url : '',
			title : '',
			tags : ''
		},
		
		validate: function(attribs) {
			if (attribs.url == '') {
				return 'Set an url for your bookmark!';
			}
			if (attribs.title == '') {
				return 'Set a tittle for your bookmark!';
			}
		},
		
		urlRoot: 'api/Bookmark.php',
		
		initialize: function() {
			console.log('BookmarkModel initialize');
		}
	
	});
	
	app.models.TagModel = Backbone.Model.extend({
	});
	
	app.models.TagCountModel = Backbone.Model.extend({
	});
	
	app.collections = {};
	app.collections.BookmarkCollection = Backbone.Collection.extend({

		model: app.models.BookmarkModel,
		url: 'api/BookmarkList.php',
		
		byTag : function(tag) {
			var filtered = this.filter(function(bookmark) {
				var tags = bookmark.get('tags').split(',');
				for (var i = 0; i < tags.length; i++) {
					var match = tags[i].trim() === tag.trim();
					if (match) {
						return true;
					}
				}
				return false;	
				});
			return new app.collections.BookmarkCollection(filtered);
		}
	
	});
	
	app.collections.TagListCollection = Backbone.Collection.extend({
	
		model: app.models.TagModel,
		url: 'api/TagList.php'
	
	});
	
	app.collections.TagCountListCollection = Backbone.Collection.extend({
	
		model: app.models.TagCountModel,
		url: 'api/TagCountList.php'
	
	});
	
	app.views = {};
	app.views.BookmarkView = Backbone.View.extend({
	
		tagName :'li',
		
		events : {
			'click button.edit' : 'edit',
			'click button.delete' : 'delete'
		},
		
		template : _.template($('#bookmark_view').html()),
		
		render: function( event ){
			this.$el.html(this.template(this.model.toJSON()));
			return this;
		},
		
		edit : function() {
			app.globalEvents.trigger('editBookmark',this.model);
		},
		
		delete : function(event) {
			this.model.destroy({data: { id: this.model.get('id')}, processData: true});
			app.globalEvents.trigger('saveBookmark');
		}
	
	});
	
	app.views.BookmarkListView = Backbone.View.extend({
	
		initialize: function(bookmarks) {
			this.bookmarks = bookmarks;
			this.refresh();
		},
		
		filter : function(tag) {
			this.refresh(tag);
		},
		
		render: function() {
			this.refresh();
		},
		
		refresh : function(tag) {
			$("#bookmarkList").html('');
			this.bookmarks.fetch({
					async:false,
					success : function(data) {
					
						for (var i in data.models) {
						
							var bookmarkModel = data.models[i];
							var tagListCollection = new app.collections.TagListCollection;
							tagListCollection.fetch({data: { id_bookmark: bookmarkModel.get('id')}, processData: true, async : false})
							var tags = '';
							for (var j in tagListCollection.models) {
								var tagModel = tagListCollection.models[j];
								tags += tagModel.get('tag') + ',';
							}
							bookmarkModel.set('tags', tags.substring(0, tags.length - 1));
						}
					
					}
				});
			if (tag) {
				this.bookmarks = this.bookmarks.byTag(tag);
			}
			this.bookmarks.each(this.add, this);
		},
		
		add: function(bookmarkModel) {
		  var view = new app.views.BookmarkView({model: bookmarkModel});
		  $("#bookmarkList").append(view.render().el);
		}
		
	});
	
	app.views.BookmarkFormView = Backbone.View.extend({

		el : 'form',
		
		events: {
			'click #btnSave':	'saveBookmark',
			'click #btnClear':   'clearForm'
		},
		
		render: function() {
			$('#form #id').val(this.model.get('id'));
			$('#form #url').val(this.model.get('url'));
			$('#form #title').val(this.model.get('title'));
			$('#form #tags').val(this.model.get('tags'));
		},
		
		saveBookmark : function(event) {
			event.preventDefault();
			this.model.set('id', $('#form #id').val());
			this.model.set('url', $('#form #url').val());
			this.model.set('title', $('#form #title').val());
			this.model.set('tags', $('#form #tags').val());
			console.log('save');
			if (!this.model.isValid()) {
				this.renderAlert(this.model.validationError);
			} else {
				this.model.save();
				app.globalEvents.trigger('saveBookmark');
			}
			
		},
		
		clearForm : function(event) {
			event.preventDefault();
			$('#form #id').val('');
			$('#form #url').val('');
			$('#form #title').val('');
			$('#form #tags').val('');
		},
	
		renderAlert : function(msg) {
			$('#form .alert p').text(msg);
			$('#form .alert').attr('style', '');
		}
	});
	
	app.views.TagCountView = Backbone.View.extend({
	
		tagName :'li',
		
		events: {
			'click a':	'filter'
		},
		
		template : _.template($('#tagcount_view').html()),
		
		render: function( event ){
			this.$el.html(this.template(this.model.toJSON()));
			return this;
		},
		
		filter : function (event) {
			app.globalEvents.trigger('filterBookmark', event.target.text);
		}
	
	});
	
	app.views.TagCountListView = Backbone.View.extend({
	
		initialize: function(tagCounts) {
			this.tagCounts = tagCounts;
			this.refresh();
		},
		
		render: function() {
			this.refresh();
		},
		
		refresh : function() {
			$("#tagCountList").html('');
			this.tagCounts.fetch({async:false});
			this.tagCounts.each(this.add, this);
		},
		
		add: function(ookmarkModel) {
		  var view = new app.views.TagCountView({model: ookmarkModel});
		  $("#tagCountList").append(view.render().el);
		}
		
	});
	
	app.views.BookmarkListContainerView = Backbone.View.extend({
		
		el : 'div',
		
		events : {
			'click a#clearFilter' : 'clearFilter'
		},

		setFilter : function(tag) {
			$('#bookmarkTagFilter').text(tag);
		},
		
		clearFilter : function(event) {
			$('#bookmarkTagFilter').text('None');
			app.globalEvents.trigger('saveBookmark');
		}	
	
	});

	app.routers = {};
	app.routers.Router = Backbone.Router.extend({
		routes : {
			'loadBookmarks' : '_loadBookmarkListCollection'
		},
		_loadBookmarkListCollection : function() {
			Bookmarks.fetch({async:false});
		}
	});

	app.globalEvents = _.extend({}, Backbone.Events);
	
	var bookmarks = new app.collections.BookmarkCollection;
	var bookmarksList = new app.views.BookmarkListView(bookmarks);
	var bookmarkEditModel = new app.models.BookmarkModel;
	var bookmarkForm = new app.views.BookmarkFormView({ model : bookmarkEditModel});
	
	var tagCount = new app.collections.TagCountListCollection;
	var tagCountList = new app.views.TagCountListView(tagCount);
	
	var bookmarkListContainer = new app.views.BookmarkListContainerView;
	
	app.globalEvents.on('saveBookmark', function(){
		bookmarksList.refresh();
		tagCountList.refresh();
	});
	app.globalEvents.on('editBookmark', function(bookmark){
		bookmarkForm.model.set(bookmark.toJSON());
		bookmarkForm.render();
	});
	app.globalEvents.on('filterBookmark', function(tag){
		bookmarkListContainer.setFilter(tag);
		bookmarksList.filter(tag);
	});
	
});
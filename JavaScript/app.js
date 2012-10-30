$(function() {

	Parse.$ = jQuery;

	Parse.initialize("hCUZFiOue9RLNMOOKuC91YVx7zMCTOwVhVSpm9CG",
			"FoT3FhKGxK2BmQxyMBpohb2icTh1yj4W59kjcwmE");
	var PositionObject = Parse.Object.extend("PositionObject"); // New subclass
	// of
	// Parse.Object

	var map = null;
	var mapOptions = {
		zoom : 15,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	initWIMP();

	function initWIMP() {
		$(".add-point-form").bind("submit", function() {
			insert();
		});
		$(".logout").bind("click", function() {
			logout();
		});
		$(".login-form button").bind("click", login);

		$(".signup-form button").bind("click", function() {
			signup();
		});

		if (Parse.User.current()) {
			showApp();
		} else {
			showLogin();
		}
	}

	function login() {
		$(".login-form button").attr("disabled", "disabled");
		var username = $("#login-username").val();
		var password = $("#login-password").val();

		Parse.User.logIn(username, password, {
			success : function(user) {
				showApp();
			},
			error : function(user, error) {
				$(".login-form .error").html(
						"Invalid username or password. Please try again.")
						.show();
				$(".login-form button").removeAttr("disabled");
			}
		});
	}

	function showApp() {
		$(".login").hide();
		$(".logout").show();
		$(".appcontent").show();

		var themap = $("#themap")[0]; // ID-Selector: #
		map = new google.maps.Map(themap, mapOptions);
		update();
	}

	function showLogin() {
		$(".login").show();
		$(".logout").hide();
		$(".appcontent").hide();
		$(".login-form button").removeAttr("disabled");
	}

	function update() {
		var query = new Parse.Query(PositionObject);
		query.descending("createdAt");
		query.equalTo("User", Parse.User.current());
		query.limit(10);
		query
				.find({
					success : function(results) {
						if (map == null)
							return;
						var myLatlng = null;
						var point = null;
						for ( var i = results.length - 1; i >= 0; i--) {
							point = results[i];
							var lat = point.get("lat");
							var lon = point.get("long");
							var createdAt = point.createdAt;

							myLatlng = new google.maps.LatLng(lat, lon);
							new google.maps.Marker({
								position : myLatlng,
								map : map,
								title : createdAt
							});
						}
						map.setCenter(myLatlng);

						var posTxt = $(".position");
						var tmptxt = "The last position of your phone is: <br><br>Latitude: "
								+ point.get("lat")
								+ "<br>Longitude: "
								+ point.get("long")
								+ "<br>Timestamp "
								+ point.createdAt;
						posTxt.html(tmptxt);
					},
					error : function(error) {
					}
				});
	}

	function logout() {
		Parse.User.logOut();
		initWIMP();
	}

	function signup() {
		var self = this;
		var username = this.$("#signup-username").val();
		var password = this.$("#signup-password").val();

		Parse.User.signUp(username, password, {
			ACL : new Parse.ACL()
		}, {
			success : function(user) {
				self.showApp();
			},
			error : function(user, error) {
				self.$(".signup-form .error").html(error.message).show();
				this.$(".signup-form button").removeAttr("disabled");
			}
		});

		this.$(".signup-form button").attr("disabled", "disabled");

		return false;
	}

	function insert() {
		var longitude = parseFloat(this.$("#lon").val());
		var latitude = parseFloat(this.$("#lat").val());

		var positionObject = new PositionObject(); // New Instance of
		// TestObject
		positionObject.set("long", longitude);
		positionObject.set("lat", latitude);
		positionObject.set("ACL", new Parse.ACL(Parse.User.current()));
		positionObject.set("User", Parse.User.current());

		positionObject.save(null, {
			success : function(object) {
				this.$(".add-point-form button").removeAttr("disabled");
				update();
			},
			error : function(model, error) {
				$(".add-point-form .error").html(error.message).show();
				this.$(".add-point-form button").removeAttr("disabled");
			}
		});
		this.$(".add-point-form button").attr("disabled", "disabled");

		return false;
	}
});
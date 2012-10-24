
$(function() {

  Parse.$ = jQuery;
  
  Parse.initialize("hCUZFiOue9RLNMOOKuC91YVx7zMCTOwVhVSpm9CG", "FoT3FhKGxK2BmQxyMBpohb2icTh1yj4W59kjcwmE");
  
  var PositionObject = Parse.Object.extend("PositionObject"); 	// New subclass of Parse.Object
  var positionObject = new PositionObject();		// New Instance of TestObject
  positionObject.set("uid", "12121212121");
  positionObject.set("long", "20.0");
  positionObject.set("lat", "20.0");
  
  positionObject.save(null, {
    success: function(object) {
      $(".success").show();
    },
    error: function(model, error) {
      $(".error").show();
    }
  });
  
});
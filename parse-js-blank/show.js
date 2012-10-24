
$(function() {

  Parse.$ = jQuery;
  
  Parse.initialize("hCUZFiOue9RLNMOOKuC91YVx7zMCTOwVhVSpm9CG", "FoT3FhKGxK2BmQxyMBpohb2icTh1yj4W59kjcwmE");
  var PositionObject = Parse.Object.extend("PositionObject"); 	// New subclass of Parse.Object
  
  var query = new Parse.Query(PositionObject);
  query.equalTo("uid", "12121212121");
  query.find({
    success: function(results) {
      alert("Successfully retrieved " + results.length + " scores.");
    },
    error: function(error) {
      alert("Error: " + error.code + " " + error.message);
    }
  });
  
});
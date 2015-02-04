
// Follow a link

this.get('links.previous')
  .GET()
  .then( function(result) { ... }, function(error) { ... } );


// Post a form

this.get('controls.change_quantity')
  .submit({"quantity": newQty})
  .then( function(result) { ... }, function(error) { ... } );

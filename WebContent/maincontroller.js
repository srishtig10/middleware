app.controller('MainController', ['$scope', function($scope, HttpGetter) { 
	function getParams() {

	    var params = {},
	        pairs = document.URL.split('?')
	               .pop()
	               .split('&');

	    for (var i = 0, p; i < pairs.length; i++) {
	           p = pairs[i].split('=');
	           params[ p[0] ] =  p[1];
	    }     

    return params;
	}
	var vp;
	if(vp) vp.dispose();

	var params = getParams();
	trailerLink = unescape(params["trailer"]);
	$scope.link = trailerLink;
	console.log(trailerLink);
}]);
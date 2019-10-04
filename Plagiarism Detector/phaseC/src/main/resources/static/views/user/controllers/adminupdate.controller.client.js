(function(){
	angular
	.module("UserDemo")
	.controller("adminUpdateController", adminUpdateController);
	
	function adminUpdateController(UserService, AdminService,$location, $routeParams, $scope, $window) {
		//$scope.name="sample";
		var vm = this;
		vm.logout = logout;
		vm.userId = $window.localStorage.getItem("sher_user_id");
		console.log($routeParams);
		vm.cancel = cancel;
		vm.updateUser = updateUser;
		vm.addUser = addUser;
		vm.userEmail = $routeParams.uemail;
		$scope.uId = $window.localStorage.getItem("sher_user_id");
		$scope.uRole = $window.localStorage.getItem("sher_user_role");
		vm.error = "";
		vm.success = "";
		vm.setCurrentSemester = setCurrentSemester;
		if (!$scope.uId){
			window.location.replace("#/");
		} else {
			if ($scope.uRole != "admin"){
			window.location.replace("#/");
			}
		}
		
		function init(){
		
			console.log("Inside Admin"+vm.userEmail);
			UserService
			.findUserByEmailId(vm.userEmail)
			.success(function (user) {
				console.log(user);
				vm.user = user;
			})
			.error(function(){
				console.log("didnt find user");
			});
		}
		init();
		
		function addUser(){
			$location.url("/admin/addUser");
		}
		
		function setCurrentSemester(){
			$location.url("/admin/setSemester");
		}
		
		function updateUser(user){
			console.log(user);
			var r = confirm("Are you sure?");
			if(r==true){
				AdminService
				.updateUser(user)
				.then(function (response){
					vm.success = "User info updated!";
					//$location.url("/admin/");
				}
				,function (error){
					vm.error="unable to update";
				});
			}
		}
		
		function cancel(){
			$location.url("/admin/");
		}
		
		function logout(){
			var auth2 = null;
			var googleUser = null;

			gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
				gapi.auth2.init(
						{
							client_id: '360591443014-895c59hru7tn8ppdo0fdjh0kplggvn4p.apps.googleusercontent.com'
						}
				);

				auth2 = gapi.auth2.getAuthInstance();
				googleUser = auth2.currentUser.get();
				console.log(googleUser.isSignedIn() + "latest");
				if(googleUser.isSignedIn()){
					auth2.signOut().then(function(){
						auth2.disconnect();
						auth2.currentUser.get().reloadAuthResponse();
					});
				}
			});
			var currentUrl = $window.location.href;

			$window.location.href="https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue="+currentUrl;

			UserService.logout();
		}
		

	}

})();

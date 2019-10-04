(function(){
	angular
	.module("UserDemo")
	.controller("adminAddUserController", adminAddUserController);
	
	function adminAddUserController(UserService, AdminService,InstructorService,$location, $routeParams, $scope, $window) {
		//$scope.name="sample";
		var vm = this;
		vm.logout = logout;
		vm.userId = $window.localStorage.getItem("sher_user_id");
		console.log($routeParams);
		vm.cancel = cancel;
		vm.register = register;
		$scope.uId = $window.localStorage.getItem("sher_user_id");
		$scope.uRole = $window.localStorage.getItem("sher_user_role");
		vm.error = "";
		vm.success = "";
		$scope.reg_succ="";
		$scope.reg_error="";
		vm.getAllSemesters = getAllSemesters;
		vm.setSemester = setSemester;
		vm.setCurrentSemester = setCurrentSemester;
		$scope.semlist = [];
		function getAllSemesters(){
			InstructorService
			.getAllSemesters()
			.then(function(response){
				console.log(response.data)
				$scope.semlist = response.data;
			}
			,function (error){

			})
		}
		
		getAllSemesters();
		
		if (!$scope.uId){
			window.location.replace("#/");
		} else {
			if ($scope.uRole != "admin"){
			window.location.replace("#/");
			}
		}
		
		
		function setCurrentSemester(){
			$location.url("/admin/setSemester");
		}
		
		function setSemester(semester){
			console.log(semester);
			AdminService
				.setCurrentSemester(semester)
				.success(function (response) {
					console.log(response)
					$scope.reg_succ = "Current Semester Set!";
				})
				.error(function(){
					$scope.reg_error = "Sorry! Couldn't set semester";
				});
		}
		
		function register(user) {
			console.log(user)
			if(user==undefined)
			{
				$scope.reg_error = "Please enter all details before Registration";
			}
			else {
				UserService
				.findUserByEmailId(user.emailId)
				.success(function (user) {
					console.log(user)
					$scope.reg_error = "Sorry, that username is taken!"
				})
				.error(function(){
					if ((user.emailId).endsWith("@husky.neu.edu")){
						user.role = "Student";
					}
					else if ((user.emailId).endsWith("@northeastern.edu")){
						user.role = "Instructor";
					}
					else{
						$scope.reg_error = 'Invalid email. Please use your valid northeastern credentials';
						return;
					}
					UserService
					.createUser(user)
					.success(function(user){
						$scope.reg_succ = "Successfully Registered!";
						$scope.reg_error = "";
						$scope.model = null;

					})
					.error(function () {
						$scope.reg_error = "ERROR! Can't Register";
					});
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

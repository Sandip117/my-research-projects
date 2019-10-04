(function(){
	angular
	.module("UserDemo")
	.controller("adminController", adminController);

	function adminController(UserService, AdminService,$location, $routeParams, $scope, $window,$route) {
		//$scope.name="sample";
		var vm = this;
		vm.logout = logout;
		vm.userId = $window.localStorage.getItem("sher_user_id");
		console.log($routeParams);
		vm.allUser=[];
		vm.getAllUsers = getAllUsers;
		vm.removeUser = removeUser;
		vm.editUser = editUser;
		vm.addUser = addUser;
		vm.setCurrentSemester = setCurrentSemester;
		$scope.userlist=[];
		
		$scope.uId = $window.localStorage.getItem("sher_user_id");
		$scope.uRole = $window.localStorage.getItem("sher_user_role");
		
		if (!$scope.uId){
			window.location.replace("#/");
		} else {
			if ($scope.uRole != "admin"){
			window.location.replace("#/");
			}
		}
		
//		
		function init(){
			console.log("Inside Admin");
			getAllUsers();
			console.log($scope.userlist);			
		}
		init();
		
		function editUser(userEmailId){
			$location.url("/admin/updateUser/"+userEmailId);
		}
		
		function setCurrentSemester(){
			$location.url("/admin/setSemester");
		}
		
		function getAllUsers(){
			AdminService
			.getAllUsers()
			.then(function(response){
				$scope.userlist = response.data;
			}
			,function (error){

			})
		}
		
		function removeUser(userId){
			console.log(userId);
			var r = confirm("Are you sure?");
			if(r == true){
				AdminService
				.removeUser(userId)
				.then(function (response){
					for(x in $scope.userlist){
						if(userId==$scope.userlist[x].id){
							$scope.userlist.splice(x,1);
							$route.reload();
							break;
						}
						//init();
					}
				}
				,function (error){
					vm.error="unable to delete";
				})
			}
		}
		
		function addUser(){
			$location.url("/admin/addUser");
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

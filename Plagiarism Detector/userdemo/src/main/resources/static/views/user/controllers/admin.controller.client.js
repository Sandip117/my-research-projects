(function(){
	angular
	.module("UserDemo")
	.controller("adminController", adminController);

	function adminController(AdminService,$location, $routeParams, $scope) {
		//$scope.name="sample";
		var vm = this;
		vm.userId = $routeParams.uid;
		console.log($routeParams);
		vm.allUser=[];
		vm.getAllUsers = getAllUsers;
		vm.removeUser = removeUser;
		$scope.userlist=[];
		
//		
		function init(){
			console.log("Inside Admin");
			getAllUsers();
			console.log($scope.userlist);
			
		}
		init();
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
				.removeUser(userId,this.userId)
				.then(function (response){
					for(x in vm.allUser){
						if(userId==vm.allUser[x].id){
							vm.allUser.splice(x,1);
						}
						init();
						break;
					}
				}
				,function (error){
					vm.error="unable to delete";
				})
			}
		}
		

	}

})();

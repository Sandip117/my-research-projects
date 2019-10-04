(function(){
	angular
	.module("UserDemo")
	.controller("adminController", adminController);

	function adminController(AdminService,$location, $routeParams, $scope) {
		//$scope.name="sample";
		var vm = this;
		vm.userId = $routeParams.uid;
		console.log($routeParams)
		vm.getAllUsers = getAllUsers;
		
		$scope.userlist=[]
		
//		
		function init(){
			console.log("Inside Admin");
			getAllUsers();
			
		}
		init();
		function getAllUsers(){
			AdminService
			.getAllUsers()
			.then(function(response){
				console.log(response.data)
				$scope.userlist = response.data;
			}
			,function (error){

			})
		}

	}

})();
